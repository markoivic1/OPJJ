package hr.fer.zemris.java.custom.scripting.nodes;

/**
 * Visitor interface.
 * Used to iterate over nodes
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface INodeVisitor {
    /**
     * Executes defines implementation for text node.
     * @param node node
     */
    public void visitTextNode(TextNode node);
    /**
     * Executes defines implementation for for loop node.
     * @param node node
     */
    public void visitForLoopNode(ForLoopNode node);
    /**
     * Executes defines implementation for echo node.
     * @param node node
     */
    public void visitEchoNode(EchoNode node);
    /**
     * Executes defines implementation for document node.
     * @param node node
     */
    public void visitDocumentNode(DocumentNode node);

    public void visitNowNode(NowNode node);
}
