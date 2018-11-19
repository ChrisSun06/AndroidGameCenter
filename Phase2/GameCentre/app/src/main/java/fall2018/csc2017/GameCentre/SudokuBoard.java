package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

public class SudokuBoard extends Observable implements Serializable{
    /**
     * The number of rows.
     */
    private int numRows = 6;


    /**
     * The number of rows.
     */
    private int numCols= 6;

    private Integer[][] tiles = new Integer[numRows][numCols];

    SudokuBoard(List<Integer> tiles) {
        Iterator<Integer> iter = tiles.iterator();
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

    void setTiles(int row, int col, int value){
        tiles[row][col] = value;
    }

    int getTiles(int row, int col){
        return tiles[row][col];
    }

    public Iterable<Integer> horizontal(){return new Iterable<Integer>() {
        @NonNull
        @Override
        public Iterator<Integer> iterator() {
            return new SudokuIterator();
        }
    };}

    public Iterable<Integer> vertical(){return new Iterable<Integer>() {
        @NonNull
        @Override
        public Iterator<Integer> iterator() {
            return new SudokuVerticalIterator();
        }
    };}

    public Iterable<Integer> sectional(){return new Iterable<Integer>() {
        @NonNull
        @Override
        public Iterator<Integer> iterator() {
            return new SudokuSectionalIterator();
        }
    };}

    private class SudokuIterator implements Iterator<Integer> {

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
        public Integer next() {
            int curRow = nextIndex / numRows;
            int curColumn = nextIndex % numCols;
            Integer tile = tiles[curRow][curColumn];
            nextIndex += 1;
            return tile;
        }
    }

    public int getRows(){return numRows;}

    public int getCols(){return numCols;}

    private class SudokuVerticalIterator implements Iterator<Integer> {

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
        public Integer next() {
            int curRow = nextIndex / numRows;
            int curColumn = nextIndex % numCols;
            Integer tile = tiles[curColumn][curRow];
            nextIndex += 1;
            return tile;
        }
    }

    private class SudokuSectionalIterator implements Iterator<Integer> {

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
        public Integer next() {
            int curRow = (nextIndex % 18) / 3;
            int curColumn = (nextIndex % 3) + (nextIndex / 18) * 3;
            Integer tile = tiles[curColumn][curRow];
            nextIndex += 1;
            return tile;
        }
    }

    public void incrementTile(int row, int col){
        if (tiles[row][col] == numRows) {
            tiles[row][col] = 0;
        } else {
            tiles[row][col] = tiles[row][col] + 1;
        }
    }


}
