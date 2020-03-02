package hr.fer.zemris.java.hw07.demo4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Class is used to demonstrate implementations of given tasks.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class StudentDemo {
    /**
     * Method main prints some calculated values.
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        List<String> lines;
        try {
            lines = Files.readAllLines(Paths.get("./studenti.txt"));
        } catch (IOException ex) {
            System.out.println("File student.txt could not be opened.");
            return;
        }
        List<StudentRecord> records = convert(lines);

        printTasks(records);
    }

    private static void printTasks(List<StudentRecord> records) {
        // first
        System.out.println("Zadatak1\n=========");
        System.out.println(vratiBodovaViseOd25(records));
        // second
        System.out.println("Zadatak2\n=========");
        System.out.println(vratiBrojOdlikasa(records));
        // third
        System.out.println("Zadatak3\n=========");
        vratiListuOdlikasa(records).forEach(System.out::println);
        // fourth
        System.out.println("Zadatak4\n=========");
        vratiSortiranuListuOdlikasa(records).forEach(System.out::println);
        // fifth
        System.out.println("Zadatak5\n=========");
        vratiPopisNepolozenih(records).forEach(System.out::println);
        // sixth
        System.out.println("Zadatak6\n=========");
        razvrstajStudentePoOcjenama(records).forEach((k,v) -> System.out.println(k + " => " + v));
        // seventh
        System.out.println("Zadatak7\n=========");
        vratiBrojStudenataPoOcjenama(records).forEach((k,v) -> System.out.println(k + " => " + v));
        // eight
        System.out.println("Zadatak8\n=========");
        razvrstajProlazPad(records).forEach((k,v) -> System.out.println(k + " => " + v));
    }

    /**
     * Counts all of {@link StudentRecord} which have sum of points greater than 25.
     * @param records Records from which the data will be extracted.
     * @return Returns number of {@link StudentRecord} which have sum of points greater than 25.
     */
    private static long vratiBodovaViseOd25(List<StudentRecord> records) {
        return records.stream().filter(s -> s.getBrBodovaNaMI() + s.getBrBodovaNaZI() + s.getBrBodovaLab() > 25)
                .count();
    }

    /**
     * Searches for students which have grade of 5.
     * @param records Records from which the data will be extracted.
     * @return Returns number of students which have a grade of 5.
     */
    private static long vratiBrojOdlikasa(List<StudentRecord> records) {
        return records.stream().filter(s -> s.getOcjena() == 5).count();
    }

    /**
     * Creates a list of {@link StudentRecord} which have a grade of 5.
     * @param records Records from which the data will be extracted.
     * @return Returns list of students which have a grade of 5.
     */
    private static List<StudentRecord> vratiListuOdlikasa(List<StudentRecord> records) {
        return records.stream().filter(s -> s.getOcjena() == 5).collect(Collectors.toList());
    }

    /**
     * Creates a sorted list of {@link StudentRecord} which have a grade of 5.
     * @param records Records from which the data will be extracted.
     * @return Returns sorted list of students which have a grade of 5.
     */
    private static List<StudentRecord> vratiSortiranuListuOdlikasa(List<StudentRecord> records) {
        return records.stream().filter(s -> s.getOcjena() == 5)
                .sorted((s1, s2) -> {
                    Double bodoviOdS1 = s1.getBrBodovaNaMI() + s1.getBrBodovaNaZI() + s1.getBrBodovaLab();
                    Double bodoviOdS2 = s2.getBrBodovaNaMI() + s2.getBrBodovaNaZI() + s2.getBrBodovaLab();
                    return bodoviOdS2.compareTo(bodoviOdS1);
                }).collect(Collectors.toList());
    }

    /**
     * Creates a list of {@link StudentRecord} which have failed.
     * @param records Records from which the data will be extracted.
     * @return Returns list of students which have a grade of 1.
     */
    private static List<String> vratiPopisNepolozenih(List<StudentRecord> records) {
        return records.stream().filter(s -> s.getOcjena() == 1)
                .map(StudentRecord::getJmbag)
                .sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    /**
     * Sorts students by their grades.
     * @param records Records from which the data will be extracted.
     * @return Returns students sorted by their grades.
     */
    private static Map<Integer, List<StudentRecord>> razvrstajStudentePoOcjenama(List<StudentRecord> records) {
        return records.stream().collect(Collectors.groupingBy(StudentRecord::getOcjena));
    }

    /**
     * Calculates number of students that have gotten the same grade.
     * @param records Records from which the data will be extracted.
     * @return Returns map of students mapped by their grades.
     */
    private static Map<Integer, Integer> vratiBrojStudenataPoOcjenama(List<StudentRecord> records) {
        return records.stream()
                .collect(Collectors.toMap(StudentRecord::getOcjena, s -> 1
                        , (numberOfOccurrences, valueOfASingleOccurrence) -> numberOfOccurrences + valueOfASingleOccurrence));

    }

    /**
     * Sorts students that have failed and those who have not.
     * @param records Records from which the data will be extracted.
     * @return Returns map of students.
     */
    private static Map<Boolean, List<StudentRecord>> razvrstajProlazPad(List<StudentRecord> records) {
        return records.stream().collect(Collectors.partitioningBy(s -> s.getOcjena() > 1));
    }

    /**
     * Converts List of String containing {@link StudentRecord} data to {@link StudentRecord}
     * @param lines Lines containing student data.
     * @return Returns list of {@link StudentRecord}.
     */
    private static List<StudentRecord> convert(List<String> lines) {
        List<StudentRecord> studentRecords = new ArrayList<>();
        for (String line : lines) {
            studentRecords.add(new StudentRecord(line));
        }
        return studentRecords;
    }
}
