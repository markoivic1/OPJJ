package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * Servlet which lists comments for an 'entryID' parameter.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/comment")
public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Calls jsp which will list all of the comments under this blog entry.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String entryID = req.getParameter("entryID");
        if (entryID == null) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return;
        }
        BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(entryID));
        BlogComment blogComment = new BlogComment();
        blogComment.setBlogEntry(blogEntry);
        String message = req.getParameter("message");
        String email = req.getParameter("email");

        if (req.getSession().getAttribute("current.user.nick") != null) {
            email = DAOProvider.getDAO().getBlogUser((String) req.getSession().getAttribute("current.user.nick")).getEmail();
        }

        if (message == null ||
            email == null ||
            message.equals("") ||
            email.equals("")) {
            req.setAttribute("message", message == null ? "" : message);
            req.setAttribute("email", email == null ? "" : email);
            req.setAttribute("blogEntry", blogEntry);
            req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
            return;
        }

        blogComment.setMessage(message);
        blogComment.setUsersEMail(email);
        blogComment.setPostedOn(new Date());
        DAOProvider.getDAO().setComment(blogComment);
        req.getRequestDispatcher("/servleti/author/" + blogEntry.getCreator().getNick() + "/" + req.getParameter("entryID")).forward(req, resp);
    }
}
