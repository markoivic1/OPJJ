package hr.fer.zemris.java.gui.charts;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class loads data from a file and shows its visaul representation.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class BarChartDemo extends JFrame {

    /**
     * Model of a {@link BarChart}.
     */
    private BarChart model;
    /**
     * Path to the file from which the data will be loaded.
     */
    private Path path;

    /**
     * Constructor which initializes GUI.
     * @param model Model of a {@link BarChart}.
     * @param path Path to the file from which the data will be loaded.
     */
    public BarChartDemo(BarChart model, Path path) {
        super();
        this.model = model;
        this.path = path;
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Prozor1");
        setLocation(100, 100);
        setSize(750, 750);
        initGUI();
    }

    /**
     * Method which initializes GUI.
     */
    private void initGUI() {
        getContentPane().setLayout(new BorderLayout());
        JComponent barChart = new BarChartComponent(model);
        getContentPane().add(barChart, BorderLayout.CENTER);
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout());
        topPanel.add(new JLabel(path.toAbsolutePath().toString()));
        getContentPane().add(topPanel, BorderLayout.NORTH);
    }

    /**
     * Main method which creates new thread and runs the BarChartDemo.
     * @param args Path to a file from which the data will be used.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Please enter path to the data.");
            return;
        }
        Path path = Paths.get(args[0]);
        BarChart model;
        try (BufferedReader is = new BufferedReader(new InputStreamReader(new BufferedInputStream(Files.newInputStream(path))))) {
            String xDescription = is.readLine();
            String yDescription = is.readLine();
            List<XYValue> values = Arrays.stream(is.readLine().split(" +")).map(p -> new XYValue(Integer.parseInt(p.split(",")[0]), Integer.parseInt(p.split(",")[1]))).collect(Collectors.toList());
            int yMin = Integer.parseInt(is.readLine());
            int yMax = Integer.parseInt(is.readLine());
            int spacing = Integer.parseInt(is.readLine());
            model = new BarChart(values, xDescription, yDescription, yMin, yMax, spacing);
        } catch (IOException e) {
            System.out.println("Invalid path.");
            return;
        }

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                BarChartDemo prozor = new BarChartDemo(model, path);
                prozor.setVisible(true);
            }
        });
    }
}
