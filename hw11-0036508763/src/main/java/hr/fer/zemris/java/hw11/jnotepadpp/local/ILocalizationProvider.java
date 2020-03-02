package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Provider interface.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface ILocalizationProvider {
    /**
     * Adds localization listener.
     * @param listener Listener which will be added.
     */
    void addLocalizationListener(ILocalizationListener listener);

    /**
     * Remove localization listener.
     * @param listener Listener which will be removed.
     */
    void removeLocalizationListener(ILocalizationListener listener);

    /**
     * Gets the string
     * @param string String which will be used as a key.
     * @return Returns string for a proper key.
     */
    String getString(String string);

    /**
     * Getter for current language.
     * @return Returns current language.
     */
    String getCurrentLanguage();
}
