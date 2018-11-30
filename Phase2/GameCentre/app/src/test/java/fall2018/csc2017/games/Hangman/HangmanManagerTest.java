package fall2018.csc2017.games.Hangman;

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

    /**
     * Sets up a hangmanManager with hangman of medium difficulty
     */
    @Before
    public void setUp() {
        hangmanManager = new HangmanManager("medium");
    }

    /**
     * tests that the difficulty defaults properly
     */
    @Test
    public void testDifficultyDefault() {
        assertEquals("medium", hangmanManager.getDifficulty()); //should default to medium
    }

    /**
     * tests changing difficulty to easy
     */
    @Test
    public void testDifficultyEasy() {

        hangmanManager.setDifficulty("easy");
        assertSame("easy", hangmanManager.getDifficulty());
    }

    /**
     * tests changing difficulty to hard
     */
    @Test
    public void testDifficultyHard() {
        hangmanManager.setDifficulty("hard");
        assertSame("hard", hangmanManager.getDifficulty());
    }

    /**
     * tests that each new manager has a different word than the last
     */
    @Test
    public void testNewWordNoRepeats() {
        String oldWord = hangmanManager.getHangman().currWord;
        for (int i = 0; i < 30; i++) {
            assertNotEquals(oldWord, hangmanManager.getNewWord());
        }
    }

    /**
     * tests the game name is correct
     */
    @Test
    public void testGameName() {

        assertEquals("hangman", hangmanManager.getGameId());
        assertTrue(hangmanManager.highTopScore());
    }

    /**
     * tests undo getter + setter
     */
    @Test
    public void setNumUndos() {

        assertEquals(3, hangmanManager.getNumUndos());
        hangmanManager.setNumUndos(5);
        assertEquals(5, hangmanManager.getNumUndos());
    }

    /**
     * tests a hangman is returned
     */
    @Test
    public void testGetHangman() {

        assertNotNull(hangmanManager.getHangman());
    }

    /**
     * tests a new word is actually created
     */
    @Test
    public void testGetNewWord() {

        assertNotNull(hangmanManager.getNewWord());
    }

    /**
     * tests that puzzle is solved when expected
     */
    @Test
    public void testPuzzleSolvedCustomWord() {
        HangmanManager manager = new HangmanManager("medium", "A");
        Hangman hangman = manager.getHangman();
        hangman.makeVisible('A');
        assertTrue(manager.puzzleSolved());
    }

    /**
     * tests puzzled is solved when expected when guessing in order
     */
    @Test
    public void testPuzzleSolveTrueForward() {

        String word = hangmanManager.getHangman().currWord;
        for (int i = 0; i < word.length(); i++) {
            hangmanManager.getHangman().makeVisible(word.charAt(i));
        }
        assertTrue(hangmanManager.puzzleSolved());
    }

    /**
     * tests puzzled is solved when expected when guessing in a different order
     */
    @Test
    public void testPuzzleSolveTrueReverse() {

        String word = hangmanManager.getHangman().currWord;
        for (int i = word.length() - 1; i >= 0; i--) {
            hangmanManager.getHangman().makeVisible(word.charAt(i));
        }

        assertTrue(hangmanManager.puzzleSolved());
    }

    /**
     * tests puzzle isn't initialized as solved
     */
    @Test
    public void testPuzzleSolvedFalseInitial() {

        assertFalse(hangmanManager.puzzleSolved());
    }

    /**
     * tests that puzzle isn't solved when not expected
     */
    @Test
    public void testPuzzleSolvedFalseOneGuess() {

        String word = hangmanManager.getHangman().currWord;
        hangmanManager.getHangman().makeVisible(word.charAt(0));
        assertFalse(hangmanManager.puzzleSolved());
    }

    /**
     * tests the puzzle is lost when expected
     */
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

    /**
     * tests various input the ensure only correct inputs are allowed
     */
    @Test
    public void testIsValid() {
        assertTrue(hangmanManager.isValid("a"));
        assertTrue(hangmanManager.isValid("B"));
        assertFalse(hangmanManager.isValid("%"));
        assertFalse(hangmanManager.isValid("@"));
        assertFalse(hangmanManager.isValid(""));
        assertFalse(hangmanManager.isValid("aa"));
    }

    /**
     * Tests the score for easy difficulty
     */
    @Test
    public void testGetScoreEasy() {
        hangmanManager = new HangmanManager("easy", "red");
        hangmanManager.getHangman().makeVisible('z');
        assertEquals(hangmanManager.getScore(), 15);
    }

    /**
     * Tests the score for medium difficulty
     */
    @Test
    public void testGetScoreMedium() {
        hangmanManager = new HangmanManager("medium", "fruitfly");
        hangmanManager.getHangman().makeVisible('z');
        assertEquals(hangmanManager.getScore(), 30);
    }

    /**
     * Tests the score for hard difficulty
     */
    @Test
    public void testGetScoreHard() {
        hangmanManager = new HangmanManager("hard", "poingant");
        hangmanManager.getHangman().makeVisible('z');
        assertEquals(hangmanManager.getScore(), 45);
    }


}
