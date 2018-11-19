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
    private int changeAvailable;

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

    /*public void setChangeAvailable(int changeAvailable) {
        this.changeAvailable = changeAvailable;
    }*/

    public int getChangeAvailable() {
        return changeAvailable;
    }

    public SudokuTile(int id, int number, int changeAvailable) {
        setId(id);
        this.number = number;
        // changeAvailable is access only
        this.changeAvailable = changeAvailable;
    }
}
