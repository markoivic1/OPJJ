package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Class which is used to calculate surface and perimeter of a rectangle.
 * If 2 arguments were given the surface and the perimeter will be calculated using those two values as width and height.
 * If 0 arguments were given the program will prompt user to enter proper values for width and height.
 *
 * Any other number of arguments will cause program to be shut down with error code 1.
 *
 * @author marko
 * @version 1.0.0
 */
public class Rectangle {

    /**
     * Calculates an area of a rectangle S = width * height.
     * @param width of type {@code integer} is used to represent one side of a rectangle.
     * @param height of type {@code integer} is used to represent other side of a rectangle.
     * @return Returns {@code double} for calculated area of a rectangle.
     */
    public static double calculateRectangleArea(double width, double height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Širina i visina moraju biti brojevi veći od 0");
        }
        return width*height;
    }

    /**
     * Calculates a perimeter of a rectangle P = 2*width + 2*height
     * @param width of type {@code integer} is used to represent one side of a rectangle.
     * @param height of type {@code integer} is used to represent other side of a rectangle.
     * @return Returns {@code double} for calculated perimeter of a rectangle
     */
    public static double calculateRectanglePerimeter(double width, double height){
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Širina i visina moraju biti brojevi veći od 0");
        }
        return 2*width + 2*height;
    }

    /**
     * Print method with width, height, area and perimeter of a corresponding rectangle.
     *
     * @param width of type {@code integer} is used to represent one side of a rectangle.
     * @param height of type {@code integer} is used to represent other side of a rectange.
     * @param area of type {@code integer} is an area of a corresponding rectangle.
     * @param perimeter of type {@code integer} is a perimeter of a corresponding rectangle.
     */

    public static void printAreaAndPerimeter(double width, double height, double area, double perimeter){
        System.out.printf("Pravokutnik širine %.1f i visine %.1f ima površinu %.1f te opseg %.1f.",
                width, height, area, perimeter);
    }

    /**
     * Checks number and prints out appropriate message if number is negative or zero
     *
     * @param number {@code integer} which is being checked.
     * @return Returns {@code true} if number is negative or zero. {@code false} if number is positive.
     */

    public static boolean isNegativeOrZero(double number) {
        if (number < 0) {
            System.out.println("Unijeli ste negativnu vrijednost");
            return true;
        }
        if (number == 0) {
            System.out.println("Unijeli ste nulu");
            return true;
        }
        return false;
    }

    /**
     * Parsing double from input string. Reads from system input until valid number is given.
     *
     * @param side For a cleaner code, input {@code string} with correct grammar form is used for a method call.
     * @return {@code double} parsed from input string.
     */
    public static double tidyScannerInput(String side, Scanner sc) {
        double sizeOfAside;
        while (true) {
            System.out.print("Unesite " + side + " > ");
            String line = sc.next();
            try {
                sizeOfAside = Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                System.out.println("\'" + line + "\' se ne može protumačiti kao broj.");
                continue;
            }
            if (isNegativeOrZero(sizeOfAside)) {
                continue;
            }
            break;
        }
        return sizeOfAside;
    }

    /**
     *
     * Checks if the string is able to be parsed to double if not it terminates the program.
     *
     * @param side Is {@code string} trying to be parsed as double.
     * @return {@code double} parsed from input string.
     */
    public static double tidyArgumentInput(String side) {
        double sizeOfAside = 0;
        try {
            sizeOfAside = Double.parseDouble(side);
            if (sizeOfAside <= 0){
                System.out.println("Unešeni argumenti moraju biti veći od nule");
                System.exit(1);
            }
        } catch (NumberFormatException ex) {
            System.out.println("\'" + side + "\' se ne može protumačiti kao broj.");
            System.exit(1);
        }
        return sizeOfAside;
    }

    public static void main(String[] args) {
        if (args.length == 2) {
            double width = tidyArgumentInput(args[0]);
            double height = tidyArgumentInput(args[1]);
            double area = calculateRectangleArea(width, height);
            double perimeter = calculateRectanglePerimeter(width,height);
            printAreaAndPerimeter(width,height,area,perimeter);
        } else if (args.length == 0) {
            Scanner sc = new Scanner(System.in);
            double width = tidyScannerInput("širinu", sc);
            double height = tidyScannerInput("visinu", sc);
            double area = calculateRectangleArea(width, height);
            double perimeter = calculateRectanglePerimeter(width,height);
            printAreaAndPerimeter(width,height,area,perimeter);
            sc.close();
        } else {
            System.out.println("Unešen je krivi broj argumenata.");
            System.exit(1);
        }
    }
}
