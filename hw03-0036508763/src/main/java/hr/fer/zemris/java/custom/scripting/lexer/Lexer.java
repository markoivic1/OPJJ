package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Lexer extracts tokens of predefined types. Next token is given only when requested.
 * @author Marko Ivić
 */
public class Lexer {
    /**
     * Data used to store input data as an array of chars.
     */
    private char[] data;
    /**
     * Token used to store value and type of an extracted token.
     */
    private Token token;
    /**
     * States of the Lexer.
     * This lexer can be in Text or Tag state.
     */
    private LexerState state;
    /**
     * Index of a char in data.
     */
    private int currentIndex;


    /**
     * Constructor which sets up Lexer and requests first {@link Token}.
     * @param text Input string which is being analyzed.
     * @throws LexerException if given input is null.
     */
    public Lexer(String text) {
        if (text == null) {
            throw new LexerException();
        }
        data = text.toCharArray();
        currentIndex = 0;
        state = LexerState.TEXT;
        nextToken();
    }

    /**
     * Method used to extract next {@link Token}.
     * @return Retruns new extracted {@link Token}.
     */
    public Token nextToken() {
        skipSpaces();
        if (state == LexerState.TAG) {
            skipBlanks();
        }
        if (checkIfEOF()) {
            if (state == LexerState.TAG) {
                throw new LexerException();
            }
            return token = new Token(TokenType.EOF, null);
        } else if (tagIsClosed()) {
            //state = LexerState.TEXT;
            currentIndex += 2;
            token = new Token(TokenType.TAG_CLOSED, "$}");
        } else if (newTagIsOpened()) {
            skipBlanks();
            //state = LexerState.TAG;
            currentIndex += 2;
            token = new Token(TokenType.TAG_OPENED, "{$");
        } else if (state == LexerState.TEXT) {
            token = new Token(TokenType.TEXT, null);
            readTextUntilNextTag();
        } else if ((token.getType() == TokenType.TAG_OPENED) && (state == LexerState.TAG)) {
            String tagName = getTag();
            token = new Token(TokenType.TAG, tagName);
            currentIndex++;
        } else if (state == LexerState.TAG) {
            setToken();
        }
        return token;
    }

    /**
     * Private method which determines the next {@link Token} and it's type.
     * If a token is of type string following characters will be replaced:
     * \n, \r and \t are replaced with '\n', '\r' and '\t'.
     * \\ and \" are replaced with '\\' and '\"'.
     */
    private void setToken() {
        skipBlanks();
        int startingIndex = currentIndex;
        if (Character.isLetter(data[currentIndex])) {
            setTokenToVariableName(startingIndex);
        } else if (Character.isDigit(data[currentIndex]) || isNegativeNumber()) {
            setTokenToDoubleOrInteger(startingIndex);
        } else if (data[currentIndex] == '\"') {
            setTokenToString(startingIndex);
      } else if (charIsAnOperator()) {
            setTokenToOperator(startingIndex);
       } else if (data[currentIndex] == '@') {
            setTokenToFunction(startingIndex);
           } else {
            throw new LexerException();
        }
    }

    /**
     * Sets Token type to function and adds proper value.
     * @param startingIndex index from which this method reads.
     */
    private void setTokenToFunction(int startingIndex) {
        startingIndex++;
        increaseCurrentIndexAndCheck();
        if (!Character.isLetter(data[currentIndex])) {
            throw new LexerException();
        }
        increaseCurrentIndexAndCheck();
        while (validVariable()) {
            currentIndex++;
        }
        token = new Token(TokenType.FUNCTION_NAME, String.valueOf(data, startingIndex, currentIndex - startingIndex));

    }

    /**
     * Setts Token type to operator and adds proper value.
     * @param startingIndex index from which this method reads.
     */
    private void setTokenToOperator(int startingIndex) {
        increaseCurrentIndexAndCheck();
        token = new Token(TokenType.OPERATOR, String.valueOf(data, startingIndex, currentIndex - startingIndex));
    }

    /**
     * Setts Token type to string and adds proper value.
     * @param startingIndex index from which this method reads.
     */
    private void setTokenToString(int startingIndex) {
        startingIndex++;
        increaseCurrentIndexAndCheck();
        while ((data[currentIndex] != '\"')
                || (data[currentIndex - 1]) == '\\') {
            if (data[currentIndex] == '\\') {
                if (!checkIfProperCharIsEscaped()) {
                    throw new LexerException();
                }
            }
            increaseCurrentIndexAndCheck();
        }
        String stringInTag = String.valueOf(data, startingIndex, currentIndex - startingIndex).replace("\\n", "\n").replace("\\r", "\r")
                .replace("\\t", "\t").replace("\\\"", "\"")
                .replace("\\\\", "\\");
        currentIndex++;
        token = new Token(TokenType.STRING, stringInTag);
    }

    /**
     * Setts Token type to Double or Integer depending on the situation and adds proper value.
     * @param startingIndex index from which this method reads.
     */
    private void setTokenToDoubleOrInteger(int startingIndex) {
        int dotCounter = 0;
        while (Character.isDigit(data[currentIndex]) || (data[currentIndex] == '.')) {
            if (data[currentIndex] == '.') {
                dotCounter++;
            }
            if (dotCounter == 2) {
                throw new LexerException();
            }
            increaseCurrentIndexAndCheck();
        }
        try {
            if (dotCounter == 0) {
                token = new Token(TokenType.CONSTANT_INTEGER, String.valueOf(data, startingIndex, currentIndex - startingIndex));
            } else if (dotCounter == 1) {
                token = new Token(TokenType.CONSTANT_DOUBLE, String.valueOf(data, startingIndex, currentIndex - startingIndex));
            }
        } catch (Exception ex) {
            throw new LexerException();
        }
    }

