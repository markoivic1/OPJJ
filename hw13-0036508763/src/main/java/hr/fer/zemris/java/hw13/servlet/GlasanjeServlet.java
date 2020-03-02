package hr.fer.zemris.java.hw13.servlet;

import hr.fer.zemris.java.hw13.BandEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Main page for voting on your favourite band.
 * Bands are loaded from a file and sorted by their ID.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje", urlPatterns = {"/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    /**
     * Loads available bands, sorts them by their id and sets list of them to a "availableBands" attribute
     * @param req Request
     * @param resp Response
     * @throws ServletException Thrown when setting an attribute fails
     * @throws IOException Thrown when opening definition file fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //Učitaj raspoloživebendove
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        Path path = Paths.get(fileName);
        List<String> contents = Files.readAllLines(path);
        List<BandEntry> bandEntryList = contents.stream().map(e -> new BandEntry(e.split("\\t"))).collect(Collectors.toList());

        bandEntryList.sort(Comparator.comparing(BandEntry::getID));

        req.setAttribute("availableBands", bandEntryList);

        //Pošalji ih JSP-u...
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
