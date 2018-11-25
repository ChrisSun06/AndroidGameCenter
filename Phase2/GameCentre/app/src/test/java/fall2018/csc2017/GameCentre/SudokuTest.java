package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.SlidingTile;
import fall2018.csc2017.GameCentre.tiles.SudokuTile;

import static org.junit.Assert.*;

public class SudokuTest {

    SudokuBoardManager boardManager;

    SudokuBoard board;

    /**
     * Make a partially solved Board.
     */
    @Test
    public void setUpCorrect(){
        this.boardManager = new SudokuBoardManager();
    }

    /**
     * Make a first row of the Sudoku board be numbers from 1-6.
     */

    private void setFirstRow(){
        for (int i = 0; i < 9; i++){
            this.boardManager.getBoard().setTile(0, i, i+1);
        }
    }

    /**
     * Make a empty Board, filled with all 0s.
     */
    private void setUp(){
        ArrayList<Integer> numbers = new ArrayList<>(Collections.nCopies(81, 0));
        ArrayList<SudokuTile> tiles = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            tiles.add(new SudokuTile(numbers.get(i), false));
        }
        this.board = new SudokuBoard(tiles);
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
        assertEquals(0, boardManager.getBoard().getTile(0, 0).getNumber());
        boardManager.touchMove(0);
        assertEquals(1, boardManager.getBoard().getTile(0, 0).getNumber());
        boardManager.touchMove(0);
        assertEquals(2, boardManager.getBoard().getTile(0, 0).getNumber());
    }

    @Test
    public void testTouchMoveEmptyLast(){
        setUp();
        assertEquals(0, boardManager.getBoard().getTile(8, 8).getNumber());
        boardManager.touchMove(80);
        assertEquals(1, boardManager.getBoard().getTile(8, 8).getNumber());
        boardManager.touchMove(80);
        assertEquals(2, boardManager.getBoard().getTile(8, 8).getNumber());
    }

    @Test
    public void testTouchMoveSolved(){
        setUp();
        SudokuTile temp = boardManager.getBoard().getTile(3,2);
        SudokuTile tile = new SudokuTile(temp.getNumber(), temp.getIsMutable());
        boardManager.touchMove(29);
        SudokuTile newTile = boardManager.getBoard().getTile(3, 2);
        if (tile.getNumber() == 9){
            assertEquals(0, newTile.getNumber());
        } else {
            assertEquals(tile.getNumber() + 1, newTile.getNumber());
        }
    }

    @Test
    public void testTouchMoveReset(){
        setUp();
        for (int i = 0; i <= 9; i++){
            boardManager.touchMove(1);
        }
        assertTrue(boardManager.getBoard().getTile(0, 1).getNumber() == 0);
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
        boardManager.getBoard().setTile(1, 0, 1);
        assertFalse(boardManager.isValid());
    }


}
