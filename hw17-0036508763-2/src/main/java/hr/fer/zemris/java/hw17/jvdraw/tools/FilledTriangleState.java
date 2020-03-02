package hr.fer.zemris.java.hw17.jvdraw.tools;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.JDrawingCanvas;
import hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider;
import hr.fer.zemris.java.hw17.jvdraw.object.FilledTriangle;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FilledTriangleState implements Tool {
    private Point first;
    private Point second;
    private Point third;

    /**
     * Provider for area color
     */
    private IColorProvider bgProvider;
    /**
     * Provider for outline color
     */
    private IColorProvider fgProvider;
    /**
     * Drawing model which will be used for registering new lines.
     */
    private DrawingModel drawingModel;
    /**
     * Canvas in which this circle will be drawn
     */
    private JDrawingCanvas drawingCanvas;


    public FilledTriangleState(DrawingModel drawingModel, IColorProvider fgProvider, hr.fer.zemris.java.hw17.jvdraw.color.IColorProvider bgProvider, JDrawingCanvas drawingCanvas) {
        this.drawingModel = drawingModel;
        this.fgProvider = fgProvider;
        this.bgProvider = bgProvider;
        this.drawingCanvas = drawingCanvas;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (first == null) {
            first = e.getPoint();
        } else if (second == null) {
            second = e.getPoint();
        } else {
            third = e.getPoint();
            drawingModel.add(new FilledTriangle(first, second, third, fgProvider.getCurrentColor(), bgProvider.getCurrentColor()));
            first = null;
            second = null;
            third = null;
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    @Override
    public void paint(Graphics2D g2d) {

    }
}
