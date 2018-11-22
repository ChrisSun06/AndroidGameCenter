package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

public class AccountAndAccountManagerTest {

    private UserAccManager accManager;

    private UserAccount testAccount;

    private Map<String, UserAccount> accountMap = new HashMap<>();

    private void setUpAccountMap(){
        testAccount = new UserAccount("207", "207");
        accountMap.put("207", testAccount);
    }

    private void setUp(){
        accManager = new UserAccManager();
    }

    @Test
    public void testAccountExist(){
        setUp();
        accManager.writeAcc("CSC207", "hello");
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
        setUpAccountMap();
        accManager.setAccountMap(accountMap);
        assertEquals(accountMap, accManager.getAccountMap());
    }

    @Test
    public void testSetCurrentGameState(){
        setUp();
        setUpAccountMap();
        accManager.setAccountMap(accountMap);
        BoardManager boardManager = new BoardManager(5);
        accManager.setCurrentUser("207");
        accManager.setCurrentGame("5X5sliding");
        accManager.setCurrentGameState(boardManager);
        assertSame(accManager.getCurrentGameStateMap("5X5sliding"), boardManager);
    }

    @Test
    public void testGetCurrentGame(){
        setUp();
        accManager.setCurrentGame("5X5sliding");
        assertSame(accManager.getCurrentGame(),"5X5sliding");
    }

    @Test
    public void testGetUserUndoTime(){
        setUp();
        setUpAccountMap();
        accManager.setAccountMap(accountMap);
        accManager.setCurrentUser("207");
        accManager.updateUndoTime(5);
        assertEquals(accManager.getUserUndoTime(), 5);
    }

    @Test
    public void testUserGetName(){
        setUpAccountMap();
        assertEquals(testAccount.getName(),"207");
    }
}
