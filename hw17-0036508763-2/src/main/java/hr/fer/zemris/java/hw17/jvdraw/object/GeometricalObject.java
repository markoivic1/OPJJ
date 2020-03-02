package hr.fer.zemris.java.hw17.jvdraw.object;

import hr.fer.zemris.java.hw17.jvdraw.object.editor.GeometricalObjectEditor;

/**
 * Models geometrical object such as a line, circle or filled circle.
 * Supports Listener Design pattern for adding and removing listeners.
 * Supports Visitor design pattern.
 */
public abstract class GeometricalObject {

    /**
     * Visitor design pattern.
     * Method accept executes proper code for a given visitor.
     * @param v Visitor
     */
    public abstract void accept(GeometricalObjectVisitor v);

    /**
     * Register the given listener.
     * @param l Listener
     */
    public void addGeometricalObjectListener(GeometricalObjectListener l) {
    }

    /**
     * Remove the given listener
     * @param l Listener
     */
    public void removeGeometricalObjectListener(GeometricalObjectListener l) {
    }

    /**
     * Creates an editor for itself.
     * @return returns editor.
     */
    public abstract GeometricalObjectEditor createGeometricalObjectEditor();
}