package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOException;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;
import hr.fer.zemris.java.tecaj_13.web.form.RegisterForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet for handling new blog user register process.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/register/save")
public class RegisterSaveServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Requires user to fill given form correctly.
     * Nick name must be unique.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException{
        RegisterForm rf = new RegisterForm();
        rf.fillWithHttpRequest(req);
        req.setAttribute("user", rf);
        rf.validate();
        if (!rf.isValid()) {
            req.getRequestDispatcher("/WEB-INF/pages/RegisterForm.jsp").forward(req, resp);
            return;
        }
        BlogUser user = new BlogUser();
        try {
            user.fillWithHttpRequest(req);
            DAOProvider.getDAO().setBlogUser(user);
        } catch (DAOException e) {
            user.setNick("");
            req.getRequestDispatcher("/WEB-INF/pages/RegisterForm.jsp").forward(req, resp);
            return;
        }
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

}
