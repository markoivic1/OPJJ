package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;

/**
 * Class which is wrapper for JLabel which adds support for i18n.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LJLabel extends JLabel {
    /**
     * Key for a provider
     */
    private String key;
    /**
     * Provider of translation,
     */
    private ILocalizationProvider provider;
    /**
     * Translation.
     */
    private String translation;

    /**
     * Constructor.
     * @param key Key used for this an instance of this class.
     * @param provider Provider for translation.
     */
    public LJLabel(String key, ILocalizationProvider provider) {
        this.key = key;
        this.provider = provider;
        translation = provider.getString(key);
        setText(translation);
        provider.addLocalizationListener(() -> updateLabel());
    }

    /**
     * Adds text to the translation.
     * @param text
     */
    public void addAdditionalText(String text) {
        setText(translation + text);
    }

    /**
     * Executed when provider fires its listeners.
     */
    private void updateLabel() {
        translation = provider.getString(key);
        setText(translation);
    }
}
