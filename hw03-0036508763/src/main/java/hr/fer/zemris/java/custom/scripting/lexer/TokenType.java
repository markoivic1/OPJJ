package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defined some types of tokens.
 * @author Marko Ivić
 */
public enum TokenType {
    /**
     * EOF indicates that the whole input has been analyzed.
     */
    EOF,
    /**
     * TEXT indicates that the token contains text
     */
    TEXT,
    /**
     * VARIABLE_NAME indicates that the token has proper variable name stored.
     */
    VARIABLE_NAME,
    /**
     * FUNCTION_NAME indicates that the token has proper function name stored.
     */
    FUNCTION_NAME,
    /**
     * OPERATOR indicates that the token has proper operator stored.
     */
    OPERATOR,
    /**
     * STRING indicates that the string inside of a tag
     */
    STRING,
    /**
     * CONSTANT_DOUBLE indicates that the double value is stored.
     */
    CONSTANT_DOUBLE,
    /**
     * CONSTANT_INTEGER indicates that the integer value is stored.
     */
    CONSTANT_INTEGER,
    /**
     * TAG_CLOSED indicates that the tag is closed.
     */
    TAG_CLOSED,
    /**
     * TAG_OPENED indicates that the tag is opened.
     */
    TAG_OPENED,
    /**
     * TAG as in indicator for {@link hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser} to use proper parse.
     */
    TAG
}
