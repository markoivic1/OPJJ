package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.LSystemBuilderProvider;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.gui.LSystemViewer;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class LSystemBuilderImplTest {

    @Test
    void generateTest() {
        LSystemBuilderImpl builder = new LSystemBuilderImpl();
        builder.setAxiom("F").registerProduction('F', "F+F--F+F");
        assertEquals(builder.build().generate(0), "F");
        assertEquals(builder.build().generate(1), "F+F--F+F");
        assertEquals(builder.build().generate(2), "F+F--F+F+F+F--F+F--F+F--F+F+F+F--F+F");
    }


}