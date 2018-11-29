package fall2018.csc2017.GameCentre;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import fall2018.csc2017.GameCentre.SlidingTile.*;
import fall2018.csc2017.GameCentre.The2048.*;
import fall2018.csc2017.GameCentre.Sudoku.*;

public class GameActivityControllerTest {

    private GameCenterActivityController gController;

    private SlidingTileFragment slidingTileFragment;
    private The2048Fragment the2048Fragment;
    private SudokuFragment sudokuFragment;
    private SlidingTileFragment nullFragment;

    private GameCenterActivity gameCenterActivity;
    private SlidingTileGameActivity slidingTileGameActivity;
    private SudokuGameActivity sudokuGameActivity;
    private The2048GameActivity the2048GameActivity;

    /**
     * Create 3 fragment instances except nullFragment.
     */
    private void setUpFragments(){
        slidingTileFragment = new SlidingTileFragment();
        the2048Fragment = new The2048Fragment();
        sudokuFragment = new SudokuFragment();
    }

    private void setUpClass(){
        gameCenterActivity = new GameCenterActivity();
        slidingTileGameActivity = new SlidingTileGameActivity();
        sudokuGameActivity = new SudokuGameActivity();
        the2048GameActivity = new The2048GameActivity();
    }

    private void setUp(){
        gController = new GameCenterActivityController();
    }

    @Test
    public void testSwitchFragment(){
        setUp();
        setUpFragments();
        assertEquals(gController.switchFragment(null), nullFragment);
        assertEquals(gController.switchFragment("sliding").getClass(),
                slidingTileFragment.getClass());
        assertEquals(gController.switchFragment("2048").getClass(),
                the2048Fragment.getClass());
        assertEquals(gController.switchFragment("Sudoku").getClass(),
                sudokuFragment.getClass());
        assertEquals(gController.switchFragment("abcd").getClass(),
                slidingTileFragment.getClass());
        assertNotEquals(gController.switchFragment("2048").getClass(),
                sudokuFragment.getClass());
        assertNotEquals(gController.switchFragment("2048").getClass(),
                slidingTileFragment.getClass());
    }
}
