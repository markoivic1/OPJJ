package hr.fer.zemris.java.custom.scripting.nodes;

import hr.fer.zemris.java.custom.scripting.elems.Element;
import hr.fer.zemris.java.custom.scripting.elems.ElementVariable;

import java.util.Objects;

/**
 * Class which represents for loop.
 * @author Marko Ivić
 */
public class ForLoopNode extends Node {
    /**
     * A variable
     */
    private ElementVariable variable;
    /**
     * An expression at which the for loop starts.
     */
    private Element startExpression;
    /**
     * An expression at which the for loop ends.
     */
    private Element endExpression;
    /**
     * A step expression.
     */
    private Element stepExpression;

    /**
     * Constructor which takes expressions.
     * @param variable {@link ElementVariable}  Needs to be of valid name which starts with a letter and is follower by numbers, letters and underscores
     * @param startExpression An expression needs to be be of type {@link Element}
     * @param endExpression An expression needs to be be of type {@link Element}
     * @param stepExpression An expression needs to be be of type {@link Element} or null.
     * @throws NullPointerException Throw when any of the variable, startexpression and endExpression is null.
     */
    public ForLoopNode(ElementVariable variable, Element startExpression, Element endExpression, Element stepExpression) {
        if ((variable == null) ||
                (startExpression == null) ||
                endExpression == null) {
            throw new NullPointerException();
        }

        this.variable = variable;
        this.startExpression = startExpression;
        this.endExpression = endExpression;
        this.stepExpression = stepExpression;
    }

    public void accept(INodeVisitor visitor) {
        visitor.visitForLoopNode(this);
    }

    /**
     * When an output of this method is parsed it will be the an object with the same value.
     * @return Returns values as strings surrounded with "{$ for" from the left and "$}" from the right.
     */
    @Override
    public String toString() {
        String step = "";
        if (stepExpression != null) {
            step = stepExpression.asText();
        }
        return "{$ FOR " + variable.asText() +
                " " + startExpression.asText() +
                " " + endExpression.asText() +
                " " + step + " $}";
    }

    /**
     * Method used for comparing.
     * @param o Object which is compared to this one.
     * @return Returns true if every value is the same, returns false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        ForLoopNode that = (ForLoopNode) o;
        if (!(Objects.equals(variable, that.variable) &&
                Objects.equals(startExpression, that.startExpression) &&
                Objects.equals(endExpression, that.endExpression) &&
                Objects.equals(stepExpression, that.stepExpression))) {
            return false;
        }
        return super.equals(o);
    }

    /**
     * Method with proper implementation of hash code.
     * @return Returns hash code.
     */
    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), variable, startExpression, endExpression, stepExpression);
    }

    /**
     * Getter for variable.
     * @return Returns variable.
     */
    public ElementVariable getVariable() {
        return variable;
    }

    /**
     * Getter for startExpression.
     * @return returns startExpression.
     */
    public Element getStartExpression() {
        return startExpression;
    }

    /**
     * Getter for endExpression.
     * @return Returns end expression.
     */
    public Element getEndExpression() {
        return endExpression;
    }

    /**
     * Getter for stepExpression.
     * @return Returns step expression.
     */
    public Element getStepExpression() {
        return stepExpression;
    }

}
