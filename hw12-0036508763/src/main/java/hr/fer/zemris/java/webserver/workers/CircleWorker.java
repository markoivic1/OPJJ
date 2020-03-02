package hr.fer.zemris.java.webserver.workers;

import hr.fer.zemris.java.webserver.IWebWorker;
import hr.fer.zemris.java.webserver.RequestContext;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Worker which draws red circle with 300x300 px dimension.
 * Stored in png format.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CircleWorker implements IWebWorker {
    /**
     * Draws circle.
     * @param context Context
     * @throws Exception Exception is thrown when image is unable to be written in byte array
     */
    @Override
    public void processRequest(RequestContext context) throws Exception {
        context.setMimeType("image/png");
        try {
            BufferedImage bim = new BufferedImage(300, 300, BufferedImage.TYPE_3BYTE_BGR);

            Graphics2D g2d = bim.createGraphics();

            g2d.setColor(Color.RED);
            g2d.fillOval(0, 0, 300, 300);
            g2d.dispose();

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            try {
                ImageIO.write(bim, "png", bos);
            } catch (IOException e) {
            }
            byte[] podatci = bos.toByteArray();
            context.write(podatci);
        } catch (IOException ex) {
            System.out.println("An error has occured while executing Circle Worker");
        }
    }
}
