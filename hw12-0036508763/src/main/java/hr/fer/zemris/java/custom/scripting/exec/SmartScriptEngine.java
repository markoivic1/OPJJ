package hr.fer.zemris.java.custom.scripting.exec;

import hr.fer.zemris.java.custom.scripting.elems.*;
import hr.fer.zemris.java.custom.scripting.nodes.*;
import hr.fer.zemris.java.webserver.RequestContext;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Stack;

/**
 * Engine for the .smscr files.
 * This class is used to process .smscr files in their predefined way.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SmartScriptEngine {
    /**
     * Document node which holds all of the other nodes..
     */
    private DocumentNode documentNode;
    /**
     * Context
     */
    private RequestContext requestContext;
    /**
     * Stack with multiple keys
     */
    private ObjectMultistack multistack = new ObjectMultistack();
    /**
     * Visitor processes the files
     */
    private INodeVisitor visitor = new INodeVisitor() {
        @Override
        public void visitNowNode(NowNode node) {
            LocalDateTime now = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(node.getText().asText());
            String formatDateTime = now.format(formatter);
            try {
                requestContext.write(formatDateTime);
            } catch (IOException e) {
                //
            }
        }

        /**
         * Writes node's text to request context
         * @param node Node
         */

        @Override
        public void visitTextNode(TextNode node) {
            try {
                requestContext.write(node.getText());
            } catch (IOException e) {
                System.out.println("Text node's text couldn't be written.");
            }
        }

        /**
         * Defines for loop with start, step and end expressions.
         * For each iteration it will call {@link Node#accept(INodeVisitor)} for every child
         * @param node
         */
        @Override
        public void visitForLoopNode(ForLoopNode node) {
            ValueWrapper value = new ValueWrapper(node.getStartExpression().asText());
            multistack.push(node.getVariable().asText(), value);
            ValueWrapper endValue = new ValueWrapper(node.getEndExpression().asText());
            while (value.numCompare(endValue) <= 0) {
                for (int i = 0; i < node.numberOfChildren(); i++) {
                    node.getChild(i).accept(visitor);
                }
                value = multistack.pop(node.getVariable().asText());
                value.add(node.getStepExpression().asText());
                multistack.push(node.getVariable().asText(), value);
            }
            multistack.pop(node.getVariable().asText());
        }

        /**
         * Decides what to do with each echo node
         * @param node node
         */
        @Override
        public void visitEchoNode(EchoNode node) {
            Stack<Object> tempStack = new Stack<>();
            for (Element element : node.getElements()) {
                if (element instanceof ElementConstantDouble) {
                    tempStack.push(((ElementConstantDouble) element).getValue());
                }else if (element instanceof ElementConstantInteger) {
                    tempStack.push(((ElementConstantInteger) element).getValue());
                } else if (element instanceof ElementString) {
                    tempStack.push(element.asText());
                } else if (element instanceof ElementVariable) {
                    tempStack.push(multistack.peek(element.asText()).getValue());
                } else if (element instanceof ElementOperator) {
                    tempStack.push(calculateValue(tempStack, element));
                } else if (element instanceof ElementFunction) {
                    FunctionUtil.executeFunction(tempStack, (ElementFunction) element, requestContext);
                }
            }
            tempStack.forEach((v) -> {
                try {
                    requestContext.write(v.toString());
                } catch (IOException e) {
                    System.out.println("An error has occured while writing to a request context");
                }
            });
        }

        /**
         * Calculates value when an operator has occured.
         * @param stack Stack
         * @param element Elemnet
         * @return Returns calculated value
         */
        private Object calculateValue(Stack<Object> stack, Element element) {
            ValueWrapper firstValue = new ValueWrapper(stack.pop());
            ValueWrapper secondValue = new ValueWrapper(stack.pop());
            switch (element.asText()) {
                case "+":
                    firstValue.add(secondValue);
                    break;
                case "-":
                    firstValue.subtract(secondValue);
                    break;
                case "*":
                    firstValue.multiply(secondValue);
                    break;
                case "/":
                    firstValue.divide(secondValue);
                    break;
            }
            return firstValue.getValue();
        }

        /**
         * Calls {@link Node#accept(INodeVisitor)} for every child
         * @param node node
         */
        @Override
        public void visitDocumentNode(DocumentNode node) {
            for (int i = 0; i < node.numberOfChildren(); i++) {
                node.getChild(i).accept(visitor);
            }
        }
    };

    /**
     * Constructor
     * @param documentNode Document node
     * @param requestContext Context
     */
    public SmartScriptEngine(DocumentNode documentNode, RequestContext requestContext) {
        this.documentNode = documentNode;
        this.requestContext = requestContext;
    }

    /**
     * Calls {@link Node#accept(INodeVisitor)} on document node.
     */
    public void execute() {
        documentNode.accept(visitor);
    }
}
