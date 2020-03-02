package hr.fer.zemris.java.hw06.shell.commands;

import hr.fer.zemris.java.hw06.crypto.Util;
import hr.fer.zemris.java.hw06.shell.Environment;
import hr.fer.zemris.java.hw06.shell.SharedUtility;
import hr.fer.zemris.java.hw06.shell.ShellCommand;
import hr.fer.zemris.java.hw06.shell.ShellStatus;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Command hexdump writes contents of a given file as they are stored in memory from memory address 0.
 * Command hexdump takes one argument which is a path to a file.
 * Given file is printed in this format.
 *      address in memory | 16 hex-digits | 16 hex-digits | 16 letters which are represented in second in third column of this row
 * This is printed until the whole file has been read.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class HexdumpShellCommand implements ShellCommand {
    /**
     * Size of a buffer which will be used in a stream of bytes.
     */
    private static final int BUFFER_SIZE = 1024;
    /**
     * Size of a one columnt of hex-digits.
     */
    private static final int SIZE_OF_MESSAGE = 16;

    /**
     * Prints hexdump or a given file.
     * @param env Given environment in which the command will be executed.
     * @param arguments Arguments which are used for command execution.
     * @return Returns {@link ShellStatus} CONTINUE.
     */
    @Override
    public ShellStatus executeCommand(Environment env, String arguments) {
        String[] splitArguments = SharedUtility.splitArguments(arguments);
        if (splitArguments.length != 1) {
            env.writeln("Invalid number of arguments in hexdump command");
            return ShellStatus.CONTINUE;
        }
        Path input = Paths.get(SharedUtility.splitArguments(arguments)[0]);

        try (InputStream is = Files.newInputStream(input)) {
            byte[] buff = new byte[BUFFER_SIZE];
            while (true) {
                int r = is.read(buff);
                if (r < 1) break;
                env.write(hexdumpString(buff, r));
            }
        } catch (IOException ex) {
            env.writeln("Command hexdump could not be executed.");
        }
        return ShellStatus.CONTINUE;
    }

    /**
     * Method which read file and formats it in hexdump format.
     * @param buffer An array of bytes which are read from a file.
     * @param numberOfBytes Number of bytes stored in a given array.
     * @return Returns hexdump as string.
     */
    private String hexdumpString(byte[] buffer, int numberOfBytes) {
        int numberOfReadChars = 0;
        StringBuilder sb = new StringBuilder();

        while (numberOfReadChars < numberOfBytes) {
            String numberOfReadCharsInHex = Integer.toHexString(numberOfReadChars);
            sb.append("0".repeat(8 - numberOfReadCharsInHex.length()));
            sb.append(numberOfReadCharsInHex).append(": ");

            int numberOfCharsToRead;

            numberOfCharsToRead = readBytesAndReturnNumberOfReadBytes(buffer, numberOfReadChars, sb, numberOfBytes);

            sb.append("| ");
            numberOfReadChars += numberOfCharsToRead;

            numberOfCharsToRead = readBytesAndReturnNumberOfReadBytes(buffer, numberOfReadChars, sb, numberOfBytes);

            numberOfReadChars += numberOfCharsToRead;
            sb.append("| ");
            Charset charset = Charset.forName("UTF-8");
            sb.append(new String(extractSubArray(buffer, numberOfReadChars - 16, numberOfBytes), charset)).append("\n");
        }
        return sb.toString();
    }

    /**
     * Fills one column of hex-digits.
     * @param buffer Buffer from which data is read.
     * @param numberOfReadChars Number of chars that have been read.
     * @param sb StringBuilder in which the processed data will be stored.
     * @param numberOfBytes Number of bytes that are stored in a buffer.
     * @return Returns number of processed bytes.
     */
    private int readBytesAndReturnNumberOfReadBytes(byte[] buffer, int numberOfReadChars, StringBuilder sb, int numberOfBytes) {
        int numberOfCharsToRead;
        numberOfCharsToRead = getNumberOfCharsToRead(buffer, numberOfReadChars);
        for (int i = numberOfReadChars; i < numberOfReadChars + numberOfCharsToRead; i++) {
            String stringRepresentation = String.format("%02x", buffer[i]);
            if (i + 1 > numberOfBytes) {
                stringRepresentation = "  ";
            }
            sb.append(stringRepresentation).append(" ");
        }
        return numberOfCharsToRead;
    }

    /**
     * Extracts subarray of 32 bytes.
     * If char's value is greater than 127 or lower than 32, char '.' will be stored instead.
     * @param array An array from which the subarray will be extracted
     * @param index From which index the extraction starts.
     * @param numberOfBytes Number of bytes that are contained in an array.
     * @return Returns byte array of extracted subarray.
     */
    private byte[] extractSubArray(byte[] array, int index, int numberOfBytes) {
        byte[] subArray = new byte[SIZE_OF_MESSAGE];
        for (int i = 0; i < SIZE_OF_MESSAGE; i++) {
            subArray[i] = array[index + i];
            if ((subArray[i] > 32) && (subArray[i] < 127)) {
                continue;
            }
            subArray[i] = '.';
            if (index + i + 1 > numberOfBytes) {
                subArray[i] = ' ';
            }
        }
        return subArray;
    }

    /**
     * Calculates the number of chars that need to be read.
     * @param buffer An array of bytes which is used in a calculation.
     * @param numberOfReadChars Number of currently read chars.
     * @return Returns number of chars that need to be read.
     */
    private int getNumberOfCharsToRead(byte[] buffer, int numberOfReadChars) {
        int numberOfCharsToRead;
        if (numberOfReadChars + 8 > buffer.length) {
            numberOfCharsToRead = buffer.length - numberOfReadChars;
        } else {
            numberOfCharsToRead = 8;
        }
        return numberOfCharsToRead;
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String getCommandName() {
        return "hexdump";
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public List<String> getCommandDescription() {
        List<String> description = new ArrayList<>();
        description.add("Command hexdump prints given file in this format");
        description.add("Address in memory | 8 bytes of data in hex | 8 bytes of data in hex | string literal");
        return Collections.unmodifiableList(description);
    }
}
