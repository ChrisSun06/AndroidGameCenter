package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.Arrays;
import fall2018.csc2017.GameCentre.tiles.TofeTile;
import static org.junit.Assert.*;
import fall2018.csc2017.GameCentre.The2048.*;

public class The2048BoardManagerTest {
    private The2048BoardManager boardManager;

    /**
     * setting up a merge object in which the list to be merged is the second row of the board
     * and the direction of moving is left
     */
    private void setUp() {
        boardManager = new The2048BoardManager();
    }

    private void SetUnfinihsedBoard(){
        TofeTile[] tiles = new TofeTile[16];
        tiles[0] = new TofeTile(4, 0);
        tiles[1] = new TofeTile(4, 1);
        tiles[2] = new TofeTile(4, 2);
        tiles[3] = new TofeTile(4, 3);
        tiles[4] = new TofeTile(4, 4);
        tiles[5] = new TofeTile(4, 5);
        tiles[6] = new TofeTile(4, 6);
        tiles[7] = new TofeTile(4, 7);
        tiles[8] = new TofeTile(4, 8);
        tiles[9] = new TofeTile(4, 9);
        tiles[10] = new TofeTile(4, 10);
        tiles[11] = new TofeTile(4, 11);
        tiles[12] = new TofeTile(4, 12);
        tiles[13] = new TofeTile(4, 13);
        tiles[14] = new TofeTile(4, 14);
        tiles[15] = new TofeTile(4, 15);
        boardManager.getBoard().setAllTiles(tiles);
    }

    private void SetFinihsedBoard(){
        TofeTile[] tiles = new TofeTile[16];
        tiles[0] = new TofeTile(2, 0);
        tiles[1] = new TofeTile(4, 1);
        tiles[2] = new TofeTile(2, 2);
        tiles[3] = new TofeTile(4, 3);
        tiles[4] = new TofeTile(4, 4);
        tiles[5] = new TofeTile(2, 5);
        tiles[6] = new TofeTile(4, 6);
        tiles[7] = new TofeTile(2, 7);
        tiles[8] = new TofeTile(2, 8);
        tiles[9] = new TofeTile(4, 9);
        tiles[10] = new TofeTile(2, 10);
        tiles[11] = new TofeTile(4, 11);
        tiles[12] = new TofeTile(4, 12);
        tiles[13] = new TofeTile(2, 13);
        tiles[14] = new TofeTile(4, 14);
        tiles[15] = new TofeTile(2, 15);
        boardManager.getBoard().setAllTiles(tiles);
    }

    @Test
    public void testConstructor() {
        setUp();
        //int onBoardValue = boardManager.getBoard().getScore();
        //assertTrue((onBoardValue == 4 || onBoardValue == 6 || onBoardValue == 8));
        int score = boardManager.getBoard().getScore();
        assertTrue((score == 0));
    }

    @Test
    public void testMove(){
        setUp();
        SetUnfinihsedBoard();
        boardManager.move("column", false);
        //int onBoardValue = boardManager.getBoard().getScore();
        //assertTrue((onBoardValue == 66 || onBoardValue == 68));
        int score = boardManager.getBoard().getScore();
        assertTrue((score == 64));
    }

    @Test
    public void testPuzzleSolvedFalse(){
        setUp();
        SetUnfinihsedBoard();
        assertTrue(!boardManager.puzzleSolved());
    }

    @Test
    public void testPuzzleSolvedTrue(){
        setUp();
        SetFinihsedBoard();
        assertTrue(boardManager.puzzleSolved());
    }

    @Test
    public void testUndoIfUndoable(){
        setUp();
        SetUnfinihsedBoard();
        TofeTile[] previousTiles = boardManager.getBoard().getAllTiles();
        boardManager.move("row", true);
        TofeTile[] tilesAfterMove = boardManager.getBoard().getAllTiles();
        boardManager.undo();
        TofeTile[] tilesAfterUndo = boardManager.getBoard().getAllTiles();
        assertTrue((Arrays.equals(previousTiles, tilesAfterUndo) &&
                (!(Arrays.equals(previousTiles, tilesAfterMove)))));
    }

    @Test
    public void testUndoIfNotUndoable(){
        setUp();
        SetUnfinihsedBoard();
        TofeTile[] previousTiles = boardManager.getBoard().getAllTiles();
        boardManager.undo();
        TofeTile[] tilesAfterUndo = boardManager.getBoard().getAllTiles();
        assertTrue((Arrays.equals(previousTiles, tilesAfterUndo)));
    }

}

