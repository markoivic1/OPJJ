package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Scales the effective length for a given factor.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ScaleCommand implements Command {
    /**
     * Factor for which the turtle will be scaled
     */
    private double factor;

    /**
     * Takes the factor and stores it in a variable.
     * @param factor Factor for which the turtle will be scaled.
     */
    public ScaleCommand(double factor) {
        this.factor = factor;
    }
    /**
     * {@inheritDoc}
     * @param ctx Current context.
     * @param painter Painter for the turtle.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setEffectiveLength(ctx.getCurrentState().getEffectiveLength() * factor);
    }
}
