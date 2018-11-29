package fall2018.csc2017.GameCentre;


import org.junit.Test;

import static org.junit.Assert.*;

import static org.mockito.Mockito.*;
import fall2018.csc2017.GameCentre.SlidingTile.*;
public class SlidingTileMovementControlTest {

    private int gridSize = 4;
    SlidingTileMovementController movementController = new SlidingTileMovementController();

    SlidingTileBoardManager boardManager = new SlidingTileBoardManager(gridSize);

    static final int validTap = 2;
    static final int invalidTap = 4;
    static final int blankPosition = 1;


   // private SlidingTileBoardManager boardManager = new SlidingTileBoardManager(4);

    public void setup(){
        movementController.setBoardManager(boardManager);

    }

    /**
     * Test a tap movement on a valid tap
     */
    @Test
     public void testValidTapMovement() {
        setup();
        movementController.processTapMovement(validTap);
        //when(boardManager.touchMove(2, true)).thenAnswer("Successful tap!");
        when(boardManager.blankTilePosition()).thenCallRealMethod();
        assertEquals(validTap, boardManager.blankTilePosition());

    }

    /**
     * Test a tap movement with a non-valid tap
     */
    @Test
    public void testInvalidTapMovement() {
        setup();
        SlidingTileBoard origin = boardManager.getBoard();
        movementController.processTapMovement(invalidTap);
        assertEquals(origin, boardManager.getBoard());

    }

    /**
     * Test a tap movement that is a undo tap
     */
    @Test
    public void testUndoTapMovement() {
        setup();
        SlidingTileBoard origin = boardManager.getBoard();
        movementController.processTapMovement(validTap);
        SlidingTileBoard newBoard = boardManager.getBoard();
        movementController.processTapMovement(blankPosition);
        SlidingTileBoard UndoBoard = boardManager.getBoard();
        assertNotEquals(origin, newBoard);
        assertEquals(origin, UndoBoard);

    }


    /**
     * Test a tap movement that is a undo tap
     */
    @Test
    public void testUndoTapWithNoMoreUndoNumberMovement() {
        setup();
        SlidingTileBoard origin = boardManager.getBoard();
        boardManager.getBoard().setMaxUndoTime(0);
        movementController.processTapMovement(validTap);
        SlidingTileBoard newBoard = boardManager.getBoard();
        movementController.processTapMovement(blankPosition);
        SlidingTileBoard UndoBoard = boardManager.getBoard();
        assertNotEquals(origin, newBoard);
        assertEquals(newBoard, UndoBoard);

    }

}
