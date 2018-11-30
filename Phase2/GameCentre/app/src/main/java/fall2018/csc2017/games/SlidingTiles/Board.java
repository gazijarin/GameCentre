package fall2018.csc2017.games.SlidingTiles;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Observable;

/**
 * The sliding tiles board.
 */
public class Board extends Observable implements Serializable, Iterable<Tile> {

    /**
     * The dimension of this board.
     */
    private int dimension;

    /**
     * The tiles on the board in row-major order.
     */
    private Tile[][] tiles;

    /**
     * A new board made with tiles
     * Precondition: len(tiles) is a perfect square
     *
     * @param tiles the list of tiles in this board
     */
    Board(List<Tile> tiles) {
        this.dimension = (int) Math.sqrt(tiles.size());

        Iterator<Tile> iter = tiles.iterator();
        this.tiles = new Tile[dimension][dimension];

        for (int row = 0; row != dimension; row++) {
            for (int col = 0; col != dimension; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }


    /**
     * returns the dimension
     *
     * @return the dimension
     */
    public int getDimension() {
        return dimension;
    }

    @NonNull
    @Override
    public Iterator<Tile> iterator() {
        return new TileIterator();
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    int numTiles() {
        return dimension * dimension;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    Tile getTile(int row, int col) {
        return tiles[row][col];
    }

    /**
     * Return the corresponding [row, column] of Tile given its id.
     *
     * @param id the tile's id
     * @return the list containing row and column
     */
    ArrayList<Integer> getRowCol(int id) {

        ArrayList<Integer> tileRowCol = new ArrayList<>();

        for (int row = 0; row != dimension; ) {
            for (int col = 0; col != dimension; ) {
                if (this.tiles[row][col].getId() == id) {
                    tileRowCol.add(row);
                    tileRowCol.add(col);
                }
                col++;
            }
            row++;
        }
        return tileRowCol;
    }

    /**
     * Swap the tiles at (row1, col1) and (row2, col2)
     *
     * @param row1 the first tile row
     * @param col1 the first tile col
     * @param row2 the second tile row
     * @param col2 the second tile col
     */
    void swapTiles(int row1, int col1, int row2, int col2) {
        Tile firstTile = this.tiles[row1][col1];

        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = firstTile;

        setChanged();
        notifyObservers();
    }


    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }


    /**
     * The Tile iterator.
     */
    public class TileIterator implements Iterator<Tile> {
        /**
         * The index of the next row of the next tile in the tiles list.
         */
        int nextRowIndex = 0;
        /**
         * The index of the next column of the next tile in the tiles list.
         */
        int nextColIndex = 0;

        @Override
        public boolean hasNext() {
            return nextRowIndex != dimension;
        }

        @Override
        public Tile next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more rows and/or columns, sorry.");
            }

            Tile result = tiles[nextRowIndex][nextColIndex];
            nextColIndex++;

            if (nextColIndex >= dimension) {
                nextColIndex = 0;
                nextRowIndex++;
            }

            return result;
        }
    }
}
