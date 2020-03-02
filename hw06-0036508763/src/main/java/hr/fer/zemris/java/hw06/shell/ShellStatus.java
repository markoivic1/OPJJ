package hr.fer.zemris.java.hw06.shell;

/**
 * Enum for indicating when to terminate {@link MyShell}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public enum ShellStatus {
    /**
     * {@link MyShell} when this status is used the program will read a new line and execute given command extracted from read line.
     */
    CONTINUE,
    /**
     * {@link MyShell} when this status is used {@link MyShell} will terminate.
     */
    TERMINATE
}
