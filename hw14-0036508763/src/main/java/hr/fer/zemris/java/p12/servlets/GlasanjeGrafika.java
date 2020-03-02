package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.PollOptionEntry;
import hr.fer.zemris.java.p12.dao.DAOProvider;
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
import java.util.List;


/**
 * Servlet used to draw Pie Chart representation for the current results from table PollOptions.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = {"/servleti/glasanje-grafika"})
public class GlasanjeGrafika extends HttpServlet {
    /**
     * Draws png image of a pie chart
     *
     * @param req  Requset
     * @param resp Response
     * @throws ServletException Never thrown here.
     * @throws IOException      Thrown when definition and results files fail to open.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");
        OutputStream outputStream = resp.getOutputStream();
        PieDataset dataset = createDataset(req);
        JFreeChart chart = createChart(dataset, "Glasovi");
        int width = 500;
        int height = 270;
        ChartUtilities.writeChartAsPNG(outputStream, chart, width, height);
    }

    /**
     * Creates data set using results
     *
     * @param req Servlet request
     * @return Returns pie dataset.
     * @throws IOException Never thrown here
     */
    private PieDataset createDataset(HttpServletRequest req) throws IOException {
        DefaultPieDataset result = new DefaultPieDataset();
        Long pollID = (Long) req.getServletContext().getAttribute("pollID");
        if (pollID == null) {
            return null;
        }
        List<PollOptionEntry> entries = DAOProvider.getDao().getPollOptions(pollID);
        long sum = entries.stream().mapToLong(PollOptionEntry::getVotesCount).sum();

        if (sum == 0) {
            return result;
        }
        for (PollOptionEntry entry : entries) {
            result.setValue(entry.getOptionTitle(), Math.round(1.d * entry.getVotesCount() / sum * 100));
        }

        return result;
    }

    /**
     * Creates pie chart using given dataset and title.
     *
     * @param dataset Dataset which will be used for drawing a pie chart.
     * @param title   Title for a pie chart.
     * @return Returns chart with initialized values.
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
