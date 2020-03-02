package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command help is used to list all supported commands or give specific details about some command.
 * Commands takes one or without an argument.
 * If no argument is given the command will list all supported commands.
 * If one argument is given command will write a description of a given file.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class HelpShellCommand implements ShellCommand {
    /**
     * Writes all supported commands if no argument is given.
     * Writes specific help for given argument.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.equals("")) {
            env.writeln("Supported commands");
            env.commands().forEach( (k, v) -> env.writeln(k));
            return ShellStatus.CONTINUE;
        }

        String[] splitArguments = SharedUtility.splitArguments(arguments);

        if (splitArguments.length != 0 && splitArguments.length != 1) {
            env.writeln("Invalid number of arguments in help command");
            return ShellStatus.CONTINUE;
        }

        if (!env.commands().containsKey(splitArguments[0])) {
            env.writeln("This command is unsupported.");
            return ShellStatus.CONTINUE;
        }
        List<String> description = env.commands().get(splitArguments[0]).getCommandDescription();
        for (String line : description) {
            env.writeln(line);
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "help";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("This command takes zero or one argument.");
        description.add("If no argument is given this command will list all supported commands.");
        description.add("If one argument is given it will print help documentation of a given argument,");
        description.add("if its supported");
        return Collections.unmodifiableList(description);
    }
}
