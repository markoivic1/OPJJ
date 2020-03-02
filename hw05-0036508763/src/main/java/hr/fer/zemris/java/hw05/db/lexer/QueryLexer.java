package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Lexer specified for processing query entries.
 * It's tokens can be of type: OPERATOR, ATTRIBUTE, GRADE and EOF.
 *
 * For example: firstname like "M*" < attribute
 *              ^attribute ^operator
 * @author Marko Ivić
 * @version 1.0.0
 */
public class QueryLexer {
    /**
     * Data that is given in the constructor is stored as a array of chars for easier processing.
     */
    private char[] data;
    /**
     * Current token.
     */
    private Token token;
    /**
     * Current index.
     */
    private int currentIndex;

    /**
     * Constructor which takes a string and extracts tokens from it.
     * @param text String which will be processed.
     */
    public QueryLexer(String text) {
        data = text.toCharArray();
        currentIndex = 0;
        nextToken();
    }

    /**
     * Method which extracts next token and classifies it accordingly.
     * @return Return newly extracted token.
     */
    public Token nextToken() {
        skipBlanks();
        int startingIndex = currentIndex;
        if (checkIfEOF()) {
            token = new Token(TokenType.EOF, null);
        }else if (data[currentIndex] == '\"') {
            startingIndex++;
            currentIndex++;
            while ((currentIndex < data.length) && (data[currentIndex] != '\"')) {
                currentIndex++;
            }
            token = new Token(TokenType.STRING_LITERAL, String.valueOf(data, startingIndex, currentIndex - startingIndex));
            currentIndex++;
        } else if (checkIfOperator()) {
            currentIndex++;
            while (((currentIndex < data.length) && checkIfOperator())) {
                currentIndex++;
            }
            token = new Token(TokenType.OPERATOR, String.valueOf(data, startingIndex, currentIndex - startingIndex));
        } else if (Character.isLetter(data[currentIndex])) {
            currentIndex++;
            while (((currentIndex < data.length) && Character.isLetter(data[currentIndex]))) {
                currentIndex++;
            }
            String text = String.valueOf(data, startingIndex, currentIndex - startingIndex);
            if (text.toLowerCase().equals("like")) {
                token = new Token(TokenType.OPERATOR, text);
            } else {
                token = new Token(TokenType.ATTRIBUTE, text);
            }
        } else if (Character.isDigit(data[currentIndex])) {
            currentIndex++;
            while (Character.isDigit(data[currentIndex])) {
                currentIndex++;
            }
            token = new Token(TokenType.GRADE, Double.parseDouble(String.valueOf(data, startingIndex, currentIndex - startingIndex)));
        }

        return token;
    }

    /**
     * Returns current token.
     * @return Return current token.
     */
    public Token getToken() {
        return token;
    }

    /**
     * Method used to check if the token is an operator
     * @return Returns true if the token is an operator, false otherwise.
     */
    private boolean checkIfOperator() {
        char[] operators = {'=', '>', '<', '!'};
        for (char operator : operators) {
            if (operator == data[currentIndex]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used for checking if all of the data has been read.
     * @return Returns true if the every char in an array has been processed.
     */
    private boolean checkIfEOF() {
        return currentIndex == data.length;
    }

    /**
     * Skips blanks from a text as they dont matter in extracting these token.
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
