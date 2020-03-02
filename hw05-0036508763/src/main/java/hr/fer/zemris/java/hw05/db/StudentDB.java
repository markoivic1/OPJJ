package hr.fer.zemris.java.hw05.db;

import hr.fer.zemris.java.hw05.db.lexer.QueryLexer;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * Method used to get queries from the command line.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class StudentDB {
    /**
     * Main method which is used to read from the command line and print appropriate message.
     * @param args No arguments are used from command line.
     */
    public static void main(String[] args) {
        List<String> lines = new ArrayList<>();
        try {
            lines = Files.readAllLines(Paths.get("./database.txt"), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            //
        }
        StudentDatabase db = new StudentDatabase(lines);
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.printf("> ");
            String line = sc.nextLine();
            if (line.equals("exit")) {
                System.out.println("Goodbye!");
                sc.close();
                break;
            }
            if (!line.contains("query")) {
                System.out.println("Please enter a query");
                continue;
            }
            line = line.replace("query", "");
            QueryParser parser;
            try {
                parser = new QueryParser(line);
            } catch (Exception ex) {
                System.out.println("Invalid input!");
                continue;
            }
            List<StudentRecord> records = new ArrayList<>();
            if (parser.isDirectQuery()) {
                StudentRecord r = db.forJMBAG(parser.getQueriedJMBAG());
                if (r == null) {
                    System.out.println("Records selected: 0");
                    continue;
                }
                records.add(r);
            } else {
                try {
                    for (StudentRecord r : db.filter(new QueryFilter(parser.getQuery()))) {
                        records.add(r);
                    }
                } catch (Exception ex) {
                    System.out.println(ex.getMessage());
                    continue;
                }
            }
            List<String> output = RecordFormatter.format(records);
            output.forEach(System.out::println);
            System.out.println("Records selected: " + records.size());
        }
    }
}
