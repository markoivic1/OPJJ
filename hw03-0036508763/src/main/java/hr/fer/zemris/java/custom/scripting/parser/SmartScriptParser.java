package hr.fer.zemris.java.custom.scripting.parser;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ObjectStack;
import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.lexer.Lexer;
import hr.fer.zemris.java.custom.scripting.lexer.LexerState;
import hr.fer.zemris.java.custom.scripting.lexer.TokenType;
import hr.fer.zemris.java.custom.scripting.nodes.*;

import javax.lang.model.element.VariableElement;
import java.util.Arrays;

/**
 * Parser which is used to identify token values and types and store it accordingly.
 * @author Marko Ivić
 */
public class SmartScriptParser {
    /**
     * Lexer which is used to get tokens
     */
    private Lexer lexer;
    /**
     * Stack which is used for implementation of nested Nodes.
     */
    private ObjectStack node;

    /**
     * Constructor takes a documentBody and passes it to {@link Lexer} which will extract token from it.
     * @param documentBody
     */
    public SmartScriptParser(String documentBody) {
        try {
            node = new ObjectStack();
            lexer = new Lexer(documentBody);
            node.push(new DocumentNode());
            parse();
        } catch (Exception ex) {
            throw new SmartScriptParserException(ex);
        }
    }

    /**
     * Method used to identify the type and a value of every token.
     * @return Returns a object which inherited {@link Node}.
     * @throws SmartScriptParserException If tag is nonexistent or the value could not be sorted properly.
     */
    private Node parse() {
        while (true) {
            if (tokenTypeIs(TokenType.EOF)) {
                return getDocumentNode();
            }
            if (tokenTypeIs(TokenType.TAG_OPENED)) {
                lexer.setState(LexerState.TAG);
                lexer.nextToken();
                if (((String) lexer.getToken().getValue()).toLowerCase().equals("for")) {
                    ForLoopNode forLoopNode = parseFor();
                    ((Node) node.peek()).addChildNode(forLoopNode);
                    node.push(forLoopNode);
                } else if (lexer.getToken().getValue().equals("=")) {
                    EchoNode echoNode = parseEcho();
                    ((Node) node.peek()).addChildNode(echoNode);
                } else if (((String) lexer.getToken().getValue()).toLowerCase().equals("end")) {
                    lexer.setState(LexerState.TEXT);
                    node.pop();
                    if (node.size() <= 0) {
                        throw new SmartScriptParserException();
                    }
                } else {
                    throw new SmartScriptParserException();
                }
            } else if (tokenTypeIs(TokenType.TEXT)) {
                TextNode textNode = new TextNode((String) lexer.getToken().getValue());
                ((Node) node.peek()).addChildNode(textNode);
            }
            lexer.nextToken();
        }
    }

    /**
     * Method used to pare echo tag.
     * @return Returns {@link EchoNode}.
     * @throws SmartScriptParserException Thrown when echo tag contains no elements.
     */
    private EchoNode parseEcho() {
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        lexer.nextToken();
        while (!tokenTypeIs(TokenType.TAG_CLOSED)) {
            sortElementsAndAddToCollectionExpanded(elements, true);
            lexer.nextToken();
        }
        lexer.setState(LexerState.TEXT);
        if (elements.size() == 0) {
            throw new SmartScriptParserException();
        }
        Element[] castedElements = Arrays.copyOf(elements.toArray(), elements.size(), Element[].class);
        return new EchoNode(castedElements);
    }

