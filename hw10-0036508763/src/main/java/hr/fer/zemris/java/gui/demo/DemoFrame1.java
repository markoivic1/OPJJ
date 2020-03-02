package hr.fer.zemris.java.gui.demo;

import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;

/**
 * Class given as an example in problem description.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DemoFrame1 extends JFrame{
    /**
     * Constructor.
     */
    public DemoFrame1() {
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setSize(500, 500);
        initGUI();
        //pack();
    }

    /**
     * Initializes GUI
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(3));
        cp.add(l("tekst 1"), new RCPosition(1, 1));
        cp.add(l("tekst 2"), new RCPosition(2, 3));
        cp.add(l("tekst stvarno najdulji"), new RCPosition(2, 7));
        cp.add(l("tekst kraći"), new RCPosition(4, 2));
        cp.add(l("tekst srednji"), new RCPosition(4, 5));
        cp.add(l("tekst"), new RCPosition(4, 7));
        cp.add(l("tekst"), new RCPosition(5,2)); //TODO dodano naknadno
        cp.add(l("test"), new RCPosition(1,6));
        cp.add(l("test"), new RCPosition(2,6));
        cp.add(l("Test"), new RCPosition(2,5));
        cp.add(l("Test"), new RCPosition(3,2));
    }

    /**
     * Method that generates labels with a given string.
     * @param text Text which will be written in a label.
     * @return Returns JLabel which has given text written.
     */
    private JLabel l(String text) {
        JLabel l = new JLabel(text);
        l.setBackground(Color.ORANGE);
        l.setOpaque(true);
        return l;
    }

    /**
     * Main executes frame defined here.
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new DemoFrame1().setVisible(true);
        });
    }
}
