package hr.fer.zemris.java.hw17.jvdraw.color;

import javax.swing.*;

/**
 * Class used for displaying colors from providers given in the constructor as a JLabel.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ColorJLabel extends JLabel {

    /**
     * Constructor which takes providers for foreground and background colors.
     * @param fgColorProvider Foreground color provider.
     * @param bgColorProvider Background color provider.
     */
    public ColorJLabel(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {

        initListeners(fgColorProvider, bgColorProvider);
    }

    /**
     * Initializes listeners which will set new JLabel text using data from a given color providers
     * @param fgColorProvider Foreground color provider.
     * @param bgColorProvider Background color provider.
     */
    private void initListeners(IColorProvider fgColorProvider, IColorProvider bgColorProvider) {
        String text = "Foreground color: (" +
                bgColorProvider.getCurrentColor().getRed() + ", " +
                bgColorProvider.getCurrentColor().getGreen() + ", " +
                bgColorProvider.getCurrentColor().getBlue() + ")" +
                ", background color: (" +
                bgColorProvider.getCurrentColor().getRed() + ", " +
                bgColorProvider.getCurrentColor().getGreen() + ", " +
                bgColorProvider.getCurrentColor().getBlue() + ")";
        ColorJLabel.this.setText(text);

        fgColorProvider.addColorChangeListener((source, oldColor, newColor) -> {
            String newText = "Foreground color: (" +
                    newColor.getRed() + ", " +
                    newColor.getGreen() + ", " +
                    newColor.getBlue() + ")" +
                    ", background color: (" +
                    bgColorProvider.getCurrentColor().getRed() + ", " +
                    bgColorProvider.getCurrentColor().getGreen() + ", " +
                    bgColorProvider.getCurrentColor().getBlue() + ")";
            ColorJLabel.this.setText(newText);
        });
        bgColorProvider.addColorChangeListener((source, oldColor, newColor) -> {
            String newText = "Foreground color: (" +
                    fgColorProvider.getCurrentColor().getRed() + ", " +
                    fgColorProvider.getCurrentColor().getGreen() + ", " +
                    fgColorProvider.getCurrentColor().getBlue() + ")" +
                    ", background color: (" +
                    newColor.getRed() + ", " +
                    newColor.getGreen() + ", " +
                    newColor.getBlue() + ")";
            ColorJLabel.this.setText(newText);
        });
    }
}
