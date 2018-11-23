package fall2018.csc2017.games.Hangman;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * A class that represents a Hangman game.
 */
public class Hangman {
    /**
     * The number of characters in current word.
     */
    private int numChars;
    /**
     * The number of characters correctly guessed.
     */
    private int numCorr = 0;
    /**
     * The current word being guessed.
     */
    private String currWord;
    /**
     * View of all the characters.
     */
    private TextView[] charViews;

    /**
     * Represents a hangman object with generic difficulty.
     *
     * @param word the word to be guessed
     */
    Hangman(String word) {
        currWord = word;
        charViews = new TextView[currWord.length()];
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

    /**
     * Reveals the correctly guessed characters.
     *
     * @param guessedChar the guessed character
     */
    void makeVisible(char guessedChar) {
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == guessedChar) {
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
    }

}
