package hr.fer.zemris.java.webserver;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.*;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

/**
 * Server which is used to execute scripts and open some files.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SmartHttpServer {
    /**
     * Address of a server
     */
    private String address;
    /**
     * Domain name
     */
    private String domainName;
    /**
     * Port at which the server is connected
     */
    private int port;
    /**
     * Number of worker threads
     */
    private int workerThreads;
    /**
     * Indicates how long it takes until session times out.
     */
    private int sessionTimeout;
    /**
     * Map of mime types
     */
    private Map<String, String> mimeTypes = new HashMap<>();
    /**
     * Server thread
     */
    private ServerThread serverThread;
    /**
     * Pool of threads which will execute server's jobs
     */
    private ExecutorService threadPool;
    /**
     * Document root
     */
    private Path documentRoot;
    /**
     * Map which defines workers
     */
    private Map<String, IWebWorker> workersMap = new HashMap<>();
    /**
     * Map which stores sessions.
     */
    private Map<String, SessionMapEntry> sessions = new HashMap<>();
    /**
     * Random value generator
     */
    private Random sessionRandom = new Random();

    /**
     * Main which is used to start server
     * @param args Path to a config file
     */
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Invalid number of arguments");
            return;
        }
        String configFileName = args[0];
        SmartHttpServer smartHttpServer = new SmartHttpServer(configFileName);
        smartHttpServer.start();
    }

    /**
     * Constructor
     * @param configFileName takes config file name
     */
    public SmartHttpServer(String configFileName) {
        // ... do stuff here ...
        this.serverThread = new ServerThread();
        Properties properties = new Properties();
        try {
            properties.load(new BufferedInputStream(Files.newInputStream(Paths.get(configFileName))));
        } catch (IOException e) {
            System.out.println("An error has occured while loading properties");
        }
        initValues(properties);
        setMimeTypes(properties);
        initWorkers(properties);
    }

    /**
     * Initializes workers from a property server.workers
     * @param properties Gets the path from this property.
     */
    @SuppressWarnings("unchecked")
    private void initWorkers(Properties properties) {
        Enumeration<String> enums;
        Properties workersProp = new Properties();
        try {
            workersProp.load(new BufferedInputStream(Files.newInputStream(Paths.get(properties.getProperty("server.workers")))));
        } catch (IOException e) {
            System.out.println("Mime properties couldnt be read.");
        }
        enums = (Enumeration<String>) workersProp.propertyNames();
        while (enums.hasMoreElements()) {
            String path = enums.nextElement();
            String fqcn = workersProp.getProperty(path);
            Object newObject = null;
            try {
                Class<?> referenceToClass = this.getClass().getClassLoader().loadClass(fqcn);
                newObject = referenceToClass.newInstance();
            } catch (ClassNotFoundException e) {
                System.out.println("Class not found.");
            } catch (InstantiationException e) {
                System.out.println("Instantiation error.");
            } catch (IllegalAccessException e) {
                System.out.println("Illegal access.");
            }
            IWebWorker iww = (IWebWorker) newObject;
            workersMap.put(path, iww);
        }
    }

    /**
     * Sets mime type store in mime.properties
     * @param properties Property used to get path to mime.properties
     */
    @SuppressWarnings("unchecked")
    private void setMimeTypes(Properties properties) {
        Properties mimeProp = new Properties();
        try {
            mimeProp.load(new BufferedInputStream(Files.newInputStream(Paths.get(properties.getProperty("server.mimeConfig")))));
        } catch (IOException e) {
            System.out.println("Mime properties couldnt be read.");
        }
        Enumeration<String> enums = (Enumeration<String>) mimeProp.propertyNames();
        while (enums.hasMoreElements()) {
            String key = enums.nextElement();
            String value = mimeProp.getProperty(key);
            mimeTypes.put(key, value);
        }
    }

    /**
     * Initalizes values from server.properties file
     * @param properties Properties
     */
    private void initValues(Properties properties) {
        address = properties.getProperty("server.address");
        domainName = properties.getProperty("server.domainName");
        port = Integer.parseInt(properties.getProperty("server.port"));
        workerThreads = Integer.parseInt(properties.getProperty("server.workerThreads"));
        documentRoot = Paths.get(properties.getProperty("server.documentRoot"));
        sessionTimeout = Integer.parseInt(properties.getProperty("session.timeout"));
    }

    /**
     * Method which starts the server
     */
    protected synchronized void start() {
        if (!serverThread.isAlive()) {
            serverThread.start();
        }
        threadPool = Executors.newFixedThreadPool(workerThreads);
        Thread removeExpiredThread = new RemoveExpiredThread();
        removeExpiredThread.setDaemon(true);
        removeExpiredThread.start();
    }

    /**
     * Method which stops the server
     */
    protected synchronized void stop() {
        threadPool.shutdown();
    }

    /**
     * Thread which removes expired sessions.
     */
    private class RemoveExpiredThread extends Thread {
        /**
         * Sleeps for 5 minutes then clears expired sessions.
         */
        @Override
        public void run() {
            while (true) {
                try {
                    sleep(300 * 1000);
                } catch (InterruptedException e) {
                    System.out.println("Sleep has been interrupted.");
                }
                Iterator<SessionMapEntry> iterator = sessions.values().iterator();
                while (iterator.hasNext()) {
                    SessionMapEntry entry = iterator.next();
                    if (entry.validUntil < (new Date().getTime())) {
                        sessions.values().remove(entry);
                    }
                }
            }
        }
    }

    /**
     * Server thread which accepts connections to the socket.
     */
    protected class ServerThread extends Thread {
        /**
         * Runs server thread
         */
        @Override
        @SuppressWarnings("resource")
        public void run() {
            try {
                ServerSocket serverSocket = new ServerSocket();
                serverSocket.bind(
                        new InetSocketAddress(InetAddress.getByName(address), port)
                );
                while (true) {
                    Socket client = serverSocket.accept();
                    ClientWorker clientWorker = new ClientWorker(client);
                    threadPool.submit(clientWorker);
                }
            } catch (IOException e) {
                System.out.println("Socket error");
            }
        }
    }

    /**
     * Define session entry
     */
    private static class SessionMapEntry {
        /**
         * Session id
         */
        String sid;
        /**
         * Host
         */
        String host;
        /**
         * Time in millis until this cookies is valid
         */
        long validUntil;
        /**
         * map
         */
        Map<String, String> map;
    }

    /**
     * Class which defines single client worker.
     * Every time a new socket is accepted a client worker is made and added to thread pool
     */
    private class ClientWorker implements Runnable, IDispatcher {

        /**
         * Length of a session id
         */
        private final int SID_LENGTH = 20;

        /**
         * Socket
         */
        private Socket csocket;
        /**
         * Input stream
         */
        private PushbackInputStream istream;
        /**
         * Output stream
         */
        private OutputStream ostream;
        /**
         * Version of HTML
         */
        private String version;
        /**
         * Method of HTML
         */
        private String method;
        /**
         * Host url
         */
        private String host;
        /**
         * Parameters given by the url arguments
         */
        private Map<String, String> params = new HashMap<>();
        /**
         * Temporary parameters
         */
        private Map<String, String> tempParams = new HashMap<>();
        /**
         * Permanent parameters
         */
        private Map<String, String> permPrams = new HashMap<>();
        /**
         * Output cookies
         */
        private List<RequestContext.RCCookie> outputCookies = new ArrayList<>();
        /**
         * Session id
         */
        private String SID;
        /**
         * Context
         */
        private RequestContext context = null;

        /**
         * Constructor
         * @param csocket Socket of this client.
         */
        public ClientWorker(Socket csocket) {
            super();
            this.csocket = csocket;
        }

        /**
         * Generates sid and initializes values.
         */
        private void generateSid() {
            String sid = generateRandomSid();
            SessionMapEntry entry = new SessionMapEntry();
            entry.sid = sid;
            entry.validUntil = (new Date()).getTime() + sessionTimeout * 1000;
            if (entry.host == null) {
                entry.map = new ConcurrentHashMap<>();
            }
            entry.host = host;
            this.SID = sid;
            sessions.put(sid, entry);
            outputCookies.add(new RequestContext.RCCookie("sid", SID, null, host, "/"));
        }

        /**
         * Generate new 20 uppercase characters long session id
         */
        private String generateRandomSid() {
            Iterator<Integer> iterator = sessionRandom.ints('A', 'Z' + 1).iterator();
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < SID_LENGTH; i++) {
                sb.append((char) (iterator.next().intValue()));
            }
            return sb.toString();
        }

        /**
         * Method which is used to disptach requests to their appropriate implementation.
         * @param urlPath path of a request
         * @param directCall Flag which indicates whether this is a direct call from client.
         * @throws Exception Exception
         */
        public void internalDispatchRequest(String urlPath, boolean directCall) throws Exception {
            Path requestedPath = SmartHttpServer.this.documentRoot.resolve(Paths.get(urlPath.substring(1)));
            if (directCall && (urlPath.equals("/private") || urlPath.startsWith("/private/"))) {
                sendNotFound();
                return;
            }
            if (urlPath.startsWith("/ext/")) {
                Class<?> referenceToClass = this.getClass().getClassLoader().loadClass("hr.fer.zemris.java.webserver.workers." + urlPath.substring("/ext/".length()));
                Object newObject = referenceToClass.newInstance();
                IWebWorker iww = (IWebWorker) newObject;
                iww.processRequest(getContext());
                csocket.close();
                return;
            }
            if (workersMap.containsKey(urlPath)) {
                try {
                    synchronized (ClientWorker.class) {
                        workersMap.get(urlPath).processRequest(getContext());
                    }
                } catch (Exception e) {
                    System.out.println("Worker couldn't be obtained.");
                }
                csocket.close();
                return;
            }
            if (!Files.exists(requestedPath)
                    || !Files.isRegularFile(requestedPath)
                    || !Files.isReadable(requestedPath)) {
                sendNotFound();
                return;
            }
            String fileExtension = requestedPath.toString().substring(requestedPath.toString().substring(1).indexOf(".") + 2);
            String mimeType = SmartHttpServer.this.mimeTypes.get(fileExtension);
            mimeType = mimeType == null ? "application/octet-stream" : mimeType;
            getContext().setMimeType(mimeType);
            getContext().setStatusCode(200);

            if (urlPath.endsWith(".smscr")) {
                if (!requestedPath.startsWith(SmartHttpServer.this.documentRoot)) {
                    sendForbidden();
                    return;
                }
                readScript(requestedPath, getContext());
            } else {
                try {
                    getContext().write(Files.readAllBytes(requestedPath));
                } catch (IOException e) {
                    System.out.println("File couldn't be read.");
                }
            }
            closeSocket();
        }

        /**
         * Dispatches internal request
         * @param urlPath Url which is used to dispatch requests
         * @throws Exception Exception
         */
        @Override
        public void dispatchRequest(String urlPath) throws Exception {
            internalDispatchRequest(urlPath, false);
        }

        /**
         * Job that this client needs to do.
         */
        @Override
        public void run() {
            try {
                this.ostream = csocket.getOutputStream();
                this.istream = new PushbackInputStream(csocket.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
            List<String> request;
            try {
                request = readRequest();
            } catch (IOException e) {
                System.out.println("Header cant be read");
                return;
            }
            if (request == null || request.size() < 1) {
                sendBadRequest();
                return;
            }
            String firstLine = request.get(0);
            String[] elements = firstLine.split(" +");
            if (!elements[0].toUpperCase().equals("GET") ||
                    !(elements[2].toUpperCase().equals("HTTP/1.0") ||
                            elements[2].toUpperCase().equals("HTTP/1.1"))) {
                sendBadRequest();
                return;
            }
            this.method = elements[0];
            String requestedHeaderPath = elements[1];
            this.version = elements[2];
            for (String header : request) {
                if (header.startsWith("Host:")) {
                    host = header.substring("Host:".length()).trim();
                    if (host.contains(":")) {
                        host = host.substring(0, host.indexOf(':'));
                    }
                    break;
                }
            }
            if (host == null) {
                host = domainName;
            }
            int indexOfQuestionmark = requestedHeaderPath.indexOf("?");
            String path = indexOfQuestionmark != -1 ? requestedHeaderPath.substring(0, indexOfQuestionmark) : requestedHeaderPath;
            String paramString = indexOfQuestionmark != -1 ? requestedHeaderPath.substring(indexOfQuestionmark + 1) : "";

            checkSession(request);
            parseParameters(paramString);

            try {
                internalDispatchRequest(path, true);
            } catch (Exception e) {
                System.out.println("This url can't be dispatched");
            }
        }

        /**
         * Checks some whether the header is valid.
         * @param header Lines of header in a list
         */
        private void checkSession(List<String> header) {
            System.out.print("");
            String sidCandidate = null;

            for (String line : header) {
                if (!line.startsWith("Cookie:")) {
                    continue;
                }
                for (String cookie : line.substring("Cookie: ".length()).replace(";", "").split(" +")) {
                    if (cookie.startsWith("sid")) {
                        sidCandidate = cookie.split("=")[1].replace("\"", "");
                        break;
                    }
                }
            }
            sessionUpdate(sidCandidate);
        }

        private void sessionUpdate(String sidCandidate) {
            synchronized (SessionMapEntry.class) {
                if (sidCandidate == null) {
                    generateSid();
                    return;
                }

                SessionMapEntry entry = sessions.get(sidCandidate);
                if (entry == null || !entry.host.equals(host)) {
                    generateSid();
                } else if (entry.validUntil < (new Date()).getTime()) {
                    sessions.remove(sidCandidate);
                    generateSid();
                } else {
                    entry.validUntil = (new Date()).getTime() + sessionTimeout * 1000;
                    sessions.put(sidCandidate, entry);
                }
                entry = sessions.get(sidCandidate);
                if (entry == null) {
                    entry = sessions.get(SID);
                }
                permPrams = entry.map;

            }
        }

        /**
         * Closes socket
         */
        private void closeSocket() {
            try {
                csocket.close();
            } catch (IOException e) {
                System.out.println("Socket is unable to close.");
            }
        }

        /**
         * Creates and returns new context if it doesn't exist otherwise it returns existing
         * @return Returns context.
         */
        private RequestContext getContext() {
            if (context == null) {
                context = new RequestContext(ostream, params, permPrams, outputCookies, this, tempParams, SID);
            }
            return context;
        }

        /**
         * Reads script to a document body
         * @param requestedPath Path to a script
         * @param rc Context
         */
        private void readScript(Path requestedPath, RequestContext rc) {
            String documentBody = null;
            try {
                documentBody = Files.readString(requestedPath);
            } catch (IOException e) {
                System.out.println("Unable to read document body");
            }
            new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc).execute();
        }

        /**
         * Header that is sent when not found message is appropriate.
         */
        private void sendNotFound() {
            try {
                ostream.write((version + " + 404 Not Found\n" +
                        "Content-Type: text/html; charset=UTF-8").getBytes(StandardCharsets.US_ASCII));
            } catch (IOException e) {
                System.out.println("Not found.");
            }
            closeSocket();
        }

        /**
         * Header that is sent when forbidden message is appropriate.
         */
        private void sendForbidden() {
            try {
                ostream.write((version + " 403 Forbidden\n" +
                        "Content-Type: text/html; charset=UTF-8").getBytes(StandardCharsets.US_ASCII));
            } catch (IOException e) {
                System.out.println("Forbidden.");
            }
            closeSocket();
        }

        /**
         * Parses parameter from given argument
         * @param paramString String of parameters
         */
        private void parseParameters(String paramString) {
            if (paramString.equals("")) {
                return;
            }
            for (String parameter : paramString.split("&")) {
                String[] keyAndValue = parameter.split("=");
                params.put(keyAndValue[0], keyAndValue[1]);
            }
        }

        /**
         * Header that is sent when bad request message is appropriate.
         */
        private void sendBadRequest() {
            try {
                ostream.write((version + " 400 Bad request\n" +
                        "Content-Type: text/html; charset=UTF-8").getBytes(StandardCharsets.US_ASCII));
            } catch (IOException e) {
                System.out.println("Bad request.");
            }
            closeSocket();
        }

        /**
         * Reads request and stores it in a list of strings by lines
         * @return Returns list of string
         * @throws IOException Thrown when unable to read from output stream
         */
        private List<String> readRequest()
                throws IOException {

            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            int state = 0;
            l:
            while (true) {
                int b = istream.read();
                if (b == -1) return null;
                if (b != 13) {
                    bos.write(b);
                }
                switch (state) {
                    case 0:
                        if (b == 13) {
                            state = 1;
                        } else if (b == 10) state = 4;
                        break;
                    case 1:
                        if (b == 10) {
                            state = 2;
                        } else state = 0;
                        break;
                    case 2:
                        if (b == 13) {
                            state = 3;
                        } else state = 0;
                        break;
                    case 3:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                    case 4:
                        if (b == 10) {
                            break l;
                        } else state = 0;
                        break;
                }
            }
            return Arrays.stream((new String(bos.toByteArray())).split("\n")).collect(Collectors.toList());
        }
    }
}
