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
     * The Hangman body.
     */
    private HangmanBody hangmanBody;
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

        numChars = currWord.length();


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
     * Returns the current word.
     *
     * @return the current word
     */
    public String getCurrWord() {
        return currWord;
    }

    /**
     * Set numChars to inputted numChars.
     *
     * @param numChars the number of characters
     */
    public void setNumChars(int numChars) {
        this.numChars = numChars;
    }

    /**
     * Set numCorr to inputted numCorr.
     *
     * @param numCorr the number of correct characters
     */
    public void setNumCorr(int numCorr) {
        this.numCorr = numCorr;
    }

    /**
     * Set currWord to inputted currWord.
     *
     * @param currWord the current word
     */
    public void setCurrWord(String currWord) {
        this.currWord = currWord;
    }

    /**
     * Set difficulty to inputted difficulty.
     *
     * @param difficulty the difficulty
     */
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
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
