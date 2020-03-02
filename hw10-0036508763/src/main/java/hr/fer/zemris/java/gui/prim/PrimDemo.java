package hr.fer.zemris.java.gui.prim;

import javax.swing.*;
import java.awt.*;

/**
 * JFrame which is used to display primes in two JScrollPanes.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PrimDemo extends JFrame {
    /**
     * Constructor.
     */
    public PrimDemo() {
        setLocation(100, 100);
        setSize(300, 300);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initGUI();
    }

    /**
     * Initializes GUI.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new BorderLayout());

        PrimListModel<Integer> model = new PrimListModel<>();

        JList<Integer> list1 = new JList<>(model);
        JList<Integer> list2 = new JList<>(model);

        JPanel scrollPane = new JPanel(new GridLayout(1, 2));

        scrollPane.add(new JScrollPane(list1));
        scrollPane.add(new JScrollPane(list2));

        cp.add(scrollPane, BorderLayout.CENTER);

        JButton next = new JButton("Next prime");
        next.addActionListener(e -> model.next());
        cp.add(next, BorderLayout.SOUTH);
    }

    /**
     * Main runs this class.
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new PrimDemo();
            frame.setVisible(true);
        });
    }
}
