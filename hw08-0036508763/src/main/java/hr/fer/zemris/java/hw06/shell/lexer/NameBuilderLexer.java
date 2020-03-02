package hr.fer.zemris.java.hw06.shell.lexer;

/**
 * Lexer is used to extract tokens from a given input.
 * Every lexer has its own special set of rules.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class NameBuilderLexer {
    /**
     * Input text is stored as a char array.
     */
    private char[] data;
    /**
     * Current token.
     */
    private Token token;
    /**
     * Current index which indicates position in a data array.
     */
    private int currentIndex;

    /**
     * Constructor which takes text and extracts first token from it.
     * @param text Text from which tokens will be extracted.
     */
    public NameBuilderLexer(String text) {
        data = text.toCharArray();
        currentIndex = 0;
        nextToken();
    }

    /**
     * Method used to extract new token.
     * @return Returns new token.
     */
    public Token nextToken() {
        skipBlanks();
        if (currentIndex == data.length) {
            token = new Token(TokenType.EOF, null);
        } else if ((currentIndex + 1 < data.length) && (data[currentIndex] == '$') && (data[currentIndex + 1] == '{')) {
            token = extractTag();
        } else {
            token = extractText();
        }
        return token;
    }

    /**
     * Method which skips blank chars.
     */
    private void skipBlanks() {
        while ((currentIndex != data.length) && (Character.isSpaceChar(data[currentIndex]))) {
            currentIndex++;
        }
    }

    /**
     * Method used to extract text from a data array.
     * @return Returns token extracted from text.
     */
    private Token extractText() {
        int startingIndex = currentIndex;
        while (true) {
            if (currentIndex == data.length) {
                break;
            }
            if (currentIndex + 1 < data.length) {
                if (data[currentIndex] == '$' && data[currentIndex + 1] == '{') {
                    break;
                }
            }
            currentIndex++;
        }
        return new Token(TokenType.TEXT, String.valueOf(data, startingIndex, currentIndex - startingIndex));
    }

    /**
     * Method used to extract tokens from a tag.
     * @return Returns token extracted from a tag.
     */
    private Token extractTag() {
        currentIndex += 2;
        int startingIndex = currentIndex;
        while(data[currentIndex] != '}') {
            currentIndex++;
        }
        currentIndex++;
        return new Token(TokenType.TAG, String.valueOf(data, startingIndex, currentIndex - startingIndex - 1));
    }

    /**
     * Getter for a token.
     * @return Returns token.
     */
    public Token getToken() {
        return token;
    }
}
