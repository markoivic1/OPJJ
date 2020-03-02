package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet which is used to handle operations specific for each user.
 * It supports add new entries, opening and editing old ones.
 * Examples of supported urls are:
 *      /new        -create new
 *      /edit/(id)  -edit existing
 *      /(id)       -open existing
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/servleti/author/*")
public class AuthorServlet extends HttpServlet {

    /**
     * Processes given requse
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Processes given requse
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs.
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        process(req, resp);
    }

    /**
     * Processes requests with previously described urls.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs.
     */
    private void process(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        if (pathInfo.length() == 1) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
            return;
        }
        List<String> arguments = Arrays.stream(pathInfo.split("/")).filter(e -> !e.equals("")).collect(Collectors.toList());
        String userNick = arguments.get(0);
        req.setAttribute("nick", userNick);

        List<BlogEntry> blogEntries = DAOProvider.getDAO().getEntries(userNick);
        req.setAttribute("entries", DAOProvider.getDAO().getEntries(userNick));
        if (arguments.size() == 1) {
            if (blogEntries == null) {
                req.getRequestDispatcher("/index.jsp").forward(req, resp);
                return;
            }
            req.getRequestDispatcher("/WEB-INF/pages/Entries.jsp").forward(req, resp);
            return;
        }
        BlogEntry blogEntry = new BlogEntry();
        blogEntry.fillFromHttpRequest(req, DAOProvider.getDAO().getBlogUser(userNick));
        req.setAttribute("blogEntry", blogEntry);

        String sessionOwner = (String) req.getSession().getAttribute("current.user.nick");

        if (arguments.get(1).equals("new")) {
            if (!arguments.get(0).equals(sessionOwner)) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }
        } else if (arguments.get(1).equals("edit")) {
            if (processEdit(req, resp, arguments, sessionOwner)) return;
        } else if (arguments.get(1).equals("save")) {
            if (!arguments.get(0).equals(sessionOwner)) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return;
            }
            if (arguments.size() == 3) {
                BlogEntry dbBlogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(arguments.get(2)));
                if (!dbBlogEntry.getCreator().getNick().equals(sessionOwner)) {
                    resp.sendRedirect(req.getContextPath() + "/index.jsp");
                    return;
                }
                blogEntry.setCreator(dbBlogEntry.getCreator());
                if (blogEntry.valid()) {
                    createBlogEntry(req, resp, arguments, userNick, blogEntry);
                    return;
                } else if (!blogEntry.valid()) {
                    if (requestValidBlogEntry(req, resp, arguments, blogEntry)) return;
                }
            } else if (arguments.size() == 2) {
                if (saveNewBlogEntry(req, resp, userNick, blogEntry, sessionOwner)) return;
            }
        } else {
            if (showBlogEntries(req, resp, arguments)) return;

        }
        req.getRequestDispatcher("/WEB-INF/pages/ManageEntry.jsp").forward(req, resp);

    }

    /**
     * Shows blog entries with given id to a user.
     * @param arguments Url parsed as arguments
     * @return Returns true if redirect to a homepage is needed.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private boolean showBlogEntries(HttpServletRequest req, HttpServletResponse resp, List<String> arguments) throws ServletException, IOException {
        BlogEntry blogEntry;
        try {
            Long id = Long.parseLong(arguments.get(1));
            blogEntry = DAOProvider.getDAO().getBlogEntry(id);
            req.setAttribute("blogEntry", blogEntry);
            req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return true;
        }
        return false;
    }

    /**
     * Save new blog entry
     * @return Returns true if redirect to a homepage is needed.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private boolean saveNewBlogEntry(HttpServletRequest req, HttpServletResponse resp, String userNick, BlogEntry blogEntry, String sessionOwner) throws ServletException, IOException {
        blogEntry.setCreator(DAOProvider.getDAO().getBlogUser(sessionOwner));
        if (blogEntry.valid()) {
            DAOProvider.getDAO().setEntry(blogEntry);
            req.setAttribute("blogEntry", blogEntry);
            req.getRequestDispatcher("/servleti/author/" + userNick).forward(req, resp);
            return true;
        }
        return false;
    }

    /**
     * Asks user to fill form again.
     * @param arguments Url parsed as arguments
     * @return Returns true if redirect to a homepage is needed.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private boolean requestValidBlogEntry(HttpServletRequest req, HttpServletResponse resp, List<String> arguments, BlogEntry blogEntry) throws IOException {
        try {
            Long id = Long.parseLong(arguments.get(2));
            blogEntry.setId(id);
            req.setAttribute("blogEntry", blogEntry);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return true;
        }
        return false;
    }

    /**
     * Creates new blog entry.
     * @param arguments Url parsed as arguments
     * @return Returns true if redirect to a homepage is needed.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private void createBlogEntry(HttpServletRequest req, HttpServletResponse resp, List<String> arguments, String userNick, BlogEntry blogEntry) throws ServletException, IOException {
        try {
            Long id = Long.parseLong(arguments.get(2));
            blogEntry.setId(id);
            DAOProvider.getDAO().setEntry(blogEntry);
        } catch (NumberFormatException e) {
        }
        req.getRequestDispatcher("/servleti/author/" + userNick).forward(req, resp);
        return;
    }

    /**
     * Processes edit request.
     * If logged in user is an owner of a blog entry than editing the entry will be allowed
     *
     * Shows blog entries to a user.
     * @param arguments Url parsed as arguments
     * @return Returns true if redirect to a homepage is needed.
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     * @throws ServletException Thrown when an error occurs
     * @throws IOException Throw when an error occurs
     */
    private boolean processEdit(HttpServletRequest req, HttpServletResponse resp, List<String> arguments, String sessionOwner) throws ServletException, IOException {
        BlogEntry blogEntry;
        if (arguments.size() == 2) {
            req.getRequestDispatcher("/WEB-INF/pages/ListEntries.jsp").forward(req, resp);
            return true;
        }
        try {
            blogEntry = DAOProvider.getDAO().getBlogEntry(Long.parseLong(arguments.get(2)));
            if (!blogEntry.getCreator().getNick().equals(sessionOwner)) {
                resp.sendRedirect(req.getContextPath() + "/index.jsp");
                return true;
            }
            req.setAttribute("entryID", blogEntry.getId());
            req.setAttribute("blogEntry", blogEntry);
        } catch (NumberFormatException e) {
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
            return true;
        }
        return false;
    }
}
