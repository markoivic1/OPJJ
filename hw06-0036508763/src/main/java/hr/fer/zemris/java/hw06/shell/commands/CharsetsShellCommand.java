package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;

/**
 * charsets command takes no arguments and lists all available charsets on users OS.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CharsetsShellCommand implements ShellCommand {
    /**
     * Prints all available charsets.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Command charsets should not have any arguments");
            return ShellStatus.CONTINUE;
        }
        SortedMap<String, Charset> charsets = Charset.availableCharsets();
        charsets.forEach( (s,c) -> env.write(s + "\n"));
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "charsets";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Command charsets takes no arguments");
        description.add("and lists names of supported charsets for your Java platform");
        return Collections.unmodifiableList(description);
    }
}
