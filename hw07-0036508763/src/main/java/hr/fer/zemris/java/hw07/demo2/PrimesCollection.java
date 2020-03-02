package hr.fer.zemris.java.hw07.demo2;

import java.util.Iterator;

/**
 * Collection which is used to calculate primes.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PrimesCollection implements Iterable<Integer>, Iterator<Integer> {
    /**
     * Number of primes that need to be calculated.
     */
    private int numberOfPrimes;
    /**
     * Number of primes that have been calculated.
     */
    private int numberOfCalculatedPrimes;

    /**
     * Constructor which stored numberOfPrimes and initializes numberOfCalculatedPrimes to 0.
     * @param numberOfPrimes Number of primes that need to be calculated.
     */
    public PrimesCollection(int numberOfPrimes) {
        this.numberOfPrimes = numberOfPrimes;
        numberOfCalculatedPrimes = 0;
    }

    /**
     * Iterator over collection of primes.
     * @return Returns iterator of {@link PrimesCollection}.
     */
    @Override
    public Iterator iterator() {
        return new PrimesCollection(numberOfPrimes);
    }

    /**
     * Check if all primes have been calculated.
     * @return Returns true if there are more primes to be calculater, returns false otherwise.
     */
    @Override
    public boolean hasNext() {
        return numberOfPrimes > numberOfCalculatedPrimes;
    }

    /**
     * Calculates next prime.
     * @return Returns next prime.
     */
    @Override
    public Integer next() {
        int indexOfCurrentPrime = 0;
        int currentPrime = 2;
        while (indexOfCurrentPrime < numberOfCalculatedPrimes) {
            currentPrime++;
            if (isPrime(currentPrime)) {
                indexOfCurrentPrime++;
            }
        }
        numberOfCalculatedPrimes++;
        return currentPrime;
    }

    /**
     * Checks if the give number is prime.
     * @param number Number which will be checked if prime.
     * @return Returns true if given number is prime, returns false otherwise.
     */
    private boolean isPrime(int number) {
        for (int i = 2; i < number / 2 + 1; i++) {
            if ((number % i) == 0) {
                return false;
            }
        }
        return true;
    }
}
