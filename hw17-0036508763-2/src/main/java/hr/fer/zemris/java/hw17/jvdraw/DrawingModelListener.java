package hr.fer.zemris.java.hw17.jvdraw;

/**
 * Models listener for drawing model.
 * It will execute specific method(s) depending on an event.
 * @author Marko Ivić
 * @version 1.0.0.
 */
public interface DrawingModelListener {
    /**
     * Executed when objects were added to internal collection.
     * @param source Drawing model which sent notification
     * @param index0 Starting index which was affected by change.
     * @param index1 Ending index which was affected by change.
     */
    public void objectsAdded(DrawingModel source, int index0, int index1);
    /**
     * Executed when objects were removed from internal collection.
     * @param source Drawing model which sent notification
     * @param index0 Starting index which was affected by change.
     * @param index1 Ending index which was affected by change.
     */
    public void objectsRemoved(DrawingModel source, int index0, int index1);
    /**
     * Executed when objects were change in internal collection.
     * @param source Drawing model which sent notification
     * @param index0 Starting index which was affected by change.
     * @param index1 Ending index which was affected by change.
     */
    public void objectsChanged(DrawingModel source, int index0, int index1);
}