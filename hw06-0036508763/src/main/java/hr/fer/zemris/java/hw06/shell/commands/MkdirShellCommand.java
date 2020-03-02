package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.*;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command mkdir makes a new folder from a given argument which needs to be a path.
 * If some directories from a given path don't exist they will be made.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class MkdirShellCommand implements ShellCommand {
    /**
     * Makes directory for a given path.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 1) {
            env.writeln("Invalid number of arguments in mkdir command");
            return ShellStatus.CONTINUE;
        }
        File file = new File(SharedUtility.splitArguments(arguments)[0]);
        if (file.exists() && file.isDirectory()) {
            env.write("This directory already exists");
        }
        if (!file.mkdirs()) {
            env.writeln("The directory could not be made");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "mkdir";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("This command makes a new directory");
        description.add("Command takes single argument which path to a new directory");
        return Collections.unmodifiableList(description);
    }
}
