package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Class that is used to remove last directory from a stack.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DropdShellCommand implements ShellCommand {
    /**
     * Pop directory
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Command dropd takes no arguments");
        } else {
            ((Stack<Path>) env.getSharedData("cdstack")).pop();
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "dropd";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Removes last path pushed to the stack");
        return description;
    }
}
