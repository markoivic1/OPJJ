package hr.fer.zemris.java.hw05.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class defines student database which sorts given input data as {@link StudentRecord}.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class StudentDatabase {
    /**
     * ArrayList is used in order to get O(1) if the query is direct
     */
    private ArrayList<StudentRecord> studentRecords;
    /**
     * Map is used for storing {@link StudentRecord}, JMBAG of a {@link StudentRecord} is used as a key.
     */
    private Map<String, StudentRecord> mapOfRecords;

    /**
     * Constructor which organizes given list of strings in {@link StudentRecord}s.
     * @param content List of {@link StudentRecord}s.
     */
    public StudentDatabase(List<String> content) {
        studentRecords = new ArrayList<>();
        mapOfRecords = new HashMap<>();
        for (String individual : content) {
            String[] studentRecord = individual.replace(" +", " ").replace("\t+", "\t").split("\t");
            String jmbag;
            String firstName = studentRecord[2];
            String lastName = studentRecord[1];
            int finalGrade = -1;
            if (mapOfRecords.containsKey(jmbag = studentRecord[0])) {
                System.out.println("Two identical jmbags have been entered");
                System.exit(1);
            } else if (((finalGrade = Integer.parseInt(studentRecord[3])) > 5) || (finalGrade < 1)) {
                System.out.println("Final grade must be set to a number in range from 1 to 5");
                System.exit(1);
            }
            StudentRecord record = new StudentRecord(jmbag, firstName, lastName, finalGrade);
            mapOfRecords.put(jmbag, record);
            studentRecords.add(record);
        }
    }

    /**
     * Returns {@link StudentRecord} by its JMBAG.
     * @param jmbag JMBAG of the {@link StudentRecord} which will be returned.
     * @return Returns {@link StudentRecord} if JMBAG is corresponds with an entry in a map, returns null if the {@link StudentRecord} doesn't exist.
     */
    public StudentRecord forJMBAG(String jmbag) {
        return mapOfRecords.get(jmbag);
    }

    /**
     * Filter is applied to each entry in a list.
     * @param filter Filter by which the entries will be filtered.
     * @return Returns new list of students which had passed the filter.
     */
    public List<StudentRecord> filter(IFilter filter) {
        List<StudentRecord> temporaryList = new ArrayList<>();
        for (StudentRecord record : studentRecords) {
            if (filter.accepts(record)) {
                temporaryList.add(record);
            }
        }
        return temporaryList;
    }
}
