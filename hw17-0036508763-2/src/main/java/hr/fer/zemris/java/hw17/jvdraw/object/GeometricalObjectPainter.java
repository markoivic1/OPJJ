package hr.fer.zemris.java.hw17.jvdraw.object;

import java.awt.*;
import java.util.ArrayList;

/**
 * Visitor which will draw {@link GeometricalObject} on Graphics2D.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class GeometricalObjectPainter implements GeometricalObjectVisitor {
    /**
     * Graphics2d
     */
    private Graphics2D graphics2D;

    /**
     * Constructor
     * @param g2d Will be used in drawing {@link GeometricalObject}.
     */
    public GeometricalObjectPainter(Graphics2D g2d) {
        this.graphics2D = g2d;
    }

    /**
     * Draws given line.
     * @param line Line which will be drawn.
     */
    @Override
    public void visit(Line line) {
        graphics2D.setColor(line.getColor());
        graphics2D.drawLine(line.getxStart(), line.getyStart(), line.getxEnd(), line.getyEnd());
    }

    /**
     * Draws given circle.
     * @param circle Circle which will be drawn.
     */
    @Override
    public void visit(Circle circle) {
        graphics2D.setColor(circle.getColor());
        graphics2D.drawOval(circle.getxCoordinate() - circle.getRadius(), circle.getyCoordinate() - circle.getRadius(), 2*circle.getRadius(), 2*circle.getRadius());
    }

    @Override
    public void visit(FilledTriangle filledTriangle) {
        graphics2D.setColor(filledTriangle.getFillColor());
        int[] xCoor = new int[3];
        xCoor[0] = filledTriangle.getFirst().x;
        xCoor[1] = filledTriangle.getSecond().x;
        xCoor[2] = filledTriangle.getThird().x;

        int[] yCoor = new int[3];
        yCoor[0] = filledTriangle.getFirst().y;
        yCoor[1] = filledTriangle.getSecond().y;
        yCoor[2] = filledTriangle.getThird().y;

        graphics2D.fillPolygon(xCoor, yCoor, 3);

        graphics2D.setColor(filledTriangle.getOutlineColor());

        graphics2D.drawPolygon(xCoor, yCoor, 3);
    }

    /**
     * Draws given filled circle.
     * @param filledCircle Filled circle which will be drawn.
     */
    @Override
    public void visit(FilledCircle filledCircle) {
        graphics2D.setColor(filledCircle.getAreaColor());
        graphics2D.fillOval(filledCircle.getxCoordinate() - filledCircle.getRadius(), filledCircle.getyCoordinate() - filledCircle.getRadius(), 2*filledCircle.getRadius(), 2*filledCircle.getRadius());

        graphics2D.setColor(filledCircle.getOutlineColor());
        graphics2D.drawOval(filledCircle.getxCoordinate() - filledCircle.getRadius(), filledCircle.getyCoordinate() - filledCircle.getRadius(), 2*filledCircle.getRadius(), 2*filledCircle.getRadius());
    }
}
