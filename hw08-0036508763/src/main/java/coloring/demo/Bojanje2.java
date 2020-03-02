package coloring.demo;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

import java.util.Arrays;

/**
 * Demonstrates some implementation of a strategy.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Bojanje2 {
    /**
     * Executes implementation
     * @param args No arguments are taken.
     */
    public static void main(String[] args) {
        FillApp.run(FillApp.OWL, Arrays.asList(dfs));
    }

    private static final FillAlgorithm dfs = new FillAlgorithm() {
        @Override
        public String getAlgorithmTitle() {
            return "Moj dfs!";
        }

        @Override
        public void fill(int x, int y, int color, Picture picture) {
            Coloring col = new Coloring(new Pixel(x, y), picture, color);
            SubspaceExploreUtil.dfs(col, col, col, col);
        }
    };
}
