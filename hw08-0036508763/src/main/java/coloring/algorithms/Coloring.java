package coloring.algorithms;

import marcupic.opjj.statespace.coloring.Picture;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class which defines strategies for a coloring.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Coloring implements Consumer<Pixel>, Function<Pixel, List<Pixel>>, Predicate<Pixel>, Supplier<Pixel> {
    /**
     * First selected pixel
     */
    private Pixel reference;
    /**
     * Picture which the methods will depend on.
     */
    private Picture picture;
    /**
     * Fill color.
     */
    private int fillColor;
    /**
     * Reference color of a first pixel.
     */
    private int refColor;

    /**
     * Constructor which takes values and stores them.
     * @param reference Reference to a first selected pixel
     * @param picture Picture which will be used,
     * @param fillColor Fill color.
     */
    public Coloring(Pixel reference, Picture picture, int fillColor) {
        this.reference = reference;
        this.picture = picture;
        this.fillColor = fillColor;
        this.refColor = picture.getPixelColor(reference.getX(), reference.getY());
    }

    /**
     * Sets a color to a given pixel.
     * @param pixel Pixel which will be set to a fill color.
     */
    @Override
    public void accept(Pixel pixel) {
        picture.setPixelColor(pixel.getX(), pixel.getY(), fillColor);
    }

    /**
     * Method which returns adjacent pixels.
     * @param pixel Pixel which is used as a starting point.
     * @return Returns list of pixels.
     */
    @Override
    public List<Pixel> apply(Pixel pixel) {
        List<Pixel> listOfPixels = new LinkedList<>();
        Pixel pixelUp = new Pixel(pixel.getX(), pixel.getY() - 1);
        Pixel pixelDown = new Pixel(pixel.getX(), pixel.getY() + 1);
        Pixel pixelRight = new Pixel(pixel.getX() + 1, pixel.getY());
        Pixel pixelLeft = new Pixel(pixel.getX() - 1, pixel.getY());
        if (isInPicture(pixelUp)) {
            listOfPixels.add(pixelUp);
        }
        if (isInPicture(pixelDown)) {
            listOfPixels.add(pixelDown);
        }
        if (isInPicture(pixelRight)) {
            listOfPixels.add(pixelRight);
        }
        if (isInPicture(pixelLeft)) {
            listOfPixels.add(pixelLeft);
        }
        return listOfPixels;
    }

    /**
     * Method used to test if given pixel is inside of a picture.
     * @param pixel Pixel which is being checked.
     * @return Returns true if the pixel is inside of a picture, false otherwise.
     */
    private boolean isInPicture(Pixel pixel) {
        if (pixel.getX() < 0 || pixel.getY() < 0 || pixel.getX() >= picture.getWidth() || pixel.getY() >= picture.getHeight()) {
            return false;
        }
        return true;
    }

    /**
     * Test if the given pixel is same as a reference color.
     * @param pixel Pixel which will be checked.
     * @return Returns true if the colors match, returns false otherwise.
     */
    @Override
    public boolean test(Pixel pixel) {
        return picture.getPixelColor(pixel.getX(), pixel.getY()) == refColor;
    }

    /**
     * Getter for a reference pixel.
     * @return Return reference pixel
     */
    @Override
    public Pixel get() {
        return reference;
    }
}
