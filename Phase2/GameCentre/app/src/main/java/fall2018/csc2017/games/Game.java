package fall2018.csc2017.games;

/**
 * an interface for a Game
 */
public interface Game {

    /**
     * Set the game difficulty level, "easy", "medium", or "hard"
     */
    void setDifficulty(String difficulty);

    /**
     * Gets the game difficulty level, higher number means higher difficulty,
     * starts at 0
     *
     * @return the game's difficulty
     */
    String getDifficulty();

    /**
     * Returns this game's unique game ID
     *
     * @return the game's ID
     */
    String getGameId();

    /**
     * Returns whether a high score is a good score or bad in this game
     *
     * @return whether a high score is a good score or bad in this game
     */
    boolean highTopScore();

    /**
     * Sets how many undos are possible in this game
     */
    void setNumUndos(int undos);



}
