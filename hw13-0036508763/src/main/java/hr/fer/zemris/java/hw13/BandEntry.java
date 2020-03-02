package hr.fer.zemris.java.hw13;

/**
 * Defines class which stores data for a single band
 * @author Marko Ivić
 * @version 1.0.0
 */
public class BandEntry {
    /**
     * Id of a band
     */
    private String ID;
    /**
     * Name of a band
     */
    private String name;
    /**
     * url of a band
     */
    private String URL;
    /**
     * score of a band.
     */
    private int score;

    /**
     * Constructor.
     * @param data Data which will be used to initialize. Expected ordering by indexes is ID, name, url
     */
    public BandEntry(String[] data) {
        this(data[0], data[1], data[2]);
    }

    /**
     * Constructor
     * @param ID Id of a band.
     * @param name Name of a band.
     * @param URL Url of a band.
     */
    public BandEntry(String ID, String name, String URL) {
        this.ID = ID;
        this.name = name;
        this.URL = URL;
        score = 0;
    }

    /**
     * Setter for a score
     * @param score Sets current score to a given value.
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Getter for a score
     * @return Returns score
     */
    public int getScore() {
        return score;
    }

    /**
     * Getter for ID
     * @return Returns ID.
     */
    public String getID() {
        return ID;
    }

    /**
     * Getter for name.
     * @return Returns name.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for url
     * @return Returns url.
     */
    public String getURL() {
        return URL;
    }
}
