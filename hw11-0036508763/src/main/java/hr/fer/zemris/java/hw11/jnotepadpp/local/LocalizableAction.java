package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * Defines localizable action.
 * @author Marko Ivić
 * @version 1.0.0
 */
public abstract class LocalizableAction extends AbstractAction {
    /**
     * Key used to get translation from ILocalizationProvider
     */
    private String key;
    /**
     * Provider
     */
    private ILocalizationProvider lp;
    /**
     * Translation.
     */
    private String translation;

    /**
     * Contructor.
     * @param key Key
     * @param lp ILocalizationProvider
     */
    public LocalizableAction(String key, ILocalizationProvider lp) {
        this.key = key;
        this.lp = lp;
        translation = lp.getString(key);
        putValue(NAME, translation);

        lp.addLocalizationListener(() -> {
            firePropertyChange(AbstractAction.NAME, translation, translation = lp.getString(key) );
            putValue(NAME, translation);
        });
    }
}
