package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

/**
 * Class pops the turtle from a stack
 * @author Marko Ivić
 * @version 1.0.0
 */
public class PopCommand implements Command {
    /**
     * {@inheritDoc}
     * @param ctx Current context from which the turtle will be popped.
     * @param painter Painter for the turtle
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.popState();
    }
}
