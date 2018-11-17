package fall2018.csc2017.game.SlidingTiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.game.FixedStack;
import fall2018.csc2017.game.Game;
import fall2018.csc2017.game.SlidingTiles.Board;
import fall2018.csc2017.game.SlidingTiles.Tile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
public class BoardManager implements Serializable, Game {

    /**
     * Set score for the game.
     */
    private int score = 0;
    /**
     * The board being managed.
     */
    private Board board;
    /**
     * The number of possible undos/redos
     */
    public int numUndos = 3;
    /**
     * The stacks of moves to be undone
     */
    public FixedStack<Integer> undoMoves;
    /**
     * The stacks of moves to be redone
     */
    private FixedStack<Integer> redoMoves;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    public BoardManager(Board board) {
        this.board = board;
        setNumUndos(3);
    }

    /**
     * Manage a new shuffled board.
     *
     * @param dimension the dimension of the desired board
     */
    public BoardManager(int dimension) {
        board = new Board(generateTiles(dimension * dimension));
        setNumUndos(3);
    }

    /**
     * Generates a shuffled list of tiles
     * Creates the last tile blank
     *
     * @param numTiles the number of tiles
     * @return a shuffled list of Tile
     */
    List<Tile> generateTiles(int numTiles) {
        List<Tile> tiles = new ArrayList<>();

        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (tileNum == numTiles - 1) {
                tiles.add(new Tile(-1, numTiles));
            } else {
                tiles.add(new Tile(tileNum));
            }
        }

        Collections.shuffle(tiles);
        return tiles;
    }

    /**
     * Return the current board.
     */
    public Board getBoard() {
        return board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    public boolean puzzleSolved() {
        int i = 1;
        for (Tile t : board) {
            if (t.getId() != i) {
                return false;
            }
            i++;
        }
        return true;
    }

    /**
     * Return's the score for this BoardManager
     *
     * @return the score of this BoardManager
     */
    public int getScore() {
        return this.score;
    }

    @Override
    public int getDifficulty() {
        final int DEFAULT_DIMENSION = 3;
        int size = getBoard().getDimension();
        return size - DEFAULT_DIMENSION;
    }

    @Override
    public String getGameId() {
        return "slidingtiles";
    }

    @Override
    public boolean highTopScore() {
        return false;
    }


    @Override
    public void setNumUndos(int undos) {
        this.numUndos = undos;
        undoMoves = new FixedStack<>(this.numUndos);
        redoMoves = new FixedStack<>(this.numUndos);
    }

    @Override
    public boolean undo() {
        if (undoMoves.size() > 0) {
            int lastMove = undoMoves.pop();
            storeMove(lastMove, redoMoves);
            touchMove(lastMove);
            score -= 2;
            return true;
        }

        return false;
    }

    @Override
    public boolean redo() {
        if (redoMoves.size() > 0) {
            int lastUndo = redoMoves.pop();
            storeMove(lastUndo, undoMoves);
            touchMove(lastUndo);
            return true;
        }
        return false;
    }

    /**
     * Stores the last move made into specified FixedStack
     */
    public void storeMove(int position, FixedStack<Integer> fs) {
        //calculates the position of where the last tile that moved went
        //adds that position into the stack
        int row = position / board.getDimension();
        int col = position % board.getDimension();
        List<Integer> newLocation = findBlankNeighbour(row, col, board.numTiles());
        fs.push((newLocation.get(0) * board.getDimension()) + newLocation.get(1));
    }


    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    public boolean isValidTap(int position) {

        int row = position / board.getDimension();
        int col = position % board.getDimension();
        int blankId = board.numTiles();
        // Are any of the 4 the blank tile?
        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getDimension() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getDimension() - 1 ? null : board.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Process a touch at position in the board, swapping tiles as appropriate.
     *
     * @param position the position
     */
    public void touchMove(int position) {

        //cuts score each time plays
        this.score += 1;

        int row = position / board.getDimension();
        int col = position % board.getDimension();
        int blankId = board.numTiles();

        if (isValidTap(position)) {
            ArrayList<Integer> blankRowCol = board.getRowCol(blankId);
            board.swapTiles(row, col, blankRowCol.get(0), blankRowCol.get(1));
        }
    }

    /**
     * Returns (row, col) of a Tile that has the blankId as its neighbour.
     *
     * @param row     the row of the tile
     * @param col     the position
     * @param blankId the position
     * @return returns (row, col)
     */
    private List<Integer> findBlankNeighbour(int row, int col, int blankId) {

        List<Integer> result = new ArrayList<>();

        Tile above = row == 0 ? null : board.getTile(row - 1, col);
        Tile below = row == board.getDimension() - 1 ? null : board.getTile(row + 1, col);
        Tile left = col == 0 ? null : board.getTile(row, col - 1);
        Tile right = col == board.getDimension() - 1 ? null : board.getTile(row, col + 1);

        if (above != null && above.getId() == blankId) {
            result.add(row - 1);
            result.add(col);
        }
        if (below != null && below.getId() == blankId) {
            result.add(row + 1);
            result.add(col);
        }
        if (left != null && left.getId() == blankId) {
            result.add(row);
            result.add(col - 1);
        }
        if (right != null && right.getId() == blankId) {
            result.add(row);
            result.add(col + 1);
        }
        return result;
    }


}
