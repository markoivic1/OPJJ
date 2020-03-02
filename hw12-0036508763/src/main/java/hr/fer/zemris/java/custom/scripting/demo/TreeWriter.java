package hr.fer.zemris.java.custom.scripting.demo;


import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.custom.scripting.parser.SmartScriptParser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Method used to visit and print every node
 * @author Marko Ivić
 * @version 1.0.0
 */
public class TreeWriter {
    /**
     * Main method
     * @param args Path to a file
     */
    public static void main(String[] args) {

        if (args.length != 1) {
            System.out.println("Invalid arguments");
            return;
        }

        String fileName = args[0];

        String docBody;
        try {
            docBody = Files.readString(Paths.get(fileName));
        } catch (IOException e) {
            System.out.println("File couldn't be read.");
            return;
        }
        SmartScriptParser p = new SmartScriptParser(docBody);
        WriterVisitor visitor = new WriterVisitor();
        p.getDocumentNode().accept(visitor);
    }

    /**
     * Determines what to do with each type of node
     */
    private static class WriterVisitor implements INodeVisitor {

        @Override
        public void visitNowNode(NowNode node) {
            System.out.println("unsupported");
        }

        @Override
        public void visitTextNode(TextNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitForLoopNode(ForLoopNode node) {
            System.out.print(node.toString());
            for (int i = 0; i < node.numberOfChildren(); i++) {
                System.out.print(node.getChild(i));
            }
            System.out.print("{$ END $}");
        }

        @Override
        public void visitEchoNode(EchoNode node) {
            System.out.print(node.toString());
        }

        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(this);
            }
        }
    }
}
