package coloring.demo;

import coloring.algorithms.Coloring;
import coloring.algorithms.Pixel;
import coloring.algorithms.SubspaceExploreUtil;
import marcupic.opjj.statespace.coloring.FillAlgorithm;
import marcupic.opjj.statespace.coloring.FillApp;
import marcupic.opjj.statespace.coloring.Picture;

import java.util.Arrays;

/**
 * Demonstration of bfsv strategy.
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Bojanje3 {
    /**
     * Executes bfsv strategy.
     * @param args No arguments are taken.
     */
    public static void main(String[] args) {
        FillApp.run(FillApp.OWL, Arrays.asList(bfsv));
    }

    private static final FillAlgorithm bfsv = new FillAlgorithm() {
        @Override
        public String getAlgorithmTitle() {
            return "Moj bfsv!";
        }

        @Override
        public void fill(int x, int y, int color, Picture picture) {
            Coloring col = new Coloring(new Pixel(x, y), picture, color);
            SubspaceExploreUtil.bfsv(col, col, col, col);
        }
    };
}
