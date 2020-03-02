package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObjectPainter;
import hr.fer.zemris.java.hw17.jvdraw.tools.Tool;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.function.Supplier;

/**
 * Canvas used for drawing {@link hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject}s.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class JDrawingCanvas extends JComponent {

    /**
     * Objects which will be drawn are stored here.
     */
    private DrawingModel drawingModel;
    /**
     * Used for drawing {@link hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject}
     */
    private Graphics2D graphics2D;
    /**
     * Supplier for current state
     */
    private Supplier<Tool> supplier;

    /**
     * Constructor.
     * @param supplier Supplier for current state.
     * @param drawingModel Drawing model which contains objects which will be drawn
     */
    public JDrawingCanvas(Supplier<Tool> supplier, DrawingModel drawingModel) {
        this.drawingModel = drawingModel;
        this.supplier = supplier;
        initListeners();
    }

    /**
     * Initializes listeners.
     * Redirects mouse listeners to current state.
     * Adds listeners for repainting canvas when objects in drawing model are changed.
     */
    private void initListeners() {
        this.drawingModel.addDrawingModelListener(new DrawingModelListener() {
            @Override
            public void objectsAdded(DrawingModel source, int index0, int index1) {
                JDrawingCanvas.this.repaint();
                supplier.get().paint(graphics2D);
            }

            @Override
            public void objectsRemoved(DrawingModel source, int index0, int index1) {
                JDrawingCanvas.this.repaint();
                supplier.get().paint(graphics2D);
            }

            @Override
            public void objectsChanged(DrawingModel source, int index0, int index1) {
                JDrawingCanvas.this.repaint();
                supplier.get().paint(graphics2D);
            }
        });
        this.addMouseMotionListener(new MouseMotionListener() {
            @Override
            public void mouseDragged(MouseEvent e) {
                supplier.get().mouseDragged(e);
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                supplier.get().mouseMoved(e);
            }
        });
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                supplier.get().mouseClicked(e);
            }

            @Override
            public void mousePressed(MouseEvent e) {
                supplier.get().mousePressed(e);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                supplier.get().mouseReleased(e);
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                //
            }

            @Override
            public void mouseExited(MouseEvent e) {
                //
            }
        });
    }

    /**
     * Draws canvas with all of its stored objects and redirects paint request to the supplier.
     * @param g Graphics
     */
    @Override
    public void paintComponent(Graphics g) {
        graphics2D = (Graphics2D) g;
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, getWidth(), getHeight());
        GeometricalObjectPainter geometricalObjectPainter = new GeometricalObjectPainter(graphics2D);
        for (int i = 0; i < drawingModel.getSize(); i++) {
            drawingModel.getObject(i).accept(geometricalObjectPainter);
        }
        supplier.get().paint(graphics2D);
    }
}
