package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet which maps home page for a user.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/main/home")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Renders home page if user is logged in.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.nick") == null) {
            resp.sendRedirect("/index.jsp");
            return;
        }
        req.setAttribute("users", DAOProvider.getDAO().getUsers());
        req.setAttribute("nick", req.getSession().getAttribute("current.user.nick"));
        req.setAttribute("fn", req.getSession().getAttribute("current.user.fn"));
        req.setAttribute("ln", req.getSession().getAttribute("current.user.ln"));
        req.getRequestDispatcher("/WEB-INF/pages/Home.jsp").forward(req, resp);
    }
}
