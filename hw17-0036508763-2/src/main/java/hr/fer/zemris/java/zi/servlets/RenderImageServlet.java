package hr.fer.zemris.java.zi.servlets;

import hr.fer.zemris.java.hw17.jvdraw.DrawingModel;
import hr.fer.zemris.java.hw17.jvdraw.DrawingModelImpl;
import hr.fer.zemris.java.hw17.jvdraw.JVDraw;
import hr.fer.zemris.java.hw17.jvdraw.object.*;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
@SuppressWarnings("ALL")
@WebServlet(name = "render", urlPatterns = {"/renderImage"})
public class RenderImageServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("image/png");

        String imageName = req.getParameter("imageName");
        Path path = Paths.get(getServletContext().getRealPath("/WEB-INF/images/" + imageName));

        DrawingModel drawingModel = new DrawingModelImpl();

        java.util.List<String> lines = Files.readAllLines(path);
        for (String line : lines) {
            drawingModel.add(createGeometricalObject(line));
        }

        GeometricalObjectBBCalculator bbcalc = new GeometricalObjectBBCalculator();
        for (int i = 0; i < drawingModel.getSize(); i++) {
            drawingModel.getObject(i).accept(bbcalc);
        }
        Rectangle box = bbcalc.getBoundingBox();
        BufferedImage image = new BufferedImage(box.width, box.height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g = image.createGraphics();
        g.translate(-box.x, -box.y);
        GeometricalObjectPainter painter = new GeometricalObjectPainter(g);
        for (int i = 0; i < drawingModel.getSize(); i++) {
            drawingModel.getObject(i).accept(painter);
        }
        g.dispose();

        ImageIO.write(image, "png", resp.getOutputStream());
    }

    private GeometricalObject createGeometricalObject(String line) {
        String[] data = line.split(" ");
        Color color;
        switch (data[0]) {
            case "LINE":
                color = new Color(Integer.parseInt(data[5]), Integer.parseInt(data[6]), Integer.parseInt(data[7]));
                return new Line(color,
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        Integer.parseInt(data[4]));
            case "CIRCLE":
                color = new Color(Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                return new Circle(
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        color);
            case "FCIRCLE":
                color = new Color(Integer.parseInt(data[4]), Integer.parseInt(data[5]), Integer.parseInt(data[6]));
                Color areaColor = new Color(Integer.parseInt(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[9]));
                return new FilledCircle(
                        Integer.parseInt(data[1]),
                        Integer.parseInt(data[2]),
                        Integer.parseInt(data[3]),
                        color,
                        areaColor);
            case "FTRIANGLE":
                Color outlineColor = new Color(Integer.parseInt(data[7]), Integer.parseInt(data[8]), Integer.parseInt(data[9]));
                Color fillColor = new Color(Integer.parseInt(data[10]), Integer.parseInt(data[11]), Integer.parseInt(data[12]));
                return new FilledTriangle(
                        new Point(Integer.parseInt(data[1]),
                                Integer.parseInt(data[2])),
                        new Point(Integer.parseInt(data[3]),
                                Integer.parseInt(data[4])),
                        new Point(Integer.parseInt(data[5]),
                                Integer.parseInt(data[6])),
                        outlineColor,
                        fillColor);
            default:
                throw new IllegalArgumentException("Geometrical object " + data[0] + "is not supported.");
        }
    }
}
