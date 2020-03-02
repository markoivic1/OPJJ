package searching.demo;

import searching.algorithms.Node;
import searching.algorithms.SearchUtil;
import searching.slagalica.KonfiguracijaSlagalice;
import searching.slagalica.Slagalica;
import searching.slagalica.gui.SlagalicaViewer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class which demonstrates use of defined strategy.
 * This class uses gui to better show steps.
 * @author Marko Ivić
 * @version 1.0.0
 */
@SuppressWarnings("Duplicates")
public class SlagalicaMain {
    /**
     * Executes given strategy.
     * @param args No arguments are taken.
     */
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Invalid number of arguments");
            return;
        }
        int[] userInput = processInput(args[0]);
        if (!inputIsOkay(userInput)) {
            System.out.println("Invalid input");
            return;
        }
        Slagalica slagalica = new Slagalica(new KonfiguracijaSlagalice(userInput));
        Node<KonfiguracijaSlagalice> rješenje = SearchUtil.bfsv(slagalica, slagalica, slagalica);
        if (rješenje == null) {
            System.out.println("Nisam uspio pronaći rješenje.");
        } else {
            System.out.println("Imam rješenje. Broj poteza je: " + rješenje.getCost());
            List<KonfiguracijaSlagalice> lista = new ArrayList<>();
            Node<KonfiguracijaSlagalice> trenutni = rješenje;
            while (trenutni != null) {
                lista.add(trenutni.getState());
                trenutni = trenutni.getParent();
            }
            Collections.reverse(lista);
            lista.stream().forEach(k -> {
                System.out.println(k);
                System.out.println();
            });
            SlagalicaViewer.display(rješenje);
        }
    }

    /**
     * Check if the given values are valid.
     * @param input Input array
     * @return Returns true if they are valid, returns false otherwise
     */
    private static boolean inputIsOkay(int[] input) {
        if (input.length != 9) {
            return false;
        }
        for (int i = 0; i < 9; i++) {
            if (!containsValue(input, i)) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the given array contains value.
     * @param input An array in which the value will be checked.
     * @param value Value which is being checked
     * @return Returns true if the array contains value, returns false otherwise.
     */
    private static boolean containsValue(int[] input, int value) {
        for (int i = 0; i < input.length; i++) {
            if (input[i] == value) {
                return true;
            }
        }
        return false;
    }

    /**
     * Method used for parsing and arranging an array of int
     * @param input Input which will processed
     * @return Returns array of ints.
     */
    private static int[] processInput(String input) {
        String[] inputAsString = input.split("");
        int[] processedInput = new int[inputAsString.length];
        for (int i = 0; i < inputAsString.length; i++) {
            processedInput[i] = Integer.parseInt(inputAsString[i]);
        }
        return processedInput;
    }
}
