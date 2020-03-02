package hr.fer.zemris.java.hw06.shell;

import java.nio.file.Path;
import java.util.SortedMap;

/**
 * Defines the methods which can be used by classes which implement {@link ShellCommand} and by the {@link MyShell}.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface Environment {
    /**
     * Defines method which reads line from a specific implementation.
     *
     * @return Returns String of a read line.
     * @throws ShellIOException Thrown when this method is unable to read line.
     *                          This exception will be propagated to the MyShell.
     */
    String readLine() throws ShellIOException;

    /**
     * Defines method which writes to a destination defined by implementation of this class.
     *
     * @param text Writes given text to the destination.
     * @throws ShellIOException Thrown when text cannot be written to the destination.
     */
    void write(String text) throws ShellIOException;

    /**
     * Defines method which writes to a destination defined by implementation of this class.
     *
     * @param text Writes given text to the destination and enter line break at the end of a text.
     * @throws ShellIOException Thrown when text cannot be written to the destination.
     */
    void writeln(String text) throws ShellIOException;

    /**
     * Returns internally stored SortedMap of supported commands.
     *
     * @return Returns map of supported commands.
     */
    SortedMap<String, ShellCommand> commands();

    /**
     * Getter for the symbol which is used for multiline.
     *
     * @return Returns the symbol which is used for multiline entry
     */
    Character getMultilineSymbol();

    /**
     * Setter for the symbol of multiline.
     *
     * @param symbol Sets given Character as a new symbol for multiline entry.
     */
    void setMultilineSymbol(Character symbol);

    /**
     * Getter for currently used prompt symbol.
     *
     * @return Returns currently used prompt symbol
     */
    Character getPromptSymbol();

    /**
     * Sets the new symbol for the prompt.
     *
     * @param symbol Given symbol will be set as a new prompt symbol
     */
    void setPromptSymbol(Character symbol);

    /**
     * Getter for morelines symbol
     *
     * @return Returns morelines symbol as Character.
     */
    Character getMorelinesSymbol();

    /**
     * Setter for morelines symbol
     *
     * @param symbol Sets morelines symbol to a given argument.
     */
    void setMorelinesSymbol(Character symbol);

    /**
     * Method which is used to get current working directory.
     * @return Returns path to a current directory.
     */
    Path getCurrentDirectory();

    /**
     * Method which is used to set current working directory.
     * @param path Path which will be set as a new working directory.
     */
    void setCurrentDirectory(Path path);

    /**
     * Returns shared data.
     * @param key Key which is used to get shared data.
     * @return Returns data stored with a given key.
     */
    Object getSharedData(String key);

    /**
     * Sets given value to a key.
     * @param key Key in which the value will be stored.
     * @param value Value which will be stored under a key.
     */
    void setSharedData(String key, Object value);
}
