package hr.fer.zemris.java.zi.servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "main", urlPatterns = {"/main"})
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Path images = Paths.get(getServletContext().getRealPath("/WEB-INF/images"));
        System.out.println(images);
        File[] files = images.toFile().listFiles();
        if (files == null) {
            req.getRequestDispatcher("/WEB-INF/pages/ShowImages.jsp").forward(req, resp);
        }
        List<String> imageNames = new ArrayList<>();
        for (File file : files) {
            imageNames.add(file.getName());
        }

        String invalid = (String) req.getAttribute("invalid");
        if (invalid != null) {
            req.setAttribute("invalid", invalid);
        }
        java.util.Collections.sort(imageNames);

        req.setAttribute("imageNames", imageNames);
        req.getRequestDispatcher("/WEB-INF/pages/ShowImages.jsp").forward(req, resp);
    }
}
