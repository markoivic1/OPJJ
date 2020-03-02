/**
 * This package includes a {@link hr.fer.zemris.java.custom.collections.demo.StackDemo} class which implements method to calculate in a postfix form.
 */
package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.EmptyStackException;
import hr.fer.zemris.java.custom.collections.ObjectStack;

/**
 * This class is used to test the functionality of a {@link ObjectStack}.
 *
 *
 */
public class StackDemo {

    /**
     * Main method used for running the {@link StackDemo#postfixCalculator(String[])}.
     * @param args Takes input from program arguments and passes theem to {@link StackDemo#postfixCalculator(String[])}.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments");
            System.exit(1);
        }

        String[] inputArguments = args[0].split(" ");
        postfixCalculator(inputArguments);
    }

    /**
     * This method is used to calculate arguments given in a postfix format.
     *
     * Operations that are implemented are: adding, subtracting, dividing and multiplying.
     * @param inputArguments Input is an array of characters each representing an {@code integer} or an operand.
     * @throws NullPointerException Thrown when inputArguments is null.
     * @throws NumberFormatException Thrown when there is an invalid character that is neither an {@code integer} nor an operand.
     */
    public static void postfixCalculator(String[] inputArguments) {
        if (inputArguments == null) {
            throw new NullPointerException();
        }
        ObjectStack stack = new ObjectStack();

        for (int i = 0; i < inputArguments.length; i++) {
            if (valueIsANumber(inputArguments[i])) {
                stack.push(inputArguments[i]);
                continue;
            }
            int firstValue = 0; // Set to 0 because IDE worries it might not be initialized.
            int secondValue = 0; // Doesn't really matter it will be overwritten.
            try {
                firstValue = Integer.parseInt((String) stack.pop());
                secondValue = Integer.parseInt((String) stack.pop());
            } catch (NumberFormatException ex) {
                System.err.println("Arguments are not stored properly in the stack");
                break;
            } catch (EmptyStackException ex) {
                System.err.println("Arguments are not valid");
                break;
            }
            switch (inputArguments[i]) {
                case ("*") :
                    stack.push(Integer.toString(secondValue * firstValue));
                    break;
                case ("/") :
                    stack.push(Integer.toString(secondValue / firstValue));
                    break;
                case ("%") :
                    stack.push(Integer.toString(secondValue % firstValue));
                    break;
                case ("+") :
                    stack.push(Integer.toString(secondValue + firstValue));
                    break;
                case ("-") :
                    stack.push(Integer.toString(secondValue - firstValue));
                    break;
            }
        }
        if (stack.size() != 1) {
            System.err.println("Stack size should be equal to one");
        } else {
            System.out.println("Expression evaluates to " + stack.pop() + ".");
        }
    }

    /**
     * This method is used to check if the given string is an {@code integer}.
     * @param value String that is being checked.
     * @return Returns {@code true} if string is a number, {@code false} otherwise.
     */
    private static boolean valueIsANumber(String value) {
        try {
            int checkedNumber = Integer.parseInt(value);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
