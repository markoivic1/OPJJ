package hr.fer.zemris.java.tecaj_13.model;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.crypto.Util;

import javax.persistence.*;
import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Class which defined table 'blog_users'.
 * @author Marko Ivić
 * @version 1.0.0
 */
@Entity
@Table(name = "blog_users")
public class BlogUser {
    /**
     * Auto generated id
     */
    @GeneratedValue
    private long id;

    /**
     * First name of a user.
     */
    @Column(length = 50)
    private String firstName;

    /**
     * Last name of a user.
     */
    @Column(length = 50)
    private String lastName;

    /**
     * Nick of a user.
     */
    @Id
    @Column(length = 50, nullable = false, unique = true)
    private String nick;

    /**
     * email of a user.
     */
    @Column(length = 50)
    private String email;

    /**
     * Hashed password of a user.
     */
    @Column(length = 40)
    private String passwordHash;

    /**
     * Default constructor.
     */
    public BlogUser() {
    }

    /**
     * Fills data from HttpServletRequest.
     * @param req HttpServletRequest
     */
    public void fillWithHttpRequest(HttpServletRequest req) {
        firstName = req.getParameter("firstname");
        lastName = req.getParameter("lastname");
        email = req.getParameter("email");
        nick = req.getParameter("nick");
        passwordHash = req.getParameter("password") == null ? null : Util.bytetohex(Crypto.digestMessage(req.getParameter("password")));
    }

    /**
     * Gets id
     * @return Returns id.
     */
    public long getId() {
        return id;
    }

    /**
     * Sets id
     * @param id new id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Gets first name.
     * @return Returns first name.
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Sets first name
     * @param firstName new first name
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Gets last name
     * @return Returns last name.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets last name
     * @param lastName new last name
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Gets nick
     * @return Returns nick.
     */
    public String getNick() {
        return nick;
    }

    /**
     * Sets nick
     * @param nick new nick
     */
    public void setNick(String nick) {
        this.nick = nick;
    }

    /**
     * Gets email
     * @return Returns email.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email
     * @param email new email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets hashed password
     * @return Returns hashed password
     */
    public String getPasswordHash() {
        return passwordHash;
    }

    /**
     * Sets hashed password
     * @param passwordHash new hashed password
     */
    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

}
