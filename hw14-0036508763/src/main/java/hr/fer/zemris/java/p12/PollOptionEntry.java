package hr.fer.zemris.java.p12;

/**
 * Stores data from a single row from PollOptions table
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PollOptionEntry {
    /**
     * id
     */
    private long id;
    /**
     * title
     */
    private String optionTitle;
    /**
     * link
     */
    private String optionLink;
    /**
     * corresponding poll id
     */
    private long pollID;
    /**
     * number of votes
     */
    private long votesCount;

    /**
     * Constructor
     */
    public PollOptionEntry(long id, String optionTitle, String optionLink, long pollID, long votesCount) {
        this.id = id;
        this.optionTitle = optionTitle;
        this.optionLink = optionLink;
        this.pollID = pollID;
        this.votesCount = votesCount;
    }

    /**
     * Gets id
     * @return Returns id
     */
    public long getId() {
        return id;
    }

    /**
     * Gets option title
     * @return Returns option title
     */
    public String getOptionTitle() {
        return optionTitle;
    }

    /**
     * Gets option link
     * @return Returns option link
     */
    public String getOptionLink() {
        return optionLink;
    }

    /**
     * Gets poll id
     * @return Returns poll id
     */
    public long getPollID() {
        return pollID;
    }

    /**
     * Gets vote count
     * @return Returns vote count.
     */
    public long getVotesCount() {
        return votesCount;
    }
}
