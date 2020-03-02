package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.List;

/**
 * Method which reformats given list of {@link StudentRecord}s.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RecordFormatter {
    /**
     * Method which is used for printing a table containing {@link StudentRecord}s.
     * @param records List of {@link StudentRecord} which will be printed.
     * @return Returns list of strings which are in printable format.
     */
    public static List<String> format(List<StudentRecord> records) {
        List<String> output = new ArrayList<>();
        if (records.isEmpty()) {
            output.add("Records selected: 0");
            // System.out.println("Records selected: 0");
            return output;
        }
        int longestJMBAG = 0;
        int longestLastName = 0;
        int longestFirstName = 0;
        for (StudentRecord record : records) {
            longestJMBAG = (record.getJmbag().length() > longestJMBAG) ? record.getJmbag().length() : longestJMBAG;
            longestLastName = (record.getLastName().length() > longestLastName) ? record.getLastName().length() : longestLastName;
            longestFirstName = (record.getFirstName().length() > longestFirstName) ? record.getFirstName().length() : longestFirstName;
        }
        StringBuilder headerAndFooter = new StringBuilder();
        // first row and last row
        headerAndFooter.append("+");
        headerAndFooter.append("=".repeat(Math.max(0, longestJMBAG + 2)));
        headerAndFooter.append("+");
        headerAndFooter.append("=".repeat(Math.max(0, longestLastName + 2)));
        headerAndFooter.append("+");
        headerAndFooter.append("=".repeat(Math.max(0, longestFirstName + 2)));
        headerAndFooter.append("+");
        headerAndFooter.append("===+");

        output.add(headerAndFooter.toString());

        // records
        for (StudentRecord record : records) {
            StringBuilder sb = new StringBuilder();
            sb.append("| ");
            sb.append(record.getJmbag());
            int numberOfSpaces = longestJMBAG - record.getJmbag().length() + 1;
            while (numberOfSpaces != 0) {
                sb.append(" ");
                numberOfSpaces--;
            }
            sb.append("| ");
            sb.append(record.getLastName());
            numberOfSpaces = longestLastName - record.getLastName().length() + 1;
            while (numberOfSpaces != 0) {
                sb.append(" ");
                numberOfSpaces--;
            }
            sb.append("| ");
            sb.append(record.getFirstName());
            numberOfSpaces = longestFirstName - record.getFirstName().length() + 1;
            while (numberOfSpaces != 0) {
                sb.append(" ");
                numberOfSpaces--;
            }
            sb.append("| ");
            sb.append(record.getFinalGrade());
            sb.append(" |");
            output.add(sb.toString());
        }
        output.add(headerAndFooter.toString());
        return output;
    }
}
