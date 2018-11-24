package fall2018.csc2017.GameCentre;

import java.io.Serializable;

abstract class AbstractBoardManager implements Serializable {

    AbstractBoard board;

    AbstractBoard getBoard(){return board;}

    abstract boolean puzzleSolved();
}
