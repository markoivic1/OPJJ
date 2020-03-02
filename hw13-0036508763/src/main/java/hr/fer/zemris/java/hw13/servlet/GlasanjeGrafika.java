package hr.fer.zemris.java.hw13.servlet;

import hr.fer.zemris.java.hw13.BandEntry;
import hr.fer.zemris.java.hw13.InitUtil;
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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hr.fer.zemris.java.hw13.InitUtil.initScoreMap;

/**
 * Servlet used to draw Pie Chart representation for the current results.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-grafika", urlPatterns = {"/glasanje-grafika"})
public class GlasanjeGrafika extends HttpServlet {
    /**
     * Draws png image of a pie chart
     * @param req Requset
     * @param resp Response
     * @throws ServletException Never thrown here.
     * @throws IOException Thrown when definition and results files fail to open.
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
     * @param req
     * @return
     * @throws IOException
     */
    private PieDataset createDataset(HttpServletRequest req) throws IOException {
        DefaultPieDataset result = new DefaultPieDataset();

        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        Path path = Paths.get(fileName);

        String definitionFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<BandEntry> bandEntries = Files.readAllLines(Paths.get(definitionFile)).stream().map(e -> new BandEntry(e.split("\\t"))).collect(Collectors.toList());

        if (!Files.exists(path)) {
            InitUtil.initVotes(path, bandEntries);
        }

        long score = 0;
        String[] votes = Files.readString(Paths.get(req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt"))).split("\n");

        for (String vote : votes) {
            score += Integer.parseInt(vote.split("\\t")[1]);
        }
        Map<String, Integer> scoreMap = initScoreMap(votes);

        if (score == 0) {
            result.setValue("No yet voted", 100);
        }

        for (BandEntry entry : bandEntries) {
            if (score == 0) {
                result.setValue(entry.getName(), 0);
                continue;
            }
            result.setValue(entry.getName(), Math.round(1.d * scoreMap.get(entry.getID()) / score * 100));
        }

        return result;
    }

    /**
     * Creates pie chart using given dataset and title.
     * @param dataset Dataset which will be used for drawing a pie chart.
     * @param title Title for a pie chart.
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
