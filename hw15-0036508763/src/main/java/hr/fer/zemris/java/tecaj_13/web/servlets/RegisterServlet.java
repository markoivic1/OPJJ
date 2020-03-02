package hr.fer.zemris.java.tecaj_13.web.servlets;
import hr.fer.zemris.java.tecaj_13.web.form.RegisterForm;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used to register new blog users.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Prompts user to provide form data.
     * If user is already logged in this will not allow registration of new users.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getSession().getAttribute("current.user.nick") != null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        RegisterForm rf = new RegisterForm();
        req.setAttribute("user", rf);
        req.getRequestDispatcher("/WEB-INF/pages/RegisterForm.jsp").forward(req, resp);
    }
}
