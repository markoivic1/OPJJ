package hr.fer.zemris.java.p12.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used to redirect from /index.html to /servlet/index.html
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "index", urlPatterns = {"/index.html"})
public class IndexServlet extends HttpServlet {
    /**
     * Redirects /index.html to /servlet/index.html
     * @param req Request
     * @param resp Response
     * @throws ServletException Thrown when setting an attribute fails.
     * @throws IOException Thrown when setting an attribute fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/servleti/index.html");
    }
}
