package hr.fer.zemris.java.p12.dao.sql;

import hr.fer.zemris.java.p12.PollEntry;
import hr.fer.zemris.java.p12.PollOptionEntry;
import hr.fer.zemris.java.p12.dao.DAO;
import hr.fer.zemris.java.p12.dao.DAOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Ovo je implementacija podsustava DAO uporabom tehnologije SQL. Ova
 * konkretna implementacija očekuje da joj veza stoji na raspolaganju
 * preko {@link SQLConnectionProvider} razreda, što znači da bi netko
 * prije no što izvođenje dođe do ove točke to trebao tamo postaviti.
 * U web-aplikacijama tipično rješenje je konfigurirati jedan filter
 * koji će presresti pozive servleta i prije toga ovdje ubaciti jednu
 * vezu iz connection-poola, a po zavrsetku obrade je maknuti.
 *
 * @author marcupic
 */
public class SQLDAO implements DAO {
    /**
     * Returns all rows from PollOptions table which contain pollID equal to given value.
     * @param pollId Returned list will only have entries which contain this pollID
     * @return Returns list of all entries which have given pollID set as pollID
     * @throws DAOException Thrown when connection is unable to be established.
     */
    @Override
    public List<PollOptionEntry> getPollOptions(long pollId) throws DAOException {
        Connection connection = SQLConnectionProvider.getConnection();
        List<PollOptionEntry> optionsEntries = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM PollOptions WHERE pollID = " + pollId + "");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                PollOptionEntry poe = new PollOptionEntry(
                        rs.getLong(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getLong(4),
                        rs.getLong(5)
                );
                optionsEntries.add(poe);
            }
        } catch (SQLException e) {
            System.out.println("Unable to select poll options by pollID");
        }
        return optionsEntries;
    }

    /**
     * Returns all rows contained in table Polls
     * @return Returns all rows contained in server Polls as {@link PollEntry} list
     * @throws DAOException Thrown when connection can't be made.
     */
    @Override
    public List<PollEntry> getPolls() throws DAOException {
        PollEntry pollEntry;
        Connection connection = SQLConnectionProvider.getConnection();
        List<PollEntry> entries = new ArrayList<>();
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM Polls");
            ResultSet rs = pst.executeQuery();
            while (rs.next()) {
                pollEntry = new PollEntry(rs.getLong(1), rs.getString(2), rs.getString(3));
                entries.add(pollEntry);
            }
        } catch (SQLException e) {
            System.out.println("Unable to select Polls");
        }
        return entries;
    }

    /**
     * Used to register a vote under the given ID.
     * @param voteID ID under which the value will be increased.
     * @throws DAOException Thrown when connection can't be established.
     */
    @Override
    public void registerAVote(long voteID) throws DAOException {
        Connection connection = SQLConnectionProvider.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("UPDATE PollOptions SET votesCount = votesCount + 1 WHERE id = " + voteID);
            pst.executeUpdate();
        } catch (SQLException e) {
            System.out.println("An error occured when selecting from the table poll options");
        }
    }

    /**
     * Get row from the table Polls with a specific ID
     * @param voteId Id of a table
     * @return Returns row of a table with corresponding ID.
     * @throws DAOException Thrown when connection can't be made.
     */
    @Override
    public PollEntry getPoll(long voteId) throws DAOException {
        Connection connection = SQLConnectionProvider.getConnection();
        try {
            PreparedStatement pst = connection.prepareStatement("SELECT * FROM Polls WHERE id = "+voteId+"");
            ResultSet rs = pst.executeQuery();
            rs.next();
            return new PollEntry(rs.getLong(1), rs.getString(2), rs.getString(3));
        } catch (SQLException e) {
            System.out.println("Unable to select Polls");
        }
        return null;
    }
}