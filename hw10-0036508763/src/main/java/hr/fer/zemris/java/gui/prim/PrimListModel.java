package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;
import java.util.ArrayList;
import java.util.List;

/**
 * ListModel which is used to generate primes.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PrimListModel<T> implements ListModel<T> {
    /**
     * List of primes
     */
    private List<Integer> primes;
    /**
     * List of listeners.
     */
    private List<ListDataListener> listeners;

    /**
     * Constructor.
     */
    public PrimListModel() {
        this.primes = new ArrayList<>();
        listeners = new ArrayList<>();
        this.primes.add(1);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int getSize() {
        return primes.size();
    }

    /**
     * {@inheritDoc}
     * @param index
     * @return
     */
    @Override
    @SuppressWarnings("unchecked")
    public T getElementAt(int index) {
        if (index >= primes.size()) {
            throw new IndexOutOfBoundsException("Given index is not in range.");
        }
        try {
            return (T) primes.get(index);
        } catch (ClassCastException ex) {
            throw new ClassCastException("This class works only with Integer as parameter");
        }
    }

    /**
     * {@inheritDoc}
     * @param l
     */
    @Override
    public void addListDataListener(ListDataListener l) {
        listeners.add(l);
    }

    /**
     * {@inheritDoc}
     * @param l
     */
    @Override
    public void removeListDataListener(ListDataListener l) {
        listeners.remove(l);
    }

    /**
     * Incrementally gets next prime.
     */
    public void next() {
        int position = primes.size();
        calculatePrimeStartingFrom(primes.get(position - 1));
        ListDataEvent dataEvent = new ListDataEvent(this, ListDataEvent.CONTENTS_CHANGED, position, position);
        for (ListDataListener listener : listeners) { // notify listeners
            listener.contentsChanged(dataEvent);
        }
    }

    /**
     * Method used to calculate prime that is greater than the given value.
     * @param prime Last calculated number (doesn't have to be a prime).
     */
    private void calculatePrimeStartingFrom(int prime) {
        while (true) {
            prime++;
            if (isPrime(prime)) {
                primes.add(prime);
                return;
            }
        }
    }

    /**
     * Checks if the given number is prime.
     * @param prime Number that is being checked.
     * @return Returns true if the number is prime; returns false otherwise.
     */
    private boolean isPrime(int prime) {
        int numberOfDivs = 0;
        for (int i = 1; i <= prime / 2; i++) {
            if (prime % i == 0) {
                numberOfDivs++;
            }
        }
        return numberOfDivs == 1;
    }
}
