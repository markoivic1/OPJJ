package hr.fer.zemris.java.hw17;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Vocabulary which contains every word in a which is contained in documents.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Vocabulary {

    /**
     * Collection of words
     */
    private static Set<String> vocabulary;
    /**
     * Collection of non ending words which are ignored.
     */
    private Set<String> nonEndingWords;

    /**
     * Constructor which creates collection of non ending words
     * @param pathToNonEndingWords Path to non ending words.
     */
    public Vocabulary(Path pathToNonEndingWords) {
        vocabulary = new HashSet<>();
        try {
            nonEndingWords = new HashSet<>(Files.readAllLines(pathToNonEndingWords));
        } catch (IOException e) {
            System.out.println("Couldn't read nonending words from a given path.");
        }
    }

    /**
     * Fill a vocabulary from a given directory path.
     * @param root Path to a directory which contain documents.
     */
    public void fillVocabulary(Path root) {
        vocabulary = new HashSet<>();
        try {
            Files.walkFileTree(root, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Set<String> currentWords = new HashSet<>(splitWords(Files.readString(file)));
                    for (String word : currentWords) {
                        if (!nonEndingWords.contains(word)) {
                            vocabulary.add(word);
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("Couldn't read from a given root");
        }
    }

    /**
     * Method used for splitting words.
     * Method relies on Character.isAlphabetic() method on determining whether some thing is a word or not
     * @param document Single document which contains words
     * @return Returns list of words.
     */
    public static List<String> splitWords(String document) {
        char[] data = document.toLowerCase().toCharArray();
        List<String> words = new ArrayList<>();
        int startingIndex = 0;
        for (int i = 0; i < data.length; i++) {
            if (!Character.isAlphabetic(data[i])) {
                if (i - startingIndex > 0) {
                    words.add(String.valueOf(data, startingIndex, i - startingIndex));
                }
                startingIndex = i + 1;
            }
        }
        if (startingIndex != data.length) {
            words.add(String.valueOf(data, startingIndex, data.length - startingIndex));
        }
        return words;
    }

    /**
     * Getter for vocabulary.
     * @return returns vocabulary.
     */
    public Set<String> getVocabulary(){
        if (vocabulary == null) {
            throw new NullPointerException("Vocabulary was not yet created.");
        }
        return vocabulary;
    }
}
