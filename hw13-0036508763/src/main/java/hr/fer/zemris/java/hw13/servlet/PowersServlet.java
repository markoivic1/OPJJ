package hr.fer.zemris.java.hw13.servlet;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Creates .xls file calculated by given parameters.
 * a = starting number [-100, 100]
 * b = ending number [-100, 100]
 * n = power and number of pages of a xls document [1, 5]
 *
 * if a > b then they swap.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
@WebServlet(name = "powers", urlPatterns = {"/powers"})
public class PowersServlet extends HttpServlet {
    /**
     * Creates xls document with given parameters.
     * @param req Request
     * @param resp Response
     * @throws ServletException Thrown if setting content type or header fails.
     * @throws IOException Thrown when xls document is unable to be created.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Integer a;
        Integer b;
        Integer n;
        try {
            a = Integer.parseInt(req.getParameter("a"));
            b = Integer.parseInt(req.getParameter("b"));
            n = Integer.parseInt(req.getParameter("n"));
        } catch (NumberFormatException e) {
            req.getRequestDispatcher("/WEB-INF/invalidpowers.jsp").forward(req, resp);
            return;
        }
        if (a < -100 || a > 100
                || b < -100 || b > 100
                || n < 1 || n > 5) {
            req.getRequestDispatcher("/WEB-INF/invalidpowers.jsp").forward(req, resp);
            return;
        }
        HSSFWorkbook hwb = new HSSFWorkbook();
        for (int i = 1; i <= n; i++) {
            HSSFSheet sheet = hwb.createSheet("Power of " + i);
            for (int j = a; j <= b; j++) {
                HSSFRow row = sheet.createRow((short) j-a);
                row.createCell((short) 0).setCellValue(j);
                row.createCell((short) 1).setCellValue(Math.pow(j, i));
            }
        }
        resp.setContentType("application/vnd.ms-excel");
        resp.setHeader("Content-Disposition", "attachment; filename=\"tablica.xls\"");
        hwb.write(resp.getOutputStream());
    }
}
