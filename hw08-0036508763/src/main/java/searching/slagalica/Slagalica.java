package searching.slagalica;

import searching.algorithms.Transition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class which defines strategies for our puzzle
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Slagalica implements Supplier<KonfiguracijaSlagalice>, Function<KonfiguracijaSlagalice, List<Transition<KonfiguracijaSlagalice>>>, Predicate<KonfiguracijaSlagalice> {
    /**
     * Initial puzzle configuration.
     */
    private KonfiguracijaSlagalice konfiguracijaSlagalice;

    /**
     * Constructor that takes value and stores it.
     * @param konfiguracijaSlagalice Initial puzzle configuration.
     */
    public Slagalica(KonfiguracijaSlagalice konfiguracijaSlagalice) {
        this.konfiguracijaSlagalice = konfiguracijaSlagalice;
    }

    /**
     * Method used to return list of valid transitions horizontal and vertical from a space char.
     * @param konfiguracijaSlagalice Configuration which will be used
     * @return Returns list of valid transitions.
     */
    @Override
    public List<Transition<KonfiguracijaSlagalice>> apply(KonfiguracijaSlagalice konfiguracijaSlagalice) {
        List<Transition<KonfiguracijaSlagalice>> transitionList = new ArrayList<>();
        int[][] table = new int[3][3];
        int x = 0;
        int y = 0;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                table[i][j] = konfiguracijaSlagalice.getPolje()[i * 3 + j];
                if (table[i][j] == 0) {
                    x = i;
                    y = j;
                }
            }
        }
        if (isInBounds(x + 1, y)) {
            transitionList.add(new Transition<>(new KonfiguracijaSlagalice(switchTile(table, x + 1, y, x, y)), 1));
        }
        if (isInBounds(x - 1, y)) {
            transitionList.add(new Transition<>(new KonfiguracijaSlagalice(switchTile(table, x - 1, y, x, y)), 1));
        }
        if (isInBounds(x, y + 1)) {
            transitionList.add(new Transition<>(new KonfiguracijaSlagalice(switchTile(table, x, y + 1, x, y)), 1));
        }
        if (isInBounds(x, y - 1)) {
            transitionList.add(new Transition<>(new KonfiguracijaSlagalice(switchTile(table, x, y - 1, x, y)), 1));
        }
        return transitionList;
    }

    /**
     * Method used to switch values in some tile.
     * @param tiles 2d array representation of tiles.
     * @param x first X coordinate
     * @param y first Y coordinate
     * @param x1 second X coordinate
     * @param y1 second Y coordinate
     * @return Returns an array of processed tiles.
     */
    private int[] switchTile(int[][] tiles, int x, int y, int x1, int y1) {
        int firstValue = tiles[x][y];
        int secondValue = tiles[x1][y1];
        int[] table = new int[9];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tiles[i][j] == firstValue) {
                    table[i * 3 + j] = secondValue;
                } else if (tiles[i][j] == secondValue) {
                    table[i * 3 + j] = firstValue;
                } else {
                    table[i * 3 + j] = tiles[i][j];
                }
            }
        }
        return table;
    }

    /**
     * Checks if the given coordinates are inside of a puzzle
     * @param x X coordinate
     * @param y Y coordinate
     * @return Returns true if given coordinates are inside of a puzzle, returns false otherwise.
     */
    private boolean isInBounds(int x, int y) {
        return x >= 0 && x < 3 && y >= 0 && y < 3;
    }

    /**
     * Checks if the given configuration corresponds with the goal configuration
     * @param konfiguracijaSlagalice Configuration which will be used in a comparison.
     * @return Returns true if the match, returns false otherwise.
     */
    @Override
    public boolean test(KonfiguracijaSlagalice konfiguracijaSlagalice) {
        int[] goal = {1, 2, 3, 4, 5, 6, 7, 8, 0};
        return Arrays.equals(konfiguracijaSlagalice.getPolje(), goal);
    }

    /**
     * Getter for puzzle configuration
     * @return returns puzzle configuration.
     */
    @Override
    public KonfiguracijaSlagalice get() {
        return konfiguracijaSlagalice;
    }
}
