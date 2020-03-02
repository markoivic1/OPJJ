package hr.fer.zemris.java.hw06.crypto;
/**
 * Util class which contains some methods which can be universally used.
 * It defines two public static methods: hextobyte and bytetohex.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Util {
    /**
     * This method converts given String of hexadecimal number to an array of bytes.
     * @param hexadecimal Hexadecimal number without spaces in between two number.
     * @return Returns an array of converted bytes.
     * @throws IllegalArgumentException Thrown when given hexadecimal number does not contain. Valid hexadecimal number.
     */
    public static byte[] hextobyte(String hexadecimal) {
        if (hexadecimal.length()%2 != 0) {
            throw new IllegalArgumentException();
        }
        if (hexadecimal.length() == 0) {
            return new byte[0];
        }
        int numberOfBytes = hexadecimal.length()/2;
        byte[] convertedBytes = new byte[numberOfBytes];
        int i = 0;
        while (numberOfBytes != 0) {
            convertedBytes[i] = extractByte(hexadecimal, 2*i);
            i++;
            numberOfBytes--;
        }
        return convertedBytes;
    }

    /**
     * Method which takes an array of bytes and calculates hexadecimal numbers which are then stored in a string.
     * @param bytes Byte array which will be converted to a string.
     * @return Return String of converted bytes
     */
    public static String bytetohex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte someByte : bytes) {
            sb.append(extractHex(someByte));
        }
        return sb.toString();
    }

    /**
     * Class used for simpler byte extraction from a string.
     * @param input Takes an input from which 2 substring will be extracted.
     * @param index Index used to extract two subsequent characters from a given input.
     * @return Returns extracted byte
     */
    private static byte extractByte(String input, int index) {
        String firstHex = input.substring(index, index + 1);
        String secondHex = input.substring(index + 1, index + 2);
        return (byte) (hexEvaluator(firstHex)*16 + hexEvaluator(secondHex));
    }

    /**
     * Extracts hex digit from a given byte.
     * @param givenByte Byte from which the hex digit will be extracted.
     * @return Return hexadecimal digit in form of a string.
     */
    private static String extractHex(byte givenByte) {
        int byteToInt = givenByte;
        if (byteToInt < 0) {
            byteToInt += 256;
        }
        String firstSign = byteEvaluator(byteToInt / 16);
        String secondSign = byteEvaluator(byteToInt % 16);
        return firstSign + secondSign;
    }

    /**
     * Method which helps to evaluate expanded range of hexadecimal number.
     * It converts given byte to a hexadecimal format.
     * @param givenByte Byte which will be converted to hexadecimal format.
     * @return Returns hexadecimal number as string.
     */
    private static String byteEvaluator(int givenByte) {
        switch (givenByte) {
            case 10:
                return "a";
            case 11:
                return "b";
            case 12:
                return "c";
            case 13:
                return "d";
            case 14:
                return "e";
            case 15:
                return "f";
            default:
                return String.valueOf(givenByte);
        }
    }

    /**
     * Method which evaluates given hexadecimal argument.
     * It converts given hexadecimal argument to a decimal.
     * @param hex Hexadecimal number
     * @return Returns converted hexadecimal as int.
     */
    private static int hexEvaluator(String hex) {
        switch (hex.toLowerCase()) {
            case "a":
                return 10;
            case "b":
                return 11;
            case "c":
                return 12;
            case "d":
                return 13;
            case "e":
                return 14;
            case "f":
                return 15;
            default:
                return Integer.parseInt(hex);
        }
    }
}
