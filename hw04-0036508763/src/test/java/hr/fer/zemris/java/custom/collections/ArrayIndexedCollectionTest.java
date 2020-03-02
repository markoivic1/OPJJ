package hr.fer.zemris.java.custom.collections;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ArrayIndexedCollectionTest {
    ArrayIndexedCollection<String> collection;
    ArrayIndexedCollection<String> sourceCollection;
    String message;

    @BeforeEach
    void init() {
        message = "This constructor works!";
        collection = new ArrayIndexedCollection<>();
        collection.add("T");
        collection.add("h");
        collection.add("i");
        collection.add("s");
        collection.add(" ");
        collection.add("c");
        collection.add("o");
        collection.add("n");
        collection.add("s");
        collection.add("t");
        collection.add("r");
        collection.add("u");
        collection.add("c");
        collection.add("t");
        collection.add("o");
        collection.add("r");
        collection.add(" ");
        collection.add("w");
        collection.add("o");
        collection.add("r");
        collection.add("k");
        collection.add("s");
        collection.add("!");
    }


    @Test
    public void constructorWithoutAnyArguments() {
        // ArrayIndexedCollection constructor without any arguments is called in the init() method.
        // init() method is called before each other method.

        assertTrue(collectionElementsMatchTheMessage(collection, message));
        assertEquals(collection.size(), 23);
    }

    @Test
    public void constructorWithInitialCapacity() {
        // With an array of this capacity it is expected for it to be resized multiple times.
        ArrayIndexedCollection<String> collectionWithInitialCapacity = new ArrayIndexedCollection<>(2);
        String messageStoredInCollection = "This is not a drill!";
        collectionWithInitialCapacity.add("T");
        collectionWithInitialCapacity.add("h");
        collectionWithInitialCapacity.add("i");
        collectionWithInitialCapacity.add("s");
        collectionWithInitialCapacity.add(" ");
        collectionWithInitialCapacity.add("i");
        collectionWithInitialCapacity.add("s");
        collectionWithInitialCapacity.add(" ");
        collectionWithInitialCapacity.add("n");
        collectionWithInitialCapacity.add("o");
        collectionWithInitialCapacity.add("t");
        collectionWithInitialCapacity.add(" ");
        collectionWithInitialCapacity.add("a");
        collectionWithInitialCapacity.add(" ");
        collectionWithInitialCapacity.add("d");
        collectionWithInitialCapacity.add("r");
        collectionWithInitialCapacity.add("i");
        collectionWithInitialCapacity.add("l");
        collectionWithInitialCapacity.add("l");
        collectionWithInitialCapacity.add("!");
        assertTrue(collectionElementsMatchTheMessage(collectionWithInitialCapacity, messageStoredInCollection));
    }

    @Test
    public void constructorThatUsesExistingArray() {
        // This dodatak takes existing ArrayIndexedCollection and uses it as an argument
        ArrayIndexedCollection collectionWhichTakesExistingCollection = new ArrayIndexedCollection(collection);
        // If collection doesn't match the message the dodatak should fail.
        assertTrue(collectionElementsMatchTheMessage(collectionWhichTakesExistingCollection, message));
        assertEquals(collection.size(), collectionWhichTakesExistingCollection.size());
    }

    @Test
    void constructorWithInitialValueSetToZero() {
        try {
            ArrayIndexedCollection list = new ArrayIndexedCollection(0);
            fail("This constructor should throw IllegalArgumentException!");
        } catch (IllegalArgumentException ex) {
            //
        }
    }

    @Test
    void constructorThatTakesCollectionOfADifferentType() {
        LinkedListIndexedCollection<String> linkedList = new LinkedListIndexedCollection<>();
        linkedList.add("S");
        linkedList.add("u");
        linkedList.add("n");
        linkedList.add("d");
        linkedList.add("a");
        linkedList.add("y");
        linkedList.add("!");
        ArrayIndexedCollection arrayList = new ArrayIndexedCollection(linkedList);
        collectionElementsMatchTheMessage(arrayList, "Sunday");
    }

    @Test
    void constructorThatTakesCollectionOfADifferentTypeAndInitialCapacity() {

        LinkedListIndexedCollection<String> linkedList = new LinkedListIndexedCollection<>();
        linkedList.add("S");
        linkedList.add("u");
        linkedList.add("n");
        linkedList.add("d");
        linkedList.add("a");
        linkedList.add("y");
        linkedList.add("!");
        ArrayIndexedCollection arrayList = new ArrayIndexedCollection(linkedList, 2);
        collectionElementsMatchTheMessage(arrayList, "Sunday");
    }

    @Test
    public void constructorThatTakesNullValueAsAnArgument() {
        try {
            ArrayIndexedCollection collectionThatTakesNull = new ArrayIndexedCollection(null);
            fail("This constructor should not accept null as an input value");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    public void constructorThatTakesExistingCollectionAndInitialCapacity() {
        ArrayIndexedCollection<String> collectionWithSizeLowerThanNeeded = new ArrayIndexedCollection<>(collection, 10);
        ArrayIndexedCollection<String> collectionWithSizeGreaterThanNeeded = new ArrayIndexedCollection<>(collection, 30);
        if (!(collectionElementsMatchTheMessage(collectionWithSizeGreaterThanNeeded, message)
            || collectionElementsMatchTheMessage(collectionWithSizeLowerThanNeeded, message))) {
            fail("This collection does not resize array properly or does not add all of the elements");
        }
        assertEquals(collection.size(), collectionWithSizeGreaterThanNeeded.size());
        assertEquals(collection.size(), collectionWithSizeLowerThanNeeded.size());
    }

    @Test
    public void constructorThatTakesNullValueAndInitialCapacity() {
        try {
            ArrayIndexedCollection<String> collectionWithNullValue = new ArrayIndexedCollection<>(null, 10);
            fail("This constructor should trhow NullPointerException");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void collectionSizeWithSomeElementsAdded() {
        assertEquals(collection.size(), 23);
    }

    @Test
    void emptyCollectionSize() {
        ArrayIndexedCollection<String> emptyCollection = new ArrayIndexedCollection<>();
        assertEquals(emptyCollection.size(), 0);
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
    void emptyCollectionToAnArray() {
        ArrayIndexedCollection<String> emptyCollection = new ArrayIndexedCollection<>();
        Object[] emptyArrayFromGivenCollection = emptyCollection.toArray();
        assertEquals(emptyArrayFromGivenCollection.length, 0);
    }

    @Test
    void eachCollectionElementAddedToString() {
        ArrayIndexedCollection<String> collectionObjectsAreAddedTo = new ArrayIndexedCollection<>();
        StringBuilder sb = new StringBuilder();
        Processor p = (value) -> sb.append((String) value);
        collection.forEach(p);
        assertTrue(collectionElementsMatchTheMessage(collection, sb.toString()));
    }

    @Test
    void collectionContainsElement() {
        class LetterWrapper {
            private String letter;
            private LetterWrapper(String letter) {
                this.letter = letter;
            }
        }
        ArrayIndexedCollection<LetterWrapper> collectionOfLetters = new ArrayIndexedCollection<>();
        collectionOfLetters.add(new LetterWrapper("H"));
        collectionOfLetters.add(new LetterWrapper("e"));
        collectionOfLetters.add(new LetterWrapper("l"));
        collectionOfLetters.add(new LetterWrapper("l"));
        collectionOfLetters.add(new LetterWrapper("o"));

        Object objectFromCollection = collection.get(1); // letter c
        assertTrue(collection.contains(objectFromCollection));
        LetterWrapper objectNotFromThisCollection = new LetterWrapper("e");
        assertFalse(collection.contains(objectNotFromThisCollection));
        assertFalse(collection.contains("I cannot be contained"));
    }

    @Test
    void successfullyAddObjectToCollection() {
        ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
        array.add("a");
        String firstStringFromCollection = (String) array.get(0);
        assertTrue(array.contains(firstStringFromCollection));
        array.add("b");
        String secondStringFromCollection = (String) array.get(1);
        assertTrue(array.contains(secondStringFromCollection));
        assertFalse(array.contains("c"));
    }

    @Test
    void addingNullValueToCollection() {
        ArrayIndexedCollection<String> array = new ArrayIndexedCollection<>();
        try {
            array.add(null);
            fail("This method should throw NullPointerException");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void gettingObjectsFromCollection() {
        assertEquals("T", (String) collection.get(0));
        assertNotEquals("t", (String) collection.get(0));
        assertEquals("h", (String) collection.get(1));
        assertEquals("!", (String) collection.get(collection.size() - 1));
    }

    @Test
    void gettingAnUnreachableObject() {
        try {
            collection.get(collection.size());
            fail("Collection should throw IndexOutOfBoundsException when getting an unreachable object");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void clearACollection() {
        collection.clear();
        try {
            collection.get(0);
            fail("Collection did not clear it's data properly.");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
        assertEquals(collection.size(), 0);

        ArrayIndexedCollection<String> collectionWithSingleElement = new ArrayIndexedCollection<>(1);
        collectionWithSingleElement.clear();
        try {
            collectionWithSingleElement.get(0);
            fail("Collection did not clear it's data properly.");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void clearEmptyCollection() {
        ArrayIndexedCollection<String> emptyCollection = new ArrayIndexedCollection<>();
        emptyCollection.clear();
        assertEquals(emptyCollection.size(), 0);
        try {
            emptyCollection.get(0);
            fail("Collection did not clear it's data properly.");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void insertToACollectionWithSomeElements() {
        ArrayIndexedCollection<String> filledCollection = new ArrayIndexedCollection<>(3);
        filledCollection.add("H");
        filledCollection.add("i");
        filledCollection.add("!");
        String letterInserted = "c";
        // Inserted to second to last position. Expected to resize the array.
        filledCollection.insert(letterInserted, 2);
        String messageThatCollactionHas = "Hic";
        assertTrue(filledCollection.contains(letterInserted));
        // Inserted to first position. Array should already be resized to double it's original size.
        filledCollection.insert("A", 0);
        assertEquals((String) filledCollection.get(0), "A");
        assertEquals(filledCollection.size(), 5);
        // Inserted to middle position.
        filledCollection.insert("c", 3);
        filledCollection.insert("?", filledCollection.size() - 1);
        filledCollection.insert("#", filledCollection.size());
        assertTrue(collectionElementsMatchTheMessage(filledCollection, "AHicc?!#"));

        // This check whether the list is correctly connected after insertion.
        StringBuilder sb = new StringBuilder();
        Processor p = (value) -> sb.append((String)value);
        collection.forEach(p);
        assertTrue(collectionElementsMatchTheMessage(collection, sb.toString()));
        // Out of bounds insertion.
        try {
            collection.insert("Hello", collection.size() + 1);
            collection.insert("Hi", -1);
            fail("IndexOutOfBoundsException should be thrown here");
        } catch (IndexOutOfBoundsException ex) {
            //

        }
    }

    @Test
    void insertingNullValue() {
        try {
            collection.insert(null, 5);
            fail("This method should throw NullPointerException");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void insertToAnEmptyCollection() {
        ArrayIndexedCollection<String> emptyCollection = new ArrayIndexedCollection<>();
        emptyCollection.insert("Kala", 0);
        assertEquals((String) emptyCollection.get(0), "Kala");
        try {
            collection.insert("Hello", collection.size() + 1);
            collection.insert("Hi", -1);
            fail("IndexOutOfBoundsException should be thrown here");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void indexOfObjectInCollection() {
        ArrayIndexedCollection<String> collectionThatContainsObject = new ArrayIndexedCollection<>();
        String objectAtFirstPosition = "Reese!";
        collectionThatContainsObject.add(objectAtFirstPosition);
        String objectAtSomeMiddlePosition = "Malcom";
        collectionThatContainsObject.add(objectAtSomeMiddlePosition);
        String objectAtLastPosition = "Dewey";
        collectionThatContainsObject.add(objectAtLastPosition);

        assertEquals(collectionThatContainsObject.indexOf(objectAtFirstPosition), 0);
        assertEquals(collectionThatContainsObject.indexOf(objectAtSomeMiddlePosition), 1);
        assertEquals(collectionThatContainsObject.indexOf(objectAtLastPosition), 2);
    }

    @Test
    void indexOfObjectThatIsNotInTheCollection() {
        ArrayIndexedCollection<String> collectionThatDoesntContainObject = new ArrayIndexedCollection<>();
        collectionThatDoesntContainObject.add("Hello there");
        collectionThatDoesntContainObject.add("general");
        assertEquals(collectionThatDoesntContainObject.indexOf("GNU is not UNIX"), -1);
        assertEquals(collectionThatDoesntContainObject.indexOf(null), -1);
    }

    @Test
    void removeObjectFromCollectionWithAnIndex() {
        ArrayIndexedCollection<String> collectionThatNeedsObjectRemoved = new ArrayIndexedCollection<>(4);
        String firstObjectToBeRemoved = "Ingwer";
        String middleObjectToBeRemoved = "Orangen";
        String lastObjectToBeRemoved = "Bonbons";
        collectionThatNeedsObjectRemoved.add(firstObjectToBeRemoved);
        collectionThatNeedsObjectRemoved.add(middleObjectToBeRemoved);
        collectionThatNeedsObjectRemoved.add("Zuckerfrie");
        collectionThatNeedsObjectRemoved.add(lastObjectToBeRemoved);
        // Removing the middle object first
        assertTrue(collectionThatNeedsObjectRemoved.contains(middleObjectToBeRemoved));
        collectionThatNeedsObjectRemoved.remove(1);
        assertFalse(collectionThatNeedsObjectRemoved.contains(middleObjectToBeRemoved));
        // Removing the first object
        assertTrue(collectionThatNeedsObjectRemoved.contains(firstObjectToBeRemoved));
        collectionThatNeedsObjectRemoved.remove(0);
        assertFalse(collectionThatNeedsObjectRemoved.contains(firstObjectToBeRemoved));
        // Removing the last object
        assertTrue(collectionThatNeedsObjectRemoved.contains(lastObjectToBeRemoved));
        collectionThatNeedsObjectRemoved.remove(1);
        assertFalse(collectionThatNeedsObjectRemoved.contains(lastObjectToBeRemoved));
        try {
            collection.remove(collection.size() + 1);
            collection.remove(-1);
            fail("IndexOutOfBoundsException should be thrown here");
        } catch (IndexOutOfBoundsException ex) {
            //
        }
    }

    @Test
    void removingNullFromCollection() {
        try {
            collection.remove(null);
            fail("This method should throw NullPointerException");
        } catch (NullPointerException ex) {
            //
        }
    }

    @Test
    void removingObjectFromACollectionWithReference() {
        collection.remove("T");
        collection.remove("i");
        collection.remove("!");
        assertTrue(collectionElementsMatchTheMessage(collection, "hs constructor works"));

        assertFalse(collection.remove("m"));
    }

    @Test
    void addAllToCollection() {
        ArrayIndexedCollection<String> collectionObjectsAreAddedTo = new ArrayIndexedCollection<>();
        collectionObjectsAreAddedTo.addAll(collection);
        collectionElementsMatchTheMessage(collectionObjectsAreAddedTo, "This constructor works!");
    }

    @Test
    void removeObjectFromACollectionWithAValue() {
        assertTrue(collection.remove("T"));
        assertNotEquals((String) collection.get(0), "T");
        assertFalse(collection.remove("Jablan"));
        assertTrue(collection.remove("c"));
        assertNotEquals((String) collection.get(4), "c");
        assertTrue(collection.remove("!"));
        assertNotEquals((String) collection.get(collection.size() - 1), "!");
    }
    @Test
    void removeFromEmptyCollection() {
        ArrayIndexedCollection<String> emptyCollection = new ArrayIndexedCollection<>();
        assertFalse(emptyCollection.remove("Moo"));
    }

    /**
     * Method for checking the contents of a collection and comparing it to a given string. Uses .equals() for checking.
     * @param collection Collection that is being compared.
     * @param message String that is being compared.
     * @return Returns {@code true} if inputs are the same and {@code false} otherwise.
     */
    public boolean collectionElementsMatchTheMessage(ArrayIndexedCollection collection, String message) {
        for (int i = 0; i < message.length(); i++) {
            // This statement checks each char in a string with a corresponding value that you get from elements
            // String is inherited from Object so it can be used in this collection.
            if (((String) collection.get(i)).equals(message.substring(i,i + 1))) {
                continue;
            }
            return false;
        }
        return true;
    }
}