    /**
     * Sets Token type to variable name and adds proper value.
     * @param startingIndex index from which this method reads.
     */
    private void setTokenToVariableName(int startingIndex) {
        increaseCurrentIndexAndCheck();
        while (validVariable()) {
            increaseCurrentIndexAndCheck();
        }
        token = new Token(TokenType.VARIABLE_NAME, String.valueOf(data, startingIndex, currentIndex - startingIndex));
    }

    /**
     * Method used to read all of the text until next tag is opened.
     * Read a tag if it's escaped
     * Valid escapes are \{ and \\
     */
    private void readTextUntilNextTag() {
        int startingIndex = currentIndex;
        while ((currentIndex  < data.length) && (!newTagIsOpened())) {

            if (data[currentIndex] == '\\') {
                if (currentIndex + 2 < data.length) {
                    if (data[currentIndex + 1] == '\\') {
                        currentIndex++;
                        continue;
                    } else if ((data[currentIndex + 1] == '{') && (data[currentIndex + 2] == '$')) {
                        currentIndex += 2;
                        continue;
                    }
                } else {
                    throw new LexerException();
                }
                currentIndex++;
            }

            currentIndex++;
        }
        int reachedTheEnd = 0;
        if (currentIndex + 1 == data.length) {
            currentIndex++;
        }
        String text = String.valueOf(data, startingIndex, currentIndex - startingIndex + reachedTheEnd).replaceAll("\\\\\\\\", "\\\\").replaceAll("\\\\\\{", "\\{" );
        token = new Token(token.getType(), text);
    }

    /**
     * Checks the given tag and gets the type of tag.
     * @return Returns valid tag names.
     */
    private String getTag() {
        int startingPosition = currentIndex;
        if (data[currentIndex] == '=') {
            return "=";
        }
        skipBlanks();
        if (!Character.isLetter(data[currentIndex])) {
            throw new LexerException();
        }
        currentIndex++;
        while (validVariable()) {
            increaseCurrentIndexAndCheck();
        }
        currentIndex--;
        return String.valueOf(data, startingPosition, currentIndex - startingPosition + 1);
    }

    /**
     * Method used for checking whether the tag has proper values after the first char.
     * @return Returns true if value at a given index in data is valid, returns false if not.
     */
    private boolean validVariable() {
        if ((data[currentIndex]) == '_' ||
                Character.isLetter(data[currentIndex]) ||
                Character.isDigit(data[currentIndex])) {
            return true;
        }
        return false;
    }

    /**
     * Checks whether the char at a current index is validly escaped.
     * @return Returns true if the char is validly escaped, false otherwise
     */
    private boolean checkIfProperCharIsEscaped() {
        char[] validEscapeCharacters = {'\\','\"','n','r','t'};
        for (int i = 0; i < validEscapeCharacters.length; i++) {
            if (validEscapeCharacters[i] == data[currentIndex + 1]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks char at current index against predefined allowed values.
     * @return Returns true if a char matches some of the operators, returns false otherwise.
     */
    private boolean charIsAnOperator() {
        if ((data[currentIndex]) == '+' ||
                (data[currentIndex]) == '-' ||
                (data[currentIndex]) == '*' ||
                (data[currentIndex]) == '/' ||
                (data[currentIndex]) == '^') {
            return true;
        }
        return false;
    }

    /**
     * Helper method used for checking whether the index has been increased beyond the length of data.
     */
    private void increaseCurrentIndexAndCheck() {
        currentIndex++;
        checkIfOutOfRange();
    }

    /**
     * Checks whether the upcoming token is a negative number.
     * @return Returns true if the negative number succeeds the minus char.
     */
    private boolean isNegativeNumber() {
        if (data[currentIndex] == '-') {
            try {
                if (Character.isDigit(data[currentIndex + 1])) {
                    currentIndex++;
                    return true;
                }
            } catch (Exception ex) {
                throw new LexerException();
            }
        }
        return false;
    }

    /**
     * Checks whether the current index has gone beyong the size of a data array.
     */
    private void checkIfOutOfRange() {
        if (currentIndex >= data.length) {
            throw new LexerException();
        }
    }

    /**
     * Skips all of the blank spots.
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

    /**
     * Skips spaces.
     */
    private void skipSpaces() {
        while (currentIndex < data.length) {
            char c = data[currentIndex];
            if (c == ' ') {
                currentIndex++;
                continue;
            }
            return;
        }
    }

    /**
     * Checks whether the new tag is opened.
     * @return Returns true if a new tag is opened.
     */
    private boolean newTagIsOpened() {
        try {
            if (data[currentIndex] == '{') {
                if (data[currentIndex + 1] == '$') {
                    return true;
                }
            }
        } catch (Exception ex) {
            throw new LexerException();
        }
        return false;
    }

    /**
     * Checks whether the tag is closed.
     * @return
     */
    private boolean tagIsClosed() {
        try {
            if (data[currentIndex] == '$') {
                if (data[currentIndex + 1] == '}') {
                    //state = LexerState.TEXT;
                    return true;
                }
            }
        } catch (Exception ex) {
            throw new LexerException();
        }
        return false;
    }

    /**
     * Checks whether the current index has gone through the whole data array.
     * @return Returns true if the whole array has been gone through, false otherwise.
     */
    private boolean checkIfEOF() {
        if (currentIndex == data.length) {
            return true;
        }
        return false;
    }

    /**
     * Method used to get {@link Token} of this lexer.
     * @return Returns {@link Token}.
     */
    public Token getToken() {
        return token;
    }

    public void setState(LexerState state) {
        this.state = state;
    }
}
