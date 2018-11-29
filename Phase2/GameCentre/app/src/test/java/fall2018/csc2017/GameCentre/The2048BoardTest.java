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
import fall2018.csc2017.GameCentre.The2048.*;

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
        assertEquals(0, board.getScore());
    }


    @Test
    public void testMergeAll2Up() {
        setUpBoardWithAll2();
        TofeTile[] result = board.merge("column", false);
        TofeTile[] expectedResult = new TofeTile[16];
        for(int i = 0; i < 16; i++){
            if(i<8)
                expectedResult[i] = new TofeTile(4, i);
            else
                expectedResult[i] = new TofeTile(0, i);
        }
        assertTrue(Arrays.equals(result, expectedResult));
    }

    @Test
    public void testGenerateNewTileNoBlank(){
        setUpBoardWithAll2();
        board.setAllTiles(board.generateNewTile(board.getAllTiles()));
        assertEquals(0, board.getScore());
    }

    @Test
    public void testGenerateNewTileWithBlank(){
        setUpBoardWithAll2();
        TofeTile[] result = board.merge("column", false);
        int before = 0;
        for(int i = 0; i < 16; i ++){
            if(result[i].getValue() != 0)
                before++;
        }
        TofeTile[] generated = board.generateNewTile(result);
        int after = 0;
        for(int i = 0; i < 16; i ++){
            if(generated[i].getValue() != 0)
                after++;
        }
        assertTrue((after - before == 1 ));
    }

    @Test
    public void testMerge3columns2Left() {
        setUpBoardWith3columnsAll2();
        TofeTile[] result = board.merge("row", false);
        TofeTile[] expectedResult = new TofeTile[16];
        for(int i = 0; i < 16; i++){
            if(i % 4 == 0)
                expectedResult[i] = new TofeTile(4, i);
            else if(i % 4 == 1)
                expectedResult[i] = new TofeTile(2, i);
            else
                expectedResult[i] = new TofeTile(0, i);
        }
    }

    @Test
    public void testMerge3columns2Right() {
        setUpBoardWith3columnsAll2();
        TofeTile[] result = board.merge("row", true);
        TofeTile[] expectedResult = new TofeTile[16];
        for(int i = 0; i < 16; i++){
            if(i % 4 == 3)
                expectedResult[i] = new TofeTile(4, i);
            else if(i % 4 == 2)
                expectedResult[i] = new TofeTile(2, i);
            else
                expectedResult[i] = new TofeTile(0, i);
        }
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
