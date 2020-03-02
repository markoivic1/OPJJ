package hr.fer.zemris.lsystems.impl.demo;

import hr.fer.zemris.lsystems.gui.LSystemViewer;
import hr.fer.zemris.lsystems.impl.LSystemBuilderImpl;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Glavni3 {
    public static void main(String[] args) {
        LSystemViewer.showLSystem(LSystemBuilderImpl::new);
    }
}
