package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.GameCentre.abstract_classes.Tile;

public class SudokuBoard extends Observable implements Serializable{
    /**
     * The number of rows.
     */
    private int numRows = 9;
    /**
     * The number of rows.
     */
    private int numCols= 9;

    private SudokuTile[][] tiles = new SudokuTile[numRows][numCols];

    SudokuBoard(List<SudokuTile> tiles) {
        Iterator<SudokuTile> iter = tiles.iterator();
        for (int row = 0; row != numRows; row++) {
            for (int col = 0; col != numCols; col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    int numTiles(){
        return numCols * numRows;
    }

    int getNumRows(){return numRows;}

    int getNumCols(){return numCols;}

    void setTile(int row, int col, int value){
        tiles[row][col].setNumber(value);
    }

    SudokuTile getTile(int row, int col){
        return tiles[row][col];
    }

    public Iterable<SudokuTile> horizontal(){return new Iterable<SudokuTile>() {
        @NonNull
        @Override
        public Iterator<SudokuTile> iterator() {
            return new SudokuIterator();
        }
    };}

    public Iterable<SudokuTile> vertical(){return new Iterable<SudokuTile>() {
        @NonNull
        @Override
        public Iterator<SudokuTile> iterator() {
            return new SudokuVerticalIterator();
        }
    };}

    public Iterable<SudokuTile> sectional(){return new Iterable<SudokuTile>() {
        @NonNull
        @Override
        public Iterator<SudokuTile> iterator() {
            return new SudokuSectionalIterator();
        }
    };}

    private class SudokuIterator implements Iterator<SudokuTile> {

        /**
         * The index of next item in tiles list.
         */
        int nextIndex = 0;

        /**
         * Return if there's a next tile in the array of Tiles.
         *
         * @return whether there's a next tile in the array.
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        /**
         * Return the next tile in the array, and update the index tracker by 1.
         *
         * @return the next tile in the array.
         */
        @Override
        public SudokuTile next() {
            int curRow = nextIndex / numRows;
            int curColumn = nextIndex % numCols;
            SudokuTile tile = tiles[curRow][curColumn];
            nextIndex += 1;
            return tile;
        }
    }

    public int getRows(){return numRows;}

    public int getCols(){return numCols;}

    private class SudokuVerticalIterator implements Iterator<SudokuTile> {

        /**
         * The index of next item in tiles list.
         */
        int nextIndex = 0;

        /**
         * Return if there's a next tile in the array of Tiles.
         *
         * @return whether there's a next tile in the array.
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        /**
         * Return the next tile in the array, and update the index tracker by 1.
         *
         * @return the next tile in the array.
         */
        @Override
        public SudokuTile next() {
            int curRow = nextIndex / numRows;
            int curColumn = nextIndex % numCols;
            SudokuTile tile = tiles[curColumn][curRow];
            nextIndex += 1;
            return tile;
        }
    }

    private class SudokuSectionalIterator implements Iterator<SudokuTile> {

        /**
         * The index of next item in tiles list.
         */
        int nextIndex = 0;

        /**
         * Return if there's a next tile in the array of Tiles.
         *
         * @return whether there's a next tile in the array.
         */
        @Override
        public boolean hasNext() {
            return nextIndex != numTiles();
        }

        /**
         * Return the next tile in the array, and update the index tracker by 1.
         *
         * @return the next tile in the array.
         */
        @Override
        public SudokuTile next() {
            int curRow = (nextIndex % 18) / 3;
            int curColumn = (nextIndex % 3) + (nextIndex / 18) * 3;
            SudokuTile tile = tiles[curColumn][curRow];
            nextIndex += 1;
            return tile;
        }
    }

    public void incrementTile(int row, int col){
        if (tiles[row][col].getNumber() == numRows) {
            tiles[row][col].setNumber(0);
        } else {
            tiles[row][col].setNumber(tiles[row][col].getNumber());
        }
    }


}
