package hr.fer.zemris.java.webserver;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * Defines request context.
 * Used to store almost every data type.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RequestContext {
    /**
     * Output stream in which the data will be written
     */
    private OutputStream outputStream;
    /**
     * Charset which defines encoding.
     */
    private Charset charset;
    /**
     * Encoding
     */
    private String encoding;
    /**
     * Status code for a header
     */
    private int statusCode;
    /**
     * Status text for a header
     */
    private String statusText;
    /**
     * Mime type for a header.
     */
    private String mimeType;
    /**
     * Length of contents.
     */
    private Long contentLength;
    /**
     * Parameters given in URL
     */
    private Map<String, String> parameters;
    /**
     * Temporary parameters
     */
    private Map<String, String> temporaryParameters;
    /**
     * Persistent parameters
     */
    private Map<String, String> persistentParameters;
    /**
     * Cookies used in communication with client.
     */
    private List<RCCookie> outputCookies;
    /**
     * Flag which indicates whether the header was generated or not.
     */
    private boolean headerGenerated;
    /**
     * Dispatcher
     */
    private IDispatcher dispatcher;
    /**
     * Session ID
     */
    private String sid;

    /**
     * Constructor
     * @param outputStream Stream at which method write writes
     * @param parameters Parameters given as url arguments
     * @param persistentParameters Persistent parameters
     * @param outputCookies Cookies written in header
     * @param sid Session ID
     */
    public RequestContext(OutputStream outputStream, // must not be null!
                          Map<String, String> parameters, // if null, treat as empty
                          Map<String, String> persistentParameters, // if null, treat as empty
                          List<RCCookie> outputCookies, String sid) {
        init();
        Objects.requireNonNull(outputStream);
        this.outputStream = outputStream;
        this.parameters = parameters;
        this.persistentParameters = persistentParameters;
        this.outputCookies = outputCookies;
        this.sid = sid;
    }

    /**
     *
     * Constructor
     * @param outputStream Stream at which method write writes
     * @param parameters Parameters given as url arguments
     * @param persistentParameters Persistent parameters
     * @param outputCookies Cookies written in header
     * @param sid Session ID
     * @param temporaryParameters Temporrarry parameters
     * @param dispatcher Dispatcher
     */
    public RequestContext(OutputStream outputStream, // must not be null!
                          Map<String, String> parameters, // if null, treat as empty
                          Map<String, String> persistentParameters, // if null, treat as empty
                          List<RCCookie> outputCookies, IDispatcher dispatcher,
                          Map<String, String> temporaryParameters, String sid) {
        this(outputStream, parameters, persistentParameters, outputCookies, sid);
        this.dispatcher = dispatcher;
        this.temporaryParameters = temporaryParameters;
    }

    /**
     * Initializes variables to their default values.
     */
    private void init() {
        encoding = "UTF-8";
        statusCode = 200;
        statusText = "OK";
        mimeType = "text/html";
        contentLength = null;
        temporaryParameters = new HashMap<>();
        headerGenerated = false;
    }

    /**
     * Method that retrieves value from parameters map (or null if no association exists):
     * @return parameter
     */
    public String getParameter(String name) {
        return parameters.get(name);
    }

    /**
     * method that retrieves names of all parameters in parameters map (note, this set must be read-only):
     * @return Unmodifiable map.
     */
    public Set<String> getParameterNames() {
        return getUnmodifiableNames(parameters);
    }

    /**
     * method that retrieves value from persistentParameters map (or null if no association exists):
     * @param name name which will be used as a key.
     * @return Value
     */
    public String getPersistentParameter(String name) {
        return persistentParameters.get(name);
    }


    /**
     * method that retrieves names of all parameters in persistent parameters map (note, this set must be read-only):
     * @return Returns names of persistent parameters
     */
    public Set<String> getPersistentParameterNames() {
        return getUnmodifiableNames(persistentParameters);
    }

    /**
     * method that stores a value to persistentParameters map:
     * @param name Name which will be used as a key
     * @param value Value under a given key
     */
    public void setPersistentParameter(String name, String value) {
        persistentParameters.put(name, value);
    }

    /**
     * method that removes a value from persistentParameters map:
     * @param name Removes persistent parameter with a given name
     */
    public void removePersistentParameter(String name) {
        persistentParameters.remove(name);
    }
    /**
     * method that retrieves value from temporaryParameters map (or null if no association exists):
     * @param name Name which will be used as a key.
     * @return returns value under given key
     */
    public String getTemporaryParameter(String name) {
        return temporaryParameters.get(name);
    }
    /**
     * method that retrieves names of all parameters in temporary parameters map (note, this set must be read-only):
     * @return Returns set of unmodifiable temporary parameter names
     */
    public Set<String> getTemporaryParameterNames() {
        return getUnmodifiableNames(temporaryParameters);
    }

    /**
     * Wraps given map in an unmodifiable set
     * @param someParameters Map which will be wrapped
     * @return Returns unmodifiable set.
     */
    private Set<String> getUnmodifiableNames(Map<String, String> someParameters) {
        Set<String> names = new HashSet<>();
        someParameters.forEach((k, v) -> names.add(k));
        return Collections.unmodifiableSet(names);
    }


    /**
     * method that retrieves an identifier which is unique for current user session (for now, implement it to returnempty string):
     * @return Returns session id.
     */
    public String getSessionID() {
        return sid;
    }
    /**
     * method that stores a value to temporaryParameters map:
     * @param name Key
     * @param value value
     */
    public void setTemporaryParameter(String name, String value) {
        temporaryParameters.put(name, value);
    }
    /**
     * method that removes a value from temporaryParameters map:
     * @param name parameter name which will be removed.
     */
    public void removeTemporaryParameter(String name) {
        temporaryParameters.remove(name);
    }

    /**
     * Writes data to output stream
     * @param data Data
     * @return Returns this context
     * @throws IOException Thrown when unable to write.
     */
    public RequestContext write(byte[] data) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(data);
        return this;
    }

    public RequestContext write(byte[] data, int offset, int len) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(data, offset, len);
        return this;
    }

    /**
     * Converts given string to proper charset and writes it as bytes to outputstream
     * @param text Text which will be written
     * @return Returns this context
     * @throws IOException Thrown when unable to write to outputstream
     */
    public RequestContext write(String text) throws IOException {
        if (!headerGenerated) {
            generateHeader();
        }
        outputStream.write(text.getBytes(charset));
        return this;
    }

    /**
     * Generate HTML header.
     */
    private void generateHeader() {
        StringBuilder sb = new StringBuilder();
        // first line
        sb.append("HTTP/1.1 " + statusCode + " " + statusText + "\r\n");
        // second line
        sb.append("Content-Type: " + mimeType);
        if (mimeType.startsWith("text/")) {         // special case
            sb.append("; charset=" + encoding);
        }
        sb.append("\r\n");
        if (contentLength != null) { // optional
            sb.append("Content-Length:" + contentLength).append("\r\n");
        }
        for (RCCookie cookie : outputCookies) {
            // 'Set-Cookie: ' name '=”' value '”; Domain=' domain '; Path=' path '; Max-Age=' maxAge
            sb.append("Set-Cookie: ").append(cookie.getName() + "=").append("\"" + cookie.getValue() + "\"").append("; ");
            if (cookie.getDomain() != null) {
                sb.append("Domain=").append(cookie.getDomain()).append("; ");
            }
            if (cookie.getPath() != null) {
                sb.append("Path=").append(cookie.getPath()).append("; ");
            }
            if (cookie.getMaxAge() != null) {
                sb.append("Max-Age=").append(cookie.getMaxAge());
            }
            if (sb.toString().endsWith("; ")) {
                sb.delete(sb.length() - 2, sb.length());
            }
            sb.append("; HTTPOnly");
            sb.append("\r\n");
        }
        sb.append("\r\n");
        try {
            outputStream.write(sb.toString().getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            System.out.println("Header couldn't be generated.");
        }
        headerGenerated = true;
        charset = Charset.forName(encoding);
    }

    /**
     * Add rc cookie to output cookies
     * @param rcCookie cookie
     */
    public void addRCCookie(RCCookie rcCookie) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        outputCookies.add(rcCookie);
    }

    /**
     * Sets encoding
     * @param encoding encoding
     */
    public void setEncoding(String encoding) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        this.encoding = encoding;
    }

    /**
     * Sets status code
     * @param statusCode status code
     */
    public void setStatusCode(int statusCode) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        this.statusCode = statusCode;
    }

    /**
     * Sets status text
     * @param statusText status text
     */
    public void setStatusText(String statusText) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        this.statusText = statusText;
    }

    /**
     * Sets mime type
     * @param mimeType mime type
     */
    public void setMimeType(String mimeType) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        this.mimeType = mimeType;
    }

    /**
     * Sets content length
     * @param contentLength content length
     */
    public void setContentLength(Long contentLength) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        this.contentLength = contentLength;
    }

    /**
     * Sets output cookies
     * @param outputCookies output cookies
     */
    public void setOutputCookies(List<RCCookie> outputCookies) {
        if (headerGenerated) {
            throw new RuntimeException("Header has already been generated");
        }
        this.outputCookies = outputCookies;
    }

    /**
     * Getter for dispatcher
     * @return dispatcher.
     */
    public IDispatcher getDispatcher() {
        return dispatcher;
    }

    /**
     * Class which defines cookies used in output cookies
     */
    public static class RCCookie {
        /**
         * Name of a cookie
         */
        private String name;
        /**
         * Value of a cookie
         */
        private String value;
        /**
         * Max age of a cookie
         */
        private Integer maxAge;
        /**
         * Domain
         */
        private String domain;
        /**
         * Path
         */
        private String path;

        /**
         * Constructor
         * @param name name of a cookie
         * @param value value of a cookie
         * @param maxAge max age of a cookie
         * @param domain domain
         * @param path path
         */
        public RCCookie(String name, String value, Integer maxAge, String domain, String path) {
            this.name = name;
            this.value = value;
            this.maxAge = maxAge;
            this.domain = domain;
            this.path = path;
        }

        /**
         * Getter for name
         * @return Returns name
         */
        public String getName() {
            return name;
        }

        /**
         * Getter for value
         * @return Returns value
         */
        public String getValue() {
            return value;
        }

        /**
         * Getter for domain
         * @return Returns domain
         */
        public String getDomain() {
            return domain;
        }

        /**
         * Getter for path
         * @return Returns path
         */
        public String getPath() {
            return path;
        }

        /**
         * Getter for max age
         * @return Returns max age
         */
        public Integer getMaxAge() {
            return maxAge;
        }
    }
}
