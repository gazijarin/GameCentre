package fall2018.csc2017.games.Hangman;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class HangmanManagerTest {
    /**
     * The board manager for testing.
     */
    HangmanManager hangmanManager;

    @Before
    public void setUp() {
        hangmanManager = new HangmanManager("medium");
    }

    @Test
    public void testDifficulty() {
        setUp();
        String prevWord = hangmanManager.getHangman().getCurrWord();
        assertEquals("medium", hangmanManager.getDifficulty());
        hangmanManager.setDifficulty("hard");
        String nextWord = hangmanManager.getHangman().getCurrWord();
        assertEquals("hard", hangmanManager.getDifficulty());
        assertNotEquals(prevWord, nextWord);
        hangmanManager.setDifficulty("dummy");
        //assertEquals("medium", hangmanManager.getDifficulty()); //should default to medium
    }

    @Test
    public void testGameProperties() {
        setUp();
        assertEquals("hangman", hangmanManager.getGameId());
        assertFalse(hangmanManager.highTopScore());
    }

    @Test
    public void setNumUndos() {
        setUp();
        assertEquals(3, hangmanManager.getNumUndos());
        hangmanManager.setNumUndos(5);
        assertEquals(5, hangmanManager.getNumUndos());
    }

    public void testMakeMove() {


    }

    @Test
    public void testPuzzleSolvedTrue() {
        HangmanManager manager = new HangmanManager(1, "a");
        Hangman hangman = manager.getHangman();
        hangman.makeVisible('a');
        assertTrue(manager.puzzleSolved());


    }

    @Test
    public void testPuzzleSolvedFalse() {
        assertFalse(hangmanManager.puzzleSolved());
    }



}
