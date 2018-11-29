package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.Observable;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

public abstract class AbstractBoard  extends Observable implements Serializable {

    int numRows;
    int numCols;

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
     * set number of rows.
     *
     * @param numRows number of rows
     */
    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }

    /**
     * set number of columns.
     *
     * @param numCols number of columns
     */
    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }
}
