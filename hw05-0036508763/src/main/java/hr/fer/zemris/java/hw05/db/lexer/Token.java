package hr.fer.zemris.java.hw05.db.lexer;

/**
 * Token is used to store and categorize data which are extracted from lexer.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Token {
    /**
     * Type of a token indicates the parser how to use each type.
     */
    private TokenType type;
    /**
     * Value stored in a token.
     */
    private Object value;

    /**
     * Constructor which takes type of {@link TokenType} and value.
     * @param type Type of a token.
     * @param value Value of a token.
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Getter for type.
     * @return Returns type.
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Getter for value.
     * @return Return value.
     */
    public Object getValue() {
        return value;
    }
}
