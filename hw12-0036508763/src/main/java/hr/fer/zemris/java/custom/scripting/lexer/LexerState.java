package hr.fer.zemris.java.custom.scripting.lexer;

/**
 * Defined states at which the lexer can be in.
 * @author Marko Ivić
 */
public enum LexerState {
    /**
     * Text tag is a state used outside of these signs {$ $}
     */
    TEXT,
    /**
     * Tag is used to indicate that the lexer is inside of the {$ $}.
     */
    TAG
}
