package hr.fer.zemris.java.tecaj_13.web.form;

import hr.fer.zemris.java.tecaj_13.crypto.Crypto;
import hr.fer.zemris.java.tecaj_13.crypto.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Form which models user login.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LoginForm {
    /**
     * Provided nick
     */
    private String nick;
    /**
     * Provided password.
     */
    private String password;
    /**
     * List of errors in the given values
     */
    private Map<String, String> errors = new HashMap<>();

    /**
     * Fills values from given HttpServletRequest
     * @param req HttpServletRequest
     */
    public void fillWithHttpRequest(HttpServletRequest req) {
        nick = prepare(req.getParameter("nick"));
        password = prepare(req.getParameter("password"));
    }

    /**
     * Checks errors under a given key.
     * @param key Key
     * @return Returns true if errors are found, returns false otherwise.
     */
    public boolean hasError(String key) {
        String value = errors.get(key);
        return value != null;
    }

    /**
     * If given string is null it will return it as an empty string.
     * @param s Given string.
     * @return if s == null returns "" otherwise returns s
     */
    private String prepare(String s) {
        return s == null ? "" : s;
    }

    /**
     * Validates user values and checks stored password against given hashed password.
     * @param passwordHashed Hashed password
     */
    public void validate(String passwordHashed) {
        errors.clear();
        if (nick.equals("")) {
            errors.put("nick", "Nick name is empty");
        }
        if (password.equals("")) {
            errors.put("password", "Password name is empty");
        }
        if (!Util.bytetohex(Crypto.digestMessage(password)).equals(passwordHashed)) {
            errors.put("login", "Invalid username and/or password");
        }
    }

    /**
     * Gets error under the given key
     * @param key Key to errors.
     * @return Returns errors
     */
    public String getError(String key) {
        return errors.get(key);
    }

    /**
     * Gets nick
     * @return Returns nick
     */
    public String getNick() {
        return nick;
    }

    /**
     * gets password
     * @return Returns password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Check if this form is valid
     * @return Returns true if it is valid, returns false otherwise.
     */
    public boolean isValid() {
        return errors.isEmpty();
    }
}

