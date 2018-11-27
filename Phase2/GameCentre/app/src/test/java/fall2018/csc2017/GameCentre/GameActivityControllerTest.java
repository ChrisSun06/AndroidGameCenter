package fall2018.csc2017.GameCentre;

import org.junit.Test;

import fall2018.csc2017.GameCentre.tiles.SlidingTile;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertSame;

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

    @Test
    public void testGetIntent(){
        setUp();
        setUpClass();
        assertEquals(gController.getIntent(null), gameCenterActivity.getClass());
        assertNotEquals(gController.getIntent(null), sudokuGameActivity.getClass());
        assertEquals(gController.getIntent("Sudoku"), sudokuGameActivity.getClass());
        assertEquals(gController.getIntent("2048"), the2048GameActivity.getClass());
        assertEquals(gController.getIntent("sliding"), slidingTileGameActivity.getClass());
        assertEquals(gController.getIntent("abcd"), gameCenterActivity.getClass());
    }
}
