package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Command ls takes one argument which a path to a directory.
 * Command writes output in next format ("," is here for separation of arguments):
 *      lists user privileges (drwx), file size, date of creation, name
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LsShellCommand implements ShellCommand {
    /**
     * Prints user privileges, file size, date of creation and a name of a file.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 1) {
            env.writeln("Invalid number of arguments in split command");
            return ShellStatus.CONTINUE;
        }
        File rootDirectory = env.getCurrentDirectory().resolve(Paths.get(splitArguments[0])).toFile();
        if (!rootDirectory.isDirectory()) {
            env.writeln("Given argument is not a directory");
            return ShellStatus.CONTINUE;
        }
        if (rootDirectory.listFiles() == null) {
            return ShellStatus.CONTINUE;
        }
        for (File file : rootDirectory.listFiles()) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Path path = env.getCurrentDirectory().resolve(Paths.get(splitArguments[0]));
            BasicFileAttributeView faView = Files.getFileAttributeView(
                    path, BasicFileAttributeView.class, LinkOption.NOFOLLOW_LINKS
            );
            BasicFileAttributes attributes = null;
            try {
                attributes = faView.readAttributes();
            } catch (IOException ex) {
                System.out.println("File could not be read.");
            }
            FileTime fileTime = attributes.creationTime();
            String formattedDateTime = sdf.format(new Date(fileTime.toMillis()));
            StringBuilder sb = new StringBuilder();
            // privileges
            sb.append(getPrivileges(file));
            // file size
            String fileSize = String.valueOf(file.length());
            sb.append(" ".repeat(10 - fileSize.length()));
            sb.append(fileSize);
            // time
            sb.append(" ");
            sb.append(formattedDateTime);
            // name
            sb.append(" ");
            sb.append(file.getName());
            env.writeln(sb.toString());
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Gets privileges for a given file or a directory.
     * @param file File which is checked.
     * @return Returns privileges in "drwx", replaces some letter in privileges with "-" if some privilege is missing.
     */
    private String getPrivileges(File file) {
        return (file.isDirectory() ? "d" : "-") +
                (file.canRead() ? "r" : "-") +
                (file.canWrite() ? "w" : "-") +
                (file.canExecute() ? "x" : "-");
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "ls";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Command ls takes a single argument – directory – and writes a directory listing");
        return Collections.unmodifiableList(description);
    }
}
