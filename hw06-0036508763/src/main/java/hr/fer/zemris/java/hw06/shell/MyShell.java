package hr.fer.zemris.java.hw06.shell;

/**
 * General representation of MyShell.
 * This code can be used for every implementation of {@link Environment}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class MyShell{
    /**
     * Main method which takes no arguments from a command line.
     * Used to execute implementation of {@link Environment}.
     * @param args No arguments from a command line are taken.
     */
    public static void main(String[] args) {
        Environment environment = new MyShellEnvironment();
        ShellStatus status = ShellStatus.CONTINUE;
        do {
            try {
                String l = environment.readLine();
                String commandName = SharedUtility.extractCommandName(l);
                String arguments = SharedUtility.extractArguments(l);
                ShellCommand command = environment.commands().get(commandName);
                if (command == null) {
                    continue;
                }
                status = command.executeCommand(environment, arguments);
            } catch (ShellIOException ex) {
                environment.writeln(ex.getMessage());
                status = ShellStatus.TERMINATE;
            }
        } while (status != ShellStatus.TERMINATE);
    }


}
