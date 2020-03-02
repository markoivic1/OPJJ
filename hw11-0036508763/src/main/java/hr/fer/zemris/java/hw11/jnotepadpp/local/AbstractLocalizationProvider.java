package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class which has notifies registered listeners.
 * @author Marko Ivić
 * @version 1.0.0
 */
public abstract class AbstractLocalizationProvider implements ILocalizationProvider {
    /**
     * List of listeners.
     */
    private List<ILocalizationListener> listeners;

    /**
     * Constructor.
     */
    public AbstractLocalizationProvider() {
        listeners = new ArrayList<>();
    }

    /**
     * Method which is used to notify listeners.
     */
    public void fire() {
        for (ILocalizationListener listener : listeners) {
            listener.localizationChanged();
        }
    }

    /**
     * Adds given listener
     * @param listener Listener which will be added.
     */
    @Override
    public void addLocalizationListener(ILocalizationListener listener) {
        listeners.add(listener);
    }

    /**
     * Removes given listener.
     * @param listener Listener which will be removed.
     */
    @Override
    public void removeLocalizationListener(ILocalizationListener listener) {
        listeners.remove(listener);
    }
}
