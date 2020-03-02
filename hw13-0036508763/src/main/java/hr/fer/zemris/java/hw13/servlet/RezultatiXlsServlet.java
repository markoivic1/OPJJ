package hr.fer.zemris.java.hw13.servlet;

import hr.fer.zemris.java.hw13.BandEntry;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static hr.fer.zemris.java.hw13.InitUtil.initScoreMap;
import static hr.fer.zemris.java.hw13.InitUtil.initVotes;

/**
 * Creates xls file from data voting definition and results.
 * Columns are organized as: ID, name, url and score
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-xls", urlPatterns = {"/glasanje-xls"})
public class RezultatiXlsServlet extends HttpServlet {
    /**
     * Creates xls file from a definition and result files.
     * @param req Request
     * @param resp Response
     * @throws ServletException Never thrown here
     * @throws IOException Thrown when reading definition or files fails or when creating xls fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String fileName = req.getServletContext().getRealPath("/WEB-INF/glasanje-rezultati.txt");
        Path path = Paths.get(fileName);
        String definitionFile = req.getServletContext().getRealPath("/WEB-INF/glasanje-definicija.txt");
        List<BandEntry> bandEntries = Files.readAllLines(Paths.get(definitionFile)).stream().map(e -> new BandEntry(e.split("\\t"))).collect(Collectors.toList());

        if (!Files.exists(path)) {
            initVotes(path, bandEntries);
        }

        String[] votes = Files.readString(path).split("\n");
        Map<String, Integer> scoreMap = initScoreMap(votes);
        for (BandEntry entry : bandEntries) {
            entry.setScore(scoreMap.get(entry.getID()));
        }


        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Results");

        HSSFRow row = sheet.createRow(0);
        row.createCell((short) 0).setCellValue("ID");
        row.createCell((short) 1).setCellValue("Name");
        row.createCell((short) 2).setCellValue("URL");
        row.createCell((short) 3).setCellValue("Score");
        int i = 1;
        for (BandEntry entry : bandEntries) {
            row = sheet.createRow((short) i++);
            row.createCell((short) 0).setCellValue(entry.getID());
            row.createCell((short) 1).setCellValue(entry.getName());
            row.createCell((short) 2).setCellValue(entry.getURL());
            row.createCell((short) 3).setCellValue(entry.getScore());
        }

        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
        hwb.write(resp.getOutputStream());
    }
}
