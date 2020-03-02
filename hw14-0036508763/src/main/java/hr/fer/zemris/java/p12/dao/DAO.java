package hr.fer.zemris.java.p12.dao;

import hr.fer.zemris.java.p12.PollEntry;
import hr.fer.zemris.java.p12.PollOptionEntry;

import java.util.List;

/**
 * Sučelje prema podsustavu za perzistenciju podataka.
 * 
 * @author marcupic
 *
 */
public interface DAO {
    /**
     * Returns all poll options with given id.
     * @param id id of a poll option
     * @return Returns list of {@link PollOptionEntry}
     * @throws DAOException Thrown when connection can't be made.
     */
    List<PollOptionEntry> getPollOptions(long id) throws DAOException;

    /**
     * Returns all rows from Polls table as list of {@link PollEntry}
     * @return List of {@link PollEntry}
     * @throws DAOException Thrown when connection can't be made.
     */
    List<PollEntry> getPolls() throws DAOException;

    /**
     * Used to register a vote under a given id.
     * @param id Id under which to register a vote.
     * @throws DAOException Thrown when connection can't be made.
     */
    void registerAVote(long id) throws DAOException;

    /**
     * Returns single row from table Polls which contain given voteID under id column.
     * @param voteId Value of an ID column.
     * @return Single {@link PollEntry}.
     * @throws DAOException Thrown when connection can't be made.
     */
    PollEntry getPoll(long voteId) throws DAOException;
}