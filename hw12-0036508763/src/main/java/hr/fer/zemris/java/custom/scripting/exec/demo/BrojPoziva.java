package hr.fer.zemris.java.custom.scripting.exec.demo;

import hr.fer.zemris.java.custom.scripting.exec.SmartScriptEngine;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.webserver.RequestContext;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which counts number of times this context has been called.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class BrojPoziva {
    /**
     * Main method
     * @param args Takes no arguments
     */
    public static void main(String[] args) {
        String documentBody = null;
        try {
            documentBody = Files.readString(Paths.get("./src/main/resources/brojPoziva.smscr"));
        } catch (IOException e) {
        }
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();
        persistentParameters.put("brojPoziva", "3");
        RequestContext rc = new RequestContext(System.out, parameters, persistentParameters, cookies, "");
        new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), rc)
                .execute();
        System.out.println("\nVrijednost u mapi: " + rc.getPersistentParameter("brojPoziva"));
    }
}
