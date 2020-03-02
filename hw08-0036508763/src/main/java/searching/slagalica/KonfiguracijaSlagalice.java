package searching.slagalica;

import java.util.Arrays;

/**
 * Class which defines puzzle configuration
 * @author Marko Ivić
 * @version 1.0.0
 */
public class KonfiguracijaSlagalice {
    /**
     * Tiles in proximity.
     */
    private int[] adjacentTiles;

    /**
     * Constructor that takes value and stores it.
     * @param adjacentTiles Value is stored.
     */
    public KonfiguracijaSlagalice(int[] adjacentTiles) {
        this.adjacentTiles = adjacentTiles;
    }

    /**
     * Getter for tiles.
     * @return Returns tiles.
     */
    public int[] getPolje() {
        return Arrays.copyOf(adjacentTiles, adjacentTiles.length);
    }

    /**
     * Index of space character.
     * @return Returns index of a space char
     */
    public int indexOfSpace() {
        for (int i = 0; i < adjacentTiles.length; i++) {
            if (adjacentTiles[i] == 0) {
                return i;
            }
        }
        throw new IllegalArgumentException("This array does not contain empty field");
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (adjacentTiles[i * 3 + j] == 0) {
                    sb.append("* ");
                } else {
                    sb.append(adjacentTiles[i * 3 + j]).append(" ");
                }
            }
            if (i != 2) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KonfiguracijaSlagalice that = (KonfiguracijaSlagalice) o;
        return Arrays.equals(adjacentTiles, that.adjacentTiles);
    }

    /**
     * {@inheritDoc}
     * @return
     */
    @Override
    public int hashCode() {
        return Arrays.hashCode(adjacentTiles);
    }
}
