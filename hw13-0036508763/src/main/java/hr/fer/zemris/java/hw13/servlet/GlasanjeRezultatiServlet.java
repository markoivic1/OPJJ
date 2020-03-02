package hr.fer.zemris.java.hw13.servlet;

import hr.fer.zemris.java.hw13.BandEntry;
import hr.fer.zemris.java.hw13.InitUtil;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static hr.fer.zemris.java.hw13.InitUtil.initScoreMap;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-rezultati", urlPatterns = {"/glasanje-rezultati"})
@SuppressWarnings("unchecked")
public class GlasanjeRezultatiServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Pročitaj rezultate iz /WEB-INF/glasanje-rezultati.txt
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        // Napravi datoteku ako je potrebno; inače je samo pročitaj...
        Path path = Paths.get(fileName);

        String definitionFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<BandEntry> bandEntries = Files.readAllLines(Paths.get(definitionFile)).stream().map(e -> new BandEntry(e.split("\\t"))).collect(Collectors.toList());

        if (!Files.exists(path)) {
            InitUtil.initVotes(path, bandEntries);
        }

        String[] votes = Files.readString(path).split("\n");
        Map<String, Integer> scoreMap = initScoreMap(votes);
        for (BandEntry entry : bandEntries) {
            entry.setScore(scoreMap.get(entry.getID()));
        }

        bandEntries.sort(Comparator.comparing(BandEntry::getScore).reversed());
        req.setAttribute("rezultati", bandEntries);
        List<BandEntry> winners = new ArrayList<>();
        winners.add(bandEntries.get(0));

        for (int i = 1; i < bandEntries.size(); i++) {
            if (bandEntries.get(0).getScore() == bandEntries.get(i).getScore()) {
                winners.add(bandEntries.get(i));
                continue;
            }
            break;
        }

        req.setAttribute("pobjednici", winners);
        // Pošalji ih JSP-u...
        req.getRequestDispatcher("/WEB-INF/pages/glasanjeRez.jsp").forward(req, resp);
    }
}
