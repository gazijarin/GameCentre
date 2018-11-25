package fall2018.csc2017.games.SlidingTiles;

import org.junit.Test;

import static org.junit.Assert.*;

public class FixedStackTest {
    @Test

    public void testBelowMaxSize() {
        FixedStack<Integer> temp = new FixedStack<>(5);
        temp.push(6);
        temp.push(7);
        assertTrue(temp.pop() == 7);

    }


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
}
