package fall2018.csc2017.games.Hangman;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
//todo: add getters, setters

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
     * Body part images.
     */
    private ImageView[] bodyParts;
    /**
     * The number of body parts in total.
     */
    private int numParts = 6;
    /**
     * The current body part.
     */
    private int currPart;
    /**
     * The current word being guessed.
     */
    private String currWord;
    /**
     * The difficulty of the word; default set to medium.
     */
    private String difficulty = "medium";
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

        currPart = 0;
        numChars = currWord.length();

        for (int p = 0; p < numParts; p++) {
            bodyParts[p].setVisibility(View.INVISIBLE);
        }

    }

    /**
     * Returns the number of characters.
     *
     * @return the number of characters
     */
    public int getNumChars() {
        return numChars;
    }

    /**
     * Returns the number of characters.
     *
     * @return the number of characters
     */
    public int getNumCorr() {
        return numCorr;
    }

    /**
     * Returns the number of body parts.
     *
     * @return the number of body parts
     */
    public int getNumParts() {
        return numParts;
    }

    /**
     * Returns the current body part.
     *
     * @return the current body part
     */
    public int getCurrPart() {
        return currPart;
    }

    /**
     * Returns the current word.
     *
     * @return the current word
     */
    public String getCurrWord() {
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
