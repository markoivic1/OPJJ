package hr.fer.zemris.java.hw13.servlet;

import hr.fer.zemris.java.hw13.BandEntry;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

import static hr.fer.zemris.java.hw13.InitUtil.initVotes;

/**
 * Servlet used to register a single vote.
 * Identifies vote by ID.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-glasaj", urlPatterns = {"/glasanje-glasaj"})
@SuppressWarnings("unchecked")
public class GlasanjeGlasajServlet extends HttpServlet {
    /**
     * Creates results file if it doesn't exit.
     * Uses results from a file and increases vote by one for a given id parameter.
     * @param req Http Servlet Request
     * @param resp Http Servlet response
     * @throws ServletException Is never thrown here
     * @throws IOException Thrown when there is something wrong with definition or score files.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Zabiljezi glas...
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        // Napravi datoteku ako je potrebno; ažuriraj podatke koji su u njoj...
        // ...
        String voteID = (String) req.getParameter("id");
        if (voteID == null) {
            return;
        }
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            String definitionFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
            List<BandEntry> bandEntries = Files.readAllLines(Paths.get(definitionFile)).stream().map(e -> new BandEntry(e.split("\\t"))).collect(Collectors.toList());
            initVotes(path, bandEntries);
        }
        List<String> results = Files.readAllLines(path);
        String newContents = "";
        for (String result : results) {
            String[] element = result.split("\\t");
            if (element[0].equals(voteID)) {
                element[1] = String.valueOf(Integer.parseInt(element[1]) + 1);
            }
            newContents += element[0] + "\t" + element[1] + "\n";
        }

        newContents = newContents.substring(0, newContents.length() - 1);

        Files.writeString(path, newContents);

        // Kad je gotovo, pošalji redirect pregledniku I dalje NE generiraj odgovor
        resp.sendRedirect(req.getContextPath() + "/glasanje-rezultati");
    }
}
