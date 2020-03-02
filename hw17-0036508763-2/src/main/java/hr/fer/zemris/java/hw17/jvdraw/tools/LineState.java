package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.object.Line;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Models state for a line.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LineState implements Tool {

    /**
     * Drawing model which will be used for storing {@link hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject} made with date from this class.
     */
    private DrawingModel drawingModel;
    /**
     * Color of a line
     */
    private IColorProvider colorProvider;
    /**
     * Canvas in which this line will be drawn
     */
    private JDrawingCanvas drawingCanvas;
    /**
     * First point of a line
     */
    private Point firstPoint;
    /**
     * Second point of a line
     */
    private Point secondPoint;

    /**
     *
     * @param drawingModel  Drawing model
     * @param colorProvider Provider for line color
     * @param drawingCanvas canvas in which this will be drawn.
     */
    public LineState(DrawingModel drawingModel, IColorProvider colorProvider, JDrawingCanvas drawingCanvas) {
        this.drawingModel = drawingModel;
        this.colorProvider = colorProvider;
        this.drawingCanvas = drawingCanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    /**
     * First click save first coordinate.
     * Second click adds line to {@link hr.fer.zemris.java.hw17.jvdraw.DrawingObjectListModel}
     * After second click points are deleted and ready for drawing new line.
     * @param e Mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (firstPoint == null) {
            firstPoint = e.getPoint();
        } else {
            secondPoint = e.getPoint();
            if (firstPoint.x - secondPoint.x == 0 && firstPoint.y - secondPoint.y == 0) {
                return;
            }
            drawingModel.add(new Line(colorProvider.getCurrentColor(), firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y));
            firstPoint = null;
            secondPoint = null;
        }
    }

    /**
     * Draws lines between first point and point at which mouse currently points.
     * @param e Mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (firstPoint != null) {
            secondPoint = e.getPoint();
            drawingCanvas.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Draws line not yet stored in {@link DrawingModel}
     * @param g2d Graphics in which this method will draw.
     */
    @Override
    public void paint(Graphics2D g2d) {
        if (firstPoint != null && secondPoint != null) {
            g2d.setColor(colorProvider.getCurrentColor());
            g2d.drawLine(firstPoint.x, firstPoint.y, secondPoint.x, secondPoint.y);
        }
    }
}
