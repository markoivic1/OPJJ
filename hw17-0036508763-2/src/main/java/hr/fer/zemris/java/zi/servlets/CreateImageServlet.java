package hr.fer.zemris.java.zi.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "create", urlPatterns = {"/create"})
public class CreateImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getParameter("name");
        String data = req.getParameter("data");
        if (!isOk(fileName)) {
            req.setAttribute("invalid", "Given data is invalid");
            req.getRequestDispatcher("/main").forward(req, resp);
            return;
        }
        Path images = Paths.get(getServletContext().getRealPath("/WEB-INF/images"));
        images = Paths.get(images.toString() + "/" + fileName);
        Files.writeString(images, data);
        req.getRequestDispatcher("/main").forward(req, resp);
    }

    private boolean isOk(String name) {
        if (name.length() == 0) {
            return false;
        }
        if (!name.endsWith(".jvd")) {
            return false;
        }
        char[] data = name.toCharArray();

        for (char c : data) {
            if (Character.isLetter(c) || Character.isDigit(c) || c == '.') {
                continue;
            }
            return false;
        }
        return true;
    }
}
