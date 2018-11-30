package fall2018.csc2017.games.SlidingTiles;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.games.R;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class BoardAndTileTest {

    /**
     * The board manager for testing.
     */
    private BoardManager boardManager;

    /**
     * Make a set of tiles that are in order.
     *
     * @return a set of tiles that are in order
     */
    private List<Tile> makeTiles(int numTiles) {
        List<Tile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new Tile(tileNum + 1, tileNum));
        }

        return tiles;
    }

    /**
     * Make a solved Board.
     */
    @Before
    public void setUpCorrect() {
        int numRows = 4;
        int numCols = 4;
        List<Tile> tiles = makeTiles(numRows * numCols);
        Board board = new Board(tiles);
        boardManager = new BoardManager(board);
    }

    /**
     * Shuffle a few tiles.
     */
    private void swapFirstTwoTiles() {
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
    }

    /**
     * Test whether swapping two tiles makes a solved board unsolved.
     */
    @Test
    public void testIsSolved() {
        setUpCorrect();
        assertTrue(boardManager.puzzleSolved());
        swapFirstTwoTiles();
        assertFalse(boardManager.puzzleSolved());
    }

    /**
     * Test whether swapping the first two tiles works.
     */
    @Test
    public void testSwapFirstTwo() {
        setUpCorrect();
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(2, boardManager.getBoard().getTile(0, 1).getId());
        boardManager.getBoard().swapTiles(0, 0, 0, 1);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getId());
        assertEquals(1, boardManager.getBoard().getTile(0, 1).getId());
    }

    /**
     * Test whether swapping the last two tiles works.
     */
    @Test
    public void testSwapLastTwo() {
        setUpCorrect();
        assertEquals(15, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(16, boardManager.getBoard().getTile(3, 3).getId());
        boardManager.getBoard().swapTiles(3, 3, 3, 2);
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());
    }

    /**
     * Test whether isValidHelp works.
     */
    @Test
    public void testIsValidTap() {
        setUpCorrect();
        assertTrue(boardManager.isValidTap(11));
        assertTrue(boardManager.isValidTap(14));
        assertFalse(boardManager.isValidTap(10));
    }

    /**
     * Tests whether isSolvable works
     */
    @Test
    public void testIsSolvable() {
        setUpCorrect();
        List<Tile> tiles = makeTiles(16);
        List<Tile> tilesSolvable2 = makeTiles(16);
        List<Tile> tilesSolvable = makeTiles(16);
        List<Tile> tilesUnsolvable2 = makeTiles(9);
        List<Tile> tilesUnsolvable = makeTiles(16);

        tilesSolvable.add(tilesSolvable.remove(11));
        tilesSolvable.add(11, tilesSolvable.remove(14)); //swap the 12 tile and blank

        tilesUnsolvable2.add(tilesUnsolvable2.remove(4));
        tilesUnsolvable2.add(4, tilesUnsolvable2.remove(7));

        tilesUnsolvable.add(tilesUnsolvable.remove(7));
        tilesUnsolvable.add(7, tilesUnsolvable.remove(14)); //swap the 7 and blank

        tilesSolvable2.add(tilesSolvable2.remove(12)); //swap the blank and tile 13

        assertTrue(boardManager.isSolvable(tiles));
        assertFalse(boardManager.isSolvable(tilesUnsolvable2));
        assertTrue(boardManager.isSolvable(tilesSolvable2));
        assertFalse(boardManager.isSolvable(tilesUnsolvable));
        assertTrue(boardManager.isSolvable(tilesSolvable));
    }

    /**
     * Tests that boardManager given tiles creates a proper board object
     */
    @Test
    public void testGetBoardGivenTiles() {
        assertNotNull(boardManager.getBoard());
    }

    /**
     * Tests that a board is created given dimensions instead
     */
    @Test
    public void testGetBoardGivenDimension() {
        BoardManager manager = new BoardManager(4);
        assertNotNull(manager.getBoard());
    }

    /**
     * Tests that get score returns a proper score
     */
    @Test
    public void testGetScore() {
        assertNotNull(boardManager.getScore());
    }

    /**
     * Tests that blank tiles are made properly
     */
    @Test
    public void testBlankTile() {
        Tile tile = new Tile("test", 15);
        assertEquals(15, tile.getId());
        assertEquals(tile.getBackground(), R.drawable.tile_blank);
    }

    /**
     * Tests equality method for similar tiles
     */
    @Test
    public void testTileEqual() {
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(1);
        assertEquals(0, tile1.compareTo(tile2));

    }

    /**
     * Tests equality for non similar tiles
     */
    @Test
    public void testTileNotEqual() {
        Tile tile1 = new Tile(1);
        Tile tile2 = new Tile(2);
        assertEquals(1, tile1.compareTo(tile2));
    }

    /**
     * Test that moves are stored properly in the FixedStack(s)
     */
    @Test
    public void testStoreMove() {
        boardManager.storeMove(14, boardManager.undoMoves);
        assertEquals(15, (int) boardManager.undoMoves.pop());
    }

    /**
     * Testing undo of a single move
     */
    @Test
    public void testUndoOneMove() {
        boardManager.storeMove(14, boardManager.undoMoves);
        boardManager.touchMove(14);
        boardManager.undo();
        assertTrue(boardManager.puzzleSolved());

    }

    /**
     * Test that undo works for empty stack
     */
    @Test
    public void testUndoNoMoves() {
        assertFalse(boardManager.undo());
    }

    /**
     * Test that undo only works until max specified amount
     */
    @Test
    public void testMaxAmountOfUndo() {
        boardManager.storeMove(14, boardManager.undoMoves);
        boardManager.touchMove(14);
        boardManager.storeMove(13, boardManager.undoMoves);
        boardManager.touchMove(13);
        boardManager.storeMove(12, boardManager.undoMoves);
        boardManager.touchMove(12);
        boardManager.storeMove(8, boardManager.undoMoves);
        boardManager.touchMove(8);
        boardManager.undo();
        boardManager.undo();
        boardManager.undo();
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());

    }


    /**
     * Tests redo of a single move after a single uno
     */
    @Test
    public void testRedoOneMove() {
        boardManager.storeMove(14, boardManager.undoMoves);
        boardManager.touchMove(14);
        boardManager.undo();
        boardManager.redo();
        assertEquals(16, boardManager.getBoard().getTile(3, 2).getId());
        assertEquals(15, boardManager.getBoard().getTile(3, 3).getId());

    }

    /**
     * Test that undo works for empty stack
     */
    @Test
    public void testRedoNoMoves() {
        assertFalse(boardManager.redo());
    }

    /**
     * Next 4 tests check each seperate if/else case for setting difficulty
     */
    @Test
    public void testSetDifficultyEasy() {
        boardManager.setDifficulty("easy");
        assertEquals("easy", boardManager.getDifficulty());
        assertEquals(3, boardManager.getBoard().getDimension());
    }

    /**
     * Tests the setter for difficulty "medium".
     */
    @Test
    public void testSetDifficultyMedium() {
        boardManager.setDifficulty("medium");
        assertEquals("medium", boardManager.getDifficulty());
        assertEquals(4, boardManager.getBoard().getDimension());
    }

    /**
     * Tests the setter for difficulty "hard".
     */
    @Test
    public void testSetDifficultyHard() {
        boardManager.setDifficulty("hard");
        assertEquals("hard", boardManager.getDifficulty());
        assertEquals(5, boardManager.getBoard().getDimension());
    }

    /**
     * Tests the default difficulty.
     */
    @Test
    public void testDefaultDifficulty() {
        assertEquals("medium", boardManager.getDifficulty());
        assertEquals(4, boardManager.getBoard().getDimension());
    }

    /**
     * Tests the getter for game name
     */
    @Test
    public void testGameId() {
        assertEquals("slidingtiles", boardManager.getGameId());
    }

    /**
     * Tests the getter for one of our scoreboard methods
     */
    @Test
    public void testHighTopScore() {
        assertFalse(boardManager.highTopScore());
    }

    /**
     * Tests the toString method for Board.
     */
    @Test
    public void testToString() {
        assertTrue(boardManager.getBoard().toString().contains("Board{"));
    }
}

