package fall2018.csc2017.games.Hangman;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that represents a Hangman game.
 */
public class Hangman {
    /**
     * The current word being guessed.
     */
    String currWord;
    /**
     * A char array representing the parts of the word that have been revealed
     */
    private char[] revealedWord;

    /**
     * The number of characters correctly guessed.
     */
    int numCorr = 0;

    /**
     * The number of characters in current word.
     */
    private int numChars;

    /**
     * The total number of guesses for the hangman game.
     */
    public final int TOTAL_GUESSES = 6;

    /**
     * The current guess amount.
     */
    private int currentGuesses;

    /**
     * Represents a hangman object with generic difficulty.
     *
     * @param word the word to be guessed
     */
    Hangman(String word) {
        currWord = word;
        currentGuesses = 0;
        revealedWord = new char[word.length()];

        for (int i = 0; i < revealedWord.length; i++) {
            revealedWord[i] = '@';
        }

        numChars = currWord.length();
    }

    /**
     * Returns the number of characters.
     *
     * @return the number of characters
     */
    int getNumChars() {
        return numChars;
    }

    /**
     * Returns the number of characters correctly guessed.
     *
     * @return the number of characters correctly guessed.
     */
    int getNumCorr() {
        return numCorr;
    }

    /**
     * Returns the current word
     *
     * @return the current word
     */
    String getCurrWord() {
        return currWord;
    }

    /**
     * Makes a character visible in this hangman
     *
     * @param character the character to make visible
     * @return whether the letter was found
     */
    boolean makeVisible(char character) {
        boolean found = false;
        for (int i = 0; i < currWord.length(); i++) {
            if (Character.toLowerCase(currWord.charAt(i)) ==
                    Character.toLowerCase(character)) {
                revealedWord[i] = character;
                found = true;
            }
        }
        if (!found) {
            currentGuesses++;
        }
        return found;
    }

    /**
     * returns the char array representing the parts revealed of the word.
     *
     * @return the char array representing the parts revealed of the word.
     */
    char[] getRevealedWord() {
        return revealedWord;
    }

    /**
     * Returns how many guesses so far
     *
     * @return how many guesses so far
     */
    public int getCurrentGuesses() {
        return currentGuesses;
    }
}
