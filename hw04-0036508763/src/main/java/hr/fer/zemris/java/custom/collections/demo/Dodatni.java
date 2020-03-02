package hr.fer.zemris.java.custom.collections.demo;

import hr.fer.zemris.java.custom.collections.ArrayIndexedCollection;
import hr.fer.zemris.java.custom.collections.ElementsGetter;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
public class Dodatni {
    public static void main(String[] args) {
        ArrayIndexedCollection<Object> array = new ArrayIndexedCollection<>();
        array.add("String");
        array.add(Integer.valueOf(2));
        ElementsGetter<Object> getter = array.createElementsGetter();

        while (getter.hasNextElement()) {
            System.out.println(getter.getNextElement());
        }
    }
}
