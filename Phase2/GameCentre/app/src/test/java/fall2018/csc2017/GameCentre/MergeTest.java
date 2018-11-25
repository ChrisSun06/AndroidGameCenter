package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MergeTest {
    /** The board manager for testing. */
    private Merge2048 merge2048;

    /**
     * setting up a merge object in which the list to be merged is the second row of the board
     * and the direction of moving is left
     */
    private void setUpLeft() {
        TofeTile[] tiles = {new TofeTile(4, 4), new TofeTile(0, 5),
                new TofeTile(4, 6), new TofeTile(2, 7)};
        merge2048 = new Merge2048(tiles);
    }

    /**
     * setting up a merge object in which the list to be merged is the second row of the board
     * and the direction of moving is left
     */
    private void setUpRight() {
        TofeTile[] tiles = {new TofeTile(0, 7), new TofeTile(2, 6),
                new TofeTile(2, 5), new TofeTile(2, 4)};
        merge2048 = new Merge2048(tiles);
    }

    /**
     * Test merge works if we are trying to move to left
     */
    @Test
    public void testMergeLeft() {
        setUpLeft();
        TofeTile[] result = {new TofeTile(8, 4), new TofeTile(2, 5),
                new TofeTile(0, 6), new TofeTile(0, 7)};
        assertTrue(Arrays.equals(result, merge2048.merge()));
    }

    /**
     * Test merge works if we are trying to move to right
     */
    @Test
    public void testMergeRight() {
        setUpRight();
        TofeTile[] result = {new TofeTile(4, 7), new TofeTile(2, 6),
                new TofeTile(0, 5), new TofeTile(0, 4)};
        assertTrue(Arrays.equals(result, merge2048.merge()));
    }
}