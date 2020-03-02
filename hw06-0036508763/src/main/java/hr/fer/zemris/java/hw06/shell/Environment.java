package hr.fer.zemris.java.hw06.shell;

import java.util.SortedMap;

/**
 * Defines the methods which can be used by classes which implement {@link ShellCommand} and by the {@link MyShell}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface Environment {
    /**
     * Defines method which reads line from a specific implementation.
     * @return Returns String of a read line.
     * @throws ShellIOException Thrown when this method is unable to read line.
     * This exception will be propagated to the MyShell.
     */
    String readLine() throws ShellIOException;

    /**
     * Defines method which writes to a destination defined by implementation of this class.
     * @param text Writes given text to the destination.
     * @throws ShellIOException Thrown when text cannot be written to the destination.
     */
    void write(String text) throws ShellIOException;

    /**
     * Defines method which writes to a destination defined by implementation of this class.
     * @param text Writes given text to the destination and enter line break at the end of a text.
     * @throws ShellIOException Thrown when text cannot be written to the destination.
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns internally stored SortedMap of supported commands.
     * @return Returns map of supported commands.
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Getter for the symbol which is used for multiline.
     * @return Returns the symbol which is used for multiline entry
     */
    Character getMultilineSymbol();

    /**
     * Setter for the symbol of multiline.
     * @param symbol Sets given Character as a new symbol for multiline entry.
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Getter for currently used prompt symbol.
     * @return Returns currently used prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets the new symbol for the prompt.
     * @param symbol Given symbol will be set as a new prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Getter for morelines symbol
     * @return Returns morelines symbol as Character.
     */
    Character getMorelinesSymbol();

    /**
     * Setter for morelines symbol
     * @param symbol Sets morelines symbol to a given argument.
     */
    void setMorelinesSymbol(Character symbol);
}
