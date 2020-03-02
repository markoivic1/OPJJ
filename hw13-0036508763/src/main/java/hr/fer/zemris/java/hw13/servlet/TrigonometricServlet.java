package hr.fer.zemris.java.hw13.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Creates lists of cos and sin values for the given parameters.
 * if b > a + 720 => b = a + 720
 *
 * a is starting angle
 * b is finishing angle
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name="trig", urlPatterns ={"/trigonometric"})
public class TrigonometricServlet extends HttpServlet {
    /**
     * Initializes list with cos and sin values.
     * Sets cos values as "cos" attribute and sin values as "sin" attribute.
     *
     * a is set to "a" attribute.
     * b is set to "b" attribute.
     * @param req Request
     * @param resp Response
     * @throws ServletException Thrown when setting an attribute fails
     * @throws IOException Thrown when forwarding to /WEB-INF/pages/trigonometric.jsp fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer a;
        try {
            a = Integer.valueOf(req.getParameter("a"));
        } catch (NumberFormatException e) {
            a = Integer.valueOf(0);
        }
        Integer b;
        try {
            b = Integer.valueOf(req.getParameter("b"));
        } catch (NumberFormatException e) {
            b = Integer.valueOf(360);
        }
        if (a > b) {
            int tmp = a;
            a = b;
            b = tmp;
        }
        if (b > a + 720) {
            b = a + 720;
        }
        List<String> sinValues = new ArrayList<>();
        List<String> cosValues = new ArrayList<>();
        for (int i = a; i <= b; i++) {
            sinValues.add(String.valueOf(Math.sin(Math.toRadians(i))));
            cosValues.add(String.valueOf(Math.cos(Math.toRadians(i))));
        }
        req.setAttribute("sin", sinValues);
        req.setAttribute("cos", cosValues);
        req.setAttribute("a", a);
        req.setAttribute("b", b);
        req.getRequestDispatcher("/WEB-INF/pages/trigonometric.jsp").forward(req, resp);
    }
}
