package hr.fer.zemris.lsystems.impl;

import hr.fer.zemris.lsystems.impl.collections.ObjectStack;

/**
 * Context of a depth.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Context {
    /**
     * Turtles are can be pushed and popped from a stack.
     */
    private ObjectStack<TurtleState> stack = new ObjectStack<>();

    /**
     * Returns the last pushed turtle to the stack but doesn't remove it from there.
     * @return Returns {@link TurtleState}
     */
    public TurtleState getCurrentState() { // vraća stanje s vrha stoga bez uklanjanja
        return stack.peek();
    }

    /**
     * Pushes the new {@link TurtleState} to the stack.
     * @param state {@link TurtleState} which will be pushed to the stack.
     */
    public void pushState(TurtleState state){ // na vrh gura predano stanje
        stack.push(state);
    }

    /**
     * Pops the {@link TurtleState} form a stack.
     */
    public void popState() {// briše jedno stanje s vrha
        stack.pop();
    }
}
