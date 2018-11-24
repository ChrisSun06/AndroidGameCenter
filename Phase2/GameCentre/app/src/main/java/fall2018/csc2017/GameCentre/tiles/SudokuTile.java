package fall2018.csc2017.GameCentre.tiles;

import android.graphics.Bitmap;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.abstract_classes.Tile;

/**
 * A Tile in a sudoku game.
 */
public class SudokuTile extends Tile {
    /**
     * Number at the current position.
     */
    private int number;

    /**
     * Number is mutable if check available is 1, 0 otherwise.
     */
    private boolean changeAvailable;

    /**
     * Set number of the current position
     *
     * @param number bitmap of the background image
     */
    public void setNumber(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public void incrementNumber() {
        this.number ++;
    }

    /*public void setChangeAvailable(int changeAvailable) {
        this.changeAvailable = changeAvailable;
    }*/

    public boolean getChangeAvailable() {
        return changeAvailable;
    }

    public SudokuTile(int id, int number, boolean changeAvailable) {
        setId(id);
        this.number = number;
        // changeAvailable is access only
        this.changeAvailable = changeAvailable;
    }
}
