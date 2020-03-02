package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Command popd pops last directory from a stack.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PopdShellCommand implements ShellCommand {
    /**
     * Method which is used to pop directory from a stack.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.length() != 0) {
            env.writeln("Invalid number of arguments.");
        }
        Path oldPath = (Path) ((Stack) env.getSharedData("cdstack")).pop();
        if (!Files.isDirectory(oldPath)) {
            env.writeln("No paths were previously stored.");
        } else {
            env.setCurrentDirectory(oldPath);
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "popd";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Pops last pushed path with a command pushd");
        return description;
    }
}
