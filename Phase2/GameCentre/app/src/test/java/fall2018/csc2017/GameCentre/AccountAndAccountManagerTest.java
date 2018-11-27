package fall2018.csc2017.GameCentre;

import android.content.Context;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Strategies.SlidingTileStrategy;
import fall2018.csc2017.GameCentre.tiles.SlidingTile;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class AccountAndAccountManagerTest {

    private UserAccManager accManager;

    private UserAccount testAccount;

    private Map<String, UserAccount> accountMap = new HashMap<>();

    private Context context = mock(Context.class);

    private void setUpAccountAndGame(){
        accManager = new UserAccManager();
        testAccount = new UserAccount("207", "207");
        accountMap.put("207", testAccount);
        accManager.setAccountMap(accountMap);
        accManager.setCurrentUser("207");
        accManager.setCurrentGame("5X5sliding");
    }

    private void setUp(){
        accManager = new UserAccManager();
    }

    @Test
    public void testAccountExist(){
        setUp();
        accManager.writeAcc("CSC207", "hello", context);
        assertTrue(accManager.accountExist("CSC207", "hello"));
        assertFalse(accManager.accountExist("CSC207", "???"));
        assertFalse(accManager.accountExist("???", "hello"));
    }

    @Test
    public void testSetGetCurrentUser(){
        setUp();
        accManager.setCurrentUser("CSC207");
        assertEquals("CSC207", accManager.getCurrentUser());
    }

    @Test
    public void testSetGetAccountMap(){
        setUp();
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        assertEquals(accountMap, accManager.getAccountMap());
    }

    @Test
    public void testSetGetCurrentGameState(){
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        AbstractBoardManager boardManager = new SlidingTileBoardManager(5);
        accManager.setCurrentGameState(boardManager);
        assertSame(accManager.getCurrentGameStateMap("sliding"), boardManager);
        assertSame(accManager.getCurrentGameStateMap("Sudoku"), null);
        assertSame(accManager.getCurrentGameStateMap("abc"), null);
    }

    @Test
    public void testGetCurrentGame(){
        setUp();
        accManager.setCurrentGame("5X5sliding");
        assertSame(accManager.getCurrentGame(),"5X5sliding");
    }

    @Test
    public void testGetUserUndoTime(){
        setUpAccountAndGame();
        accManager.setAccountMap(accountMap);
        accManager.updateUndoTime(5);
        assertEquals(accManager.getUserUndoTime(), 5);
    }

    @Test
    public void testUserGetName(){
        setUpAccountAndGame();
        assertEquals(testAccount.getName(),"207");
    }

    @Test
    public void testAddScoreSlidingTile(){
        setUpAccountAndGame();
        ScoringStrategy slidingTileStrategy = new SlidingTileStrategy(accManager);
        List<SlidingTile> tiles = new ArrayList<>();
        for (int tileNum = 0; tileNum != 25; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, tileNum));
        }
        SlidingTileBoard board = new SlidingTileBoard(tiles, 5);
        AbstractBoardManager boardManager = new SlidingTileBoardManager(board);
        accManager.addScore(slidingTileStrategy, 4, boardManager);
        assertEquals(true,
                6250 == accManager.getAccountMap().get("207").
                        getScores("5X5sliding"));
    }



}
