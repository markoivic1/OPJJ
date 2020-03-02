package hr.fer.zemris.java.hw17;

import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which models single vector document.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class VectorDocument {
    /**
     * Map with frequency of each word.
     * Represents vector.
     */
    private Map<String, Double> vector;
    /**
     * Path to this document.
     */
    private Path path;

    /**
     * Constructor.
     * @param vector Initial vector
     * @param path Path to a document.
     */
    public VectorDocument(Map<String, Double> vector, Path path) {
        this.vector = vector;
        this.path = path;
    }

    /**
     * Normalize given vector.
     * sqrt(x1^2 + x2^2 + ... + xi^2) where i equals number of entries in a given vector
     * @param vector Vector which is used in calculating norm
     * @return Returns norm.
     */
    public static double norm(Map<String, Double> vector) {
        double value = 0;
        for (Map.Entry<String, Double> entry : vector.entrySet()) {
            value += entry.getValue()*entry.getValue();
        }
        return Math.sqrt(value);
    }

    /**
     * Constructor.
     */
    public VectorDocument() {
        this.vector = new HashMap<>();
    }

    /**
     * Increase value for a given key by 1. If not initializes it will set new value to 1.
     * @param key Key
     */
    public void increaseValue(String key) {
        Double oldValue = vector.get(key);
        vector.put(key,  oldValue == null ? 1.0 : oldValue + 1.0);
    }


    /**
     * Sets path to a given value
     * @param path new path.
     */
    public void setPath(Path path) {
        this.path = path;
    }

    /**
     * Getter for a map of vector values
     * @return Returns vector
     */
    public Map<String, Double> getVector() {
        return vector;
    }

    /**
     * Getter for path
     * @return Returns path.
     */
    public Path getPath() {
        return path;
    }
}
