package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListIndexedCollectionTest {

    private LinkedListIndexedCollection<String> collection;

    @BeforeEach
    void init() {
        collection = new LinkedListIndexedCollection<>();
        collection.add("T");
        collection.add("h");
        collection.add("i");
        collection.add("s");
        collection.add(" ");
        collection.add("i");
        collection.add("s");
        collection.add(" ");
        collection.add("S");
        collection.add("p");
        collection.add("a");
        collection.add("r");
        collection.add("t");
        collection.add("a");
        collection.add("!");
    }

    @Test
    void constructorWithoutGivenArguments() {
        LinkedListIndexedCollection<String> linkedListWithoutArguments = new LinkedListIndexedCollection<>();
        linkedListWithoutArguments.add("I should be added to the list");
        linkedListWithoutArguments.add("Me too");
        assertEquals(linkedListWithoutArguments.size(), 2);
        assertEquals((String) linkedListWithoutArguments.get(0), "I should be added to the list");
        assertNotEquals((String) linkedListWithoutArguments.get(1), "I shouldn't be added to the list");
    }

    @Test
    void constructorWithAnExistingCollectionOfASameType() {
        LinkedListIndexedCollection<String> newLinkedCollection = new LinkedListIndexedCollection<>(collection);
        assertTrue(collectionElementsMatchTheMessage(newLinkedCollection, "This is Sparta!"));
        try {
            LinkedListIndexedCollection collectionThatTakesNull = new LinkedListIndexedCollection<>(null);
            fail("Collection should throw an IllegalArgumentException for null value");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void constructorWithAnExistingCollectionOfADifferentType() {
        ArrayIndexedCollection<String> arrayCollection = new ArrayIndexedCollection<>();
        arrayCollection.add("W");
        arrayCollection.add("h");
        arrayCollection.add("i");
        arrayCollection.add("t");
        arrayCollection.add("e");
        arrayCollection.add(" ");
        arrayCollection.add("s");
        arrayCollection.add("o");
        arrayCollection.add("c");
        arrayCollection.add("k");
        arrayCollection.add("s");
        arrayCollection.add(".");
        LinkedListIndexedCollection<String> listCollection = new LinkedListIndexedCollection<>(arrayCollection);
        assertTrue(collectionElementsMatchTheMessage(listCollection, "White socks."));
    }

    @Test
    void constructorWithNullValue() {
        try {
            LinkedListIndexedCollection<String> list = new LinkedListIndexedCollection<>(null);
            fail("This constructor should throw NullPointerException");
        } catch (NullPointerException ex) {
            //
        }
    }
    @Test
    void sizeOfACollectionWithSomeElements() {
        assertEquals(collection.size(), 15);
        assertNotEquals(collection.size(), 10);
        LinkedListIndexedCollection<String> emptyCollection = new LinkedListIndexedCollection<>();
        assertEquals(emptyCollection.size(), 0);
    }

    @Test
    void sizeOfEmptyCollection() {
        LinkedListIndexedCollection<String> emptyCollection = new LinkedListIndexedCollection<>();
        assertEquals(emptyCollection.size(), 0);
    }
    @Test
    void addSomeElementsToALinkedListIndexedCollection() {
        LinkedListIndexedCollection<String> linkedList = new LinkedListIndexedCollection<>();
        String red = "red";
        linkedList.add(red);
        assertTrue(linkedList.contains(red));
        assertEquals(linkedList.size(), 1);
        String blue = "blue";
        linkedList.add(blue);
        assertTrue(linkedList.contains(blue));
        assertEquals(linkedList.size(), 2);
        String white = "white";
        assertFalse(linkedList.contains(white));
    }

    @Test
    void addingNullElementToCollection() {
        try {
            LinkedListIndexedCollection<String> someCollection = new LinkedListIndexedCollection<>();
            someCollection.add(null);
            fail("This method should throw an exception when adding null element");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void getAnElementFromACollection() {
        LinkedListIndexedCollection<String> linkedList = new LinkedListIndexedCollection<>();
        linkedList.add("Karabaja");
        linkedList.add("must");
        linkedList.add("find");
        linkedList.add("his");
        linkedList.add("motorbike");
        assertEquals(linkedList.get(0), "Karabaja");
        assertEquals(linkedList.get(1), "must");
        assertEquals(linkedList.get(4), "motorbike");
    }

    @Test
    void gettingANonExistentObject() {
        try {
            collection.get(collection.size());
            fail("Collection should throw an IndexOutOfBoundsException when gettin a nonexistant object");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }
    @Test
    void clearAListWithSomeElements() {
        collection.clear();
        try {
            assertNull(collection.get(0));
            fail("Collection should throw an IndexOutOfBoundsException when gettin a nonexistant object");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
        assertEquals(collection.size(), 0);
    }

    @Test
    void clearAnEmptyCollection() {
        LinkedListIndexedCollection emptyList = new LinkedListIndexedCollection();
        emptyList.clear();
        try {
            assertNull(emptyList.get(0));
            fail("This should throw IndexOutOfBoundsExcpetion");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
        assertEquals(emptyList.size(), 0);
    }

    @Test
    void insertToAEmptyCollection() {
        LinkedListIndexedCollection<String> emptyList = new LinkedListIndexedCollection<>();
        emptyList.insert("Bacon", 0);
        assertEquals((String) emptyList.get(0), "Bacon");
    }

    @Test
    void insertToACollection() {
        collection.insert("*", 5);
        assertEquals((String) collection.get(5), "*");
        assertTrue(collectionElementsMatchTheMessage(collection, "This *is Sparta!"));
        collection.insert("Y", collection.size());
        collection.insert("A", 0);
        collection.insert("#", collection.size() - 1);
        assertTrue(collectionElementsMatchTheMessage(collection, "AThis *is Sparta!#Y"));

        StringBuilder sb1 = new StringBuilder();
        Processor p = value -> sb1.append((String) value);
        collection.forEach(p);
        assertTrue(collectionElementsMatchTheMessage(collection, sb1.toString()));
        assertTrue(reverseDirectionCheck(collection));

        try {
            collection.insert("Pen", -5);
            collection.insert("Apple", collection.size() + 1);
            fail("IndexOutOfBoundsException should be thrown here");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void indexOfObject() {
        assertEquals(collection.indexOf("T"), 0);
        assertEquals(collection.indexOf("h"), 1);
        assertEquals(collection.indexOf("!"), collection.size() - 1);
        assertEquals(collection.indexOf("Bum"), -1);
        assertEquals(collection.indexOf(null), -1);
    }

    @Test
    void removeAnObjectFromCollection() {
        collection.remove(0);
        collection.remove(5);
        collection.remove(collection.size() - 1);
        assertTrue(collectionElementsMatchTheMessage(collection, "his i Sparta"));
        assertTrue(reverseDirectionCheck(collection));
        try {
            collection.remove(-5);
            collection.remove(collection.size());
            fail("IndexOutOfBoundsException should be thrown here");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void removeAnObjectFromCollectionWithAReference() {
        collection.remove("T");
        collection.remove("S");
        collection.remove("!");
        assertTrue(collectionElementsMatchTheMessage(collection, "his is parta"));
        assertTrue(reverseDirectionCheck(collection));

        try {
            collection.remove(null);
            fail("This method should throw NullPointerException");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void contains() {
        class LetterWrapper {
            private String letter;
            private LetterWrapper(String letter) {
                this.letter = letter;
            }
        }
        LinkedListIndexedCollection<LetterWrapper> list = new LinkedListIndexedCollection<>();
        LetterWrapper firstLetter = new LetterWrapper("first");
        LetterWrapper secondLetter = new LetterWrapper("second");
        LetterWrapper thirdLetter = new LetterWrapper("third");
        LetterWrapper fourthLetter = new LetterWrapper("fourth");
        list.add(firstLetter);
        list.add(secondLetter);
        list.add(thirdLetter);
        assertTrue(list.contains(firstLetter));
        assertTrue(list.contains(secondLetter));
        assertTrue(list.contains(thirdLetter));
        assertFalse(list.contains(fourthLetter));
    }

    @Test
    void collectionElementsToAnArray() {
        Object[] arrayOfObjectsFromGivenCollection = collection.toArray();
        for (int i = 0; i < collection.size(); i++) {
            if (((String) arrayOfObjectsFromGivenCollection[i]).equals((String) collection.get(i))) {
                continue;
            }
            fail("Array of Objects from given collection does not match the elements in collection");
        }
    }

    @Test
    void forEach() {
        LinkedListIndexedCollection<String> collectionObjectsAreAddedTo = new LinkedListIndexedCollection<>();
        StringBuilder sb = new StringBuilder();
        Processor p = value -> sb.append((String) value);
        collection.forEach(p);
        assertTrue(collectionElementsMatchTheMessage(collection, sb.toString()));
    }

    @Test
    void addAllToCollection() {
        LinkedListIndexedCollection<String> collectionObjectsAreAddedTo = new LinkedListIndexedCollection<>();
        collectionObjectsAreAddedTo.addAll(collection);
        assertTrue(collectionElementsMatchTheMessage(collectionObjectsAreAddedTo, "This is Sparta!"));
    }

    /**
     * Method for checking the contents of a collection and comparing it to a given string. Uses .equals() for checking.
     * @param collection Collection that is being compared.
     * @param message String that is being compared.
     * @return Returns {@code true} if inputs are the same and {@code false} otherwise.
     */
    public boolean collectionElementsMatchTheMessage(LinkedListIndexedCollection collection, String message) {
        for (int i = 0; i < message.length(); i++) {
            // This statement checks each char in a string with a corresponding value that you get from elements
            // String is inherited from Object so it can be used in this collection.
            String stringFromCollection = (String) collection.get(i);
            String stringFromMessage = message.substring(i,i + 1);
            if ((stringFromCollection).equals(stringFromMessage)) {
                continue;
            }
            return false;
        }
        return true;
    }

    /**
     * Compares the arguments when Collection starts from first and from last {@code ListNode}.
     *
     * Objects collected from the last {@code ListNode} are reversed and compared against objects collected from first {@code ListNode}.
     *
     * @param collection Collection that is going to be reversed
     * @return Returns {@code true}
     */
    public boolean reverseDirectionCheck(LinkedListIndexedCollection collection) {
        StringBuilder sb2 = new StringBuilder();
        Processor p = value -> sb2.append((String) value);
        collection.forEachBackwards(p);
        return (collectionElementsMatchTheMessage(collection, sb2.reverse().toString()));
    }
}