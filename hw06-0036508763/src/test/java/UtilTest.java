import hr.fer.zemris.java.hw06.crypto.Util;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Marko Ivić
 * @version 1.0.0
 */
class UtilTest {

    @Test
    void hextobyteTest() {
        byte[] initialArray = Util.hextobyte("01aE22");
        byte[] expectedArray = {1, -82, 34};
        assertArrayEquals(initialArray, expectedArray);
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01a"));
        assertThrows(IllegalArgumentException.class, () -> Util.hextobyte("01ra"));
    }

    @Test
    void bytetohexTest() {
        String initial = Util.bytetohex(new byte[] {1, -82, 34});
        String expected = "01ae22";
        assertEquals(initial, expected);
    }
}