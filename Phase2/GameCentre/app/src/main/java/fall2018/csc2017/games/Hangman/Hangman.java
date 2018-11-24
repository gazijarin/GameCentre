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
     * The number of characters correctly guessed.
     */
    int numCorr = 0;
    /**
     * View of all the characters.
     */
    List<Character> chars;
    /**
     * The number of characters in current word.
     */
    private int numChars;

    /**
     * Represents a hangman object with generic difficulty.
     *
     * @param word the word to be guessed
     */
    Hangman(String word) {
        currWord = word;
        chars = new ArrayList<Character>();
        numChars = currWord.length();
    }

    /**
     * Forms ArrayList of chars from currWord to be used by activity
     */
    void populateChars() {
        for (int c = 0; c < currWord.length(); c++) {
            chars.add(currWord.charAt(c));
        }
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
     * Returns the number of characters.
     *
     * @return the number of characters
     */
    int getNumCorr() {
        return numCorr;
    }

    /**
     * Returns the number of characters.
     *
     * @return the number of characters
     */
    String getCurrWord() {
        return currWord;
    }


}
