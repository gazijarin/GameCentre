package fall2018.csc2017.games.Ttt;

import android.widget.Button;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

import fall2018.csc2017.games.Game;

public class TttManager implements Game, Serializable {
    /**
     * The number of undos; default set to 3.
     */
    private int numUndos = 3;

    /**
     * The log of all the moves made in a game
     */
    public ArrayList<Button> log = new ArrayList<>();

    /**
     * boolean to keep track of p1's turn
     */
    public boolean p1Turn = true;


    /**
     * Tracking each player's points
     */
    public int p1Points = 0;
    public int p2Points = 0;

    /**
     * The message for when the game ends
     */
    public String winMessage;

    /**
     * Tacks the contents of the buttons
     */
    public Button[][] buttons = new Button[3][3];

    /**
     * Tracks the number of rounds
     */
    public int roundCount = 0;


    /**
     * The game mode
     */
    public String mode;


    TttManager(String mode) {
        this.mode = mode;
    }

    @Override
    public void setDifficulty(String difficulty) {
        this.mode = difficulty;
    }

    /**
     * Gets the game difficulty level, higher number means higher difficulty,
     * starts at 0
     *
     * @return the game's difficulty
     */
    @Override
    public String getDifficulty() {
        return mode;
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
        int time = 2;
        if (mode.equals("single")) {
            time = 1;
        }
        for (int i = time; i < 3; i++) {
            if (log.size() > 0) {
                Button x = this.log.get(log.size() - 1);
                x.setText("");
                this.log.remove(log.size() - 1);
                p1Turn = !p1Turn;
                roundCount--;
            }
        }
        return true;
    }

    /**
     * Computer makes a move on the board
     */
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

    /**
     * Checks the board for wins
     * @return whether there is a win
     */
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

        } else if (field[0][2].equals(field[1][1])
                && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")) {
            return true;
        }

        return false;
    }

    /**
     * Reset the board and buttons
     */
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
