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
 * Method which calculates fibonacci numbers and formats them as HTML
 * @author Marko Ivić
 * @version 1.0.0
 */
public class FibonacciH {
    /**
     * Main method
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        String documentBody = null;
        try {
            documentBody = Files.readString(Paths.get("./src/main/resources/fibonaccih.smscr"));
        } catch (IOException e) {
        }
        Map<String, String> parameters = new HashMap<String, String>();
        Map<String, String> persistentParameters = new HashMap<String, String>();
        List<RequestContext.RCCookie> cookies = new ArrayList<RequestContext.RCCookie>();// create engine and execute it
        new SmartScriptEngine(new SmartScriptParser(documentBody).getDocumentNode(), new RequestContext(System.out, parameters, persistentParameters, cookies, ""))
                .execute();

    }
}
