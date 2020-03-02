package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Command pushd ushes current directory from to a stack and sets given argument as a working directory.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PushdShellCommand implements ShellCommand {
    /**
     * Executes pushd command
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    @SuppressWarnings("unchecked")
    public ShellStatus executeCommand(Environment env, String arguments) {
        Path oldDirectory = env.getCurrentDirectory();
        new CdShellCommand().executeCommand(env, arguments);
        if (oldDirectory.equals(env.getCurrentDirectory())) {
            env.writeln("Command pushd cannot be executed properly.");
        } else {
            if (env.getSharedData("cdstack") == null) {
                Stack<Path> stack = new Stack<>();
                env.setSharedData("cdstack", stack);
            }
            ((Stack<Path>) env.getSharedData("cdstack")).push(oldDirectory);
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "pushd";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Pushes current Shell's directory to the stack.");
        description.add("Sets Shell's current directory to a given directory");
        return description;
    }
}
