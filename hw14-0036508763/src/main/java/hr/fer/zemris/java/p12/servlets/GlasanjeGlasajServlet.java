package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used to register a single vote in PollOptions table.
 * Identifies vote by ID.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-glasaj", urlPatterns = {"/servleti/glasanje-glasaj"})
public class GlasanjeGlasajServlet extends HttpServlet {
    /**
     * Uses results from a database and increases vote by one for a given id parameter.
     * @param req Http Servlet Request
     * @param resp Http Servlet response
     * @throws ServletException Is never thrown here
     * @throws IOException Thrown when there is something wrong with definition or score files.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        long voteID = Long.parseLong(req.getParameter("id"));
        DAOProvider.getDao().registerAVote(voteID);
        resp.sendRedirect(req.getContextPath() + "/servleti/glasanje-rezultati");
    }
}
