package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Command symbol is used to get which symbol is currently set to which operation.
 * It can also set new symbols.
 *
 * Command takes one or two arguments.
 * If one argument is given, it will return current symbol which is set for that operation.
 * If two arguments are given, it will set current symbol to a new symbol given in the argument.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SymbolShellCommand implements ShellCommand {
    /**
     * Writes current symbol set for a command, or sets a new symbol for a given command.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Return {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        if (arguments.equals("")) {
            env.writeln("No arguments are given.");
            return ShellStatus.CONTINUE;
        }
        switch (arguments) {
            case "PROMPT":
                env.writeln("Symbol for PROMPT is '" + env.getPromptSymbol() + "'");
                return ShellStatus.CONTINUE;
            case "MULTILINE":
                env.writeln("Symbol for MULTILINES is '" + env.getMultilineSymbol() + "'");
                return ShellStatus.CONTINUE;
            case "MORELINES":
                env.writeln("Symbol for MORELINES is '" + env.getMorelinesSymbol() + "'");
                return ShellStatus.CONTINUE;
            default:
                if (arguments.split(" ").length == 1) {
                    env.writeln("No such symbol");
                    return ShellStatus.CONTINUE;
                }
        }

        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 2) {
            env.writeln("Invalid number of arguments in split command");
            return ShellStatus.CONTINUE;
        }
        if (splitArguments[1].equals("") || (splitArguments[1].length() != 1)) {
            env.writeln("Invalid argument is given");
            return ShellStatus.CONTINUE;
        }
        switch (splitArguments[0]) {
            case "PROMPT":
                env.writeln("Symbol for PROMPT changed from '" +
                        env.getPromptSymbol() + "' to '" + splitArguments[1] + "'");
                env.setPromptSymbol(splitArguments[1].charAt(0));
                break;
            case "MORELINES":
                env.writeln("Symbol for MORELINES changed from '" +
                        env.getMorelinesSymbol() + "' to '" + splitArguments[1] + " '");
                env.setMorelinesSymbol(splitArguments[1].charAt(0));
                break;
            case "MULTILINE":
                env.writeln("Symbol for MULTILINES changed from '" +
                        env.getMultilineSymbol() + "' to '" + splitArguments[1] + "'");
                env.setMultilineSymbol(splitArguments[1].charAt(0));
                break;
            default:
                if (splitArguments[0].equals(env.getMorelinesSymbol().toString())) {
                    break;
                }
                env.writeln("No such symbol of such type");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "symbol";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Command symbol can be used with one or two arguments");
        description.add("If command is entered with one argument");
        description.add("Console will return the current symbol set for a given argument.");
        description.add("If two arguments are given");
        description.add("Current symbol, whose type is given in the first argument, will be set");
        description.add("to a new symbol which is given in the second argument.");
        return Collections.unmodifiableList(description);
    }
}
