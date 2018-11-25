package fall2018.csc2017.games.Ttt;

import android.widget.Button;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

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
    public boolean p1Turn = true;

    /**
     * Creates a new manager for a specific word.
     */

    // points
    public int p1Points = 0;
    public int p2Points = 0;

    //win message
    public String winMessage;

    //game Tracker
    public Button[][] buttons = new Button[3][3];

    //round count
    public int roundCount = 0;


    //game mode
    private int mode;


    TttManager(int mode) {
        this.mode = mode;
    }

    public boolean getTurn() {
        return p1Turn;
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
        p1Turn = !p1Turn;
        return true;
    }

    public Button easyMode() {
        ArrayList<Button> possibilities = new ArrayList<>();
        Random rand = new Random();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (buttons[i][j].getText().toString().equals("")) {
                    possibilities.add(buttons[i][j]);
                }
            }
        }

        return possibilities.get(rand.nextInt(possibilities.size() - 1));
    }

    public boolean checkForWin() {
        String[][] field = new String[3][3];

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                field[i][j] = buttons[i][j].getText().toString();
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[i][0].equals(field[i][1])
                    && field[i][0].equals(field[i][2])
                    && !field[i][0].equals("")) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (field[0][i].equals(field[1][i])
                    && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")) {
                return true;
            }
        }

        if (field[0][0].equals(field[1][1])
                && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")) {
            return true;
        }

        if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }


    public void resetBoard() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                buttons[i][j].setText("");
            }
        }
        log = new ArrayList<>();
        roundCount = 0;
        p1Turn = true;
    }



}
