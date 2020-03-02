package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Command pwd returns current working directory.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PwdShellCommand implements ShellCommand {
    /**
     * Writes current working directory.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Command pwd takes no arguments.");
        } else {
            env.writeln(env.getCurrentDirectory().toString());
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "pwd";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Prints Shell's current directory");
        return description;
    }
}
