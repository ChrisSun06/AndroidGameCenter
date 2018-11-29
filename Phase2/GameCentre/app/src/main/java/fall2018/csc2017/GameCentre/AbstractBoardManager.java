package fall2018.csc2017.GameCentre;

import java.io.Serializable;

public abstract class AbstractBoardManager implements Serializable {

    AbstractBoard board;

    public AbstractBoard getBoard(){return board;}

    public abstract boolean puzzleSolved();

    public abstract String toString();
}
