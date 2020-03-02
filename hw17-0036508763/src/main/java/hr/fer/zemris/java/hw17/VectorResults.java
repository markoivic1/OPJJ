package hr.fer.zemris.java.hw17;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Vector results representation.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class VectorResults {

    /**
     * Static method which is used in determining whether the number is equal to zero
     */
    private static double ZERO = 0.00001;

    /**
     * List of results
     */
    private List<Result> results;

    /**
     * Constructor.
     */
    public VectorResults() {
        results = new ArrayList<>();
    }

    /**
     * Register a result to an internal collection of results
     * @param sim Sim value
     * @param path Path to a document
     */
    public void registerResult(double sim, Path path) {
        if (sim > ZERO) {
            results.add(new Result(sim, path));
        }
    }

    /**
     * Print top 10 results.
     */
    public void printTop10() {
        results.sort(Comparator.comparing(Result::getSim).reversed());
        for (int i = 0; i < 10 && i < results.size(); i++) {
            VectorResults.Result result = results.get(i);
            System.out.println("[" + i + "]" + "(" +  (double)Math.round(result.getSim() * 10000) / 10000  + ")" + result.getPath());
        }
    }

    /**
     * Get list of results.
     * @return Returns list of results.
     */
    public List<Result> getResults() {
        results.sort(Comparator.comparingDouble(Result::getSim).reversed());
        return results;
    }

    /**
     * Class which represents single result.
     */
    public class Result {
        /**
         * Sim value.
         */
        private double sim;
        /**
         * Path to a document
         */
        private Path path;

        /**
         * Constructor
         * @param sim sim value
         * @param path Path to a document
         */
        public Result(double sim, Path path) {
            this.sim = sim;
            this.path = path;
        }

        /**
         * Getter for sim
         * @return Returns sim
         */
        public double getSim() {
            return sim;
        }

        /**
         * Getter for a path
         * @return Returns path.
         */
        public Path getPath() {
            return path;
        }
    }
}
