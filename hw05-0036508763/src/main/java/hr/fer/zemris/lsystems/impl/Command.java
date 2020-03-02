package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.Painter;

/**
 * Interface which defines execute method for every method that implements this interface.
 * @author Marko Ivić
 * @version 1.0.0
 */
public interface Command {
    /**
     * Command which will be executed to a turtle in a current context.
     *
     * @param ctx Current context
     * @param painter Painter for the turtle
     */
    void execute(Context ctx, Painter painter);
}
