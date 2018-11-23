package fall2018.csc2017.games.Hangman;

import java.io.Serializable;
import java.util.Random;

import fall2018.csc2017.games.Game;

/**
 * Manages a Hangman object.
 */
class HangmanManager implements Game, Serializable {
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
     * @param difficulty the difficulty
     */
    HangmanManager(String difficulty) {
        hangman = new Hangman(getNewWord());
        this.difficulty = difficulty;
        setNumUndos(3);
    }

    @Override
    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
        hangman = new Hangman(getNewWord());
    }

    @Override
    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public String getGameId() {
        return "hangman";
    }

    @Override
    public boolean highTopScore() {
        return false;
    }

    public void setNumUndos(int numUndos) {
        this.numUndos = numUndos;
    }

    public int getNumUndos() {
        return numUndos;
    }

    @Override
    public boolean undo() {
        return false;
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
     * Returns the hangman for this manager
     *
     * @return the hangman for this game
     */
    Hangman getHangman() {
        return hangman;
    }

    //Todo: Find a way to get resources without making this class an activity class?
    /**
     * Generates a new word based on difficulty.
     *
     * @return a word for the user to guess
     */
    private String getNewWord() {
        Random rand = new Random();
        String[] words;

//        switch (difficulty) {
//            case "easy":
//                words = getResources().getStringArray(R.array.easyWords);
//
//                break;
//            case "hard":
//                words = getResources().getStringArray(R.array.hardWords);
//                break;
//            default:
//                words = getResources().getStringArray(R.array.mediumWords);
//                break;
//        }
//        return words[rand.nextInt(words.length)];
        return "test";

    }

    public boolean redo() {
        return false;
    }

    /**
     * Returns if the puzzle was solved.
     *
     * @return if the game is solved
     */
    public boolean puzzleSolved() {
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
