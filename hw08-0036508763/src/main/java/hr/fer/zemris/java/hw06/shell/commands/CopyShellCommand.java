package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command copy is used to copy one file from one path to another.
 * Command expects two arguments.
 * First argument is source file which will be copied. First argument cannot be a directory.
 * Second argument is destination path.
 * If the given path is a directory then a new file will be made inside with the name of a source file.
 * If the given path is a file and it doesn't exit, a new file will be created.
 * If the given path is a file and it exists, a prompt will ask for confirmation on overwriting the file.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CopyShellCommand implements ShellCommand {
    /**
     * Size of a buffer for reading stream of bytes
     */
    private static final int BUFFER_SIZE = 1024;

    /**
     * Copies file from the source path to the destination path.
     *
     * @param env       Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 2) {
            env.writeln("Invalid number of arguments in copy command");
            return ShellStatus.CONTINUE;
        }
        if (splitArguments[0].equals("") || splitArguments[1].equals("")) {
            env.writeln("No arguments are given.");
            return ShellStatus.CONTINUE;
        }

        Path input = env.getCurrentDirectory().resolve(Paths.get(splitArguments[0]));
        Path output = env.getCurrentDirectory().resolve(Paths.get(splitArguments[1]));

        if (!Files.exists(input)) {
            env.writeln("Given source file doesn't exist.");
            return ShellStatus.CONTINUE;
        }
        if (Files.isDirectory(input)) {
            env.writeln("This command does not work with directories.");
            return ShellStatus.CONTINUE;
        }
        if (Files.exists(output)) {
            if (Files.isDirectory(output)) {
                output = (new File(output.toFile(), input.toFile().getName())).toPath();
            }
            if (Files.isRegularFile(output)) {
                env.write("Are you sure you want to overwrite this file (y/n): ");
                String line = env.readLine();
                if (line.equals("n")) {
                    return ShellStatus.CONTINUE;
                } else if (!line.equals("y")) {
                    env.writeln("Invalid input.");
                    return ShellStatus.CONTINUE;
                }
            }
        }
        if (input.equals(output)) {
            env.writeln("Source and destination can't be of the same path.");
            return ShellStatus.CONTINUE;
        }
        try (InputStream is = Files.newInputStream(input);
             OutputStream os = Files.newOutputStream(output)) {
            byte[] buff = new byte[BUFFER_SIZE];
            while (true) {
                int r = is.read(buff);
                if (r < 1) break;
                os.write(buff, 0, r);
            }
        } catch (IOException ex) {
            env.writeln("Command copy could not be executed.");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     *
     * @return Returns string copy.
     */
    @Override
    public String getCommandName() {
        return "copy";
    }

    /**
     * {@inheritDoc}
     *
     * @return Returns description as {@link List<String>}
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("The copy command expects two arguments:");
        description.add("source file name and destination file name.");
        return Collections.unmodifiableList(description);
    }
}
