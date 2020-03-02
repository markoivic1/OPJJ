package hr.fer.zemris.java.hw06.shell;

/**
 * Interface used to define strategy for building a name.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface NameBuilder {
    /**
     * Executes some specific command depending on a type of argument.
     * @param result Result which will be executed.
     * @param sb Some value will be appended to the StringBuilder.
     */
    void execute(FilterResult result, StringBuilder sb);
}
