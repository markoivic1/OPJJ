package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command tree recursively writes contents of a given folder.
 * Level of depth from a given directory is shown in number of indentations
 *
 * Command takes only one argument which is a path to a directory.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class TreeShellCommand implements ShellCommand {
    /**
     * Writes files in directories appropriately indented depending on depth from a root directory.
     *
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 1) {
            env.writeln("Invalid number of arguments in split command");
            return ShellStatus.CONTINUE;
        }
        File dir = new File(SharedUtility.splitArguments(arguments)[0]);
        if (!dir.isDirectory()) {
            env.writeln("Given argument is not a directory.");
            return ShellStatus.CONTINUE;
        }
        printTree(env, dir, 0);
        return ShellStatus.CONTINUE;
    }

    /**
     * Recursive method used to write every file and a directory for a given root directory in level 0.
     * @param env Given {@link Environment} implementation to which the output will be written.
     * @param dir Current directory or a file.
     * @param level Level of a directory or a file from root directory.
     */
    private void printTree (Environment env, File dir, int level) {
        if (dir == null) {
            throw new RuntimeException();
        }
        if (dir.listFiles() == null) {
            env.writeln(dir.getName());
            return;
        }
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                env.writeln(" ".repeat(level * 2) + file.getName());
            }
            if (file.isDirectory()) {
                printTree(env, file, level + 1);
            } else if (file.isFile()) {
                StringBuilder sb = new StringBuilder();
                sb.append("  ".repeat(Math.max(0, level)));
                sb.append(file.getName());
                env.writeln(sb.toString());
            }
        }
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "tree";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("The tree command expects a single argument:");
        description.add("directory name and prints a tree");
        return Collections.unmodifiableList(description);
    }
}
