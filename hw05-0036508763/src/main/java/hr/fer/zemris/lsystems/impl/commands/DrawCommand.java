package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.Vector2D;

/**
 * Class which is used for drawing the turtle.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class DrawCommand implements Command {
    /**
     * Step for which the line will be drawn
     */
    private double step;

    /**
     * Constructor which takes step value and stores it.
     * @param step
     */
    public DrawCommand(double step) {
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
        double newV2 = newV + ctx.getCurrentState().getEffectiveLength() * Math.cos(angle) * step ;  // x coordinate of a new turtle
        double newV3 = newV1 + ctx.getCurrentState().getEffectiveLength() * Math.sin(angle) * step; // y coordinate of a new turtle
        painter.drawLine(newV, newV1, newV2, newV3, ctx.getCurrentState().getTurtleColor(), 1f); // drawing a line
        ctx.getCurrentState().setCurrentPosition(new Vector2D(newV2, newV3));                // new position of a turtle
    }
}
