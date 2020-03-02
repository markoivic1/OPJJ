package hr.fer.zemris.java.hw16.servlets;

import hr.fer.zemris.java.hw16.ImageData;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet which handles drawing full size images.
 * Images are encoded in base64.
 * Alongside image is stored their corresponding name and description.
 * Json is created from aforementioned keys.
 * {
 *  "image": base64 of an image,
 *  "imageName" : imageName,
 *  "tags" : ["tag1", "tag2"]
 *  }
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/image")
public class DrawImage extends HttpServlet {
    /**
     * Encodes image from given image argument in base64.
     * Creates json with image, imageName and tags.
     * @param req Request
     * @param resp Response
     * @throws ServletException Neverthrown here
     * @throws IOException Thrown when image or info file can't be opened.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String imageName = req.getParameter("imageName");
        resp.setContentType("application/json;charset=UTF-8");
        Path path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));
        List<ImageData> data = ImageData.createList(path).stream().filter(i -> i.getName().equals(imageName)).collect(Collectors.toList());
        if (data.size() == 0) {
            resp.sendError(HttpServletResponse.SC_NO_CONTENT);
            return;
        }
        Path pathToOriginal = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + imageName));
        byte[] originalImage = Files.readAllBytes(pathToOriginal);
        resp.getWriter().write("{");
        resp.getWriter().write("\"image\":" + "\"" + new String(Base64.getEncoder().encode(originalImage)) + "\",");
        resp.getWriter().write("\"imageName\":" + "\"" + imageName + "\",");
        resp.getWriter().write("\"imageDescription\":" + "\"" + data.get(0).getDescription() + "\",");
        resp.getWriter().write("\"tags\":[");
        boolean first = true;
        int count = 0;
        for (String tag : data.get(0).getTags()) {
            count++;
            if (first) {
                first = false;
            } else if (count <= data.get(0).getTags().size()) {
                resp.getWriter().write(",");
            }
            resp.getWriter().write("\"" + tag +"\"");
        }
        resp.getWriter().write("]");
        resp.getWriter().write("}");
        resp.getWriter().flush();
    }
}
