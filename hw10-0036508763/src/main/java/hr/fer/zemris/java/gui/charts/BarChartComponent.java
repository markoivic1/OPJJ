package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * BarChartComponent defines drawing charts using given {@link BarChart} data.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class BarChartComponent extends JComponent {
    /**
     * Stored data.
     */
    private BarChart barChart;

    /**
     * Constructor
     * @param barChart {@link BarChart} from which the data will be used.
     */
    public BarChartComponent(BarChart barChart) {
        this.barChart = barChart;
    }

    /**
     * Method which is called each time the component's size is changed.
     * Graphical user interface adjusts accordingly to resize.
     * @param g Graphics which are used for drawing.
     */
    @Override
    protected void paintComponent(Graphics g) {
        Insets insets = getInsets();
        // max width on y axis
        int maxFontWidth = g.getFontMetrics().stringWidth(String.valueOf(getMaxWidth()));
        int maxFontHeight = g.getFontMetrics().getHeight();
        Font originalFont = g.getFont();
        g.setFont(g.getFont().deriveFont(15f));
        int coordinateTextSize = g.getFontMetrics().getHeight();
        g.setFont(originalFont);
        int fixedXSpacing = 20; // same spacing is used on left and right
        int fixedYSpacing = 20; // same spacing is used on top and bottom.
        int markerSize = 7;
        int widthOfAChart = getWidth() - (insets.left + insets.left + 4 * fixedXSpacing + 2 * maxFontHeight);
        int heightOfAChart = getHeight() - (insets.top + insets.bottom + 4 * fixedYSpacing + 2 * maxFontHeight);
        int xStart = fixedXSpacing + maxFontWidth + 2 * fixedXSpacing + insets.left + insets.right;
        int yStart = fixedYSpacing + maxFontHeight + 2 * fixedYSpacing + insets.top + insets.bottom;
        int heightOfATile = (heightOfAChart - yStart) / (barChart.getyMax() - barChart.getyMin());
        int widthOfATile = (widthOfAChart - xStart) / barChart.getValues().size();
        drawGrid(g, xStart, getWidth() - xStart, yStart, getHeight() - yStart, widthOfATile, heightOfATile, markerSize, fixedXSpacing, fixedYSpacing);
        drawData(g, xStart, getWidth() - xStart, yStart, getHeight() - yStart, widthOfATile, heightOfATile);
        drawAxis(g, xStart, getWidth() - xStart, yStart, getHeight() - yStart, markerSize);
        drawDescriptions(g, fixedXSpacing, fixedYSpacing, coordinateTextSize);
    }

    /**
     * Draws string descriptions on x and y axis.
     * @param g Graphics which are used for drawing.
     * @param fixedXSpacing Spacing from x axis of this component.
     * @param fixedYSpacing Spacing from x axis of this component.
     * @param fontSize Font size.
     */
    private void drawDescriptions(Graphics g, int fixedXSpacing, int fixedYSpacing, int fontSize) {
        AffineTransform at = new AffineTransform();
        Graphics2D g2d = (Graphics2D) g;
        AffineTransform saveAT = g2d.getTransform();
        at.rotate(-Math.PI / 2);
        g2d.transform(at);
        Insets insets = getInsets();
        g.setColor(Color.black);
        g.setFont(g.getFont().deriveFont(Font.BOLD, fontSize));
        g2d.drawString(barChart.getyDescription(), -getHeight() / 2 - g.getFontMetrics().stringWidth(barChart.getyDescription()) / 2 - insets.top, fixedXSpacing + g.getFontMetrics().getHeight() - insets.left);
        g2d.setTransform(saveAT);
        g2d.drawString(barChart.getxDescription(), getWidth() / 2 - g.getFontMetrics().stringWidth(barChart.getxDescription()) / 2 - insets.bottom, getHeight() - g.getFontMetrics().getHeight() - insets.right);
    }

    /**
     *
     * @param g Graphics which are used for drawing.
     * @param xStart Charts starting x coordinate.
     * @param xEnd Charts ending x coordinate.
     * @param yStart Charts starting y coordinate.
     * @param yEnd Charts ending y coordinate.
     * @param xSpacing Horizontal spacing for each tile.
     * @param ySpacing Vertical spacing for each tile.
     */
    private void drawData(Graphics g, int xStart, int xEnd, int yStart, int yEnd, int xSpacing, int ySpacing) {
        int offset = ySpacing * barChart.getyMin();
        for (int x = 0; x < barChart.getValues().size(); x++) {
            // data rectangle
            g.setColor(Color.ORANGE);
            g.fillRect(xStart + x * xSpacing, yEnd - barChart.getValues().get(x).getY() * ySpacing + offset,
                    xSpacing, ySpacing * barChart.getValues().get(x).getY() - offset);

            // white line
            g.setColor(Color.WHITE);
            g.fillRect(xStart + x * xSpacing + xSpacing - 1, yEnd - barChart.getValues().get(x).getY() * ySpacing + offset,
                    1, ySpacing * barChart.getValues().get(x).getY() - offset);

            // shadow
            g.setColor(Color.GRAY);
            AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f);
            Graphics2D g2d = (Graphics2D) g;
            Composite originalComposite = g2d.getComposite();
            g2d.setComposite(alphaComposite);
            g2d.fillRect(xStart + x * xSpacing + xSpacing, yEnd - barChart.getValues().get(x).getY() * ySpacing + xSpacing / 20 + offset,
                    xSpacing / 20, ySpacing * barChart.getValues().get(x).getY() - xSpacing / 20 - offset);
            g2d.setComposite(originalComposite);
        }
    }

    /**
     * Draws grid and adds markers.
     * @param g Graphics which are used for drawing.
     * @param xStart Charts starting x coordinate.
     * @param xEnd Charts ending x coordinate.
     * @param yStart Charts starting y coordinate.
     * @param yEnd Charts ending y coordinate.
     * @param xSpacing Horizontal spacing for each tile.
     * @param ySpacing Vertical spacing for each tile.
     * @param marker Size of a marker.
     * @param fixedYSpacing Spacing from x axis of this component.
     */
    private void drawGrid(Graphics g, int xStart, int xEnd, int yStart, int yEnd, int xSpacing, int ySpacing, int marker, int fixedXSpacing, int fixedYSpacing) {
        int spacing = barChart.getSpacing();
        // horizontal lines
        int tmpXEnd = Math.min(xEnd, barChart.getValues().size() * xSpacing + xStart);
        int i = 0;
        int offset = ySpacing * barChart.getyMin();
        for (int y = barChart.getyMin(); y < barChart.getyMax() + 1; y += spacing) {
            g.setColor(Color.ORANGE);
            g.drawLine(xStart, yEnd - y * ySpacing + offset, tmpXEnd, yEnd - y * ySpacing + offset);
            g.setColor(Color.GRAY);
            g.drawLine(xStart - marker, yEnd - y * ySpacing + offset, xStart, yEnd - y * ySpacing + offset);
            // numbers on y axis
            String yNumber = String.valueOf(y);
            int startingXForString = xStart - fixedXSpacing / 2 - g.getFontMetrics().stringWidth(yNumber);
            g.setColor(Color.black);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
            g.drawString(yNumber, startingXForString, yEnd - y * ySpacing + g.getFontMetrics().getAscent() / 2 + offset);
            i++;
        }

        // vertical lines
        int j = 0;
        for (int x = 1; x < barChart.getValues().size() + 1; x++) {
            g.setColor(Color.ORANGE);
            g.drawLine(xStart + x * xSpacing, yEnd - barChart.getyMax() * ySpacing + offset, xStart + x * xSpacing, yEnd);
            g.setColor(Color.GRAY);
            //
            g.drawLine(xStart + x * xSpacing, yEnd + marker, xStart + x * xSpacing, yEnd);

            // numbers on x axis
            String xNumber = String.valueOf(barChart.getValues().get(j).getX());
            int startingYForString = yEnd + fixedYSpacing / 2 + marker;
            g.setColor(Color.black);
            g.setFont(g.getFont().deriveFont(Font.BOLD, 15f));
            g.drawString(xNumber, xStart + xSpacing / 2 + (x - 1) * xSpacing - g.getFontMetrics().stringWidth(xNumber) / 2, startingYForString);
            j++;
        }

    }

    /**
     * Draws axis and calls method used for drawing arrows
     * @param g Graphics which are used for drawing.
     * @param xStart Charts starting x coordinate.
     * @param xEnd Charts ending x coordinate.
     * @param yStart Charts starting y coordinate.
     * @param yEnd Charts ending y coordinate.
     * @param yAndXMarkerSize Size of a marker.
     */
    private void drawAxis(Graphics g, int xStart, int xEnd, int yStart, int yEnd, int yAndXMarkerSize) {
        g.setColor(Color.GRAY);
        g.drawLine(xStart - yAndXMarkerSize, yEnd, xEnd + yAndXMarkerSize, yEnd); // horizontal
        g.drawLine(xStart, yStart - yAndXMarkerSize, xStart, yEnd + yAndXMarkerSize); // vertical
        int triangleSide = 7;
        drawHorizontalArrow(g, xEnd + 2 * yAndXMarkerSize, yEnd, triangleSide);
        drawVerticalArrow(g, xStart, yStart - 2 * yAndXMarkerSize, triangleSide);
    }

    /**
     * Draws arrow on the end of a x axis.
     * @param g Graphics which are used for drawing.
     * @param pointX end of the x axis.
     * @param pointY point at which the x axis lies.
     * @param triangleSide Size of a drawn triangle
     */
    private void drawVerticalArrow(Graphics g, int pointX, int pointY, int triangleSide) {
        Polygon p = new Polygon();
        p.addPoint(pointX, pointY);
        p.addPoint(pointX - triangleSide, pointY + triangleSide);
        p.addPoint(pointX + triangleSide, pointY + triangleSide);
        g.fillPolygon(p);
    }
    /**
     * Draws arrow on the end of a y axis.
     * @param g Graphics which are used for drawing.
     * @param pointX point at which the y axis lies.
     * @param pointY end of the y axis.
     * @param triangleSide Size of a drawn triangle
     */

    private void drawHorizontalArrow(Graphics g, int pointX, int pointY, int triangleSide) {
        Polygon p = new Polygon();
        p.addPoint(pointX, pointY);
        p.addPoint(pointX - triangleSide, pointY - triangleSide);
        p.addPoint(pointX - triangleSide, pointY + triangleSide);
        g.fillPolygon(p);
    }

    /**
     * Method that gets maximum number in the barChart values.
     * Later that value is used to determine the maximum width of a string.
     * @return Returns largest number in a barChart values list.
     */
    private int getMaxWidth() {
        int maxWidth = 0;
        for (XYValue chart : barChart.getValues()) {
            maxWidth = Math.max(chart.getY(), maxWidth);
        }
        return maxWidth;
    }
}
