package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.PollOptionEntry;
import hr.fer.zemris.java.p12.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Servlet used to show results for a given table Polls.
 * Polls' ID must be set as an attribute of servlet context under a key "pollID".
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = {"/servleti/glasanje-rezultati"})
public class GlasanjeRezultatiServlet extends HttpServlet {
    /**
     * Loads list of {@link PollOptionEntry} from database and sorts them.
     * Sets attributes "pobjednici" and "rezultati".
     * @param req Request
     * @param resp Response
     * @throws ServletException Thrown when request can't be forwarded.
     * @throws IOException Thrown when request can't be forwarded.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Long pollID = (Long)req.getServletContext().getAttribute("pollID");
        if (pollID == null) {
            return;
        }
        List<PollOptionEntry> pollOptionsEntries = DAOProvider.getDao().getPollOptions(pollID);
        pollOptionsEntries.sort(Comparator.comparing(PollOptionEntry::getVotesCount).reversed());

        List<PollOptionEntry> winners = new ArrayList<>();
        long voteCount = pollOptionsEntries.get(0).getVotesCount();
        for (PollOptionEntry entry : pollOptionsEntries) {
            if (entry.getVotesCount() == voteCount) {
                winners.add(entry);
                continue;
            }
            break;
        }
        req.setAttribute("rezultati", pollOptionsEntries);
        req.setAttribute("pobjednici", winners);

        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
