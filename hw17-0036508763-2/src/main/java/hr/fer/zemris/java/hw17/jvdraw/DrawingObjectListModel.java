package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.object.*;

import javax.swing.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DrawingObjectListModel extends AbstractListModel<String> {
    private DrawingModel drawingModel;

    public DrawingObjectListModel(DrawingModel drawingModel) {
        this.drawingModel = drawingModel;
        drawingModel.addDrawingModelListener(new DrawingModelListener() {
            @Override
            public void objectsAdded(DrawingModel source, int index0, int index1) {
                DrawingObjectListModel.this.fireIntervalAdded(source, index0, index1);
            }

            @Override
            public void objectsRemoved(DrawingModel source, int index0, int index1) {
                DrawingObjectListModel.this.fireIntervalRemoved(source, index0, index1);
            }

            @Override
            public void objectsChanged(DrawingModel source, int index0, int index1) {
                DrawingObjectListModel.this.fireContentsChanged(source, index0, index1);
            }
        });
    }

    @Override
    public int getSize() {
        return drawingModel.getSize();
    }

    @Override
    public String getElementAt(int index) {
        GeometricalObject geometricalObject = drawingModel.getObject(drawingModel.getSize() - index - 1);
        String message;
        if (geometricalObject instanceof Line) {
            Line line = (Line) geometricalObject;
            message = "Line(" + line.getxStart() + "," + line.getyStart() + ")-("
                    + line.getxEnd() + "," + line.getyEnd() + ")";
        } else if (geometricalObject instanceof Circle) {
            Circle circle = (Circle) geometricalObject;
            message = "Circle (" + circle.getxCoordinate() + "," + circle.getyCoordinate() + "), " + circle.getRadius();
        } else if (geometricalObject instanceof FilledCircle) {
            FilledCircle filledCircle = (FilledCircle) geometricalObject;
            message = "Filled filledCircle (" + filledCircle.getxCoordinate() + "," + filledCircle.getyCoordinate() + "), "
                    + filledCircle.getRadius() + ",#" + Integer.toHexString(filledCircle.getAreaColor().getRGB()).substring(2);
        } else if (geometricalObject instanceof FilledTriangle) {
            FilledTriangle filledTriangle = (FilledTriangle) geometricalObject;
            message = "Filled triangle (" + Integer.toHexString(filledTriangle.getOutlineColor().getRGB()).substring(2) + ", " + Integer.toHexString(filledTriangle.getFillColor().getRGB()).substring(2) + ")";
        } else {
            message = "Unsupported geometrical object";
        }

        return message;
    }
}
