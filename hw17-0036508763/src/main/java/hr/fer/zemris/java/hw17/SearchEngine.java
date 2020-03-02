package hr.fer.zemris.java.hw17;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Search engine which supports querying words.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SearchEngine {

    /**
     * Path to txt file of non ending words which are exempt from Vocabulary.
     */
    private static Path NON_ENDING_WORDS = Paths.get("./hrvatski_stoprijeci.txt");

    /**
     * Runs shell which will run commands
     * @param args Path to a directory which contains sources.
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments");
            return;
        }
        Vector vectors = new Vector(Paths.get(args[0]), NON_ENDING_WORDS);
        VectorResults vectorResults = new VectorResults();
        vectors.createTF();
        Scanner sc = new Scanner(System.in);
        System.out.println("Veličina riječnika je " + vectors.getVocabulary().size() + " riječi");
        System.out.print("Enter command> ");
        while (sc.hasNextLine()) {
            String line = sc.nextLine();
            if (line.startsWith("query")) {
                vectorResults = queryCommand(vectors, line);
            } else if (line.startsWith("type")) {
                typeCommand(vectorResults, line);
            } else if (line.equals("results")) {
                vectorResults.printTop10();
            } else if (line.equals("exit")) {
                return;
            } else {
                System.out.println("Invalid command.");
            }
            System.out.print("Enter command> ");
        }
    }

    /**
     * Command which prints result at a given position.
     * @param vectorResults Results
     * @param line Line which is parsed for index.
     */
    private static void typeCommand(VectorResults vectorResults, String line) {
        int index;
        try {
            index = Integer.parseInt(line.substring("type".length()).trim());
            if (index < vectorResults.getResults().size() && index >= 0) {
                printDocument(vectorResults.getResults().get(index));
            } else {
                System.out.println("Index out of range.");

            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid argument given in command type");
        }
    }

    /**
     * Query command searches documents for corresponding words given in the command line.
     * @param vectors Vectors which will be used in this query
     * @param line Line which is parsed for query vector.
     * @return Returns vector results
     */
    private static VectorResults queryCommand(Vector vectors, String line) {
        VectorResults vectorResults;
        vectorResults = new VectorResults();
        List<String> words = Vocabulary.splitWords(line.substring("query".length()));
        Map<String, Double> queryVector = vectors.createQueryVector(words, vectors.getIdf());
        for (VectorDocument document : vectors.getVectorDocuments()) {
            double sim = 0;
            for (Map.Entry<String, Double> entry : queryVector.entrySet()) {
                sim += document.getVector().get(entry.getKey()) * entry.getValue();
            }
            sim /= VectorDocument.norm(queryVector) * VectorDocument.norm(document.getVector());
            vectorResults.registerResult(sim, document.getPath());
        }
        System.out.print("Query is: " + words + "\n");
        System.out.println("Najboljih 10 rezultata:");
        vectorResults.printTop10();
        return vectorResults;
    }

    /**
     * Prints document
     * @param result Takes result which will be printed
     */
    private static void printDocument(VectorResults.Result result) {
        System.out.println("Dokument: " + result.getPath());
        try {
            System.out.println(Files.readString(result.getPath()));
        } catch (IOException e) {
            System.out.println("Unable to open file" + result.getPath());
        }
    }
}
