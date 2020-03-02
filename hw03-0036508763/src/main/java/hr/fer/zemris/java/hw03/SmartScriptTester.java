package hr.fer.zemris.java.hw03;

import hr.fer.zemris.java.custom.scripting.nodes.DocumentNode;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParserException;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser.createOriginalDocumentBody;

public class SmartScriptTester {

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Illegal number of arguments");
            System.exit(1);
        }

        String filepath = args[0];
        String docBody = "";
        try {
            docBody = new String(Files.readAllBytes(Paths.get(filepath)), StandardCharsets.UTF_8);
        } catch (IOException ex) {
            //
        }
        SmartScriptParser parser = null;
        try {
            parser = new SmartScriptParser(docBody);
        } catch (SmartScriptParserException e) {
            System.out.println("Unable to parse document!");
            System.exit(-1);
        } catch (Exception e) {
            System.out.println("If this line ever executes, you have failed this class!");
            System.exit(-1);
        }
        DocumentNode document = parser.getDocumentNode();
        String originalDocumentBody = createOriginalDocumentBody(document);
        System.out.println(originalDocumentBody); // should write something like original                                          // content of docBody
    }
}
