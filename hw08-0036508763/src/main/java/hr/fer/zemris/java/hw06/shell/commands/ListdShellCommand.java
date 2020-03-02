package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Path;
import java.util.*;

/**
 * Command listd list all of the saved directories from a stack.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ListdShellCommand implements ShellCommand {
    /**
     * Lists directories push to a stack. Directories are written from the last to first.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Command listd takes no arguments.");
            return ShellStatus.CONTINUE;
        }
        Stack<Path> stack = (Stack<Path>) env.getSharedData("cdstack");
        for (int i = stack.size() - 1; i >= 0; i--) {
            System.out.println(stack.get(i));
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "listd";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Stores current directory an a stack.");
        description.add("Current directory is then set to a given value");
        return description;
    }
}
