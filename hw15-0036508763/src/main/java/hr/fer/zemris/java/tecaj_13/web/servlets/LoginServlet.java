package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.crypto.Util;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.form.LoginForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used to handle log in process.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/main/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Checks whether values given by the request are valid.
     * If they are valid it will log in user, otherwise it will prompt user to reenter invalid parts.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        if (req.getSession().getAttribute("current.user.nick") != null) {
            resp.sendRedirect(req.getContextPath() + "/servleti/main/home");
            return;
        }
        req.setAttribute("users", DAOProvider.getDAO().getUsers());
        LoginForm lf = new LoginForm();
        lf.fillWithHttpRequest(req);
        String ep = Util.bytetohex(Crypto.digestMessage(lf.getPassword()));
        BlogUser user = DAOProvider.getDAO().getBlogUser(lf.getNick());

        if (user == null) {
            req.setAttribute("user", new BlogUser());
            req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
            return;
        }

        lf.validate(user.getPasswordHash());
        if (!lf.isValid()) {
            req.setAttribute("user", lf);
            req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
            return;
        }

        if (!user.getPasswordHash().equals(ep)) {
            req.setAttribute("user", user);
            req.getRequestDispatcher("/WEB-INF/pages/Formular.jsp").forward(req, resp);
            return;
        }

        req.getSession().setAttribute("current.user.id", user.getId());
        req.getSession().setAttribute("current.user.fn", user.getFirstName());
        req.getSession().setAttribute("current.user.ln", user.getLastName());
        req.getSession().setAttribute("current.user.nick", user.getNick());
        resp.sendRedirect(req.getContextPath() + "/index.jsp");
    }
}
