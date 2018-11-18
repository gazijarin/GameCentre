package fall2018.csc2017.games.Hangman;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
//todo: add getters, setters

/**
 * a class that represents a hangman game
 */
public class Hangman {

    //number of characters in current word
    public int numChars;
    //number correctly guessed
    public int numCorr = 0;
    //body part images
    private ImageView[] bodyParts;
    //number of body parts total
    private int numParts = 6;
    //current part - will increment when wrong answers are chosen
    private int currPart = 0;
    //current word being guessed
    private String currWord;

    //difficulty of word, default of medium
    private String difficulty = "medium";

    //view of all the characters
    private TextView[] charViews;


    /**
     * Represents a hangman object with generic difficulty
     *
     * @param word, the word to be guessed
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
     * reveals the correctly guessed chars
     *
     * @param guessed_char, the guessed character
     */
    void makeVisible(char guessed_char) {
        for (int k = 0; k < currWord.length(); k++) {
            if (currWord.charAt(k) == guessed_char) {
                numCorr++;
                charViews[k].setTextColor(Color.BLACK);
            }
        }
    }

}
