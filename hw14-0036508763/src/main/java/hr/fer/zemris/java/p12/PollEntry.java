package hr.fer.zemris.java.p12;

/**
 * Stores single row in Polls table
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PollEntry {
    /**
     * id of a table
     */
    private long id;
    /**
     * title of a table
     */
    private String title;
    /**
     * message in a table
     */
    private String message;

    /**
     * Constructor.
     */
    public PollEntry(long id, String title, String message) {
        this.id = id;
        this.title = title;
        this.message = message;
    }

    /**
     * Gets ID
     * @return Returns ID
     */
    public long getId() {
        return id;
    }

    /**
     * Gets title
     * @return Returns title.
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets message
     * @return Returns message
     */
    public String getMessage() {
        return message;
    }
}
