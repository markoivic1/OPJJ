/**
 * Package that contains all classes needed to solve hw03-problem2
 */
package hr.fer.zemris.java.custom.scripting.nodes.prob1;

/**
 * Lexer is used to extract token from a given input. It sorts them by their type and labels them accordingly.
 *
 * @author Marko Ivić
 */

public class Lexer {
    /**
     * Index of the first non processed char.
     */
    int currentIndex;
    /**
     * A char array of input string.
     */
    private char[] data;
    /**
     * Current token.
     */
    private Token token;
    /**
     * Current state of the Lexer.
     */
    private LexerState state;

    /**
     * Input string from which the tokens will be extracted.
     * @param text Input text which will be used to extract tokens from.
     */
    public Lexer(String text) {
        data = text.toCharArray();
        currentIndex = 0;
        state = LexerState.BASIC;
    }

    /**
     * Generates next token.
     * @return Returns newly generated token.
     * @throws LexerException Thrown when a token could not be extracted.
     */
    public Token nextToken() {
        if (state == LexerState.BASIC) {
            basicExtractNextToken();
        } else {
            extenderExtractNextToken();
        }
        return getToken();
    }

    /**
     * Returns last generated token. Does not extract new tokens.
     * @return Returns last generated token.
     */
    Token getToken() {
        return token;
    }

    /**
     * Token which is extracted using basic token state.
     * Token which will be extracted are WORD, NUMBER, SYMBOL and EOF.
     */
    public void basicExtractNextToken() {
        if (token != null && token.getType() == TokenType.EOF) {
            throw new LexerException();
        }
        skipBlanks();
        if (checkIfEOF()) return;

        TokenType currentType;
        StringBuilder sb = new StringBuilder();

        if (data[currentIndex] == '\\') {
            charIsAnEscape(sb);
            currentType = charIsLetter(sb);
        } else if (Character.isLetter(data[currentIndex])) {
            currentType = charIsLetter(sb);
        } else if (Character.isDigit(data[currentIndex])) {
            currentType = charIsNumber(sb);
        } else {
            currentType = charIsSymbol(sb);
        }

        if (currentType == TokenType.NUMBER) {
            try {
                Long value = Long.parseLong(sb.toString());
            } catch (NumberFormatException ex) {
                throw new LexerException();
            }
            token = new Token(currentType, Long.parseLong(sb.toString()));
        } else if (currentType == TokenType.WORD) {
            token = new Token(currentType, sb.toString());
        } else if (currentType == TokenType.SYMBOL) {
            token = new Token(currentType, sb.toString().charAt(0));
        } else {
            throw new LexerException("This type of token is not supported");
        }
    }

    /**
     * Token is extracted with new set of rules.
     * When char # is reached every other char after it will be treated as a single value until next char # appears.
     */
    public void extenderExtractNextToken() {
        skipBlanks();
        if (checkIfEOF()) return;
        if (token.getType() == TokenType.EOF) {
            throw new LexerException();
        }
        StringBuilder sb = new StringBuilder();
        while (data[currentIndex] != '#' && data[currentIndex] != ' ') {
            sb.append(data[currentIndex]);
            increaseAndCheckSize();
        }
        if (data[currentIndex] == ' ') {
            token = new Token(TokenType.WORD, sb.toString());
            increaseAndCheckSize();
            return;
        }
        if (data[currentIndex] == '#' && sb.toString().length() == 0) {
            token = new Token(TokenType.SYMBOL, '#');
        }
        if (data[currentIndex] == '#' && sb.toString().length() != 0) {
            token = new Token(TokenType.WORD, sb.toString());
            currentIndex--;
        }
        increaseAndCheckSize();
    }

    /**
     * Switches the state of a {@link Lexer}.
     * @param state New state of a Lexer.
     */
    public void setState(LexerState state) {
        if (state == null) {
            throw new NullPointerException();
        }
        this.state = state;
    }


