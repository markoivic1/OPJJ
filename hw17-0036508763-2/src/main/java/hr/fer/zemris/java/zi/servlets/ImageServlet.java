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
import java.util.List;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "image", urlPatterns = {"/image"})
public class ImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = req.getParameter("imageName");
        Path path = Paths.get(getServletContext().getRealPath("/WEB-INF/images/" + imageName));
        if (Files.notExists(path)) {
            req.getRequestDispatcher("/main").forward(req, resp);
            return;
        }
        List<String> file = Files.readAllLines(path);

        int nLines = countWords(file, "LINE");
        int nCircles = countWords(file, "CIRCLE");
        int nFCircles = countWords(file, "FCIRCLE");
        int nTriangles = countWords(file, "FTRIANGLE");


        req.setAttribute("nLines", String.valueOf(nLines));
        req.setAttribute("nCircles", String.valueOf(nCircles));
        req.setAttribute("nFCircles", String.valueOf(nFCircles));
        req.setAttribute("nTriangles", String.valueOf(nTriangles));

        req.setAttribute("imageName", imageName);

        req.getRequestDispatcher("/WEB-INF/pages/Image.jsp").forward(req, resp);
    }

    private int countWords(List<String> words, String keyword) {
        int count = 0;
        for (String word : words) {
            if (word.startsWith(keyword)) {
                count++;
            }
        }
        return count;
    }
}
