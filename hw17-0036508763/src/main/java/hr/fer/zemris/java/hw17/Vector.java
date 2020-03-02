package hr.fer.zemris.java.hw17;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

/**
 * This class creates vectors created from vocabulary and vector documents
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Vector {
    /**
     * List of vector documents
     */
    private List<VectorDocument> vectorDocuments;
    /**
     * Vocabulary which contains every word used by the vector documents.
     */
    private Vocabulary vocabulary;
    /**
     * Idf vector
     */
    private Map<String, Double> idf;
    /**
     * Document directory path.
     */
    private Path docDirectory;
    /**
     * Path to non ending words file.
     */
    private Path nonEndingWords;


    /**
     * Constructor which initializes vocabulary.
     * @param docDirectory Path to a documents directory.
     * @param nonEndingWords Path to a non ending words document.
     */
    public Vector(Path docDirectory, Path nonEndingWords) {
        this.docDirectory = docDirectory.normalize().toAbsolutePath();
        this.nonEndingWords = nonEndingWords.normalize().toAbsolutePath();
        vocabulary = new Vocabulary(this.nonEndingWords);
        vocabulary.fillVocabulary(this.docDirectory);
        vectorDocuments = new ArrayList<>();
    }

    /**
     * Creates TF vector for every file under given document directory.
     */
    public void createTF() {
        try {
            Files.walkFileTree(docDirectory, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    VectorDocument vectorDocument = new VectorDocument();
                    for (String word : Vocabulary.splitWords(Files.readString(file))) {
                        if (vocabulary.getVocabulary().contains(word)) {
                            vectorDocument.increaseValue(word);
                        }
                    }
                    for (String word : vocabulary.getVocabulary()) {
                        vectorDocument.getVector().putIfAbsent(word, 0.);
                    }
                    vectorDocument.setPath(file);
                    vectorDocuments.add(vectorDocument);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("Couldn't read from a given root");
        }
        createIDF();
    }

    /**
     * Creates idf for every vector document and vocabulary word.
     */
    private void createIDF() {
        idf = new HashMap<>();
        for (String word : vocabulary.getVocabulary()) {
            int occurrenceCounter = 0;
            for (VectorDocument document : vectorDocuments) {
                if (document.getVector().get(word) > 0) {
                    occurrenceCounter++;
                }
            }
            idf.put(word, Math.log(((double) vectorDocuments.size()) / occurrenceCounter));
        }
        createTFIDF();
    }

    /**
     * Multiplies tf and idf vectors.
     */
    private void createTFIDF() {
        for (VectorDocument document : vectorDocuments) {
            for (Map.Entry<String, Double> entry : idf.entrySet()) {
                document.getVector().put(entry.getKey(), document.getVector().get(entry.getKey()) * idf.get(entry.getKey()));
            }
        }
    }

    /**
     * Creates query vector from given arguments
     * @param query List of words in a query
     * @param idf IDF vector
     * @return Returns map which represents query vector.
     */
    public Map<String, Double> createQueryVector(List<String> query, Map<String, Double> idf) {
        Map<String, Double> vector = new HashMap<>();
        for (String word : query) {
            if (vocabulary.getVocabulary().contains(word)) {
                Double oldValue = vector.get(word);
                double newValue = oldValue == null ? 1 : oldValue + 1;
                vector.put(word, newValue);
            }
        }
        for (Map.Entry<String, Double> word : vector.entrySet()) {
            word.setValue(word.getValue() * idf.get(word.getKey()));
        }
        return vector;
    }

    /**
     * Getter for idf.
     * @return Returns idf.
     */
    public Map<String, Double> getIdf() {
        return idf;
    }

    /**
     * Getter for idf value for a given key
     * @param key Key
     * @return Returns value
     */
    public Double getIdfValue(String key) {
        return idf.get(key);
    }

    /**
     * Getter for list of vector documents
     * @return Returns list of vector documents.
     */
    public List<VectorDocument> getVectorDocuments() {
        return vectorDocuments;
    }

    /**
     * Getter for vocabulary
     * @return Returns vocabulary.
     */
    public Set<String> getVocabulary() {
        return vocabulary.getVocabulary();
    }
}
