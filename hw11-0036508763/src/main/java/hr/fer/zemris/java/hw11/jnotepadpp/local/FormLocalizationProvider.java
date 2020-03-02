package hr.fer.zemris.java.hw11.jnotepadpp.local;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Class which provides form localization.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FormLocalizationProvider extends LocalizationProviderBridge {
    /**
     * Provider
     */
    private ILocalizationProvider provider;
    /**
     * Frame
     */
    private JFrame frame;

    /**
     * Constructor.
     * @param provider frame which is used to initializes inherited class.
     * @param frame Frame which will used to register listeners.
     */
    public FormLocalizationProvider(ILocalizationProvider provider, JFrame frame) {
        super(provider);
        this.provider = provider;
        this.frame = frame;
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowOpened(WindowEvent e) {
                connect();
            }

            @Override
            public void windowClosed(WindowEvent e) {
                disconnect();
            }
        });
    }
}
