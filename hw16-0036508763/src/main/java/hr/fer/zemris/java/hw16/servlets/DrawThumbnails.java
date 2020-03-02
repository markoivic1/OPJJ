package hr.fer.zemris.java.hw16.servlets;

import hr.fer.zemris.java.hw16.ImageData;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Servlet which handles creating and returning thumbnails (150x150) as json.
 * Parameter "tag" is used to return (and create if necessary) as base64 encoded jpg image.
 * {
 *     "image": thumbnail in base64,
 *     "imageName" : name of a thumbnail
 * }
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet("/thumbnails")
public class DrawThumbnails extends HttpServlet {
    /**
     * Default height of a thumbnail
     */
    private static final int IMG_HEIGHT = 150;
    /**
     * Default width of a thumbnail
     */
    private static final int IMG_WIDTH = 150;

    /**
     * Take parameter tag with a name of a desired parameter.
     * Returns thumbnails for images which a noted with the given tag.
     * @param req Request
     * @param resp Response
     * @throws ServletException Never thrown here
     * @throws IOException Thrown if opening data info file or image fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        String tag = req.getParameter("tag");
        Path path = Paths.get(req.getServletContext().getRealPath("/WEB-INF/opisnik.txt"));
        List<ImageData> data = ImageData.createList(path);
        data = data.stream().filter(e -> e.getTags().contains(tag)).collect(Collectors.toList());
        Path cachedThumbnails = Paths.get(req.getServletContext().getRealPath("/WEB-INF/thumbnails"));
        if (!Files.exists(cachedThumbnails)) {
            Files.createDirectory(cachedThumbnails);
        }
        List<String> thumbnailNames = new ArrayList<>();
        File[] files = cachedThumbnails.toFile().listFiles();
        for (File file : files) {
            if (file.isFile()) {
                thumbnailNames.add(file.getName());
            }
        }

        createJSON(req, resp, data, cachedThumbnails, thumbnailNames);
    }

    /**
     * Method used to create json from the given parameters.
     *
     * @param req Request
     * @param resp Response
     * @param data Data which contains ImageData which will be added to the response
     * @param cachedThumbnails Path to a directory where thumbnails are cached
     * @param thumbnailNames List of already existing names,
     * @throws IOException Thrown when reading bytes from an image fails.
     */
    private void createJSON(HttpServletRequest req, HttpServletResponse resp, List<ImageData> data, Path cachedThumbnails, List<String> thumbnailNames) throws IOException {
        boolean first = true;
        int count = 0;
        resp.getWriter().write("{");
        resp.getWriter().write("\"thumbnails\":[");
        for (ImageData imageData : data) {
            count++;
            Path pathToOriginal = Paths.get(req.getServletContext().getRealPath("/WEB-INF/slike/" + imageData.getName()));
            if (!thumbnailNames.contains(imageData.getName())) {
                createThumbnail(pathToOriginal, cachedThumbnails);
            }
            if (first) {
                first = false;
            } else if (count <= data.size()) {
                resp.getWriter().write(",");
            }
            byte[] thumbnailImage = Files.readAllBytes(Paths.get(cachedThumbnails.toString() + "/" + imageData.getName()));
            resp.getWriter().write("{");
            resp.getWriter().write("\"image\":\"" + new String(Base64.getEncoder().encode(thumbnailImage)) +"\",");
            resp.getWriter().write("\"imageName\":\"" + imageData.getName() +"\"");
            resp.getWriter().write("}");
        }
        resp.getWriter().write(']');
        resp.getWriter().write("}");

        resp.getWriter().flush();
    }

    /**
     * Method which creates thumbnail.
     * @param source points to the file which is in full size
     * @param destination Points to the directory where thumbnail will be stored
     * @throws IOException Thrown when opening source image fails or when saving thumbnail fails.
     */
    private void createThumbnail(Path source, Path destination) throws IOException {
        BufferedImage originalImage = ImageIO.read(source.toFile());
        BufferedImage thumbnail = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, originalImage.getType());
        Graphics2D g2d = thumbnail.createGraphics();
        g2d.drawImage(originalImage, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);
        g2d.dispose();
        ImageIO.write(thumbnail, "jpg", new File(destination.toString() + "/" + source.getFileName()));
    }
}
