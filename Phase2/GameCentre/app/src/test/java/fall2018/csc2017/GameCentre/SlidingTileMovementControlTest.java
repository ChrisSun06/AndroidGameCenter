package fall2018.csc2017.GameCentre;

import android.content.Context;
import fall2018.csc2017.GameCentre.tiles.TofeTile;
import org.junit.Rule;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import static org.junit.Assert.*;
import fall2018.csc2017.GameCentre.SlidingTileMovementController;
import org.mockito.Mock;

import static org.mockito.Mockito.*;

public class SlidingTileMovementControlTest {

    private int gridSize = 4;
    @Mock
    SlidingTileMovementController movementController;
    @Mock
    SlidingTileBoardManager boardManager = new SlidingTileBoardManager(gridSize);
    @Mock
    Context context = mock(Context.class);

    static final int validTap = 2;
    static final int invalidTap = 4;
    static final int blankPosition = 1;


   // private SlidingTileBoardManager boardManager = new SlidingTileBoardManager(4);

    public void setup(){
        movementController.setBoardManager(boardManager);
        //set up true valid tap position
        when(boardManager.isValidTap(validTap)).thenReturn(true);
        //set up a false valid tap
        when(boardManager.isValidTap(invalidTap)).thenReturn(false);

        when(boardManager.blankTilePosition()).thenReturn(blankPosition);

    }

    /**
     * Test a tap movement on a valid tap
     */
    @Test
     public void testValidTapMovement() {
        setup();
        movementController.processTapMovement(context, validTap);
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
        movementController.processTapMovement(context, invalidTap);
        assertEquals(origin, boardManager.getBoard());

    }

    /**
     * Test a tap movement that is a undo tap
     */
    @Test
    public void testUndoTapMovement() {
        setup();
        SlidingTileBoard origin = boardManager.getBoard();
        movementController.processTapMovement(context, validTap);
        SlidingTileBoard newBoard = boardManager.getBoard();
        movementController.processTapMovement(context, blankPosition);
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
        movementController.processTapMovement(context, validTap);
        SlidingTileBoard newBoard = boardManager.getBoard();
        movementController.processTapMovement(context, blankPosition);
        SlidingTileBoard UndoBoard = boardManager.getBoard();
        assertNotEquals(origin, newBoard);
        assertEquals(newBoard, UndoBoard);

    }

}