    /**
     * Check if the char is inside of the bounds.
     * @return Returns true if its inside of the bounds, returns false otherwise.
     */
    private boolean charIsInRange() {
        return currentIndex < data.length;
    }

    /**
     * Adds the char at current index in a data array to a given StringBuilder.
     * @param sb Parameter StringBuilder at which the symbol will be added to.
     * @return Returns {@link TokenType} SYMBOL.
     */
    private TokenType charIsSymbol(StringBuilder sb) {
        sb.append(data[currentIndex++]);
        return TokenType.SYMBOL;
    }

    /**
     * Method used to handle escaped chars.
     * @param sb StringBuilder at which the valid char will be added to.
     * @return Returns {@link TokenType} WORD if the char is an escape.
     */
    private TokenType charIsAnEscape(StringBuilder sb) {
        increaseAndCheckSize();
        if (checkIfValidCharacterIsEscaped(data[currentIndex])) {
            sb.append(data[currentIndex]);
            increaseAndCheckSize();
            return TokenType.WORD;
        }
        return null;
    }

    /**
     * Method used to handle when the current char is a letter.
     * @param sb StringBuilder in which the char will be added to.
     * @return Returns {@link TokenType} WORD.
     */
    private TokenType charIsLetter(StringBuilder sb) {
        if (data[currentIndex] == '\\') {
            charIsAnEscape(sb);
        }
        while ((Character.isLetter(data[currentIndex]) || (data[currentIndex] == '\\')) && charIsInRange()){
            if (data[currentIndex] == '\\') {
                charIsAnEscape(sb);
                continue;
            }
            sb.append(data[currentIndex]);
            currentIndex++;
            if (!charIsInRange()) {
                return TokenType.WORD;
            }
        }
        return TokenType.WORD;
    }

    /**
     * Method used to handle if the char in a data array at a current index is number.
     * @param sb StringBuilder at which the char will be added to.
     * @return Returns {@link TokenType} NUMBER.
     */
    public TokenType charIsNumber(StringBuilder sb) {
        while ((Character.isDigit((data[currentIndex]))) && charIsInRange()) {
            try {
                if (areBlanks(data[currentIndex]) || (Character.isLetter(data[currentIndex]))) {
                    return TokenType.NUMBER;
                }
                sb.append(data[currentIndex]);
                currentIndex++;
                if (!charIsInRange()) {
                    return TokenType.NUMBER;
                }
            } catch (NumberFormatException ex) {
                throw new LexerException();
            }
        }
        return TokenType.NUMBER;
    }

    /**
     * Check if the char is a blank.
     * @param c Char which will be checked.
     * @return Returns true if a char is a blank, returns false otherwise.
     */
    private boolean areBlanks(char c) {
        if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {//'\r', '\n', '\t', ' '
            return true;
        }
        return false;
    }

    /**
     * Check if the valid char is escaped
     * @param value Char which is being checked.
     * @return Returns true if valid char is escaped, return false otherwise.
     */
    private boolean checkIfValidCharacterIsEscaped(char value) {
        try {
            if (value == '\\') {
                return true;
            }
            long number = Long.parseLong(String.valueOf(value)); // Should throw an exception if not valid.
            return true;
        } catch (Exception ex) {
            throw new LexerException();
        }
    }

    /**
     * Increases the size and check if the current index is still in range.
     */
    private void increaseAndCheckSize() {
        currentIndex++;
        if (currentIndex >= data.length) {
            throw new LexerException();
        }
    }

    /**
     * Check if the whole input string has been tokenized.
     * @return Returns true if the whole string has been gone through, return false otherwise.
     */
    private boolean checkIfEOF() {
        if (currentIndex == data.length) {
            token = new Token(TokenType.EOF, null);
            currentIndex++;
            return true;
        }
        return false;
    }

    /**
     * Skips blanks.
     */
    private void skipBlanks() {
        while (currentIndex < data.length) {
            char c = data[currentIndex];
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {//'\r', '\n', '\t', ' '
                currentIndex++;
                continue;
            }
            return;
        }
    }
}