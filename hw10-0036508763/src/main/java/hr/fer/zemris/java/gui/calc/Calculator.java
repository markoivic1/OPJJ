package hr.fer.zemris.java.gui.calc;

import hr.fer.zemris.java.gui.calc.model.CalcModel;
import hr.fer.zemris.java.gui.layouts.CalcLayout;
import hr.fer.zemris.java.gui.layouts.RCPosition;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;
import java.util.Stack;

/**
 * Calculator that mimics functionality of that used in Windows XP.
 *
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Calculator extends JFrame {
    /**
     * Calculator model which will be used in this implementation.
     */
    private CalcModel calcModel;
    /**
     * Used to implement stack functionality.
     */
    private Stack<Double> stack;
    /**
     * List of binaryOperators which will be notified to invert operations and function names.
     */
    private List<CalcOperator> binaryOperators;

    /**
     * {@link JLabel} which is used to display results.
     */
    private JLabel display;
    /**
     * Flag that tells whether the last function has been a function that takes two arguments
     */
    private boolean doubleFunction;
    /**
     * Current function
     */
    private CalcOperator currentFunction;
    /**
     * Main creates new thread which is used to run calculator.
     * @param args No arguments are used.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Calculator calculator = new Calculator();
                calculator.setVisible(true);
            }
        });
    }

    /**
     * Constructor for calculator
     */
    public Calculator() {
        this.calcModel = new CalcModelImpl();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.stack = new Stack<>();
        initGUI();
        //setSize(600, 600);
        pack();
        setVisible(true);
        System.out.println();
    }

    /**
     * Method used to initialize GUI.
     * It adds all of its components to the this JFrame.
     */
    private void initGUI() {
        Container cp = getContentPane();
        cp.setLayout(new CalcLayout(10));
        display = new JLabel("0", SwingConstants.RIGHT);
        display.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.GRAY));
        display.setFont(display.getFont().deriveFont(30f));
        display.setBackground(Color.ORANGE);
        display.setOpaque(true);

        // display listeners
        calcModel.addCalcValueListener(e -> display.setText(calcModel.toString()));
        cp.add(display, new RCPosition(1, 1));

        initTools(cp);
        initFunctions(cp);
        initOperators(cp);
    }

    /**
     * Initializes operators used in calculator.
     * Such as +,-,*,/
     * @param cp Container
     */
    private void initOperators(Container cp) {
        // divide /
        JButton divide = new JButton("/");
        divide.setBackground(Color.LIGHT_GRAY);
        divide.addActionListener(e -> {if (doubleFunction) {
            executeFunction();
        }
            calcModel.setPendingBinaryOperation((left, right) -> left / right);
        });
        cp.add(divide, new RCPosition(2, 6));

        // multiply *
        JButton multiply = new JButton("*");
        multiply.setBackground(Color.LIGHT_GRAY);
        multiply.addActionListener(e -> {
            if (doubleFunction) {
                executeFunction();
            }
            calcModel.setPendingBinaryOperation((l, r) -> l * r);
        });
        cp.add(multiply, new RCPosition(3, 6));

        // subtract -
        JButton subtract = new JButton("-");
        subtract.setBackground(Color.LIGHT_GRAY);
        subtract.addActionListener(e -> {
            if (doubleFunction) {
                executeFunction();
            }
            calcModel.setPendingBinaryOperation((l, r) -> l - r);
        });
        cp.add(subtract, new RCPosition(4, 6));

        // add +
        JButton add = new JButton("+");
        add.setBackground(Color.LIGHT_GRAY);
        add.addActionListener(e -> {
            if (doubleFunction) {
                executeFunction();
            }
            calcModel.setPendingBinaryOperation((l, r) -> l + r);
        });
        cp.add(add, new RCPosition(5, 6));
        initNumberPad(cp);

        // equal =
        JButton equal = new JButton("=");
        equal.setBackground(Color.LIGHT_GRAY);
        equal.addActionListener(e -> {
            if (doubleFunction) {
                executeFunction();
            }
            calcModel.setPendingBinaryOperation(null);

        });
        cp.add(equal, new RCPosition(1, 6));
    }

    /**
     * Initializes functions used in calculator.
     * Such as sin, cos, tan, 1/x, x^n... and their inverses.
     * @param cp Container
     */
    private void initFunctions(Container cp) {
        // reciprocate functions
        this.binaryOperators = new ArrayList<>();

        JButton reciprocate = new JButton("1/x");
        reciprocate.setBackground(Color.LIGHT_GRAY);
        CalcOperator reciprocateOperator = new CalcOperator((l, r) -> 1 / l, (l, r) -> 1 / l,
                "1/x", "1/x", reciprocate);
        reciprocate.addActionListener(e ->
                //calcModel.setPendingBinaryOperation(reciprocateOperator.getOperator())
                {
                    double result = reciprocateOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
                    calcModel.setValue(result);
                });
        binaryOperators.add(reciprocateOperator);
        cp.add(reciprocate, new RCPosition(2, 1));

        // log | x^10

        JButton logPow = new JButton("log");
        logPow.setBackground(Color.LIGHT_GRAY);
        CalcOperator logPowOperator = new CalcOperator((l, r) -> Math.log10(l), (l, r) -> Math.pow(10, l),
                "log", "10^x", logPow);
        logPow.addActionListener(e -> {
            //calcModel.setPendingBinaryOperation(logPowOperator.getOperator());
            double result = logPowOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
            calcModel.setValue(result);
        });
        binaryOperators.add(logPowOperator);
        cp.add(logPow, new RCPosition(3, 1));

        // ln | e^x

        JButton lnE = new JButton("ln");
        lnE.setBackground(Color.LIGHT_GRAY);
        CalcOperator lnEOperator = new CalcOperator((l, r) -> Math.log(l), (l, r) -> Math.exp(l),
                "ln", "e^x", lnE);
        lnE.addActionListener(e -> {
            //calcModel.setPendingBinaryOperation(lnEOperator.getOperator());
            double result = lnEOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
            calcModel.setValue(result);
        });
        binaryOperators.add(lnEOperator);
        cp.add(lnE, new RCPosition(4, 1));

        // x^n | x^(1/n)

        JButton xPowN = new JButton("x^n");
        xPowN.setBackground(Color.LIGHT_GRAY);
        CalcOperator xPowNOperator = new CalcOperator((l, r) -> Math.pow(l, r),
                (l, r) -> Math.pow(l, 1 / r),
                "x^n", "x^(1/n)", xPowN);
        xPowN.addActionListener(e -> {
            xPowNOperator.setSavedValue(calcModel.getValue());
            calcModel.clear();
            currentFunction = xPowNOperator;
            doubleFunction = true;
        });
        binaryOperators.add(xPowNOperator);
        cp.add(xPowN, new RCPosition(5, 1));

        // sin | arcsin

        JButton sinArcSin = new JButton("sin");
        sinArcSin.setBackground(Color.LIGHT_GRAY);
        CalcOperator sinArcSinOperator = new CalcOperator((l, r) -> Math.sin(l),
                (l, r) -> Math.asin(l),
                "sin", "arcsin", sinArcSin);
        sinArcSin.addActionListener(e -> {
            double result = sinArcSinOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
            calcModel.setValue(result);
        });
        binaryOperators.add(sinArcSinOperator);
        cp.add(sinArcSin, new RCPosition(2, 2));

        // cos | arccos

        JButton cosArcCos = new JButton("cos");
        cosArcCos.setBackground(Color.LIGHT_GRAY);
        CalcOperator cosArcCosOperator = new CalcOperator((l, r) -> Math.cos(l),
                (l, r) -> Math.acos(l),
                "cos", "arccos", cosArcCos);
        cosArcCos.addActionListener(e -> {
            double result = cosArcCosOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
            calcModel.setValue(result);
        });
        binaryOperators.add(cosArcCosOperator);
        cp.add(cosArcCos, new RCPosition(3, 2));

        // tan | arctan

        JButton tanArcTan = new JButton("tan");
        tanArcTan.setBackground(Color.LIGHT_GRAY);
        CalcOperator tanArcTanOperator = new CalcOperator((l, r) -> Math.tan(l),
                (l, r) -> Math.atan(l),
                "tan", "arctan", tanArcTan);
        tanArcTan.addActionListener(e -> {
            double result = tanArcTanOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
            calcModel.setValue(result);
        });
        binaryOperators.add(tanArcTanOperator);
        cp.add(tanArcTan, new RCPosition(4, 2));

        // ctg | arcctg

        JButton ctgArcCtg = new JButton("ctg");
        ctgArcCtg.setBackground(Color.LIGHT_GRAY);
        CalcOperator ctgArcCtgOperator = new CalcOperator((l, r) -> 1 / Math.tan(l),
                (l, r) -> 1 / Math.tan(l),
                "ctg", "arcctg", ctgArcCtg);
        ctgArcCtg.addActionListener(e -> {
            double result = ctgArcCtgOperator.getOperator().applyAsDouble(calcModel.getValue(), calcModel.getValue());
            calcModel.setValue(result);
        });
        binaryOperators.add(ctgArcCtgOperator);
        cp.add(ctgArcCtg, new RCPosition(5, 2));
    }

    /**
     * Initializes tools used in calculator.
     * Such as sign switch, ".", clear, stack
     * @param cp Container
     */
    private void initTools(Container cp) {
        // invert +/-
        JButton invert = new JButton("+/-");
        invert.setBackground(Color.LIGHT_GRAY);
        invert.addActionListener((e) -> calcModel.swapSign());
        cp.add(invert, new RCPosition(5, 4));

        // dot .
        JButton dot = new JButton(".");
        dot.setBackground(Color.LIGHT_GRAY);
        dot.addActionListener(e -> calcModel.insertDecimalPoint());
        cp.add(dot, new RCPosition(5, 5));

        // clr
        JButton clr = new JButton("clr");
        clr.setBackground(Color.LIGHT_GRAY);
        clr.addActionListener(e -> calcModel.clear());
        cp.add(clr, new RCPosition(1, 7));

        // res
        JButton res = new JButton("res");
        res.setBackground(Color.LIGHT_GRAY);
        res.addActionListener(e -> {
            calcModel.clearAll();
            stack.clear();
        });
        cp.add(res, new RCPosition(2, 7));

        // push
        JButton push = new JButton("push");
        push.setBackground(Color.LIGHT_GRAY);
        push.addActionListener(e -> stack.push(calcModel.getValue()));
        cp.add(push, new RCPosition(3, 7));

        // pop
        JButton pop = new JButton("pop");
        pop.setBackground(Color.LIGHT_GRAY);
        pop.addActionListener(e -> {
            try {
                calcModel.setValue(stack.pop());
            } catch (EmptyStackException ex) {
                JOptionPane.showMessageDialog(this, "Stack is empty");
                calcModel.setValue(Double.NaN);
            }
        });
        cp.add(pop, new RCPosition(4, 7));

        // inv
        JCheckBox inv = new JCheckBox("Inv");
        inv.setBackground(Color.LIGHT_GRAY);
        inv.addActionListener(e -> notifyOperatorListeners());
        cp.add(inv, new RCPosition(5, 7));
    }

    private void initNumberPad(Container cp) {
        // number pad
        int i = 0;
        for (int row = 2; row < 6; row++) {
            // just zero
            if (row == 5) {
                i++;
                JButton button = new JButton("0");
                button.setBackground(Color.LIGHT_GRAY);
                button.setFont(button.getFont().deriveFont(30f));
                button.addActionListener(e -> calcModel.insertDigit(Integer.parseInt(button.getText())));
                cp.add(button, new RCPosition(row, 3));
                break;
            }
            // 1 - 9
            for (int column = 3; column < 6; column++) {
                i++;
                JButton button = new JButton(Integer.valueOf(i).toString());
                button.setBackground(Color.LIGHT_GRAY);
                button.setFont(button.getFont().deriveFont(30f));
                button.addActionListener(e -> calcModel.insertDigit(Integer.parseInt(button.getText())));
                cp.add(button, new RCPosition(row, column));
            }
        }
    }

    private void executeFunction() {
        calcModel.setValue(currentFunction.getOperator().applyAsDouble(currentFunction.getSavedValue(), calcModel.getValue()));
        doubleFunction = false;
    }

    /**
     * Method used for notifying listeners to switch their functions.
     */
    private void notifyOperatorListeners() {
        for (CalcOperator operator : binaryOperators) {
            operator.inv();
        }
    }
}
