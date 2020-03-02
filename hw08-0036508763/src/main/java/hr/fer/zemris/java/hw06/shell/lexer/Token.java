package hr.fer.zemris.java.hw06.shell.lexer;

/**
 * Token is used to store value and type of some data.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Token {
    /**
     * Type of a token.
     */
    private TokenType type;
    /**
     * Value which is stored in a token.
     */
    private Object value;


    /**
     * Constructor which takes 2 values.
     * @param type Given type is set to token's type.
     * @param value Given value is set to token's value.
     */
    public Token(TokenType type, Object value) {
        this.type = type;
        this.value = value;
    }

    /**
     * Getter for type
     * @return Returns type
     */
    public TokenType getType() {
        return type;
    }

    /**
     * Getter for value
     * @return Returns value
     */
    public Object getValue() {
        return value;
    }
}
