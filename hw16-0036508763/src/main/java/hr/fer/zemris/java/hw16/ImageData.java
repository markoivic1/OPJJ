package hr.fer.zemris.java.hw16;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Class which represent info for a single image.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ImageData {
    /**
     * Name of an image.
     */
    private String name;
    /**
     * Description of an image.
     */
    private String description;
    /**
     * List of tags which are given to the image.
     */
    private List<String> tags;

    /**
     * Constructor.
     * @param name Name of an image
     * @param description Description of an image
     * @param tags ', ' separated tags which will be converted to list of strings.
     */
    public ImageData(String name, String description, String tags) {
        this.name = name;
        this.description = description;
        this.tags = Arrays.stream(tags.split(", ")).collect(Collectors.toList());
    }

    /**
     * Loads file from the given path and creates list of {@link ImageData}.
     * It is expected for data to be sorted as:
     *      index: i     -> name
     *             i + 1 -> description
     *             i + 2 -> tag(s)
     * @param path Path to a file which contains data
     * @return Returns list of {@link ImageData} which were loaded from the given path to a file.
     */
    public static List<ImageData> createList(Path path){
        List<String> lines;
        try {
            lines = Files.readAllLines(path);
        } catch (IOException e) {
            System.out.println("Unable to open file: " + path.getFileName());
            return new ArrayList<>();
        }
        List<ImageData> imageData = new ArrayList<>();
        for (int i = 0; i < lines.size(); i += 3) {
            imageData.add(new ImageData(lines.get(i), lines.get(i+1), lines.get(i+2)));
        }
        return imageData;
    }

    /**
     * Gets image name
     * @return Returns image name.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets image description.
     * @return Returns image description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Gets list of tags
     * @return Returns tags
     */
    public List<String> getTags() {
        return tags;
    }
}
