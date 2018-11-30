package fall2018.csc2017.games.Ttt;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import fall2018.csc2017.games.Game;

/**
 * Manages a Tic Tac Toe game.
 */
public class TttManager implements Game, Serializable {
    /**
     * The number of undos; default set to 1.
     */
    private int numUndos = 1;
    /**
     * boolean to keep track of p1's turn
     */
    boolean p1Turn = true;

    /**
     * Hash-map points system of p1 and p2.
     */
    Map<String, Integer> points = new HashMap<String, Integer>() {
        {
            put("p1", 0);
            put("p2", 0);
        }
    };
    /**
     * The game board where 1 represents player 1 and 2 represents player 2
     */
    int[][] board = new int[3][3];
    /**
     * Tracks the number of rounds
     */
    int roundCount = 0;
    /**
     * The game mode.
     */
    String mode;
    /**
     * The undo tracker.
     */
    private ArrayList<int[]> undoTracker = new ArrayList<>();

    private int initialUndos = 1;


    /**
     * Creates a new manager
     *
     * @param mode the mode for this manager
     */
    TttManager(String mode) {
        this.mode = mode;
    }

    @Override
    public void setDifficulty(String difficulty) {
        this.mode = difficulty;
    }

    @Override
    public String getDifficulty() {
        return mode;
    }

    @Override
    public String getGameId() {
        return "Ttt";
    }

    @Override
    public boolean highTopScore() {
        return true;
    }

    @Override
    public void setNumUndos(int undos) {
        numUndos = undos;
        initialUndos = undos;
    }

    public int getNumUndos() {
        return numUndos;
    }

    public boolean undo() {
        Boolean success = false;
        int time = getUndoTime();
        int j = 0;
        for (int i = time; i < 3; i++) {
            if (roundCount > 0 & undoTracker.size() > 0 & getNumUndos() > 0) {
                int[] coords = undoTracker.get(j);
                board[coords[0]][coords[1]] = 0;
                success = true;
                p1Turn = !p1Turn;
                roundCount--;
                undoTracker.remove(0);
                j++;

            }
        }
        numUndos = numUndos - 1;
        return success;
    }


    /**
     * Computer makes a move on the board
     */
    int[] computerPlay() {

        ArrayList<int[]> possibilities = new ArrayList<>();
        Random rand = new Random();

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    int[] candidate = new int[2];
                    candidate[0] = i;
                    candidate[1] = j;
                    possibilities.add(candidate);
                }
            }
        }
        int[] chosen = possibilities.get(rand.nextInt(possibilities.size() - 1));
        play(chosen[0], chosen[1], 2);
        roundCount--;
        return chosen;
    }

    /**
     * make a move
     * @param row the row of the move
     * @param col the col of the move
     * @param item what item should be there, 0 for nothing, 1 for player 1, 2 for player 2
     */
    void play(int row, int col, int item) {
        board[row][col] = item;
        roundCount++;

        int[] tracker = {row,col};
        undoTracker.add(0,tracker);
    }

    /**
     * Checks the board horizontally for wins
     *
     * @return whether there is a win
     */

    boolean checkDiagonals(){
        if (board[0][0] == (board[1][1])
                && board[0][0] == (board[2][2])
                && board[0][0] != 0) {
            return true;

        } else return board[0][2] == (board[1][1])
                && board[0][2] == (board[2][0])
                && board[0][2] != 0;
    }

    /**
     * Checks the board vertically for wins
     *
     * @return whether there is a win
     */
    boolean checkHorizontals(){
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == (board[1][i])
                    && board[0][i] == (board[2][i])
                    && board[0][i] != 0) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[i][0] == (board[i][1])
                    && board[i][0] == (board[i][2])
                    && board[i][0] != 0) {
                return true;
            }
        }
        return false;
    }


    /**
     * Reset the board and buttons
     */
    void resetBoard() {
        board = new int[3][3];
        roundCount = 0;
        p1Turn = true;
        numUndos = initialUndos;
    }

    /**
     * Returns how many times undo should occur (twice for single-player and once for multi)
     */
    int getUndoTime() {
        int time = 2;
        if (mode.equals("single")) {
            time = 1;
        }
        return time;
    }

    /**
     * gets the score of the game round
     */
    public int getScore() {
        return points.get("p1") - points.get("p2");
    }
}
