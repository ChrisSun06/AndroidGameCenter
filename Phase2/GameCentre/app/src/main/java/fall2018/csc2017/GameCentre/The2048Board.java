package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.Observable;

import java.io.Serializable;
import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * The board for game 2048.
 */
public class The2048Board extends AbstractBoard implements Serializable, Iterable<TofeTile> {

    /**
     * The number of rows.
     */
    static final int numCols = 4;

    /**
     * The number of rows.
     */
    static final int numRows = 4;

    /**
     * The number of tiles.
     */
    static final int numTiles = 16;

    /**
     * The tiles on the board in row-major order.
     */
    private TofeTile[][] tiles;

    /**
     * The max times player can undo(default is 3, player could set any positive integer).
     */
    private int maxUndoTime = 3;


    /**
     * A new board of tiles in row-major order.
     * Precondition: len(tiles) == NUM_ROWS * NUM_COLS
     *
     * @param tiles the tiles for the board
     */
    The2048Board(List<TofeTile> tiles) {
        Iterator<TofeTile> iter = tiles.iterator();
        this.tiles = new TofeTile[numRows][numCols];

        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    /**
     * Return the tile at (row, col)
     *
     * @param row the tile row
     * @param col the tile column
     * @return the tile at (row, col)
     */
    private TofeTile getTile(int row, int col) {
        return tiles[row][col];
    }

    private void setTile(int row, int col, TofeTile tile){
        tiles[row][col] = tile;
    }
    /**
     * Return the column corresponding to colNum, can be inverted if specified.
     * @param colNum the number of the needed column.
     * @param inverted whether we want the column to be inverted.
     * @return the column corresponding to colNum, can be inverted if specified.
     */
    private TofeTile[] getCol(int colNum, boolean inverted){
        TofeTile[] result = new TofeTile[4];
        if(! inverted){
            for(int i = 0; i < 4; i++){
                result[i] = getTile(i, colNum);
            }
        }else{
            for(int i = 0; i < 4; i++){
                result[i] = getTile(3-i, colNum);
            }
        }
        return result;
    }

    /**
     * Return the row corresponding to rowNum, can be inverted if specified.
     * @param rowNum the number of the needed row.
     * @param inverted whether we want the row to be inverted.
     * @return the row corresponding to rowNum, can be inverted if specified.
     */
    private TofeTile[] getRow(int rowNum, boolean inverted){
        TofeTile[] result = new TofeTile[4];
        if(! inverted){
            for(int i = 0; i < 4; i++){
                result[i] = getTile(rowNum, i);
            }
        }else{
            for(int i = 0; i < 4; i++){
                result[i] = getTile(rowNum, 3-i);
            }
        }
        return result;
    }

    /**
     * Return the row or column based on String rowOrColumn, the number of row or column
     * to be returned is determined by int rowORColumnNum, can be inverted if specified.
     * @param rowOrColumn whether we want row or column.
     * @param rowORColumnNum the number of row/column we need.
     * @param inverted whether we want the row/column to be inverted.
     * @return the row/column corresponding to rowOrColumnNum, can be inverted if specified.
     */
    private TofeTile[] getRowOrColumn(String rowOrColumn, int rowORColumnNum, boolean inverted){
        if(rowOrColumn.equals("row")){
            return getRow(rowORColumnNum, inverted);
        }else if(rowOrColumn.equals("column")){
            return getCol(rowORColumnNum, inverted);
        }return null;
    }

    /**
     * Return a Array with length 16 that contains all tiles from the board.
     * @return the array with length 16 that contains all tiles from the board.
     */
    TofeTile[] getAllTiles(){
        TofeTile[] result = new TofeTile[16];
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                result[row * 4 + col] = getTile(row, col);
            }
        }
        return result;
    }

    /**
     * Setting the entire board with inputTiles.
     * Precondition: inputTiles has size of 16.
     * @param inputTiles tiles to be put in the board
     */
    void setAllTiles(TofeTile[] inputTiles){
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.setTile(row, col, inputTiles[row * 4 * 3]);
            }
        }
    }

     /**
     * Return a list of TofeTile representing the result after merging.
     * @param rOrC String representing whether we are trying to merge the board by
     *             row or column
     * @param inverted representing the direction of the merging movement.
     * @return a list of TofeTile representing the result after merging. the position
     * is determined by row * 4 + col
     */
     TofeTile[] merge(String rOrC, boolean inverted) {
        TofeTile[] resultingTiles = new TofeTile[16];
        for (int i = 0; i < 4; i++) {
            TofeTile[] mergedTiles = new Merge2048(this.getRowOrColumn(rOrC, i, inverted)).merge();
            for(int j = 0; j < 4; j++)
                resultingTiles[mergedTiles[j].getId()] = mergedTiles[j];
        }
        return resultingTiles;
    }

    private Integer[] getBlankPosition(){
        TofeTile[] allTiles = this.getAllTiles();
        ArrayList<Integer> result = new ArrayList<>();
        for(int i = 0; i < allTiles.length; i++) {
            if (allTiles[i].getValue() == 0) {
                result.add(i);
            }
        }
            return (Integer[])(result.toArray());
    }

    void generateNewTiles(){
        Integer[] blankPos = this.getBlankPosition();
        if (blankPos.length != 0){
            int randomPos = blankPos[(int) (Math.random() * blankPos.length)];
            TofeTile randomTile = new TofeTile(((int) (Math.random() * 1) + 1) * 2, randomPos);
            this.setTile(randomPos/The2048Board.numRows,randomPos%The2048Board.numCols,randomTile);
        }
    }

    @Override
    public boolean equals(Object other){
        if(!(other instanceof The2048Board))
            return false;
        for(int i = 0; i < numRows; i++){
            for(int j = 0; j < numCols; j++){
                if(this.getTile(i,j).getDrawableId() !=
                        ((The2048Board) other).getTile(i,j).getDrawableId())
                    return false;
            }
        }
        return true;
    }

    /**
     * Initiate a board iterator to keep track of the order of the tiles on the board.
     *
     * @return the new board iterator
     */
    @Override
    @NonNull
    public Iterator<TofeTile> iterator() {
        return new BoardIterator();
    }


    /**
     * The board iterator which checks the tiles in corresponding board position.
     */
    private class BoardIterator implements Iterator<TofeTile> {

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
            return (nextIndex < numTiles);
        }

        /**
         * Return the next tile.
         *
         * @return the next tile.
         */
        @Override
        public TofeTile next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            TofeTile nextTile = getTile(nextIndex / numRows, nextIndex % numCols);
            nextIndex++;
            return nextTile;
        }
    }

    public int getScore(){
        int result = 0;
        Iterator<TofeTile> iter = this.iterator();
        while(iter.hasNext()){
            result += iter.next().getValue();
        }
        return result;
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
