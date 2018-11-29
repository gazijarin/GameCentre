package fall2018.csc2017.games.Ttt;

import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */

public class TttManagerTest {
    /**
     * The board manager for testing.
     */
    private TttManager tttManager;

    /**
     * Set up a default single player manager
     */
    @Before
    public void setUp() {
        tttManager = new TttManager("single");
    }

    /**
     * Test the difficulty functionality for default setup
     */
    @Test
    public void testDifficultyDefault() {
        assertEquals("single", tttManager.getDifficulty());

    }

    /**
     * Test the difficulty functionality for single player setup
     */
    @Test
    public void testModeSingle() {

        tttManager.setDifficulty("single");
        assertSame("single", tttManager.getDifficulty());
    }

    /** Test the difficulty functionality for multiplayer setup */
    @Test
    public void testModeDouble() {
        tttManager.setDifficulty("double");
        assertSame("double", tttManager.getDifficulty());
    }

    /** Test for the game properties */
    @Test
    public void testGameProperties() {

        assertEquals("Ttt", tttManager.getGameId());
        assertFalse(tttManager.highTopScore());
    }

    /** Test for the functionality of undo */
    @Test
    public void setNumUndos() {

        assertEquals(1, tttManager.getNumUndos());
        tttManager.setNumUndos(5);
        assertEquals(5, tttManager.getNumUndos());
    }

    //TODO: Write unit tests for the methods below
    @Test
    public void undo() {
    }

    @Test
    public void computerPlay() {
    }

    @Test
    public void play() {
    }

    @Test
    public void checkForWin() {
    }

    @Test
    public void resetBoard() {
    }

    @Test
    public void getUndoTime() {
    }

    @Test
    public void getScore() {
    }
}