package hr.fer.zemris.java.gui.layouts;

import org.junit.jupiter.api.Test;

import javax.naming.ldap.UnsolicitedNotification;
import javax.swing.*;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class CalcLayoutTest {

    @Test
    void testConstraints() {
        JFrame jFrame = new JFrame();
        jFrame.getContentPane().setLayout(new CalcLayout());
        assertThrows(UnsupportedOperationException.class, () -> jFrame.add(new Label("test"), new RCPosition(-1, 2)));
        assertThrows(UnsupportedOperationException.class, () -> jFrame.add(new Label("test"), new RCPosition(1, 3)));
        assertThrows(UnsupportedOperationException.class, () -> jFrame.add(new Label("test"), new RCPosition(8, 4)));
        assertThrows(UnsupportedOperationException.class, () -> jFrame.add(new Label("test"), new RCPosition(3, 8)));
        jFrame.add(new Label("test"), new RCPosition(3, 3));
        assertThrows(UnsupportedOperationException.class, () -> jFrame.add(new Label("test"), new RCPosition(3, 3)));
    }

    @Test
    void testDimension1() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(10, 30));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(20, 15));
        p.add(l1, new RCPosition(2, 2));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(dim.width, 152);
        assertEquals(dim.height, 158);
    }

    @Test
    void testDimension2() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        Dimension dim = p.getPreferredSize();
        assertEquals(dim.width, 152);
        assertEquals(dim.height, 158);
    }

    @Test
    void testRemove() {
        JPanel p = new JPanel(new CalcLayout(2));
        JLabel l1 = new JLabel("");
        l1.setPreferredSize(new Dimension(108, 15));
        JLabel l2 = new JLabel("");
        l2.setPreferredSize(new Dimension(16, 30));
        p.add(l1, new RCPosition(1, 1));
        p.add(l2, new RCPosition(3, 3));
        p.remove(l2);
    }
}