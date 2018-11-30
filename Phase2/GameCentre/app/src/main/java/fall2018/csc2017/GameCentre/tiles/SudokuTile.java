package fall2018.csc2017.GameCentre.tiles;
/**
 * A Tile in a sudoku game.
 */
public class SudokuTile extends Tile {
    /**
     * Number at the current position.
     */
    private int value;

    /**
     * Number is mutable if check available is 1, 0 otherwise.
     */
    private boolean isMutable;

    /**
     * Set value of the current position
     *
     * @param value value of the current position
     */
    public void setValue(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean getIsMutable() {
        return isMutable;
    }

    public void setIsMutable(boolean isMutable) {
        this.isMutable = isMutable;
    }

    public SudokuTile(int id, int value, boolean isMutable) {
        setId(id);
        this.value = value;
        this.isMutable = isMutable;
    }
}
