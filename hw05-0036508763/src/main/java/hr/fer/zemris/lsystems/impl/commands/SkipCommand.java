package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Skips the step distance. Doesn't draw a line.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SkipCommand implements Command {
    /**
     * Step by which the turtle will be moved.
     */
    private double step;

    /**
     * Stores step in a variable.
     * @param step Step by which the turtle will be moved without leaving the mark.
     */
    public SkipCommand(double step) {
        this.step = step;
    }
    /**
     * {@inheritDoc}
     * @param ctx Current context.
     * @param painter Painter for the turtle.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        Vector2D turtleAngle = ctx.getCurrentState().getTurtleAngle(); // calculate delta
        double angle = Math.atan2(turtleAngle.getY(), turtleAngle.getX());
        double newV = ctx.getCurrentState().getCurrentPosition().getX();                     // x coordinate of a current turtle
        double newV1 =  ctx.getCurrentState().getCurrentPosition().getY();                   // y coordinate of a current turtle
        double newV2 = newV + ctx.getCurrentState().getEffectiveLength() * Math.cos(angle) * step;  // x coordinate of a new turtle
        double newV3 = newV1 + ctx.getCurrentState().getEffectiveLength() * Math.sin(angle) * step; // y coordinate of a new turtle
        painter.drawLine(newV, newV1, newV2, newV3, ctx.getCurrentState().getTurtleColor(), 0f); // drawing a line
        ctx.getCurrentState().setCurrentPosition(new Vector2D(newV2, newV3));                // new position of a turtle

    }
}
