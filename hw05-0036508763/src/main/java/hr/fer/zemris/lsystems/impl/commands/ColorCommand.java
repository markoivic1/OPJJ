package hr.fer.zemris.lsystems.impl.commands;

import hr.fer.zemris.lsystems.Painter;
import hr.fer.zemris.lsystems.impl.Command;
import hr.fer.zemris.lsystems.impl.Context;

import java.awt.*;

/**
 * Defines color command which sets turtle's color.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class ColorCommand implements Command {
    /**
     * Color of a turtle.
     */
    private Color color;

    /**
     * Constructor which takes color.
     * @param color Color of a turtle.
     */
    public ColorCommand(Color color) {
        this.color = color;
    }

    /**
     * {@inheritDoc}
     * @param ctx Current context.
     * @param painter Painter for the turtle.
     */
    @Override
    public void execute(Context ctx, Painter painter) {
        ctx.getCurrentState().setTurtleColor(color);
    }
}
