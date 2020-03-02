package hr.fer.zemris.java.custom.scripting.nodes.prob1;

/**
 * The states at which lexer can be in.
 * @author Marko Ivić
 */
public enum LexerState {
    /**
     * Basic state.
     */
    BASIC,
    /**
     * Extended state which does the same thing as basic until it reaches char #.
     * After it it reads every other char until next char # appears.
     */
    EXTENDED
}
