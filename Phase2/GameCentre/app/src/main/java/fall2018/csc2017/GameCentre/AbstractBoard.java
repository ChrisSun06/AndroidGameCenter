package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.Observable;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

public abstract class AbstractBoard  extends Observable implements Serializable {

    int numRows;
    int numCols;

    abstract void setMaxUndoTime(int i);
    abstract int getMaxUndoTime();

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
}
