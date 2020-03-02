package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Command massrename has multiple functions.
 * Function filter writes filtered files.
 * Function group writes files grouped by the given criteria.
 * Function show writes how the files will be renamed in function execute.
 * Function execute moves and renames file to a specified name.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class MassRenameShellCommand implements ShellCommand {
    /**
     * Method which determines which function will be called.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length > 3) {
            try {
                expression(env, splitArguments);
            } catch (IOException e) {
                env.writeln("Command massrename could not be executed.");
            }
        }else {
            env.writeln("Illegal number of arguments.");
        }

        return ShellStatus.CONTINUE;
    }

    /**
     * Expression executes proper function.
     * @param env Environment in which this will be executed.
     * @param splitArguments Arguments given in the command.
     * @throws IOException Thrown when some operation with files cannot be executed.
     */
    private static void expression(Environment env, String[] splitArguments) throws IOException {
        List<FilterResult> files;
        try {
            files = filter(env.getCurrentDirectory().resolve(Paths.get(splitArguments[0])), splitArguments[3]);
        } catch (Exception ex) {
            env.writeln(ex.getMessage());
            return;
        }
        if ("filter".equals(splitArguments[2])) {
            files.forEach(f -> env.writeln(f.toString()));
        } else if ("groups".equals(splitArguments[2])) {
            files.forEach(f -> {
                env.write(f.group(0));
                for (int i = 1; i < f.numberOfGroups(); i++) {
                    env.write(" " + i + ": " + f.group(i));
                }
                env.writeln("");
            });
        } else if ("show".equals(splitArguments[2]) || "execute".equals(splitArguments[2])) {
            NameBuilderParser parser;
            try {
                parser = new NameBuilderParser(splitArguments[4]);
            } catch (Exception ex) {
                env.writeln("Given expression is invalid.");
                return;
            }
            NameBuilder builder = parser.getNameBuilder();
            for (FilterResult file : files) {
                StringBuilder sb = new StringBuilder();
                builder.execute(file, sb);
                String novoIme = sb.toString();
                if (splitArguments[2].equals("show")) {
                    env.writeln(file.toString() + " => " + novoIme);
                } else {
                    Path source = new File(env.getCurrentDirectory().resolve(Paths.get(splitArguments[0])).toFile(), file.toString()).toPath();
                    Path destination = new File(env.getCurrentDirectory().resolve(Paths.get(splitArguments[1])).toFile(), novoIme).toPath();
                    Files.move(source, destination);
                    env.writeln(file.toString() + " => " + novoIme);
                }
            }
        } else {
            env.writeln("Unsupported CMD.");
        }
    }

    /**
     * Lists all files in a given directory that match the given pattern.
     * @param dir Directory in which the files will be checked.
     * @param pattern Acceptable pattern for files.
     * @return Returns list of acceptable FilterResults
     * @throws IOException Thrown when file could not be read.
     */
    public static List<FilterResult> filter(Path dir, String pattern) throws IOException {
        Pattern compiledPattern;
        try {
            compiledPattern = Pattern.compile(pattern, Pattern.UNICODE_CASE | Pattern.CASE_INSENSITIVE);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Illegal arguments are given to Pattern");
        }
        File[] files = dir.toFile().listFiles();
        List<FilterResult> results = new ArrayList<>();
        if (files == null) {
            return results;
        }
        for (File file : files) {
            Matcher currentMatcher = compiledPattern.matcher(file.getName());
            if (currentMatcher.matches()) {
                String[] groups = new String[currentMatcher.groupCount() + 1];
                for (int i = 0; i < currentMatcher.groupCount() + 1; i++) {
                    groups[i] = currentMatcher.group(i);
                }
                results.add(new FilterResult(file, groups));
            }
        }
        return results;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "massrename";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        return null;
    }
}
