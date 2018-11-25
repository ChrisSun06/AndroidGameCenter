package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.TofeTile;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class The2048BoardTest {
    private The2048Board board;
    /**
     * setting up a merge object in which the list to be merged is the second row of the board
     * and the direction of moving is left
     */
    private void setUpBoardWithAll2() {
        List<TofeTile> tiles = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            tiles.add(new TofeTile(2, i));
        }
        board = new The2048Board(tiles);
    }

    private void setUpBoardWith3columnsAll2() {
        List<TofeTile> tiles = new ArrayList<>();
        for(int i = 0; i < 16; i++){
            if(i % 4 != 3) {
                tiles.add(new TofeTile(2, i));
            }else{
                tiles.add(new TofeTile(0, i));
            }
        }
        board = new The2048Board(tiles);
    }

    @Test
    public void testGetScore() {
        setUpBoardWithAll2();
        assertEquals(32, board.getScore());
    }


    @Test
    public void testMergeAll2Up() {
        setUpBoardWithAll2();
        TofeTile[] result = board.merge("column", false);
        TofeTile[] expectedResult = new TofeTile[16];
        expectedResult[0] = new TofeTile(4, 0);
        expectedResult[1] = new TofeTile(4, 1);
        expectedResult[2] = new TofeTile(4, 2);
        expectedResult[3] = new TofeTile(4, 3);
        expectedResult[4] = new TofeTile(4, 4);
        expectedResult[5] = new TofeTile(4, 5);
        expectedResult[6] = new TofeTile(4, 6);
        expectedResult[7] = new TofeTile(4, 7);
        expectedResult[8] = new TofeTile(0, 8);
        expectedResult[9] = new TofeTile(0, 9);
        expectedResult[10] = new TofeTile(0, 10);
        expectedResult[11] = new TofeTile(0, 11);
        expectedResult[12] = new TofeTile(0, 12);
        expectedResult[13] = new TofeTile(0, 13);
        expectedResult[14] = new TofeTile(0, 14);
        expectedResult[15] = new TofeTile(0, 15);
        assertTrue(Arrays.equals(result, expectedResult));
    }

    @Test
    public void testGenerateNewTileNoBlank(){
        setUpBoardWithAll2();
        board.generateNewTiles();
        assertEquals(32, board.getScore());
    }

    @Test
    public void testGenerateNewTileWithBlank(){
        setUpBoardWithAll2();
        TofeTile[] result = board.merge("column", false);
        board.setAllTiles(result);
        board.generateNewTiles();
        assertTrue((board.getScore() == 34 || board.getScore() == 36));
    }

    @Test
    public void testMerge3columns2Left() {
        setUpBoardWith3columnsAll2();
        TofeTile[] result = board.merge("row", false);
        TofeTile[] expectedResult = new TofeTile[16];
        expectedResult[0] = new TofeTile(4, 0);
        expectedResult[1] = new TofeTile(2, 1);
        expectedResult[2] = new TofeTile(0, 2);
        expectedResult[3] = new TofeTile(0, 3);
        expectedResult[4] = new TofeTile(4, 4);
        expectedResult[5] = new TofeTile(2, 5);
        expectedResult[6] = new TofeTile(0, 6);
        expectedResult[7] = new TofeTile(0, 7);
        expectedResult[8] = new TofeTile(4, 8);
        expectedResult[9] = new TofeTile(2, 9);
        expectedResult[10] = new TofeTile(0, 10);
        expectedResult[11] = new TofeTile(0, 11);
        expectedResult[12] = new TofeTile(4, 12);
        expectedResult[13] = new TofeTile(2, 13);
        expectedResult[14] = new TofeTile(0, 14);
        expectedResult[15] = new TofeTile(0, 15);
        assertTrue(Arrays.equals(result, expectedResult));
    }

    @Test
    public void testMerge3columns2Right() {
        setUpBoardWith3columnsAll2();
        TofeTile[] result = board.merge("row", true);
        TofeTile[] expectedResult = new TofeTile[16];
        expectedResult[0] = new TofeTile(0, 0);
        expectedResult[1] = new TofeTile(0, 1);
        expectedResult[2] = new TofeTile(2, 2);
        expectedResult[3] = new TofeTile(4, 3);
        expectedResult[4] = new TofeTile(0, 4);
        expectedResult[5] = new TofeTile(0, 5);
        expectedResult[6] = new TofeTile(2, 6);
        expectedResult[7] = new TofeTile(4, 7);
        expectedResult[8] = new TofeTile(0, 8);
        expectedResult[9] = new TofeTile(0, 9);
        expectedResult[10] = new TofeTile(2, 10);
        expectedResult[11] = new TofeTile(4, 11);
        expectedResult[12] = new TofeTile(0, 12);
        expectedResult[13] = new TofeTile(0, 13);
        expectedResult[14] = new TofeTile(2, 14);
        expectedResult[15] = new TofeTile(4, 15);
        assertTrue(Arrays.equals(result, expectedResult));
    }

    @Test
    public void testGetTiles() {
        setUpBoardWith3columnsAll2();
        TofeTile[] result = board.merge("row", true);
        board.setAllTiles(result);
        assertTrue(Arrays.equals(result, board.getAllTiles()));
    }

    @Test
    public void testEquals() {
        setUpBoardWithAll2();
        The2048Board otherBoard = new The2048Board(Arrays.asList(board.getAllTiles()));
        board.setAllTiles(board.merge("column", true));
        board.setAllTiles(board.merge("column", false));
        otherBoard.setAllTiles(board.merge("column", true));
        otherBoard.setAllTiles(board.merge("column", false));
        assertEquals(board, otherBoard);
    }
}
