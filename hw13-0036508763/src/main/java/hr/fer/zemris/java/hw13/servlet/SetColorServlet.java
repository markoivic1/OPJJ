package hr.fer.zemris.java.hw13.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Sets color given by a parameter to a "pickedBgCol" attribute.
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name="setColors", urlPatterns ={"/setcolor"})
public class SetColorServlet extends HttpServlet {
    /**
     * Loads parameter and sets attribute to pickedBgCol
     * @param req Request
     * @param resp Response
     * @throws ServletException Never thrown here.
     * @throws IOException Never thrown here.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String color = (String) req.getParameter("color");
        req.getSession().setAttribute("pickedBgCol", "#" + color);
    }
}
