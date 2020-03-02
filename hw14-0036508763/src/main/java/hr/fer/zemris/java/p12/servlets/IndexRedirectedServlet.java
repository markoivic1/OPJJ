package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.PollEntry;
import hr.fer.zemris.java.p12.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Servlet used to show index page.
 * It lists all of the available Polls.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "servleti-index", urlPatterns = {"/servleti/index.html"})
public class IndexRedirectedServlet extends HttpServlet {
    /**
     * Loads list of {@link PollEntry} and sets attribute with the key "polls" with them.
     * @param req Request
     * @param resp Reponse
     * @throws ServletException Thrown when forwarding fails.
     * @throws IOException Thrown when forwarding fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<PollEntry> availablePolls = DAOProvider.getDao().getPolls();

        req.setAttribute("polls", availablePolls);
        req.getRequestDispatcher("/WEB-INF/index.jsp").forward(req, resp);
    }
}
