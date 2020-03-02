package hr.fer.zemris.java.hw17.jvdraw.object;

/**
 * Models visitor which will execute proper method for each {@link GeometricalObject}.
 * @author Marko Ivić
 * @version 1.0.0.
 */
public interface GeometricalObjectVisitor {
    /**
     * Defines action when Line is visited.
     * @param line Line which is being visited.
     */
    public abstract void visit(Line line);

    /**
     * Defines action when Circle is visited.
     * @param circle Circle which is being visited.
     */
    public abstract void visit(Circle circle);

    /**
     * Defines action when Filled circle is visited.
     * @param filledCircle Filled circle which is being visited.
     */
    public abstract void visit(FilledCircle filledCircle);


    public abstract void visit(FilledTriangle filledTriangle);
}