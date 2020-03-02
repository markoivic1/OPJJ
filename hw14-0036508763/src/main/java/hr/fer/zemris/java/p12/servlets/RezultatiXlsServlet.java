package hr.fer.zemris.java.p12.servlets;

import hr.fer.zemris.java.p12.PollOptionEntry;
import hr.fer.zemris.java.p12.dao.DAOProvider;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Creates xls file from entries in "PollOptions" table.
 * Columns are organized as: ID, name, url and score
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "glasanje-xls", urlPatterns = {"/servleti/glasanje-xls"})
public class RezultatiXlsServlet extends HttpServlet {
    /**
     * Used to create xml table.
     * @param req  Request
     * @param resp Response
     * @throws ServletException Never thrown here
     * @throws IOException      Thrown when reading definition or files fails or when creating xls fails.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HSSFWorkbook hwb = new HSSFWorkbook();
        HSSFSheet sheet = hwb.createSheet("Results");

        HSSFRow row = sheet.createRow(0);
        row.createCell((short) 0).setCellValue("ID");
        row.createCell((short) 1).setCellValue("Name");
        row.createCell((short) 2).setCellValue("URL");
        row.createCell((short) 3).setCellValue("Score");
        int i = 1;

        Long pollID = (Long) req.getServletContext().getAttribute("pollID");
        if (pollID == null) {
            return;
        }
        List<PollOptionEntry> pollOptionsEntries = DAOProvider.getDao().getPollOptions(pollID);

        for (PollOptionEntry entry : pollOptionsEntries) {
            row = sheet.createRow((short) i++);
            row.createCell((short) 0).setCellValue(entry.getId());
            row.createCell((short) 1).setCellValue(entry.getOptionTitle());
            row.createCell((short) 2).setCellValue(entry.getOptionLink());
            row.createCell((short) 3).setCellValue(entry.getVotesCount());
        }

        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
        hwb.write(resp.getOutputStream());
    }
}
