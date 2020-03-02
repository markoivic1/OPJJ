package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Rotates the turtle by the given angle in degrees.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class RotateCommand implements Command {
    /**
     * Angle by which the turtle is rotated
     */
    private double angle;

    /**
     * Stores given angle to a variable
     * @param angle Angle in degrees.
     */
    public RotateCommand(double angle) {
        this.angle = angle;
    }

    /**
     * {@inheritDoc}
     * @param ctx Current context.
     * @param painter Painter for the turtle.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        double angleInRadians = Math.toRadians(angle);
        ctx.getCurrentState().setTurtleAngle(angleInRadians);
    }
}
