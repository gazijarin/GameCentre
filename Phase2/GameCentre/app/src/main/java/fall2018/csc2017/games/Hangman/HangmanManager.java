package fall2018.csc2017.games.Hangman;

import java.io.Serializable;
import java.util.Random;

import fall2018.csc2017.games.Game;

/**
 * Manages a Hangman object.
 */
class HangmanManager implements Game, Serializable {
    /**
     * The Hangman being managed.
     */
    private Hangman hangman;
    /**
     * The number of undos; default set to 3.
     */
    private int numUndos = 3;
    /**
     * The difficulty of the word.
     */
    private String difficulty = "medium";
    /**
     * Creates a new manager for a specific word.
     */
    HangmanManager(String difficulty, String word) {
        hangman = new Hangman(word);
        this.difficulty = difficulty;
        setNumUndos(3);
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
    public String getDifficulty() {
        return difficulty;
    }

    @Override
    public void setDifficulty(String difficulty) {
        if(difficulty.equals("easy") || difficulty.equals("medium") || difficulty.equals("hard"))
           this.difficulty = difficulty;

        hangman = new Hangman(getNewWord());
    }

    @Override
    public String getGameId() {
        return "hangman";
    }

    @Override
    public boolean highTopScore() {
        return true;
    }

    /**
     * Returns the number of undos
     *
     * @return the number of undos
     */
    public int getNumUndos() {
        return numUndos;
    }

    /**
     * Sets the number of undos to a specified one.
     *
     * @param numUndos number of undos
     */
    public void setNumUndos(int numUndos) {
        this.numUndos = numUndos;
    }

    /**
     * Returns the hangman for this manager
     * @return the hangman for this game
     */
    Hangman getHangman() {
        return hangman;
    }

    /**
     * Generates a new word based on difficulty.
     * @return a word for the user to guess
     */
    String getNewWord() {
        Random rand = new Random();
        String[] chosenWords;
        switch (this.difficulty) {
            case "easy":
                chosenWords = Dictionary.easy;
                break;
            case "hard":
                chosenWords = Dictionary.hard;
                break;
            default:
                chosenWords = Dictionary.medium;
                break;
        }

        String newWord = chosenWords[rand.nextInt(chosenWords.length)];

        while (hangman != null && newWord.equals(hangman.currWord)) {
            newWord = chosenWords[rand.nextInt(chosenWords.length)];
        }

        return newWord;
    }

    /**
     * Returns if the puzzle was solved.
     *
     * @return if the game is solved
     */
    boolean puzzleSolved() {
        return (new String(hangman.getRevealedWord())).equals(hangman.currWord);
    }

    /**
     * Returns if the game has been lost.
     *
     * @return if the game has been lost.
     */
    boolean puzzleLost() {
        return hangman.getCurrentGuesses() == hangman.TOTAL_GUESSES;
    }

    /**
     * Returns if the guess was valid.
     *
     * @param guess, the users guess
     * @return if guess is a valid guess, single char from english alphabet
     */
    boolean isValid(String guess) {
        return guess.matches("[a-zA-Z]");
    }

    /**
     * Returns the current score for this hangman game.
     *
     * @return the current score for this hangman game.
     */
    int getScore() {
        int modifier;
        switch (getDifficulty()) {
            case "easy":
                modifier = 3;
                break;
            case "medium":
                modifier = 6;
                break;
            default:
                modifier = 9;
                break;
        }
        return modifier * (6 - hangman.getCurrentGuesses());
    }
}
