package hr.fer.zemris.java.tecaj_13.dao;

import hr.fer.zemris.java.tecaj_13.model.BlogComment;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import java.util.List;

public interface DAO {

	/**
	 * Dohvaća entry sa zadanim <code>id</code>-em. Ako takav entry ne postoji,
	 * vraća <code>null</code>.
	 * 
	 * @param id ključ zapisa
	 * @return entry ili <code>null</code> ako entry ne postoji
	 * @throws DAOException ako dođe do pogreške pri dohvatu podataka
	 */
	public BlogEntry getBlogEntry(Long id) throws DAOException;

	/**
	 * Get blog user with given nick.
	 * @param nick Nick of a user.
	 * @return Returns {@link BlogUser} with a given nick, returns null if it doesn't exist.
	 * @throws DAOException Thrown when user cant be found
	 */
	public BlogUser getBlogUser(String nick) throws DAOException;

	/**
	 * Adds new user to DAO
	 * @param blogUser New user
	 * @throws DAOException Thrown when user can't be added.
	 */
	public void setBlogUser(BlogUser blogUser) throws DAOException;

	/**
	 * Gets all users.
	 * @return Returns every user in DAO as a list.
	 * @throws DAOException Thrown when users can't be accessed.
	 */
	public List<BlogUser> getUsers() throws DAOException;

	/**
	 * Gets entries whose creator has the given nick.
	 * @param nick Nick of a creator.
	 * @return Returns list of blog entries.
	 * @throws DAOException Thrown when entries can't be accessed.
	 */
	public List<BlogEntry> getEntries(String nick) throws DAOException;

	/**
	 * Adds new entry to DAO.
	 * @param blogEntry New entry which will be added.
	 * @throws DAOException Thrown when entry can't be added.
	 */
	public void setEntry(BlogEntry blogEntry) throws DAOException;

	/**
	 * Adds new comment to DAO.
	 * @param blogComment New comment which will be added.
	 * @throws DAOException Thrown when comment can't be added.
	 */
	public void setComment(BlogComment blogComment) throws DAOException;
}