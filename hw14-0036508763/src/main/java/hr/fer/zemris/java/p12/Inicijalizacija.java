package hr.fer.zemris.java.p12;

import java.beans.PropertyVetoException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import com.mchange.v2.c3p0.DataSources;

/**
 * Class used for initializing table.
 */
@WebListener
public class Inicijalizacija implements ServletContextListener {

    /**
     * If tables 'Polls' and 'PollOptions' don't exits they are made.
     * If table 'Polls' and 'Polloptions' are empty they are filled with predefined data.
     * @param sce Servlet Context Event
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        Path path = Paths.get(sce.getServletContext().getRealPath("/WEB-INF/dbsettings.properties"));
        List<String> dbSettings;
        try {
            dbSettings = Files.readAllLines(path);
        } catch (IOException e) {
            throw new IllegalArgumentException("No file was found for initializing db");
        }

        dbSettings = dbSettings.stream().map(e -> e.split("=")[1]).collect(Collectors.toList());

        String dbName = dbSettings.get(2);
        String connectionURL = "jdbc:derby://" + dbSettings.get(0) + ":" + dbSettings.get(1) + "/" + dbName + ";user=" + dbSettings.get(3) + ";password=" + dbSettings.get(4);


        //connection = cpds.getConnection();
        ComboPooledDataSource cpds = new ComboPooledDataSource();
        try {
            cpds.setDriverClass("org.apache.derby.jdbc.ClientDriver");
        } catch (PropertyVetoException e1) {
            throw new RuntimeException("Pogreška prilikom inicijalizacije poola.", e1);
        }
        cpds.setJdbcUrl(connectionURL);

        Connection connection;
        try {
            connection = cpds.getConnection();
        } catch (SQLException e) {
            System.out.println("Connection to the database couldn't be made.");
            return;
        }
        PreparedStatement pst = null;
        try {
            pst = connection.prepareStatement("SELECT * FROM Polls");
            pst.executeQuery();
        } catch (SQLException e) {
            if(e.getSQLState().equals("X0Y32")) {
                // table already exists
            } else {
                try {
                    pst = connection.prepareStatement("CREATE TABLE Polls (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, title VARCHAR(150) NOT NULL, message CLOB(2048) NOT NULL)");
                    pst.executeUpdate();
                    pst.close();
                } catch (SQLException ex) {
                    System.out.println("Failed to create table Polls");
                }
            }
        }

        try {
            pst = connection.prepareStatement("SELECT * FROM PollOptions");
            pst.executeQuery();
        } catch (SQLException e) {
            try {
                pst.close();
            } catch (SQLException ex) {

            }
            if (e.getSQLState().equals("X0Y32")) {
                // table already exists
            } else {
                try {
                    pst = connection.prepareStatement("CREATE TABLE PollOptions (id BIGINT PRIMARY KEY GENERATED ALWAYS AS IDENTITY, optionTitle VARCHAR(100) NOT NULL, optionLink VARCHAR(150) NOT NULL, pollID BIGINT, votesCount BIGINT, FOREIGN KEY (pollID) REFERENCES Polls(id))");
                    pst.executeUpdate();
                } catch (SQLException ex) {
                    System.out.println("Failed to create table PollOptions");
                }
            }
        }


        List<Long> ids = new ArrayList<>();
        ResultSet rs;
        try {
            pst = connection.prepareStatement("SELECT * FROM Polls");
            rs = pst.executeQuery();
            while (rs.next()) {
                ids.add(rs.getLong(1));
            }
        } catch (SQLException e) {
            System.out.println("Failed to ");
        }
        if (ids.size() < 2) {
            fillDB(connection);
        }
        sce.getServletContext().setAttribute("hr.fer.zemris.dbpool", cpds);
    }

    private void fillDB(Connection connection) {
        PreparedStatement pst;
        try {
            // inserts to rows in Polls
            pst = connection.prepareStatement("INSERT INTO Polls (title, message) VALUES ('Glasanje za omiljeni bend:', 'Od sljedećih bendova, koji Vam je bend najdraži? Kliknite na link kako biste glasali!')");
            pst.executeUpdate();
            pst = connection.prepareStatement("INSERT INTO Polls (title, message) VALUES ('Najdraža boja:', 'Odaberite najdražu boju!')");
            pst.executeUpdate();

            // Gets indexes
            pst = connection.prepareStatement("SELECT id from Polls");
            ResultSet rs = pst.executeQuery();
            rs.next();
            long id = rs.getLong(1);
            // Inserts rows in polloptions using first index
            pst = connection.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount)" +
                    " VALUES " +
                    "('The Beatles', 'https://www.youtube.com/watch?v=z9ypq6_5bsg', "+ id+ ", 0)," +
                    "('The Platters', 'https://www.youtube.com/watch?v=H2di83WAOhU', "+id+", 0)," +
                    "('The Beach Boys', 'https://www.youtube.com/watch?v=2s4slliAtQU', "+id+", 0)," +
                    "('The Four Seasons', 'https://www.youtube.com/watch?v=y8yvnqHmFds', "+id+", 0)," +
                    "('The Marcels', 'https://www.youtube.com/watch?v=qoi3TH59ZEs', "+id+", 0)," +
                    "('The Everly Brothers', 'https://www.youtube.com/watch?v=tbU3zdAgiX8', "+id+", 0)," +
                    "('The Mamas And The Papas', 'https://www.youtube.com/watch?v=N-aK6JnyFmk', "+id+", 0)");
            pst.executeUpdate();
            rs.next();
            id = rs.getLong(1);
            // Inserts rows in polloptions using second index
            pst = connection.prepareStatement("INSERT INTO PollOptions (optionTitle, optionLink, pollID, votesCount)" +
                    " VALUES " +
                    "('Blue', 'https://en.wikipedia.org/wiki/Blue', "+ id+ ", 0)," +
                    "('Red', 'https://en.wikipedia.org/wiki/Red', "+id+", 0)," +
                    "('White', 'https://en.wikipedia.org/wiki/White', "+id+", 0)," +
                    "('Black', 'https://en.wikipedia.org/wiki/Black', "+id+", 0)," +
                    "('Orange', 'https://en.wikipedia.org/wiki/Orange_(colour)', "+id+", 0)," +
                    "('Green', 'https://en.wikipedia.org/wiki/Green', "+id+", 0)");
            pst.executeUpdate();
            rs.close();
        } catch (SQLException e) {
            System.out.println("Failed to insert new values");
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        ComboPooledDataSource cpds = (ComboPooledDataSource) sce.getServletContext().getAttribute("hr.fer.zemris.dbpool");
        if (cpds != null) {
            try {
                DataSources.destroy(cpds);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}