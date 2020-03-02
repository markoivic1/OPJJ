package hr.fer.zemris.java.hw06.shell;

import hr.fer.zemris.java.hw06.shell.commands.*;

import java.io.Closeable;
import java.io.IOException;
import java.util.Collections;
import java.util.Scanner;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Implementation of {@link Environment} as specified in Homework 6.
 * This class also implements {@link Closeable} this is used to close scanner once the MyShell is terminated.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class MyShellEnvironment implements Environment, Closeable {
    /**
     * Character which will be used as a multiline symbol.
     */
    private Character multiLineSymbol;

    /**
     * Character which will be used as a prompt symbol.
     */
    private Character promptSymbol;

    /**
     * Character which will be used as a morelines symbol.
     */
    private Character moreLinesSymbol;

    /**
     * Sorted Map which will store supported commands.
     */
    private SortedMap<String, ShellCommand> commands;

    /**
     * Scanner which read from System.in
     */
    private Scanner sc;

    /**
     * Constructor which adds supported commands, sets default values for symbols and initializes Scanner.
     */
    public MyShellEnvironment() {
        commands = new TreeMap<>();
        commands.put("exit", new ExitShellCommand());
        commands.put("ls", new LsShellCommand());
        commands.put("cat", new CatShellCommand());
        commands.put("charsets", new CharsetsShellCommand());
        commands.put("copy", new CopyShellCommand());
        commands.put("hexdump", new HexdumpShellCommand());
        commands.put("mkdir", new MkdirShellCommand());
        commands.put("tree", new TreeShellCommand());
        commands.put("symbol", new SymbolShellCommand());
        commands.put("help", new HelpShellCommand());
        this.sc = new Scanner(System.in);
        this.promptSymbol = '>';
        this.multiLineSymbol = '|';
        this.moreLinesSymbol = '\\';
        this.writeln("Welcome to MyShell v 1.0");
    }

    /**
     * This method is called once the MyShell is terminated.
     * @throws IOException Thrown when the resource could not be closed properly.
     */
    @Override
    public void close() throws IOException {
        sc.close();
    }

    /**
     * Defines method which reads line from System.in.
     * @return Returns String of a read line.
     * @throws ShellIOException Thrown when this method is unable to read line.
     * This exception will be propagated to the MyShell.
     */
    @Override
    public String readLine() throws ShellIOException {
        String line;
        try {
            this.write(this.getPromptSymbol().toString() + " ");
            line = sc.nextLine().trim();
            while (line.endsWith(moreLinesSymbol.toString())) {
                this.write(multiLineSymbol.toString() + " ");
                line = line.replace(moreLinesSymbol.toString(), "");
                line += sc.nextLine();
            }
        } catch (Exception ex) {
            throw new ShellIOException("Scanner could not read given argument.");
        }
        return line;
    }

    /**
     * Defines method which writes to System.out.
     * @param text Writes given text to System.out.
     * @throws ShellIOException Thrown when text cannot be written to the destination.
     */
    @Override
    public void write(String text) throws ShellIOException {
        try {
            System.out.print(text);
        } catch (Exception ex) {
            throw new ShellIOException("Message can not be printed to shell.");
        }
    }

    /**
     * Defines method which writes to System.out.
     * @param text Writes given text to System.out and enters line break at the end of a text.
     * @throws ShellIOException Thrown when text cannot be written to the destination.
     */
    @Override
    public void writeln(String text) throws ShellIOException {
        try {
            System.out.println(text);
        } catch (Exception ex) {
            throw new ShellIOException("Message can not be printed to shell.");
        }
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public SortedMap<String, ShellCommand> commands() {
        return Collections.unmodifiableSortedMap(commands);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Character getMultilineSymbol() {
        return multiLineSymbol;
    }

    /**
     * {@inheritDoc}
     * @param symbol Sets given Character as a new symbol for multiline entry.
     */
    @Override
    public void setMultilineSymbol(Character symbol) {
        this.multiLineSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Character getPromptSymbol() {
        return promptSymbol;
    }

    /**
     * {@inheritDoc}
     * @param symbol Given symbol will be set as a new prompt symbol
     */
    @Override
    public void setPromptSymbol(Character symbol) {
        this.promptSymbol = symbol;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public Character getMorelinesSymbol() {
        return moreLinesSymbol;
    }

    /**
     * {@inheritDoc}
     * @param symbol Sets morelines symbol to a given argument.
     */
    @Override
    public void setMorelinesSymbol(Character symbol) {
        this.moreLinesSymbol = symbol;
    }
}
