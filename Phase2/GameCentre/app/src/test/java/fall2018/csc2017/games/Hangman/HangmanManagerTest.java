package fall2018.csc2017.games.Hangman;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HangmanManagerTest {
    /**
     * The board manager for testing.
     */
    HangmanManager hangmanManager;


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
        assertEquals("medium", hangmanManager.getDifficulty()); //should default to medium
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



}
