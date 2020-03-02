package hr.fer.zemris.java.tecaj_13.web.form;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.crypto.Util;
import hr.fer.zemris.java.tecaj_13.dao.DAOProvider;
import hr.fer.zemris.java.tecaj_13.model.BlogEntry;
import hr.fer.zemris.java.tecaj_13.model.BlogUser;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Register form models form used in registration of new users.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RegisterForm {
    /**
     * First name of a user
     */
    private String firstName;
    /**
     * last name of a user
     */
    private String lastName;
    /**
     * email of a user
     */
    private String email;

    /**
     * nick of a user
     */
    private String nick;

    /**
     * password of a user
     */
    private String password;
    /**
     * Map of errors that might occur when values are invalid.
     */
    private Map<String, String> errors = new HashMap<>();

    /**
     * Fills values from given HttpServletRequest
     * @param req HttpServletRequest
     */
    public void fillWithHttpRequest(HttpServletRequest req) {
        firstName = prepare(req.getParameter("firstname"));
        lastName = prepare(req.getParameter("lastname"));
        email = prepare(req.getParameter("email"));
        nick = prepare(req.getParameter("nick"));
        password = prepare(req.getParameter("password"));
    }

    /**
     * If given string is null it will return it as an empty string.
     * @param s Given string.
     * @return if s == null returns "" otherwise returns s
     */
    public String prepare(String s) {
        return s == null ? "" : s;
    }

    /**
     * Validates values contained in this form.
     */
    public void validate() {
        errors.clear();
        if (firstName.equals("")) {
            errors.put("firstName", "First name is empty");
        }
        if (lastName.equals("")) {
            errors.put("lastName", "Last name is empty");
        }
        if (email.equals("")) {
            errors.put("email", "Email is empty");
        }
        if (nick.equals("")) {
            errors.put("nick", "Nick name is empty");
        }
        if (password.equals("")) {
            errors.put("password", "Password name is empty");
        }
        if (DAOProvider.getDAO().getBlogUser(nick) != null) {
            errors.put("nick", "User with this nick already exists");
        }
        if (!email.matches("^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$")) {
            errors.put("email", "Invalid email");
        }
    }

    /**
     * Gets first name
     * @return Returns first name
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Gets last name
     * @return Returns last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Gets email
     * @return Returns email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Gets nick
     * @return Returns nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * Gets an error under a given key
     * @param error Key to an error
     * @return Returns null if given error can't be found otherwise returns error.
     */
    public String getError(String error) {
        return errors.get(error);
    }

    /**
     * Checks whether this form has an error with a given key
     * @param error key to an error
     * @return Returns true if an error is found, returns false otherwise.
     */
    public boolean hasError(String error) {
        String errorMessage = errors.get(error);
        return errorMessage != null;
    }

    /**
     * Checks if this form is valid
     * @return Returns true if valid.
     */
    public boolean isValid() {
        return errors.isEmpty();
    }
}
