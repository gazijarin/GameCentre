package fall2018.csc2017.games.Hangman;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class HangmanTest {

    private Hangman hangman;

    @Before
    public void setUp() {
        hangman = new Hangman("test");
    }

    /**
     * Tests for all the getters for private variables
     */
    @Test
    public void testGetCurrWord() {
        assertEquals("test", hangman.getCurrWord());
    }

    /**
     * Tests revealed word is initialized correctly
     */
    @Test
    public void testGetRevealedWord() {
        for (int i = 0; i < 4; i++) {
            assertEquals('@', hangman.getRevealedWord()[i]);
        }
    }

    /**
     * Tests word gets revealed properly
     */
    @Test
    public void testRevealedWordOneGuess() {
        hangman.makeVisible('t');
        assertEquals('T', hangman.getRevealedWord()[0]);
        assertEquals('@', hangman.getRevealedWord()[1]);
        assertEquals('@', hangman.getRevealedWord()[2]);
        assertEquals('T', hangman.getRevealedWord()[3]);

    }

    /**
     * Tests currentGuesses start at 0
     */
    @Test
    public void testGetCurrentGuessesInitial() {
        assertEquals(0, hangman.getCurrentGuesses());
    }

    /**
     * Tests currentGuesses increments
     */
    @Test
    public void testGetCurrentGuessesOneGuess() {
        hangman.makeVisible('z');
        assertEquals(1, hangman.getCurrentGuesses());
    }


    /**
     * tests on char not in word
     */
    @Test
    public void testMakeVisibleCharNoInWord() {
        char[] reveal = hangman.getRevealedWord();
        hangman.makeVisible('d'); //a character that can't be in the word
        assertEquals('@', reveal[0]);
    }

    /**
     * Tests on lower case input
     */
    @Test
    public void testMakeVisibleLowerCase() {
        hangman.getRevealedWord();
        hangman.makeVisible('t');
    }

    /**
     * Tests on upper case input
     */
    @Test
    public void testMakeVisibleUpperCase() {
        char[] reveal = hangman.getRevealedWord();
        hangman.makeVisible('T');
        assertEquals("T@@T", new String(reveal));
        hangman.makeVisible('E');
        assertEquals("TE@T", new String(reveal));
    }

    /**
     * Tests same guess twice
     */
    @Test
    public void testMakeVisibleSameGuess() {
        char[] reveal = hangman.getRevealedWord();
        hangman.makeVisible('T');
        assertEquals("T@@T", new String(reveal));
        hangman.makeVisible('t');
        assertEquals("T@@T", new String(reveal));
    }

    /**
     * Tests return true when expected
     */
    @Test
    public void testMakeVisibleReturnTrue() {
        assertTrue(hangman.makeVisible('t'));

    }

    /**
     * Tests return false when expected
     */
    @Test
    public void testMakeVisibleReturnFalse() {
        assertFalse(hangman.makeVisible('z'));
    }
}
