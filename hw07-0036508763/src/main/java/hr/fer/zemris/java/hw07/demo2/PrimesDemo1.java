package hr.fer.zemris.java.hw07.demo2;

/**
 * Class used to demonstrate simple prime iterator
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PrimesDemo1 {
    /**
     * Method main is used to print primes from prime iterator.
     * @param args No arguments are taken.
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(5); // 5: how many of them
        for (Integer prime : primesCollection) {
            System.out.println("Got prime: " + prime);
        }
    }
}