    /**
     * Method used for parsing for loop
     * @return Returns {@link ForLoopNode}
     * @throws SmartScriptParserException if invalid number of arguments is given or if an invalid argument is given.
     */
    private ForLoopNode parseFor() {
        lexer.nextToken();
        if (lexer.getToken().getType() != TokenType.VARIABLE_NAME) {
            throw new SmartScriptParserException();
        }
        ArrayIndexedCollection elements = new ArrayIndexedCollection();
        elements.add(new ElementVariable((String) lexer.getToken().getValue()));
        lexer.nextToken();
        while (!tokenTypeIs(TokenType.TAG_CLOSED)) {
            sortElementsAndAddToCollectionExpanded(elements, false);
            lexer.nextToken();
        }
        lexer.setState(LexerState.TEXT);
        if (elements.size() == 3) {
            return new ForLoopNode((ElementVariable) elements.get(0), (Element) elements.get(1), (Element) elements.get(2), null);
        } else if (elements.size() == 4) {
            return new ForLoopNode((ElementVariable) elements.get(0), (Element) elements.get(1), (Element) elements.get(2), (Element) elements.get(3));

        }
        throw new SmartScriptParserException();
    }

    /**
     * Decides at which category an {@link Element} will be stored and adds it to a given collection.
     * Can be expanded in case we want to support more types of {@link Element}s.
     * @param collection Collection in which the Elements will be stored.
     * @param expanded If expanded new types of Elements will be supported.
     * @throws SmartScriptParserException If an invalid value is given.
     */
    private void sortElementsAndAddToCollectionExpanded(ArrayIndexedCollection collection, boolean expanded) {
        if (tokenTypeIs(TokenType.VARIABLE_NAME)) {
            collection.add(new ElementVariable((String) lexer.getToken().getValue()));
            return;
        } else if (lexer.getToken().getType() == TokenType.CONSTANT_DOUBLE) {
            try {
                collection.add(new ElementConstantDouble(Double.parseDouble((String) lexer.getToken().getValue())));
                return;
            } catch (Exception ex) {
                throw new SmartScriptParserException();
            }
        } else if (tokenTypeIs(TokenType.CONSTANT_INTEGER)) {
            try {
                collection.add(new ElementConstantInteger(Integer.parseInt((String) lexer.getToken().getValue())));
                return;
            } catch (Exception ex) {
                throw new SmartScriptParserException();
            }
        } else if (tokenTypeIs(TokenType.STRING)) {
            collection.add(new ElementString((String) lexer.getToken().getValue()));
            return;
        }
        if (expanded) {
            if (tokenTypeIs(TokenType.OPERATOR)) {
                collection.add(new ElementOperator((String) lexer.getToken().getValue()));
                return;
            } else if (tokenTypeIs(TokenType.FUNCTION_NAME)) {
                collection.add(new ElementFunction((String) lexer.getToken().getValue()));
                return;
            }
        }
        throw new SmartScriptParserException();
    }

    /**
     * Method which returns an original document body.
     * @param documentNode {@link DocumentNode} from which the original body will be made.
     * @return Returns string of an original document body.
     */
    public static String createOriginalDocumentBody(DocumentNode documentNode) {
        return returnChildAsString(documentNode);
    }

    /**
     * Recursive method used to go through every {@link Node} and {@link Element} in a {@link DocumentNode}.
     * @param node Node which is checked.
     * @return Returns string of every Object contained.
     */
    private static String returnChildAsString(Node node) {
        if (node.numberOfChildren() == 0) {
            return node.toString();
        }
        StringBuilder sb = new StringBuilder();
        if (!(node instanceof DocumentNode)) {
            sb.append(node.toString());
        }
        int numberOfChildren = node.numberOfChildren();
        for (int i = 0; i < numberOfChildren; i++) {
            sb.append(returnChildAsString(node.getChild(i)));
        }
        if (node instanceof ForLoopNode) {
            sb.append("{$ END $}");
        }
        return sb.toString();
    }

    /**
     * Getter for Document Node.
     * @return Returns {@link DocumentNode}.
     */
    public DocumentNode getDocumentNode() {
        return (DocumentNode) node.peek();
    }

    /**
     * Method used for elegant checking of the token type.
     * @param type type which is being compared to a current lexer token
     * @return Returns true if types are the same, returns false otherwise.
     */
    private boolean tokenTypeIs(TokenType type) {
        return lexer.getToken().getType() == type;
    }
}
