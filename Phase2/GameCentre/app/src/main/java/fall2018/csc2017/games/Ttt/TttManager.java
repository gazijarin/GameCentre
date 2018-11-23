package fall2018.csc2017.games.Ttt;

import android.widget.Button;

import java.util.ArrayList;

import fall2018.csc2017.games.Game;

public class TttManager implements Game {
    /**
     * The number of undos; default set to 3.
     */
    private int numUndos = 1;

    /**
     * The log of all the moves in a game
     */
    public ArrayList<Button> log = new ArrayList<>();

    /**
     * boolean to keep track of turn
     */
    private boolean turn;

    /**
     * Creates a new manager for a specific word.
     */
    TttManager() {
        this.turn = true;
        this.log = log;
    }

    public boolean getTurn() {
        return this.turn;
    }

    @Override
    public void setDifficulty(String difficulty) {
        //todo make this do something
    }

    /**
     * Gets the game difficulty level, higher number means higher difficulty,
     * starts at 0
     *
     * @return the game's difficulty
     */
    @Override
    public String getDifficulty() {
        return null;
        //wtf is this
        //doesn;t apply to us
    }

    /**
     * Returns this game's unique game ID
     *
     * @return the game's ID
     */
    @Override
    public String getGameId() {
        return "Ttt";
    }

    /**
     * Returns whether a high score is a good score or bad in this game
     *
     * @return whether a high score is a good score or bad in this game
     */
    @Override
    public boolean highTopScore() {
        return false;
        //wtf is this
        //why is this even needed
    }

    /**
     * Sets how many undos are possible in this game
     *
     * @param undos
     */
    @Override
    public void setNumUndos(int undos) {
        this.numUndos = undos;
    }

    /**
     * Performs an redo
     *
     * @return whether redo was successful
     */
    @Override
    public boolean undo() {
        if (log.size() > 0) {
            Button x = this.log.get(log.size() - 1);
            x.setText("");
            this.log.remove(log.size() - 1);
        }
        this.turn = !this.turn;
        return true;
    }
}
