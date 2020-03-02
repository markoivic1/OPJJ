package hr.fer.zemris.java.hw17.jvdraw.object;

import hr.fer.zemris.java.hw17.jvdraw.object.editor.FilledTriangleEditor;
import hr.fer.zemris.java.hw17.jvdraw.object.editor.GeometricalObjectEditor;

import java.awt.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FilledTriangle extends GeometricalObject {
    private Point first;
    private Point second;
    private Point third;
    private Color outlineColor;
    private Color fillColor;

    public FilledTriangle(Point first, Point second, Point third, Color outlineColor, Color fillColor) {
        this.first = first;
        this.second = second;
        this.third = third;
        this.outlineColor = outlineColor;
        this.fillColor = fillColor;
    }

    @Override
    public String toString() {
        return "FTRIANGLE " + first.x + " " + first.y + " " + second.x + " " + second.y + " " +
                third.x + " " + third.y + " " +
                outlineColor.getRed() + " " + outlineColor.getGreen() + " " + outlineColor.getBlue() +
                " " + fillColor.getRed() + " " + fillColor.getGreen() + " " + fillColor.getBlue();
    }

    public Color getOutlineColor() {
        return outlineColor;
    }

    public Color getFillColor() {
        return fillColor;
    }

    public void setOutlineColor(Color outlineColor) {
        this.outlineColor = outlineColor;
    }

    public void setFillColor(Color fillColor) {
        this.fillColor = fillColor;
    }

    public Point getFirst() {
        return first;
    }

    public Point getSecond() {
        return second;
    }

    public Point getThird() {
        return third;
    }

    @Override
    public void accept(GeometricalObjectVisitor v) {
        v.visit(this);
    }

    @Override
    public GeometricalObjectEditor createGeometricalObjectEditor() {
        //  return new FilledCircleEditor(this);
        return new FilledTriangleEditor(this);
    }
}
