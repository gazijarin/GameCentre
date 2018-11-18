package fall2018.csc2017.games.Hangman;

import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import fall2018.csc2017.slidingtiles.R;

/**
 * Managers a hangman object
 */
//todo: implement game
public class HangmanManager extends AppCompatActivity {

    //current score
    private int score;

    //the hangman object being managed
    private Hangman hangman;

    //the number of undos, default of 3
    private int numUndos = 3;

    //difficulty of the word
    private String difficulty = "medium";

    /**
     * creates a new manager for a specific word
     */
    HangmanManager() {

        hangman = new Hangman("test");
    }

    /**
     * makes a move based on user input str
     *
     * @param i, user's guess
     */
    void makeMove(char i) {
        hangman.makeVisible(i);
    }

    /**
     * gets a new word based on difficulty
     * default of medium
     *
     * @return a word for the user to guess
     */
    private String getNewWord() {
        Random rand = new Random();
        String[] words;

        if (difficulty.equals("easy")) {
            words = getResources().getStringArray(R.array.easyWords);

        } else if (difficulty.equals("hard")) {
            words = getResources().getStringArray(R.array.hardWords);
        } else {
            words = getResources().getStringArray(R.array.mediumWords);
        }
        return words[rand.nextInt(words.length)];

    }
    //todo: implement

    /**
     * undos a single move
     *
     * @return if undo was success
     */
    boolean undo() {
        return false;
    }

    /**
     * @return if the game is solved
     */
    boolean puzzleSolved() {
        return hangman.numCorr == hangman.numChars;
    }

    /**
     * @param guess, the users guess
     * @return if guess is a valid guess, single char from english alphabet
     */
    private boolean isValid(String guess) {
        return guess.matches("[a-zA-Z]");
    }
}
