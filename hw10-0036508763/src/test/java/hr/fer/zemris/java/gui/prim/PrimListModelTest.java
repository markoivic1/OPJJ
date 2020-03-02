package hr.fer.zemris.java.gui.prim;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class PrimListModelTest {
    private PrimListModel<Integer> model = new PrimListModel<>();

    @BeforeEach
    void init() {
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();
    }

    @Test
    void getSizeTest() {
        assertEquals(model.getSize(), 7);
        model.next();
        assertEquals(model.getSize(), 8);
        model = new PrimListModel<>();
        assertEquals(model.getSize(), 1);
    }

    @Test
    void getElementAtTest() {
        assertEquals(model.getElementAt(3), 5);
        model.next();
        assertEquals(model.getElementAt(3), 5);
        model = new PrimListModel<>();
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();
        model.next();
        assertEquals(model.getElementAt(model.getSize() - 1), 17);
    }

    @Test
    void addListDataListenerTest() {
        model = new PrimListModel<>();
        model.addListDataListener(new ListDataListener() {
            @Override
            public void intervalAdded(ListDataEvent e) {

            }

            @Override
            public void intervalRemoved(ListDataEvent e) {

            }

            @Override
            public void contentsChanged(ListDataEvent e) {
                assertEquals(model.getElementAt(model.getSize() - 1), 2);
                assertNotEquals(model.getElementAt(model.getSize() - 1) , 3);
            }
        });
        model.next();
    }

    @Test
    void removeListDataListenerTest() {
    }
}