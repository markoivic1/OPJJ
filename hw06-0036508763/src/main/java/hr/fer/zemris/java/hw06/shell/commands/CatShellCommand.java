package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.*;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Cat command writes contents of a given file to the .writeln() method implemented in given {@link Environment} implementation.
 *
 * Cat command takes one or two arguments.
 * First argument is mandatory, second argument is optional.
 * First argument represents the path to a file, second specifies charset which will be used.
 * If the second argument is not given, this command will use default charset.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CatShellCommand implements ShellCommand {
    /**
     * Implementation of cat command.
     * Check {@link CatShellCommand} for more info on how this command works.
     *
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 1 && splitArguments.length != 2) {
            env.writeln("Invalid number of arguments in cat command");
            return ShellStatus.CONTINUE;
        }
        if (splitArguments[0].equals("")) {
            env.writeln("No arguments are given.");
            return ShellStatus.CONTINUE;
        }
        String charsetName;
        Path p;
        if (splitArguments.length == 1) {
            charsetName = Charset.defaultCharset().toString();
            p = Paths.get(splitArguments[0]);
        } else {
            charsetName = splitArguments[1];
            p = Paths.get(splitArguments[0]);
        }
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(p)), charsetName))) {
            String line;
            while ((line = br.readLine()) != null) {
                env.writeln(line);
            }
        } catch (IOException ex) {
            env.writeln("Command cat is unable to open given file");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Getter for the name of a command.
     * @return Returns the name of a command.
     */
    @Override
    public String getCommandName() {
        return "cat";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("This command opens given file and writes its content to console.");
        description.add("Command cat takes one or two arguments.");
        description.add("The first argument is path to some file and is mandatory.");
        description.add("The second argument is charset name that should be used to interpret chars from bytes.");
        description.add("If not provided, a default platform charset is used.");
        return Collections.unmodifiableList(description);
    }
}
