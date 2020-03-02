package hr.fer.zemris.java.hw03.prob1;

import java.util.Objects;

/**
 * Token used to specify value and token type.
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
     * Constructor takes type and value and stores them.
     * @param type Type is of {@link TokenType}
     * @param value Value is of {@link Object}.
     */
    public Token(TokenType type, Object value) {
        Objects.requireNonNull(type);
        this.type = type;
        this.value = value;
    }

    /**
     * Returns value as an Object.
     * @return Returns value
     */
    public Object getValue() {
        return value;
    }

    /**
     * Returns type as {@link TokenType}.
     * @return Returns type.
     */
    public TokenType getType() {
        return type;
    }
}