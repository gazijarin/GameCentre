package fall2018.csc2017.games.Hangman;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
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

    @Test
    public void testGetHangman() {
        setUp();
        assertNotNull(hangmanManager.getHangman());
    }

    @Test
    public void testGetNewWord() {
        setUp();
        assertNotNull(hangmanManager.getHangman());
    }

    @Test
    public void testPuzzleSolved() {
        setUp();
        String word = hangmanManager.getHangman().currWord;

        for (int i = 0; i < word.length(); i++) {
            hangmanManager.getHangman().makeVisible(word.charAt(i));
        }

        assertTrue(hangmanManager.puzzleSolved());

        setUp();

        word = hangmanManager.getHangman().currWord;

        for (int i = word.length() - 1; i >= 0; i--) {
            hangmanManager.getHangman().makeVisible(word.charAt(i));
        }

        assertTrue(hangmanManager.puzzleSolved());

        setUp();
        word = hangmanManager.getHangman().currWord;

        hangmanManager.getHangman().makeVisible(word.charAt(0));
        assertFalse(hangmanManager.puzzleSolved());

    }

    @Test
    public void testPuzzleLost() {
        setUp();
        assertFalse(hangmanManager.puzzleLost());

        for (int i = 0; i < 6; i++) {
            hangmanManager.getHangman().makeVisible('@');
        }

        assertTrue(hangmanManager.puzzleLost());

        setUp();
        String word = hangmanManager.getHangman().currWord;
        hangmanManager.getHangman().makeVisible(word.charAt(0));

        for (int i = 0; i < 5; i++) {
            hangmanManager.getHangman().makeVisible('@');
        }

        assertFalse(hangmanManager.puzzleLost());
    }

    @Test
    public void testIsValid() {
        setUp();
        assertTrue(hangmanManager.isValid("a"));
        assertTrue(hangmanManager.isValid("B"));
        assertFalse(hangmanManager.isValid("%"));
        assertFalse(hangmanManager.isValid("@"));
        assertFalse(hangmanManager.isValid(""));
        assertFalse(hangmanManager.isValid("aa"));
    }

}
