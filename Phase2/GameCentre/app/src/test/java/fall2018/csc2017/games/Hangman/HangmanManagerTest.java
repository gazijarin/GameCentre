package fall2018.csc2017.games.Hangman;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HangmanManagerTest {
    /**
     * The board manager for testing.
     */
    private HangmanManager hangmanManager;

    @Before
    public void setUp() {
        hangmanManager = new HangmanManager("medium");
    }


    @Test
    public void testDifficultyDefault() {
        assertEquals("medium", hangmanManager.getDifficulty()); //should default to medium
    }

    @Test
    public void testDifficultyEasy() {

        hangmanManager.setDifficulty("easy");
        assertSame("easy", hangmanManager.getDifficulty());
    }

    @Test
    public void testDifficultyHard() {
        hangmanManager.setDifficulty("hard");
        assertSame("hard", hangmanManager.getDifficulty());
    }

    @Test
    public void testNewWordNoRepeats() {
        String oldWord = hangmanManager.getHangman().currWord;
        for (int i = 0; i < 30; i++) {
            assertNotEquals(oldWord, hangmanManager.getNewWord());
        }
    }

    @Test
    public void testGameProperties() {

        assertEquals("hangman", hangmanManager.getGameId());
        assertFalse(hangmanManager.highTopScore());
    }

    @Test
    public void setNumUndos() {

        assertEquals(3, hangmanManager.getNumUndos());
        hangmanManager.setNumUndos(5);
        assertEquals(5, hangmanManager.getNumUndos());
    }

    @Test
    public void testGetHangman() {

        assertNotNull(hangmanManager.getHangman());
    }

    @Test
    public void testGetNewWord() {

        assertNotNull(hangmanManager.getHangman());
    }


    @Test
    public void testPuzzleSolvedCustomWord() {
        HangmanManager manager = new HangmanManager(1, "A");
        Hangman hangman = manager.getHangman();
        hangman.makeVisible('A');
        assertTrue(manager.puzzleSolved());
    }

    @Test
    public void testPuzzleSolveTrueForward() {

        String word = hangmanManager.getHangman().currWord;
        for (int i = 0; i < word.length(); i++) {
            hangmanManager.getHangman().makeVisible(word.charAt(i));
        }
        assertTrue(hangmanManager.puzzleSolved());
    }

    @Test
    public void testPuzzleSolveTrueReverse() {

        String word = hangmanManager.getHangman().currWord;
        for (int i = word.length() - 1; i >= 0; i--) {
            hangmanManager.getHangman().makeVisible(word.charAt(i));
        }

        assertTrue(hangmanManager.puzzleSolved());
    }


    @Test
    public void testPuzzleSolvedFalseInitial() {

        assertFalse(hangmanManager.puzzleSolved());
    }

    @Test
    public void testPuzzleSolvedFalseOneGuess() {

        String word = hangmanManager.getHangman().currWord;
        hangmanManager.getHangman().makeVisible(word.charAt(0));
        assertFalse(hangmanManager.puzzleSolved());
    }

    @Test
    public void testPuzzleLost() {

        assertFalse(hangmanManager.puzzleLost());

        for (int i = 0; i < 6; i++) {
            hangmanManager.getHangman().makeVisible('@');
        }

        assertTrue(hangmanManager.puzzleLost());


        String word = hangmanManager.getHangman().currWord;
        hangmanManager.getHangman().makeVisible(word.charAt(0));

        for (int i = 0; i < 5; i++) {
            hangmanManager.getHangman().makeVisible('@');
        }

        assertFalse(hangmanManager.puzzleLost());
    }

    @Test
    public void testIsValid() {

        assertTrue(hangmanManager.isValid("a"));
        assertTrue(hangmanManager.isValid("B"));
        assertFalse(hangmanManager.isValid("%"));
        assertFalse(hangmanManager.isValid("@"));
        assertFalse(hangmanManager.isValid(""));
        assertFalse(hangmanManager.isValid("aa"));
    }

}
