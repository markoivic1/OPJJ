package hr.fer.zemris.java.hw13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Utility class used to initializes needed things.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class InitUtil {
    /**
     * Initializes file which contains votes.
     * ID   score
     * all scores are set to 0
     * @param path Path to a file
     * @param bandEntries List of bands
     * @throws IOException Thrown when creating or writing to a given path fails.
     */
    public static void initVotes(Path path, List<BandEntry> bandEntries) throws IOException {
        try {
            Files.createFile(path);
        } catch (IOException ex) {
            System.out.println(ex);
        }
        String content = "";
        for (BandEntry entry : bandEntries) {
            content += entry.getID() + "\t" + "0\n";
        }
        content = content.substring(0, content.length() - 1);
        Files.writeString(path, content);
    }

    /**
     * Creates score map from a given array of entries.
     * @param entries Array of vote entries which contain ID and score
     * @return Returns initialized map.
     */
    public static Map<String, Integer> initScoreMap(String[] entries) {
        Map<String, Integer> scoreMap = new HashMap<>();
        for (String entry : entries) {
            String[] values = entry.split("\\t");
            scoreMap.put(values[0], Integer.parseInt(values[1]));
        }
        return scoreMap;
    }
}
