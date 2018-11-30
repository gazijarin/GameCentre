package fall2018.csc2017.games.SlidingTiles;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.games.Game;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class BoardManager implements Serializable, Game {

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
    FixedStack<Integer> undoMoves;
    /**
     * The stacks of moves to be redone
     */
    private FixedStack<Integer> redoMoves;

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    BoardManager(Board board) {
        this.board = board;
        setNumUndos(3);
    }

    /**
     * Manage a new shuffled board.
     *
     * @param dimension the dimension of the desired board
     */
    BoardManager(int dimension) {
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
                tiles.add(new Tile("blank", numTiles));
            } else {
                tiles.add(new Tile(tileNum));
            }
        }

        do {
            Collections.shuffle(tiles);
        } while (!isSolvable(tiles));

        return tiles;
    }

    /**
     * Return's whether the input tile configuration describes a solvable board
     *
     * @param tiles the list of tiles in the board
     * @return whether the inputted tile configuration describes a solvable board
     */
    public boolean isSolvable(List<Tile> tiles) {
        int dimension = (int) Math.sqrt(tiles.size());

        int inversions = 0;
        int blankpos = 0;
        for (int i = 0; i < tiles.size(); i++) {
            if (tiles.get(i).getId() != tiles.size()) {   //if this tile isnt the blank tile
                for (int j = i; j < tiles.size(); j++) {  //some nice n^2 complexity
                    if (tiles.get(i).getId() > tiles.get(j).getId()
                            && tiles.get(j).getId() != tiles.size()) {
                        inversions++;
                    }
                }
            } else {
                blankpos = i;
            }
        }

        boolean evenRow = ((dimension - ((int) (blankpos / dimension))) % 2) == 0;
        boolean evenInversions = inversions % 2 == 0;
        boolean evenDimension = (dimension % 2 == 0);

        return ((!evenDimension && evenInversions) ||
                (evenDimension && (!evenRow == evenInversions)));

    }

    /**
     * Return the current board.
     */
    Board getBoard() {
        return board;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
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
    public void setDifficulty(String difficulty) {
        int dimension;

        switch (difficulty) {
            case "easy":
                dimension = 3;
                break;
            case "medium":
                dimension = 4;
                break;
            default:
                dimension = 5;
                break;
        }

        board = new Board(generateTiles(dimension * dimension));
    }

    @Override
    public String getDifficulty() {
        int difficulty = getBoard().getDimension();
        if (difficulty == 3) {
            return "easy";
        } else if (difficulty == 4) {
            return "medium";
        } else
            return "hard";
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
    void storeMove(int position, FixedStack<Integer> fs) {
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
    boolean isValidTap(int position) {

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
    void touchMove(int position) {

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
