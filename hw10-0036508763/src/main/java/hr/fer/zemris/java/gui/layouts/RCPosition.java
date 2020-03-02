package hr.fer.zemris.java.gui.layouts;

import java.util.Objects;

/**
 * Class which defines the position in a CalcLayout.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RCPosition {
    /**
     * Row
     */
    private int row;
    /**
     * Column
     */
    private int column;

    /**
     * Constructor.
     * @param row Row
     * @param column Column
     */
    public RCPosition(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Getter for row.
     * @return Returns row.
     */
    public int getRow() {
        return row;
    }

    /**
     * Getter for column.
     * @return Returns column.
     */
    public int getColumn() {
        return column;
    }

    /**
     * Compares row and column data.
     * @param o Other object.
     * @return Returns true of row and column data are the same; return false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RCPosition that = (RCPosition) o;
        return row == that.row &&
                column == that.column;
    }

    /**
     * Generates hash code for an object of this class.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, column);
    }
}
