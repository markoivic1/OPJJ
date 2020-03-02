package hr.fer.zemris.java.hw17.jvdraw.object;

/**
 * Interface which models listener for changes in {@link GeometricalObject}.
 * @author Marko Ivić
 * @version 1.0.0.
 */
public interface GeometricalObjectListener {
    /**
     * Method which will be executed when listeners are notified.
     * @param o {@link GeometricalObject}
     */
    public void geometricalObjectChanged(GeometricalObject o);
}