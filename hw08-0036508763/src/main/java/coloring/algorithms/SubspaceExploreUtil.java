package coloring.algorithms;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * Class which defines different strategies of exploring the subspace.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class SubspaceExploreUtil {
    /**
     * This method expands outward as in a diamond shape
     * @param s0 Getter for the first element
     * @param process processes the given value
     * @param succ Returns the list of successful new elements.
     * @param acceptable Check if the given value is acceptable.
     * @param <S> Parameter used to parametrize all of other arguments given.
     */
    public static <S> void bfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ, Predicate<S> acceptable) {
        List<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());
        while (!toExplore.isEmpty()) {
            S currentElement = toExplore.get(0);
            toExplore.remove(0);
            if (!acceptable.test(currentElement)) {
                continue;
            }
            process.accept(currentElement);
            toExplore.addAll(succ.apply(currentElement));
        }
    }
    /**
     * This method expands to edges first then moves sideways.
     * @param s0 Getter for the first element
     * @param process processes the given value
     * @param succ Returns the list of successful new elements.
     * @param acceptable Check if the given value is acceptable.
     * @param <S> Parameter used to parametrize all of other arguments given.
     */
    public static <S> void dfs(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ, Predicate<S> acceptable) {
        List<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());
        while (!toExplore.isEmpty()) {
            S currentElement = toExplore.get(0);
            toExplore.remove(0);
            if (!acceptable.test(currentElement)) {
                continue;
            }
            process.accept(currentElement);
            toExplore.addAll(0, succ.apply(currentElement));
        }
    }
    /**
     * This method expands outward as in a diamond shape but uses hashing to cut down on repeated actions.
     * @param s0 Getter for the first element
     * @param process processes the given value
     * @param succ Returns the list of successful new elements.
     * @param acceptable Check if the given value is acceptable.
     * @param <S> Parameter used to parametrize all of other arguments given.
     */
    public static <S> void bfsv(Supplier<S> s0, Consumer<S> process, Function<S, List<S>> succ, Predicate<S> acceptable) {
        List<S> toExplore = new LinkedList<>();
        toExplore.add(s0.get());
        Set<S> visited = new HashSet<>();
        visited.add(s0.get());
        while (!toExplore.isEmpty()) {
            S currentElement = toExplore.get(0);
            toExplore.remove(0);
            if (!acceptable.test(currentElement)) {
                continue;
            }
            process.accept(currentElement);
            List<S> children = succ.apply(currentElement);
            children = children.stream().filter(c -> !visited.contains(c)).collect(Collectors.toList());
            toExplore.addAll(children);
            visited.addAll(children);
        }
    }
}
