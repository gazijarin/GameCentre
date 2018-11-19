package fall2018.csc2017.games.Hangman;

import android.support.v7.app.AppCompatActivity;

import java.util.Random;

import fall2018.csc2017.slidingtiles.R;

/**
 * Managers a Hangman object.
 */
//todo: implement game
public class HangmanManager extends AppCompatActivity {
    /**
     * The current score.
     */
    private int score;
    /**
     * The Hangman being managed.
     */
    private Hangman hangman;
    /**
     * The number of undos; default set to 3.
     */
    private int numUndos = 3;
    /**
     * The difficulty of the word; default set to medium.
     */
    private String difficulty = "medium";

    /**
     * Creates a new manager for a specific word.
     */
    HangmanManager() {
        hangman = new Hangman("test");
    }

    /**
     * Manage a new shuffled hangman game.
     *
     * @param difficulty the dimension of the desired board
     */
    HangmanManager(String difficulty) {
        hangman = new Hangman(getNewWord());
        setNumUndos(3);
    }

    public void setNumUndos(int numUndos) {
        this.numUndos = numUndos;
    }

    /**
     * Makes a move based on user input str.
     *
     * @param letter user's guess
     */
    void makeMove(char letter) {
        hangman.makeVisible(letter);
    }

    /**
     * Generates a new word based on difficulty.
     *
     * @return a word for the user to guess
     */
    private String getNewWord() {
        Random rand = new Random();
        String[] words;

        switch (difficulty) {
            case "easy":
                words = getResources().getStringArray(R.array.easyWords);

                break;
            case "hard":
                words = getResources().getStringArray(R.array.hardWords);
                break;
            default:
                words = getResources().getStringArray(R.array.mediumWords);
                break;
        }
        return words[rand.nextInt(words.length)];

    }
    //todo: implement

    /**
     * Undoes a single move.
     *
     * @return if undo was successful
     */
    boolean undo() {
        return false;
    }

    /**
     * Returns if the puzzle was solved.
     *
     * @return if the game is solved
     */
    boolean puzzleSolved() {
        return hangman.getNumCorr() == hangman.getNumChars();
    }

    /**
     * Returns if the guess was valid.
     *
     * @param guess, the users guess
     * @return if guess is a valid guess, single char from english alphabet
     */
    private boolean isValid(String guess) {
        return guess.matches("[a-zA-Z]");
    }
}
