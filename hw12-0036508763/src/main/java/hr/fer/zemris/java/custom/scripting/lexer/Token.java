package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * This class is used to store and differentiate different types of tokens.
 * @author Marko Ivić
 */
public class Token {
    /**
     * Type of a token.
     */
    private TokenType type;
    /**
     * Value of a token.
     */
    private Object value;

    /**
     * Constructor that takes given a type and a value of a token and stores it.
     * @param type Type of a token.
     * @param value Value of a token.
     * @throws NullPointerException Thrown when given type is null.
     */
    public Token(TokenType type, Object value) {
        if (type == null) {
            throw new NullPointerException();
        }
        this.type = type;
        this.value = value;
    }

    /**
     * Getter for value.
     * @return Returns value.
     */
    public Object getValue() {
        return value;
    }

    /**
     * Getter for type.
     * @return
     */
    public TokenType getType() {
        return type;
    }
}
