package hr.fer.zemris.java.hw01;

import hr.fer.zemris.java.hw01.UniqueNumbers.TreeNode;
import org.junit.jupiter.api.Test;

import static hr.fer.zemris.java.hw01.UniqueNumbers.*;
import static org.junit.jupiter.api.Assertions.*;

class UniqueNumbersTest {

    @Test
    void basicAddNode() {
        TreeNode glava = null;
        glava = addNode(glava, 42);
        glava = addNode(glava, 10);
        glava = addNode(glava, 42);
        glava = addNode(glava, 230);
        glava = addNode(glava, 5);
        glava = addNode(glava, 100);
        glava = addNode(glava, 0);
        glava = addNode(glava, -1);
        assertTrue(containsValue(glava, 42), "Tree should contain value 42 but it does not.");
        assertFalse(containsValue(glava, 1), "Tree shouldn't contain value 1 but it does");
        assertTrue(containsValue(glava, -1), "Tree doesn't contain number -1");
        assertTrue(containsValue(glava, 0),"Tree should contain number 0");
        assertEquals(treeSize(glava),7);
    }

    @Test
    void treeSizeWithSomeNodes() {
        TreeNode glava = null;
        glava = addNode(glava, 42);
        glava = addNode(glava, 10);
        glava = addNode(glava, 42);
        glava = addNode(glava, 42);
        glava = addNode(glava, -1);
        glava = addNode(glava, 0);
        glava = addNode(glava, 42);
        glava = addNode(glava, -1);
        assertEquals(treeSize(glava), 4);

    }

    @Test
    void treeSizeWithEmptyTree() {
        TreeNode glava = null;
        assertEquals(treeSize(glava), 0);
    }

    @Test
    void treeWithSomeElementsContainsValue() {
        TreeNode glava = null;
        glava = addNode(glava, 5);
        glava = addNode(glava, 10);
        glava = addNode(glava, 22);
        glava = addNode(glava, 42);
        glava = addNode(glava, -1);
        glava = addNode(glava, 0);
        glava = addNode(glava, 42);
        glava = addNode(glava, -10);
        assertTrue(containsValue(glava, 5));
        assertFalse(containsValue(glava, 2));
        assertTrue(containsValue(glava, 0));
        assertTrue(containsValue(glava, -10));
    }

    @Test
    void emptyTreeContainsValue() {
        TreeNode glava = null;
        assertFalse(containsValue(glava, 2));
    }
}