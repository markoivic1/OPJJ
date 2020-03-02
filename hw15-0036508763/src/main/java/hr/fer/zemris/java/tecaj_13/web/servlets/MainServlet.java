package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.crypto.Util;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPADAOImpl;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMFProvider;
import hr.fer.zemris.java.tecaj_13.dao.jpa.JPAEMProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.form.LoginForm;
import hr.fer.zemris.java.tecaj_13.web.form.RegisterForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet which handles main screen which is common for all non-registered users.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/main")
public class MainServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Dispatches form to a user which will allow him to log in.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.nick") != null) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main/home");
            return;
        }
        req.setAttribute("users", DAOProvider.getDAO().getUsers());
        RegisterForm lf = new RegisterForm();
        req.setAttribute("user", lf);
        req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
    }
}
