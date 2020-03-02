package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Command cd sets new working directory.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CdShellCommand implements ShellCommand {
    /**
     * Directory given in the argument will be set as a working directory.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 1) {
            env.writeln("Command cd takes only one argument");
        } else {
            Path newPath = env.getCurrentDirectory().resolve(Paths.get(splitArguments[0]));
            if (!Files.isDirectory(newPath)) {
                env.writeln("Given path is not a directory.");
                return ShellStatus.CONTINUE;
            }
            env.setCurrentDirectory(newPath);
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "cd";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Sets Shell's path to a given path");
        return description;
    }
}
