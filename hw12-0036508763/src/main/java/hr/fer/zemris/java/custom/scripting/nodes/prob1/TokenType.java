package hr.fer.zemris.java.custom.scripting.nodes.prob1;

/**
 * Types of tokens.
 * @author Marko Ivić
 */
public enum TokenType {
    /**
     * If the end of the input string is reached.
     */
    EOF,
    /**
     * If the token's value is of type word.
     */
    WORD,
    /**
     * Number indicates that the value of a token is a number.
     */
    NUMBER,
    /**
     * Symbol indicates that the value of a token is a symbol.
     */
    SYMBOL
}
