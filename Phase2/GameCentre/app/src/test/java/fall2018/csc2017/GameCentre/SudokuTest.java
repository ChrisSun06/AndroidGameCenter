package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SudokuTest {

    SudokuBoardManager boardManager;

    /**
     * Make a partially solved Board.
     */
    private void setUpCorrect(){
        this.boardManager = new SudokuBoardManager();
    }

    /**
     * Make a first row of the Sudoku board be numbers from 1-6.
     */
    private void setFirstRow(){
        for (int i = 0; i < 6; i++){
            this.boardManager.getBoard().setTiles(0, i, i+1);
        }
    }

    /**
     * Make a empty Board, filled with all 0s.
     */
    private void setUp(){
        List<Integer> tiles = new ArrayList<>(Collections.nCopies(36, 0));
        SudokuBoard board = new SudokuBoard(tiles);
        boardManager = new SudokuBoardManager(board);
    }

    @Test
    public void testIsSolved(){
        setUpCorrect();
        assertEquals(false, boardManager.puzzleSolved());
        boardManager.solve();
        assertEquals(true, boardManager.puzzleSolved());
        boardManager.touchMove(1);
        assertEquals(false, boardManager.puzzleSolved());
    }

    @Test
    public void testTouchMoveEmptyFirst(){
        setUp();
        assertEquals(0, boardManager.getBoard().getTiles(0, 0));
        boardManager.touchMove(0);
        assertEquals(1, boardManager.getBoard().getTiles(0, 0));
        boardManager.touchMove(0);
        assertEquals(2, boardManager.getBoard().getTiles(0, 0));
    }

    @Test
    public void testTouchMoveEmptyLast(){
        setUp();
        assertEquals(0, boardManager.getBoard().getTiles(5, 5));
        boardManager.touchMove(35);
        assertEquals(1, boardManager.getBoard().getTiles(5, 5));
        boardManager.touchMove(35);
        assertEquals(2, boardManager.getBoard().getTiles(5, 5));
    }

    @Test
    public void testTouchMoveSolved(){
        setUp();
        int tile = boardManager.getBoard().getTiles(4,5);
        boardManager.touchMove(29);
        int newTile = boardManager.getBoard().getTiles(4, 5);
        if (tile == 6){
            assertEquals(0, newTile);
        } else {
            assertEquals(tile+1, newTile);
        }
    }

    @Test
    public void testTouchMoveReset(){
        setUp();
        for (int i = 0; i <= 6; i++){
            boardManager.touchMove(1);
        }
        assertTrue(boardManager.getBoard().getTiles(0, 1) == 0);
    }

    @Test
    public void testIsValid(){
        setUpCorrect();
        assertEquals(true, boardManager.isValid());
    }

    @Test
    public void testIsValidEmpty(){
        setUp();
        assertTrue(boardManager.isValid());
        setFirstRow();
        assertTrue(boardManager.isValid());
        boardManager.getBoard().setTiles(1, 0, 1);
        assertFalse(boardManager.isValid());
        assertTrue(boardManager.isRowValid());
        assertFalse(boardManager.isColValid());
        assertFalse(boardManager.isSectionValid());
    }


}
