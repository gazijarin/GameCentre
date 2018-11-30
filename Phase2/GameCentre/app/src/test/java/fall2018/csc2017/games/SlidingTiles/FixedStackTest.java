package fall2018.csc2017.games.SlidingTiles;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FixedStackTest {

    /**
     * Tests that pushing and poping works when below max size
     */
    @Test
    public void testBelowMaxSize() {
        FixedStack<Integer> temp = new FixedStack<>(5);
        temp.push(6);
        temp.push(7);
        assertEquals(7, (int) temp.pop());

    }

    /**
     * Tests that pushing and poping works when pushing to above max size
     */
    @Test
    public void testAboveMaxSize() {
        FixedStack<Integer> temp = new FixedStack<>(2);
        temp.push(6);
        temp.push(7);
        temp.push(8);
        temp.pop();
        temp.pop();
        assertTrue(temp.isEmpty());

    }

    /**
     * Test that pushing works as expected to a max size of 0
     */
    @Test
    public void testSizeZero() {
        FixedStack<Integer> temp = new FixedStack<>(0);
        temp.push(8);
        assertTrue(temp.isEmpty());

    }
}
