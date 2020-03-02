package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.LSystem;
import hr.fer.zemris.lsystems.LSystemBuilder;
import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.collections.Dictionary;
import hr.fer.zemris.lsystems.impl.commands.*;

import java.awt.*;

/**
 * Method used to implement L system builder.
 * L system is used for drawing l system fractals.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class LSystemBuilderImpl implements LSystemBuilder {
    /**
     * Dictionary of productions.
     */
    private Dictionary<Character, String> productions;
    /**
     * Dictionary of commands
     */
    private Dictionary<Character, Command> commands;
    /**
     * Length of a turtle's step.
     */
    private double unitLength;
    /**
     * Scaler used for scaling fractals for different depths.
     */
    private double unitLengthDegreeScaler;
    /**
     * Origin of a fractal.
     */
    private Vector2D origin;
    /**
     * Angle of the vector.
     */
    private double angle;
    /**
     * Axiom is used to defined the first step in a fractal.
     */
    private String axiom;

    /**
     * Constructor initializes default values.
     */
    public LSystemBuilderImpl() {
        productions = new Dictionary<>();
        commands = new Dictionary<>();
        unitLength = 0.1;
        unitLengthDegreeScaler = 1;
        origin = new Vector2D(0, 0);
        angle = 0;
        axiom = "";
    }

    /**
     * Setter for unit length.
     * @param v Value to which the unit length will be set to.
     * @return Returns the same {@link LSystemBuilder} but with modified unit length.
     */
    @Override
    public LSystemBuilder setUnitLength(double v) {
        this.unitLength = v;
        return this;
    }
    /**
     * Setter for origin.
     * @param v Vector from which the fractal will begin.
     * @return Returns the same {@link LSystemBuilder} but with modified origin.
     */
    @Override
    public LSystemBuilder setOrigin(double v, double v1) {
        this.origin = new Vector2D(v, v1);
        return this;
    }
    /**
     * Setter an angle.
     * @param v Value to which the angle will be set to.
     * @return Returns the same {@link LSystemBuilder} but with modified angle.
     */
    @Override
    public LSystemBuilder setAngle(double v) {
        this.angle = v;
        return this;
    }
    /**
     * Setter for axiom.
     * @param s Axiom will be set to a given value.
     * @return Returns the same {@link LSystemBuilder} but with modified axiom.
     */
    @Override
    public LSystemBuilder setAxiom(String s) {
        this.axiom = s;
        return this;
    }
    /**
     * Setter for unit length scaler.
     * @param v Value to which the unit length scaler will be set to.
     * @return Returns the same {@link LSystemBuilder} but with modified unit length scaler.
     */
    @Override
    public LSystemBuilder setUnitLengthDegreeScaler(double v) {
        this.unitLengthDegreeScaler = v;
        return this;
    }

    /**
     * Registers production.
     * @param c This character will be replaces with given String s.
     * @param s String s will replace ever occurrence of a char c.
     * @return Returns the same {@link LSystemBuilder} but with added production.
     */
    @Override
    public LSystemBuilder registerProduction(char c, String s) {
        this.productions.put(c, s);
        return this;
    }

    /**
     * Registers the given input to a proper command.
     * @param c Command which will be registered.
     * @param s String s defines what the command does.
     * @return Returns the same {@link LSystemBuilder} but with added command.
     */
    @Override
    public LSystemBuilder registerCommand(char c, String s) {
        String[] newCommand = s.toLowerCase().trim().split(" +");
        Command command;
        switch (newCommand[0]) {
            case "draw":
                command = new DrawCommand(Double.parseDouble(newCommand[1]));
                break;
            case "color":
                String colorAsString = s.split(" ")[1];
                command = new ColorCommand(Color.decode("0x" + colorAsString));
                break;
            case "pop":
                command = new PopCommand();
                break;
            case "push":
                command = new PushCommand();
                break;
            case "rotate":
                command = new RotateCommand(Double.parseDouble(newCommand[1]));
                break;
            case "scale":
                command = new ScaleCommand(Double.parseDouble(newCommand[1]));
                break;
            case "skip":
                command = new SkipCommand(Double.parseDouble(newCommand[1]));
                break;
            default:
                command = null;
        }
        if (command == null) {
            throw new NullPointerException();
        }
        this.commands.put(c, command);
        return this;
    }

    /**
     * Method which is used to configure fractal from a text input.
     * @param strings Array of string from which the fractal will be formed.
     * @return Returns the same {@link LSystemBuilder} but configured with given text.
     */
    @Override
    public LSystemBuilder configureFromText(String[] strings) {
        for (String string : strings) {
            String[] newString = string.trim().split(" +");
            switch (newString[0]) {
                case "origin":
                    this.origin = new Vector2D(Double.parseDouble(newString[1]), Double.parseDouble(newString[2]));
                    break;
                case "angle":
                    this.angle = Double.parseDouble(newString[1]);
                    break;
                case "unitLength":
                    this.unitLength = Double.parseDouble(newString[1]);
                    break;
                case "unitLengthDegreeScaler":
                    if (newString.length >= 3) {
                        String operation = "";
                        for (int i = 1; i < newString.length; i++) {
                            operation += newString[i]; // only 3 or 4 string will be concatenated so it's not too expensive.
                        }
                        String firstNumber = operation.substring(0, operation.indexOf("/"));
                        String secondNumber = operation.substring(operation.indexOf("/") + 1);
                        this.unitLengthDegreeScaler = Double.parseDouble(firstNumber) / Double.parseDouble(secondNumber);
                    } else {
                        this.unitLengthDegreeScaler = Double.parseDouble(newString[1]);

                    }
                    break;
                case "":
                    break;
                case "command":
                    if (newString.length == 4) {
                        this.registerCommand(newString[1].charAt(0), newString[2] + " " + newString[3]);
                    } else {
                        this.registerCommand(newString[1].charAt(0), newString[2]);
                    }
                    break;
                case "axiom":
                    this.axiom = newString[1];
                    break;
                case "production":
                    this.registerProduction(newString[1].charAt(0), newString[2]);
                    break;
            }
        }
        return this;
    }

    /**
     * Nested class which is used to generate and draw fractals.
     */
    public class MyLSystem implements LSystem {
        /**
         * Generates depth of a fractal
         * @param i This is the depth.
         * @return Returns String of commands for a given depth.
         */
        @Override
        public String generate(int i) {
            StringBuilder sb;
            String symbolSequence = LSystemBuilderImpl.this.axiom;
            for (int j = 0; j < i; j++) {
                sb = new StringBuilder();
                for (int k = 0; k < symbolSequence.length(); k++) {
                    String charToBeReplaced = productions.get(symbolSequence.charAt(k));
                    if (charToBeReplaced == null) {
                        sb.append(symbolSequence.charAt(k));
                        continue;
                    }
                    sb.append(charToBeReplaced);
                }
                symbolSequence = sb.toString();
            }
            return symbolSequence;
        }

        /**
         * This method draws a fractal for a given depth.
         * @param i Depth of a fractal.
         * @param painter Painter for the turtle.
         */
        @Override
        public void draw(int i, Painter painter) {
            Context context = new Context();
            TurtleState newTurtleState = new TurtleState(origin, new Vector2D(1, 0).rotated(Math.toRadians(angle)), Color.BLACK, unitLength * Math.pow(unitLengthDegreeScaler, i));
            context.pushState(newTurtleState);
            String symbolSequence = generate(i);
            char[] symbols = symbolSequence.toCharArray();
            for (char symbol : symbols) {
                Command currentCommand = commands.get(symbol);
                if (currentCommand == null) {
                    continue;
                }
                currentCommand.execute(context, painter);
            }
        }
    }


    /**
     * Method used to create {@link MyLSystem}.
     * @return Returns {@link LSystem}.
     */
    @Override
    public LSystem build() {
        return new MyLSystem();
    }
}
