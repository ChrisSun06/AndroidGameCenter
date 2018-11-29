package fall2018.csc2017.GameCentre.Sudoku;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.tiles.SudokuTile;

public class SudokuBoard extends AbstractBoard implements Serializable{

    private SudokuTile[][] tiles;

    public SudokuBoard(List<SudokuTile> tiles) {
        setNumCols(9);
        setNumRows(9);
        this.tiles = new SudokuTile[getNumRows()][getNumCols()];
        Iterator<SudokuTile> iter = tiles.iterator();
        for (int row = 0; row != getNumRows(); row++) {
            for (int col = 0; col != getNumCols(); col++) {
                this.tiles[row][col] = iter.next();
            }
        }
    }

    int numTiles(){
        return getNumCols() * getNumRows();
    }

    public void setTile(int row, int col, int value){
        tiles[row][col].setNumber(value);
    }

    public SudokuTile getTile(int row, int col){
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
            int curRow = nextIndex / getNumRows();
            int curColumn = nextIndex % getNumCols();
            SudokuTile tile = tiles[curRow][curColumn];
            nextIndex += 1;
            return tile;
        }
    }

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
            int curRow = nextIndex / getNumRows();
            int curColumn = nextIndex % getNumCols();
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
            int curRow = ((nextIndex / 9) / 3) * 3 + (nextIndex % 9) / 3;
            int curCol = ((nextIndex / 9) % 3) * 3 + (nextIndex % 9) % 3;
            //int curRow = (nextIndex % 18) / 3;
            //int curCol = (nextIndex % 3) + (nextIndex / 18) * 3;
            SudokuTile tile = tiles[curCol][curRow];
            nextIndex += 1;
            return tile;
        }
    }

    void incrementTile(int row, int col){
        if (tiles[row][col].getNumber() == getNumRows()) {
            tiles[row][col].setNumber(0);
        } else {
            tiles[row][col].setNumber(tiles[row][col].getNumber() + 1);
        }
        setChanged();
        notifyObservers();
    }


}
