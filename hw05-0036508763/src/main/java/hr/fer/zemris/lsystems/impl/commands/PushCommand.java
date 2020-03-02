package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;
import hr.fer.zemris.lsystems.impl.TurtleState;

/**
 * Pushes the turtle to a stack.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PushCommand implements Command {
    /**
     * {@inheritDoc}
     * @param ctx Context to which the turtle will be pushed.
     * @param painter Painter for the turtle.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        TurtleState state = ctx.getCurrentState().copy();
        ctx.pushState(state);
    }
}
