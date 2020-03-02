package hr.fer.zemris.java.hw06.shell;

import java.util.ArrayList;
import java.util.List;

/**
 * This class defines methods which mostly extract command names and arguments from a given String.
 * These methods are used by almost all commands which implement {@link ShellCommand} and by some methods in {@link Environment}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SharedUtility {
    /**
     * This class is used primarily to return multiple things from a method.
     */
    private static class StringAndIndex {
        /**
         * Stores a string.
         */
        private String string;
        /**
         * Stores a current index-
         */
        private int currentIndex;
    }

    /**
     * Extracts arguments from a given String.
     * It skips the command and returns concatenated arguments.
     * @param arguments Extract arguments from a given input.
     * @return Return arguments as string.
     */
    public static String extractArguments(String arguments) {
        int currentIndex = 0;
        char[] data = arguments.trim().toCharArray();
        while ((data.length != currentIndex) && !Character.isSpaceChar(data[currentIndex])) {
            currentIndex++;
        }
        while ((data.length != currentIndex) && Character.isSpaceChar(data[currentIndex])) {
            currentIndex++;
        }
        if (data.length == currentIndex) {
            return "";
        }
        return String.valueOf(data, currentIndex, data.length - currentIndex);
    }

    /**
     * Splits arguments from a given input.
     * @param arguments Arguments which will be split.
     * @return Returns split arguments as an array.
     */
    public static String[] splitArguments(String arguments) {
        List<String> listOfArguments = new ArrayList<>();
        char[] data = arguments.trim().toCharArray();
        // extract first argument
        StringAndIndex stringAndIndex = new StringAndIndex();
        while (stringAndIndex.currentIndex != data.length) {
            stringAndIndex = extractArgument(data, stringAndIndex.currentIndex);
            listOfArguments.add(stringAndIndex.string);
        }
        String[] extractedArguments = new String[listOfArguments.size()];
        listOfArguments.toArray(extractedArguments);
        return extractedArguments;
    }

    /**
     * Extracts command name from a given String.
     * @param argument String which contains argument.
     * @return Returns argument name.
     */
    public static String extractCommandName(String argument) {
        char[] data = argument.trim().toCharArray();
        int currentIndex = 0;
        while ((currentIndex != data.length) && (data[currentIndex] != ' ')) {
            currentIndex++;
        }
        return String.valueOf(data, 0, currentIndex);
    }

    /**
     * Extract single argument from an array of chars starting from a current index.
     * @param data An array of chars from which the argument will be extracted
     * @param currentIndex An index starting from which the data will be extracted.
     * @return Returns {@link StringAndIndex} which stores new String and newly calculated current index.
     */
    private static StringAndIndex extractArgument(char[] data, int currentIndex) {
        StringAndIndex stringAndIndex;
        currentIndex = skipBlanks(data, currentIndex);
        if (data[currentIndex] == '\"') {
            stringAndIndex = extractArgumentInQuotes(data, currentIndex);
        } else {
            stringAndIndex = extractArgumentWithoutQuotes(data, currentIndex);
        }
        stringAndIndex.currentIndex = skipBlanks(data, stringAndIndex.currentIndex);
        if (stringAndIndex.currentIndex == data.length) {
            return stringAndIndex;
        }
        if (data[currentIndex] == '\"') {
            stringAndIndex = extractArgumentInQuotes(data, currentIndex);
        } else {
            stringAndIndex = extractArgumentWithoutQuotes(data, currentIndex);
        }
        return stringAndIndex;
    }

    /**
     * Extracts argument which doesn't contain quotes.
     * @param data An array of chars from which the argument will be extracted.
     * @param currentIndex Extracts argument starting from given index.
     * @return Returns {@link StringAndIndex} in which the argument and new calculated index is stored.
     */
    private static StringAndIndex extractArgumentWithoutQuotes(char[] data, int currentIndex) {
        currentIndex = skipBlanks(data, currentIndex);
        int startingIndex = currentIndex;
        while ((currentIndex != data.length) && (data[currentIndex] != ' ')) {
            currentIndex++;
        }
        StringAndIndex stringAndIndex = new StringAndIndex();
        if (stringAndIndex.string == null) {
            stringAndIndex.string = String.valueOf(data, startingIndex, currentIndex - startingIndex);
        } else {
            stringAndIndex.string += " " + String.valueOf(data, startingIndex, currentIndex - startingIndex);
        }
        stringAndIndex.currentIndex = skipBlanks(data, currentIndex);
        return stringAndIndex;
    }

    /**
     * Extracts argument which contains quotes.
     * @param data An array of chars from which the argument will be extracted.
     * @param currentIndex Extracts argument starting from given index.
     * @return Returns {@link StringAndIndex} in which the argument and new calculated index is stored.
     */
    private static StringAndIndex extractArgumentInQuotes(char[] data, int currentIndex) {
        currentIndex = skipBlanks(data, currentIndex);
        int startingIndex = currentIndex;
        currentIndex++;
        while ((currentIndex != data.length) && (data[currentIndex] != '\"')) {
            if (data[currentIndex] == '\\') {
                if (currentIndex + 1 == data.length) {
                    break;
                }
                if ((data[currentIndex + 1] == '\"') || (data[currentIndex + 1] == '\\')) {
                    currentIndex += 2;
                    continue;
                }

            }
            currentIndex++;
        }
        if (currentIndex != data.length) {
            currentIndex++;
        }
        StringAndIndex stringAndIndex = new StringAndIndex();
        stringAndIndex.string = String.valueOf(data, startingIndex, currentIndex - startingIndex)
                .replace("\"", "")
                .replace("\\\"", "\"").replace("\\\\", "\\");

        stringAndIndex.currentIndex = skipBlanks(data, currentIndex);
        return stringAndIndex;
    }

    /**
     * Method which skips space chars.
     * @param data An array of chars from which the data will be read.
     * @param currentIndex Index from which the data will be read.
     * @return Returns calculated new currend index.
     */
    private static int skipBlanks(char[] data, int currentIndex) {
        while (currentIndex < data.length) {
            char c = data[currentIndex];
            if (c == ' ' || c == '\r' || c == '\n' || c == '\t') {
                currentIndex++;
                continue;
            }
            return currentIndex;
        }
        return currentIndex;
    }
}
