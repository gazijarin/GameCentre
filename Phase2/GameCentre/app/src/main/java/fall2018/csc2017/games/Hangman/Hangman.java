package fall2018.csc2017.games.Hangman;

import java.io.Serializable;

/**
 * A class that represents a Hangman game.
 */
public class Hangman implements Serializable {
    /**
     * The current word being guessed.
     */
    String currWord;
    /**
     * A char array representing the parts of the word that have been revealed
     */
    private char[] revealedWord;
    /**
     * The total number of guesses for the hangman game.
     */
    final int TOTAL_GUESSES = 6;

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
            if (Character.toUpperCase(currWord.charAt(i)) ==
                    Character.toUpperCase(character)) {
                revealedWord[i] = Character.toUpperCase(character);
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
    int getCurrentGuesses() {
        return currentGuesses;
    }
}
