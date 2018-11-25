package fall2018.csc2017.games.Hangman;

import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertEquals;

public class HangmanTest {

    Hangman hangman;

    @Before
    public void setUp() {
        hangman = new Hangman("test");
    }

    @Test
    public void testGetters() {
        assertNotNull(hangman.getCurrWord());
        assertNotNull(hangman.getRevealedWord());
        assertEquals(0, hangman.getCurrentGuesses());
    }

    @Test
    public void testMakeVisible() {
        char[] reveal = hangman.getRevealedWord();
        assertEquals('@', reveal[0]);
        hangman.makeVisible('d'); //a character that can't be in the word
        assertEquals('@', reveal[0]);
        hangman.makeVisible('t'); //case shouldn't matter
        assertEquals("T@@T", new String(reveal)); //test if its been replaced
        hangman.makeVisible('t'); //doing it again shouldn't make a difference
        assertEquals("T@@T", new String(reveal)); //test if its been replaced
        hangman.makeVisible('E');
        assertEquals("TE@T", new String(reveal)); //test if its been replaced
    }
}
