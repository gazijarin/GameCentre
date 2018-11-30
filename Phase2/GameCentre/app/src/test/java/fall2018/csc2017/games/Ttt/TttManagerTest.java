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
     * Set up a single player manager
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

    /**
     * Test the difficulty functionality for multiplayer setup
     */
    @Test
    public void testModeDouble() {
        tttManager.setDifficulty("double");
        assertSame("double", tttManager.getDifficulty());
    }

    /**
     * Test for the game properties
     */
    @Test
    public void testGameProperties() {

        assertEquals("Ttt", tttManager.getGameId());
    }

    /**
     * Testing if the number of undo change appropriately
     */
    @Test
    public void setNumUndos() {

        assertEquals(1, tttManager.getNumUndos());
        tttManager.setNumUndos(5);
        assertEquals(5, tttManager.getNumUndos());
    }


    /**
     * Test the undo functionality
     */
    @Test
    public void undo() {
        tttManager = new TttManager("double");
        tttManager.play(0, 0, 1);
        tttManager.undo();
        assertEquals(0, tttManager.board[0][0]);

    }


    /**
     * Test the AI for the computer
     */
    @Test
    public void computerPlay() {
        tttManager = new TttManager("single");
        int[] move = tttManager.computerPlay();
        assertEquals(tttManager.board[move[0]][move[1]], 2);

    }

    /**
     * Test if he move is being made in the game
     */
    @Test
    public void play() {
        tttManager = new TttManager("double");
        tttManager.play(0, 0, 1);
        assertEquals(1, tttManager.board[0][0]);
    }

    /**
     * Test if the the game recognizes horizontal strikes
     */
    @Test
    public void checkHorizontals() {
        tttManager = new TttManager("double");
        tttManager.play(0, 0, 1);
        tttManager.play(0, 1, 1);
        assertFalse(tttManager.checkHorizontals());

        tttManager.play(0, 2, 1);
        assertTrue(tttManager.checkHorizontals());

        tttManager = new TttManager("double");
        tttManager.play(0, 0, 1);
        tttManager.play(1, 0, 1);
        tttManager.play(2, 0, 1);
        assertTrue(tttManager.checkHorizontals());
    }

    /**
     * Test if the the game recognizes vertical strikes
     */
    @Test
    public void checkDiagonals() {
        tttManager = new TttManager("double");
        tttManager.play(0, 0, 1);
        tttManager.play(1, 1, 1);
        assertFalse(tttManager.checkDiagonals());

        tttManager.play(2, 2, 1);
        assertTrue(tttManager.checkDiagonals());
    }

    /**
     * Test if he the game properly resets
     */
    @Test
    public void resetBoard() {
        tttManager = new TttManager("double");
        tttManager.play(0, 0, 1);
        tttManager.play(0, 1, 1);
        tttManager.play(0, 2, 1);

        assertEquals(1, tttManager.board[0][0]);
        assertEquals(1, tttManager.board[0][1]);
        assertEquals(1, tttManager.board[0][2]);


        tttManager.resetBoard();
        assertEquals(0, tttManager.board[0][0]);
        assertEquals(0, tttManager.board[0][1]);
        assertEquals(0, tttManager.board[0][2]);
    }


    /**
     * Check if he the game properly resets
     */
    @Test
    public void getUndoTime() {
        tttManager = new TttManager("double");
        assertEquals(2, tttManager.getUndoTime());

        tttManager = new TttManager("single");
        assertEquals(1, tttManager.getUndoTime());
    }

    /**
     * Check if the the right score is returned
     */
    @Test
    public void getScore() {
        tttManager = new TttManager("double");
        assertEquals(0, tttManager.getScore());
        tttManager.points.put("p1", tttManager.points.get("p1") + 1);
        assertEquals(1, tttManager.getScore());
    }

    /**
     * Check if the high top score is true.
     */
    @Test
    public void testHighTopScore() {
        assertEquals(true, tttManager.highTopScore());
    }
}