package hr.fer.zemris.java.hw01;

import java.util.Scanner;

/**
 * Simple factorial calculator.
 *
 * @author marko
 * @version 1.0.0
 */

public class Factorial {

    /**
     * Calculates factorial for a given number.
     *
     * @param number of type {@code long} is used to calculate factorial.
     * @return returns calculated factorial of type {@code long}.
     */
    public static long factorial(long number) {
        long calculatedNumber = 1;
        for (int i = 1; i <= number; i++) {
            calculatedNumber *= i;
        }

        if (calculatedNumber < 0 || number < 0) {
            throw new IllegalArgumentException("Factorial could not be calculated");
        }

        return calculatedNumber;
    }

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.print("Unesite broj > ");
            String line = sc.nextLine();
            if (line.equals("kraj")) {
                System.out.println("Doviđenja.");
                break;
            }
            try {
                long numberFactorial = Long.parseLong(line);
                if (numberFactorial < 3 || numberFactorial > 20) {
                    System.out.println("\'" + numberFactorial + "\' nije broj u dozvoljenom rasponu.");
                    continue;
                }

                System.out.println(numberFactorial + "! = " + factorial(numberFactorial));
            } catch (NumberFormatException ex) {
                System.out.println("\'" + line + "\' nije cijeli broj.");
            }
        }
        sc.close();
    }

}
