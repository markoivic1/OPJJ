package hr.fer.zemris.java.hw11.jnotepadpp.local;

/**
 * Bridge used in localization design.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LocalizationProviderBridge extends AbstractLocalizationProvider {
    /**
     * Indicates that provider is connected.
     */
    private boolean connected;
    /**
     * Provider.
     */
    private ILocalizationProvider provider;
    /**
     * Listener registered in provider.
     */
    private ILocalizationListener listener;

    /**
     * Constructor.
     * @param provider Provider.
     */
    public LocalizationProviderBridge(ILocalizationProvider provider) {
        this.provider = provider;
    }

    /**
     * Disconnects this class from provider.
     */
    public void disconnect() {
        if (!connected) {
            return;
        }
        connected = false;
        provider.removeLocalizationListener(listener);
        listener = null;
    }

    /**
     * Connects this class to the provider.
     */
    public void connect() {
        if (connected) {
            return;
        }
        connected = true;
        listener = this::fire;
        provider.addLocalizationListener(listener);
    }

    @Override
    public String getString(String string) {
        return provider.getString(string);
    }

    @Override
    public String getCurrentLanguage() {
        return provider.getCurrentLanguage();
    }
}
