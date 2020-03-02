package hr.fer.zemris.java.hw17.jvdraw;

import hr.fer.zemris.java.hw17.jvdraw.object.GeometricalObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of drawing model.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DrawingModelImpl implements DrawingModel {

    /**
     * List of geometrical objects.
     */
    private List<GeometricalObject> geometricalObjects;
    /**
     * List of listeners.
     */
    private List<DrawingModelListener> listeners;
    /**
     * Modification flag
     */
    private boolean modified;

    /**
     * Constructor.
     */
    public DrawingModelImpl() {
        geometricalObjects = new ArrayList<>();
        listeners = new ArrayList<>();
        modified = false;
    }

    @Override
    public int getSize() {
        return geometricalObjects.size();
    }

    @Override
    public GeometricalObject getObject(int index) {
        return geometricalObjects.get(index);
    }

    @Override
    public void add(GeometricalObject object) {
        modified = true;
        geometricalObjects.add(object);
        for (DrawingModelListener listener : listeners) {
            listener.objectsAdded(this, geometricalObjects.size() - 1, geometricalObjects.size() - 1);
            listener.objectsChanged(this, geometricalObjects.size() - 1, geometricalObjects.size() - 1);
        }
    }

    @Override
    public void remove(GeometricalObject object) {
        int index = geometricalObjects.indexOf(object);
        if (index < 0) {
            return;
        }
        modified = true;
        geometricalObjects.remove(object);
        for (DrawingModelListener listener : listeners) {
            listener.objectsRemoved(this, index, geometricalObjects.size()== 0 ? 0 : geometricalObjects.size() - 1);
            listener.objectsChanged(this, index, geometricalObjects.size()== 0 ? 0 : geometricalObjects.size() - 1);
        }
    }

    @Override
    public void changeOrder(GeometricalObject object, int offset) {
        int index = geometricalObjects.indexOf(object);
        if (index < 0) {
            return;
        }
        if (index + offset < 0 || index + offset >= geometricalObjects.size()) {
            return;
        }
        modified = true;
        geometricalObjects.remove(object);
        geometricalObjects.add(index + offset, object);

        for (DrawingModelListener listener : listeners) {
            listener.objectsChanged(this, index, geometricalObjects.size() - 1);
        }
    }

    @Override
    public int indexOf(GeometricalObject object) {
        return geometricalObjects.indexOf(object);
    }

    @Override
    public void clear() {
        clearModifiedFlag();
        geometricalObjects = new ArrayList<>();
    }

    @Override
    public void clearModifiedFlag() {
        modified = false;
    }

    @Override
    public boolean isModified() {
        return modified;
    }

    @Override
    public void addDrawingModelListener(DrawingModelListener l) {
        listeners.add(l);
    }

    @Override
    public void removeDrawingModelListener(DrawingModelListener l) {
        listeners.remove(l);
    }
}
