package searching.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * Class which offers some algorithm implementation.
 * @author Marko Ivić
 * @version 1.0.0
 */
@SuppressWarnings("Duplicates")
public class SearchUtil {
    /**
     *
     * This method offers brute force way to solving the puzzle.
     * @param s0 Getter for the first element
     * @param succ Returns the list of successful new elements.
     * @param goal Check if the given value is acceptable.
     * @param <S> Parameter used to parametrize all of other arguments given.
     * @return Returns solved node if found, returns null otherwise.
     */
    public static <S> Node<S> bfs(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
        List<Node<S>> toExplore = new LinkedList<>();
        toExplore.add(new Node<S>(null, s0.get(), 0));
        while (!toExplore.isEmpty()) {
            Node<S> currentElement = toExplore.get(0);
            toExplore.remove(0);
            if (goal.test(currentElement.getState())) {
                return currentElement;
            }
            List<Transition<S>> transitions = succ.apply(currentElement.getState());
            for (Transition<S> transition : transitions) {
                toExplore.add(new Node<S>(currentElement, transition.getState(), transition.getCost() + currentElement.getCost()));
            }
        }
        return null;
    }
    /**
     *
     * This method offers brute force way to solving the puzzle.
     * Uses caching of already visited nodes which improves performance.
     * @param s0 Getter for the first element
     * @param succ Returns the list of successful new elements.
     * @param goal Check if the given value is acceptable.
     * @param <S> Parameter used to parametrize all of other arguments given.
     * @return Returns node if solved, returns null otherwise.
     */
    public static <S> Node<S> bfsv(Supplier<S> s0, Function<S, List<Transition<S>>> succ, Predicate<S> goal) {
        List<Node<S>> toExplore = new LinkedList<>();
        toExplore.add(new Node<S>(null, s0.get(), 0));
        Set<Node<S>> visited = new HashSet<>();
        visited.add(new Node<S>(null, s0.get(), 0));
        while (!toExplore.isEmpty()) {
            Node<S> currentElement = toExplore.get(0);
            toExplore.remove(0);
            if (goal.test(currentElement.getState())) {
                return currentElement;
            }
            List<Transition<S>> transitions = succ.apply(currentElement.getState());
            for (Transition<S> transition : transitions) {
                Node<S> currentNode = new Node<S>(currentElement, transition.getState(), transition.getCost() + currentElement.getCost());
                if (visited.contains(currentNode)) {
                    continue;
                }
                toExplore.add(currentNode);
                visited.add(currentNode);
            }
        }
        return null;
    }
}
