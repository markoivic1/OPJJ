package hr.fer.zemris.java.hw13.servlet;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.util.Rotation;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
/**
 * Draws pie chart for OS usage survey.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name="chart", urlPatterns ={"/reportImage"})
public class PieChartServlet extends HttpServlet {
    /**
     * Creates png image of a pie chart with predefined values.
     * @param req Request
     * @param resp Response
     * @throws ServletException Never thrown here
     * @throws IOException Thrown when getting or writing to output stream fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        OutputStream outputStream = resp.getOutputStream();
        PieDataset dataset = createDataset();
        JFreeChart chart = createChart(dataset, "OS usage");
        int width = 500;
        int height = 270;
        ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
    }

    /**
     * Creates dataset with predefined values
     * @return Returns dataset
     */
    private  PieDataset createDataset() {
        DefaultPieDataset result = new DefaultPieDataset();
        result.setValue("Linux", 29);
        result.setValue("Mac", 20);
        result.setValue("Windows", 51);
        return result;

    }

    /**
     * Creates pie chart with given data.
     * @param dataset Dataset which contains data which will be drawn
     * @param title Title of a chart
     * @return Returns initialized pie chart.
     */
    private JFreeChart createChart(PieDataset dataset, String title) {

        JFreeChart chart = ChartFactory.createPieChart3D(
                title,                  // chart title
                dataset,                // data
                true,                   // include legend
                true,
                false
        );

        PiePlot3D plot = (PiePlot3D) chart.getPlot();
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
        plot.setForegroundAlpha(0.5f);
        return chart;

    }
}
