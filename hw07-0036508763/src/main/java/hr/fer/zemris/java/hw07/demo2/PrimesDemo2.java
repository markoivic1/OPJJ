package hr.fer.zemris.java.hw07.demo2;

/**
 * Class is used to demonstrate nested iterators.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PrimesDemo2 {
    /**
     * Nested operators which form Cartesian product.
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        PrimesCollection primesCollection = new PrimesCollection(2);
        for (Integer prime : primesCollection) {
            for (Integer prime2 : primesCollection) {
                System.out.println("Got prime pair: " + prime + ", " + prime2);
            }
        }
    }
}
