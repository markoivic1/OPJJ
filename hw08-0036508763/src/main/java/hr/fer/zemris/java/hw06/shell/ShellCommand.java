package hr.fer.zemris.java.hw06.shell;

import java.util.List;

/**
 * Defines required implementation for commands which will be used in {@link Environment} representation.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface ShellCommand {
    /**
     * Defines how the command will be executed.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} which depends on {@link ShellCommand} implementation.
     */
    ShellStatus executeCommand(Environment env, String arguments);

    /**
     * Getter for command name.
     * @return Returns command name.
     */
    String getCommandName();

    /**
     * Description of a command. Used to get specific details about Command which is used in implemented {@link Environment}.
     * @return Returns {@link List<String>} with detailed description.
     */
    List<String> getCommandDescription();
}
