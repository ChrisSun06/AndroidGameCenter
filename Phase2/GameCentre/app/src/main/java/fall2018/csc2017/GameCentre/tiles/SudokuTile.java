package fall2018.csc2017.GameCentre.tiles;
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
    private boolean isMutable;

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

    /*public void incrementNumber() {
        this.number ++;
    }*/

    /*public void setChangeAvailable(int changeAvailable) {
        this.changeAvailable = changeAvailable;
    }*/

    public boolean getIsMutable() {
        return isMutable;
    }

    public void setIsMutable(boolean isMutable) {
        this.isMutable = isMutable;
    }

    public SudokuTile(int number, boolean isMutable) {
        this.number = number;
        // changeAvailable is access only
        this.isMutable = isMutable;
    }
}
