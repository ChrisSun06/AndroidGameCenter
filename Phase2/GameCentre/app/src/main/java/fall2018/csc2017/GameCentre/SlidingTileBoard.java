package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.GameCentre.tiles.SlidingTile;

/**
 * The sliding tiles board.
 */
public class SlidingTileBoard extends AbstractBoard implements Iterable<SlidingTile> {

    /**
     * The number of rows.
     */
    private int numCols;

    /**
     * The number of rows.
     */
    private int numRows;

    /**
     * The tiles on the board in row-major order.
     */
    private SlidingTile[][] tiles;

    /**
     * The stack which keeps track of the clickable undo positions.
     */
    Stack<Integer> historyStack = new Stack<>();

    /**
     * The max times player can undo(default is 3, player could set any positive integer).
     */
    private int maxUndoTime = 3;

    /**
     * How many moves players make
     */
    private int numOfMoves = 0;

    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    SlidingTileBoard(List<SlidingTile> tiles, int gridSize) {
        Iterator<SlidingTile> iter = tiles.iterator();

        this.numRows = gridSize;
        this.numCols = gridSize;
        this.tiles = new SlidingTile[this.numRows][this.numCols];

        for (int row = 0; row != this.numRows; row++) {
            for (int col = 0; col != this.numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Function let numOfMoves increase 1.
     */
    void increaseNumOfMoves() {
        numOfMoves++;
    }

    /**
     * get number of moves.
     *
     * @return the number of moves
     */
    int getNumOfMoves() {
        return numOfMoves;
    }

    /**
     * get number of rows.
     *
     * @return the number of rows
     */
    public int getNumRows() {
        return numRows;
    }

    /**
     * get number of columns.
     *
     * @return the number of columns
     */
    public int getNumCols() {
        return numCols;
    }

    /**
     * Return the number of tiles on the board.
     *
     * @return the number of tiles on the board
     */
    public int numTiles() {
        return this.numRows * this.numCols;
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    SlidingTile getTile(int row, int col) {
        return tiles[row][col];
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
        SlidingTile temp = getTile(row1, col1);
        this.tiles[row1][col1] = this.tiles[row2][col2];
        this.tiles[row2][col2] = temp;
        setChanged();
        notifyObservers();
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     *
     * @return the new board iterator
     */
    @Override
    @NonNull
    public Iterator<SlidingTile> iterator() {
        return new BoardIterator();
    }

    /**
     * The board iterator which checks the tiles in corresponding board position.
     */
    private class BoardIterator implements Iterator<SlidingTile> {

        /**
         * The index of the next item in the class list.
         */
        private int nextIndex = 0;

        /**
         * Check whether the iterator has the next tile.
         *
         * @return whether the iterator has the next tile.
         */
        @Override
        public boolean hasNext() {
            return (nextIndex < numTiles() - 1);
        }

        /**
         * Return the next tile.
         *
         * @return the next tile.
         */
        @Override
        public SlidingTile next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            SlidingTile nextTile = getTile(nextIndex / numRows, nextIndex % numCols);
            nextIndex++;
            return nextTile;
        }
    }

    /**
     * the string representation.
     *
     * @return the tile number to string.
     */
    @Override
    public String toString() {
        return "Board{" +
                "tiles=" + Arrays.toString(tiles) +
                '}';
    }

    /**
     * get the max undo times.
     *
     * @return the number of max undo times
     */
    int getMaxUndoTime() {
        return maxUndoTime;
    }

    /**
     * user could set their own undo time instead of 3.
     */
    void setMaxUndoTime(int num) {
        maxUndoTime = num;
    }

}
