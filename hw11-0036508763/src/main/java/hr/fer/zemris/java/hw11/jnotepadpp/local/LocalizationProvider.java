package hr.fer.zemris.java.hw11.jnotepadpp.local;

import java.util.Locale;
import java.util.ResourceBundle;

/**
 * Localization provider. Singleton class.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LocalizationProvider extends AbstractLocalizationProvider {
    /**
     * Current language.
     */
    private String language;
    /**
     * Resource bundle for a current language.
     */
    private ResourceBundle bundle;

    /**
     * Only instance of this class.
     */
    private static final LocalizationProvider INSTANCE = new LocalizationProvider();

    /**
     * Contructor.
     */
    private LocalizationProvider() {
        language = "en";
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.model.translation", locale);
    }

    @Override
    public String getString(String string) {

        return bundle.getString(string);
    }

    /**
     * Sets language to a given value.
     * @param language New language.
     */
    public void setLanguage(String language) {
        Locale locale = Locale.forLanguageTag(language);
        bundle = ResourceBundle.getBundle("hr.fer.zemris.java.hw11.jnotepadpp.model.translation", locale);
        this.language = language;
        fire();
    }

    @Override
    public String getCurrentLanguage() {
        return language;
    }

    /**
     * Getter for instance.
     * @return Returns instance.
     */
    public static LocalizationProvider getInstance() {
        return INSTANCE;
    }
}
