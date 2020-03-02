package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command exit is to close {@link hr.fer.zemris.java.hw06.shell.MyShell}.
 * When this command executes the shell will terminate.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ExitShellCommand implements ShellCommand {
    /**
     * If an Environment implements {@link Closeable} it will execute close command.
     * Otherwise it will just return {@link ShellStatus} TERMINATE.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} TERMINATE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Command exit should not have any arguments.");
            return ShellStatus.CONTINUE;
        }
        return ShellStatus.TERMINATE;
    }

    /**
     * {@inheritDoc}
     * @return Returns command name.
     */
    @Override
    public String getCommandName() {
        return "exit";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Shell terminates when user enter exit command");
        return Collections.unmodifiableList(description);
    }
}
