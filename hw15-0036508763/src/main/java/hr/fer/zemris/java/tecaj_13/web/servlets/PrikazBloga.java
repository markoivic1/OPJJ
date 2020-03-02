package hr.fer.zemris.java.tecaj_13.web.servlets;

import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet used for showing all of the blog entries provided by the user id given in by the paramter.
 */
@WebServlet("/servleti/prikazi")
public class PrikazBloga extends HttpServlet {

	private static final long serialVersionUID = 1L;

	/**
	 * Renders blog entries for a given blog user's id.
	 * @param req HttpServletRequest
	 * @param resp HttpServletResponse
	 * @throws ServletException Thrown when an error occurs
	 * @throws IOException Throw when an error occurs
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String sID = req.getParameter("id");
		Long id = null;
		try {
			id = Long.valueOf(sID);
		} catch(Exception ignorable) {
		}
		if(id!=null) {
			BlogEntry blogEntry = DAOProvider.getDAO().getBlogEntry(id);
			if(blogEntry!=null) {
				req.setAttribute("blogEntry", blogEntry);
			}
		}
		req.getRequestDispatcher("/WEB-INF/pages/Prikaz.jsp").forward(req, resp);
	}
	
}