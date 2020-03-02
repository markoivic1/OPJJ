package hr.fer.zemris.java.hw17.jvdraw.object;

import java.awt.*;

/**
 * Visitor which will get dimension of a bounding box for {@link GeometricalObject}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class GeometricalObjectBBCalculator implements GeometricalObjectVisitor {

    /**
     * Flag which indicates first object
     */
    private boolean first = true;
    /**
     * x coordinate form which the bounding box starts.
     */
    private int xStart;
    /**
     * y coordinate form which the bounding box starts.
     */
    private int yStart;
    /**
     * x coordinate at which the bounding box ends.
     */
    private int xEnd;
    /**
     * y coordinate at which the bounding box ends.
     */
    private int yEnd;

    /**
     * Calculates bounds for a given Line.
     * @param line Line
     */
    @Override
    public void visit(Line line) {
        calculateBounds(line.getxStart(), line.getyStart(), line.getxEnd(), line.getyEnd());
    }

    /**
     * Calculates bounding box of a given circle.
     * @param circle Circle
     */
    @Override
    public void visit(Circle circle) {
        calculateBounds(circle.getxStart(), circle.getyStart(), circle.getxEnd(), circle.getyEnd());

    }

    /**
     * Calculates bounding box of a given filled circle.
     * @param filledCircle Circle
     */
    @Override
    public void visit(FilledCircle filledCircle) {
        calculateBounds(filledCircle.getxStart(), filledCircle.getyStart(), filledCircle.getxEnd(), filledCircle.getyEnd());
    }

    @Override
    public void visit(FilledTriangle filledTriangle) {
        int xStart = Math.min(Math.min(filledTriangle.getFirst().x, filledTriangle.getSecond().x), filledTriangle.getThird().x);
        int yStart = Math.min(Math.min(filledTriangle.getFirst().y, filledTriangle.getSecond().y), filledTriangle.getThird().y);


        int xEnd = Math.max(Math.max(filledTriangle.getFirst().x, filledTriangle.getSecond().x), filledTriangle.getThird().x);
        int yEnd = Math.max(Math.max(filledTriangle.getFirst().y, filledTriangle.getSecond().y), filledTriangle.getThird().y);

        calculateBounds(xStart, yStart, xEnd, yEnd);
    }

    /**
     * Method which returns bounding box as a rectangle
     * @return rectangle containing starting and ending coordinates.
     */
    public Rectangle getBoundingBox() {
        return new Rectangle(xStart, yStart, xEnd - xStart, yEnd - yStart);
    }

    /**
     * Calculates coordinates for given values.
     */
    private void calculateBounds(int xStart, int yStart, int xEnd, int yEnd) {
        if (first) {
            this.xStart = Math.min(xStart, xEnd);
            this.yStart = Math.min(yStart, yEnd);
            this.xEnd = Math.max(xEnd, xStart);
            this.yEnd = Math.max(yEnd, yStart);
            first = false;
            return;
        }
        this.xStart = Math.min(Math.min(xStart, this.xStart), xEnd);
        this.yStart = Math.min(Math.min(yStart, this.yStart), yEnd);
        this.xEnd = Math.max(Math.max(xEnd, this.xEnd), xStart);
        this.yEnd = Math.max(Math.max(yEnd, this.yEnd), yStart);
    }
}
