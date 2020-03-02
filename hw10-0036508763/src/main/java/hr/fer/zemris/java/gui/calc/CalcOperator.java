package hr.fer.zemris.java.gui.calc;

import javax.swing.*;
import java.awt.*;
import java.util.function.DoubleBinaryOperator;

/**
 * Defines class which contains two operations and their names.
 * This is a handy way to have inverse operations stored as a single class.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class CalcOperator {
    /**
     * Original function.
     */
    private DoubleBinaryOperator function;
    /**
     * Inverse of the original function.
     */
    private DoubleBinaryOperator invFunction;
    /**
     * Name of a function.
     */
    private String functionName;
    /**
     * Name of a inverse function.
     */
    private String invFunctionName;
    /**
     * Flag which indicates whether the original function is active or its inverse.
     */
    private boolean normal;
    /**
     * {@link JButton} which uses name of a current active function.
     */
    private JButton button;
    /**
     * Saved value
     */
    private Double savedValue;
    /**
     * Constructor
     * @param function {@link DoubleBinaryOperator} of a original function.
     * @param invFunction {@link DoubleBinaryOperator} of a inverse of the original function.
     * @param functionName Name of a function.
     * @param invFunctionName Name of a inverse function.
     * @param button {@link JButton} button which will use this classes active function as its text.
     */
    public CalcOperator(DoubleBinaryOperator function, DoubleBinaryOperator invFunction, String functionName, String invFunctionName,
                        JButton button) {
        this.function = function;
        this.invFunction = invFunction;
        this.functionName = functionName;
        this.invFunctionName = invFunctionName;
        this.button = button;
        setButtonsPreferredSize();
        normal = true;
    }

    /**
     * Sets preferred size to a button using name of a function.
     */
    private void setButtonsPreferredSize() {
        Dimension dimensionOfFirstName = button.getPreferredSize();
        button.setText(invFunctionName);
        Dimension dimensionOfSecondName = button.getPreferredSize();
        button.setText(functionName);
        button.setPreferredSize(new Dimension(
                Math.max(dimensionOfFirstName.width, dimensionOfSecondName.width),     // width
                Math.max(dimensionOfFirstName.height, dimensionOfSecondName.height))); // height

    }

    /**
     * Getter for name of a function.
     * @return Returns name of a original function.
     */
    public String getName() {
        return normal ? functionName : invFunctionName;
    }

    /**
     * Getter for the {@link DoubleBinaryOperator} which is currently used.
     * @return Returns currently used operator.
     */
    public DoubleBinaryOperator getOperator() {
        return normal ? function : invFunction;
    }

    /**
     * Switches flags and sets the text in the button to newly switched name.
     */
    public void inv() {
        normal = !normal;
        button.setText(getName());
    }

    /**
     * Getter for saved value
     * @return Returns saved value
     */
    public Double getSavedValue() {
        return savedValue;
    }

    /**
     * Setter for saved value
     * @param savedValue New value
     */
    public void setSavedValue(Double savedValue) {
        this.savedValue = savedValue;
    }
}
