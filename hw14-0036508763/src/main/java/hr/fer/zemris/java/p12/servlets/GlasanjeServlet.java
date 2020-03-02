package hr.fer.zemris.java.p12.servlets;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import hr.fer.zemris.java.p12.PollEntry;
import hr.fer.zemris.java.p12.PollOptionEntry;
import hr.fer.zemris.java.p12.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Main page for voting on your favourite band.
 * Bands are loaded from a table "pollOptions" and sorted by their ID.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje", urlPatterns = {"/servleti/glasanje"})
public class GlasanjeServlet extends HttpServlet {
    /**
     * Loads available bands, sorts them by their id and sets list of them to a "availableBands" attribute
     * @param req Request
     * @param resp Response
     * @throws ServletException Thrown when setting an attribute fails or when forwarding fails.
     * @throws IOException Thrown when opening definition file fails or when forwarding fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        long pollId = Long.parseLong(req.getParameter("pollID"));
        List<PollOptionEntry> optionsEntries = DAOProvider.getDao().getPollOptions(pollId);
        req.getServletContext().setAttribute("pollID", pollId);
        PollEntry pollEntry = DAOProvider.getDao().getPoll(pollId);

        req.setAttribute("poll", pollEntry);
        req.setAttribute("availableBands", optionsEntries);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeIndex.jsp").forward(req, resp);
    }
}
