package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject;

/**
 * Drawing model which is used for storing {@link GeometricalObject} which will be drawn.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface DrawingModel {
    /**
     * Returns number of currently stored {@link GeometricalObject}s.
     *
     * @return Returns size.
     */
    public int getSize();

    /**
     * Gets {@link GeometricalObject} at a given index.
     *
     * @param index Index from which an object is retrieved.
     * @return Returns object at a given index.
     */
    public GeometricalObject getObject(int index);

    /**
     * Adds given object to a internal collection.
     *
     * @param object Object which stored.
     */
    public void add(GeometricalObject object);

    /**
     * Removes given object from a collection
     * @param object Object which will be removed
     */
    public void remove(GeometricalObject object);

    /**
     * Moves given object for the given offset positions.
     * @param object Object which will be moved
     * @param offset Number of positions
     */
    public void changeOrder(GeometricalObject object, int offset);

    /**
     * Returns index at which given object is stored.
     * Returns -1 if objets doesn't exist.
     * @param object Object which is searched for.
     * @return Returns index of an object if found, -1 if not found.
     */
    public int indexOf(GeometricalObject object);

    /**
     * Clear internal collection
     */
    public void clear();

    /**
     * Clear modification flag.
     */
    public void clearModifiedFlag();

    /**
     * Checks whether this file has been modified.
     * @return
     */
    public boolean isModified();

    /**
     * Adds given listener to inner collection of listeners.
     * @param l Listener which will be added
     */
    public void addDrawingModelListener(DrawingModelListener l);

    /**
     * Removes given listener from inner colleciton of listeners.
     * @param l Listener which will be removed.
     */
    public void removeDrawingModelListener(DrawingModelListener l);
}
