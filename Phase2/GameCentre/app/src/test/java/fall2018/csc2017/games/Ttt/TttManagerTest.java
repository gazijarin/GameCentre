package fall2018.csc2017.games.Ttt;

import org.junit.Before;
import org.junit.Test;


import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import static org.junit.Assert.*;

public class TttManagerTest {
    /**
     * The tic tac toe board manager for testing
     */
    private TttManager tttManager;

    @Before
    public void setUp() {
        tttManager = new TttManager("single");
    }


    @Test
    public void testDifficultyDefault() {
        assertEquals("single", tttManager.getDifficulty()); //should default to single

    }

    @Test
    public void testModeSingle() {

        tttManager.setDifficulty("single");
        assertSame("single", tttManager.getDifficulty());
    }

    @Test
    public void testModeDouble() {
        tttManager.setDifficulty("double");
        assertSame("double", tttManager.getDifficulty());
    }


    @Test
    public void testGameProperties() {

        assertEquals("Ttt", tttManager.getGameId());
        assertFalse(tttManager.highTopScore());
    }

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