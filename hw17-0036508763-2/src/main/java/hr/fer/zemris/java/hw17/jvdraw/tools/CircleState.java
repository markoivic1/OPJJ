package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.object.Circle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * State which represents circle.
 * It defines specific behaviour for drawing.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CircleState implements Tool {

    /**
     * Drawing model which will be used for registering new lines.
     */
    private DrawingModel drawingModel;
    /**
     * Provider for a circle's outline color
     */
    private IColorProvider colorProvider;
    /**
     * Canvas in which this circle will be drawn
     */
    private JDrawingCanvas drawingCanvas;
    /**
     * Center of a circle
     */
    private Point center;
    /**
     * Radius of a cricle
     */
    private int radius;

    /**
     * Constructor.
     * @param drawingModel Drawing model
     * @param colorProvider Provider for outline color
     * @param drawingCanvas Drawing canvas in which this will be drawn
     */
    public CircleState(DrawingModel drawingModel, IColorProvider colorProvider, JDrawingCanvas drawingCanvas) {
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
     * First click represents center of a circle.
     * Distance between first and second click will determine radius of a circle
     * @param e Mouse event
     */
    @Override
    public void mouseClicked(MouseEvent e) {
        if (center == null) {
            center = e.getPoint();
        } else {
            radius = (int) Math.round(Math.sqrt(Math.pow(center.x - e.getPoint().x, 2) + Math.pow(center.y - e.getPoint().y, 2)));
            if (radius == 0) {
                return;
            }
            drawingModel.add(new Circle(center.x, center.y, radius, colorProvider.getCurrentColor()));
            center = null;
            radius = 0;
        }
    }

    /**
     * Draws line between circle and point when mouse is moved.
     * @param e Mouse event
     */
    @Override
    public void mouseMoved(MouseEvent e) {
        if (center != null) {
            radius = (int) Math.round(Math.sqrt(Math.pow(center.x - e.getPoint().x, 2) + Math.pow(center.y - e.getPoint().y, 2)));
            drawingCanvas.repaint();
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Paints circle which is not yet stored.
     * @param g2d Graphics in which this method will draw.
     */
    @Override
    public void paint(Graphics2D g2d) {
        if (center != null) {
            g2d.setColor(colorProvider.getCurrentColor());
            g2d.drawOval(center.x - radius, center.y - radius, 2 * radius, 2 * radius);
        }
    }
}
