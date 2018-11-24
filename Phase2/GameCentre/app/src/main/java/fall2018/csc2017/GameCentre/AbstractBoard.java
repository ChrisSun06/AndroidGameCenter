package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.Observable;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

public abstract class AbstractBoard  extends Observable implements Serializable {

    /**
     * The max times player can undo(default is 3, player could set any positive integer).
     */
    int maxUndoTime = 3;

    /**
     * How many moves players make
     */
    int numOfMoves = 0;

    abstract void setMaxUndoTime(int i);
    abstract int getMaxUndoTime();
}
