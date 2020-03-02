package hr.fer.zemris.java.gui.charts;

import java.util.List;

/**
 * Class which holds data that are needed to make visual representation of the data.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class BarChart {
    /**
     * Values stored
     */
    private List<XYValue> values;
    /**
     * y coordinate at which the y axis starts
     */
    private int yMin;
    /**
     * y coordinate at which the y axis ends.
     */
    private int yMax;
    /**
     * spacing between to y values.
     */
    private int spacing;
    /**
     * Description that goes alongside x axis.
     */
    private String xDescription;
    /**
     * Description that goes alongside y axis.
     */
    private String yDescription;

    /**
     * Constructor.
     * @param values Data values
     * @param xDescription Description that goes alongside x axis.
     * @param yDescription Description that goes alongside y axis.
     * @param yMin y coordinate at which the y axis starts.
     * @param yMax y coordinate at which the y axis ends.
     * @param spacing spacing between two y values.
     */
    public BarChart(List<XYValue> values, String xDescription, String yDescription, int yMin, int yMax, int spacing) {
        if (yMin < 0) {
            throw new IllegalArgumentException("Y min should be greater a positive number.");
        }
        if (yMax <= yMin) {
            throw new IllegalArgumentException("Y max should be greater than y min.");
        }

        while ((yMax - yMin) % spacing != 0) {
            spacing++;
        }

        this.values = values;
        this.spacing = spacing;
        this.xDescription = xDescription;
        this.yDescription = yDescription;
        this.yMin = yMin;
        this.yMax = yMax;
        checkValues();
    }

    /**
     * Getter for ymin.
     * @return Returns y min.
     */
    public int getyMin() {
        return yMin;
    }

    /**
     * Getter for y max.
     * @return Returns y max.
     */
    public int getyMax() {
        return yMax;
    }

    /**
     * Getter for spacing.
     * @return Returns spacing.
     */
    public int getSpacing() {
        return spacing;
    }

    /**
     * Getter for list of values.
     * @return Returns list of stored data values.
     */
    public List<XYValue> getValues() {
        return values;
    }

    /**
     * Getter for x axis description
     * @return Returns String which will be written on x axis
     */
    public String getxDescription() {
        return xDescription;
    }
    /**
     * Getter for y axis description
     * @return Returns String which will be written on y axis
     */
    public String getyDescription() {
        return yDescription;
    }

    /**
     * Private method used to check whether all of the values are below y min.
     */
    private void checkValues() {
        for (XYValue value : values) {
            if (value.getY() < yMin) {
                throw new IllegalArgumentException("Given XYValues should be greater than y min");
            }
        }
    }
}
