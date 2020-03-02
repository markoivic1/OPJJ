package hr.fer.zemris.java.hw06.shell;

import java.io.File;

/**
 * Class which is used to store file and it's name groups which are determined by the parser.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FilterResult {
    /**
     * File saved
     */
    private File file;
    /**
     * Number of groups
     */
    private int numberOfGroups;
    /**
     * Groups which have been detected in this file.
     */
    private String[] groups;

    /**
     * Defualt constructor.
     * @param file Sets file to a given file
     * @param groups Sets groups to a given groups.
     */
    public FilterResult(File file, String[] groups) {
        this.file = file;
        this.numberOfGroups = groups.length;
        this.groups = groups;
    }

    /**
     * Returns group at a given index-
     * @param index Index from which the group will be retrieved.
     * @return Returns requested group
     * @throws ArrayIndexOutOfBoundsException thrown when an invalid index is given.
     */
    public String group(int index) throws ArrayIndexOutOfBoundsException {
        if ((index < 0) || (index > numberOfGroups)) {
            throw new ArrayIndexOutOfBoundsException("Given index is not in range");
        }
        return groups[index];
    }

    /**
     * Getter for number of groups.
     * @return Returns number of groups.
     */
    public int numberOfGroups() {
        return numberOfGroups;
    }

    /**
     * Returns name as a string.
     * @return Name as a string.
     */
    @Override
    public String toString() {
        return file.getName();
    }
}
