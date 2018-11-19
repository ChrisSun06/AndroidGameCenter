package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;

/**
 * The serializable user account manager.
 */
public class UserAccManager implements Serializable {

    /**
     * The HashMap that stores all counts.
     */
    private HashMap<String, UserAccount> accountMap = new HashMap<>();

    /**
     * The String that stores the current user.
     */
    private String currentUser;

    /**
     * The UserAccManager instance.
     */
    private static UserAccManager userAccManagerInstance = new UserAccManager();

    /**
     * The Constructor that prevents other classes from calling it (singleton design).
     */
    private UserAccManager(){}

    /**
     * The only way other classes can access this object.
     */
    public static UserAccManager getInstance(){
        if (userAccManagerInstance == null){
            userAccManagerInstance = new UserAccManager();
        }
        return userAccManagerInstance;
    }

    /**
     * Check whether a given email and password exists in the accountMap or not.
     *
     * @param email    the email/username
     * @param password the password
     * @return whether a given email and password exists in the accountMap or not.
     */
    public Boolean accountExist(String email, String password) {
        Boolean found = false;
        for (String key : accountMap.keySet()) {
            if (key.equals(email) && accountMap.get(key).getPassword().equals(password)) {
                found = true;
            }
        }
        return found;
    }

    /**
     * Return the current user.
     *
     * @return the current user.
     */
    public String getCurrentUser(){
        return currentUser;
    }

    /**
     * Set the current user.
     *
     * @param currentUser current user.
     */
    public void setCurrentUser(String currentUser){
        this.currentUser = currentUser;
    }

    /**
     * Return the account map.
     *
     * @return account map
     */
    public HashMap<String, UserAccount> getAccountMap() {
        return accountMap;
    }

    /**
     * Write the accounts into accountMap.
     *
     * @param email    the email/username
     * @param password the password
     */
    public void writeAcc(String email, String password) {
        accountMap.put(email, new UserAccount(email, password));
    }

    /**
     * Return the current game user is playing.
     *
     * @return the current game user is playing.
     */
    private String getCurrentGame() {
        return GameCenterActivity.CURRENT_GAME;

    }

    /**
     * Return the current game user is playing.
     *
     * @param accountMap the account hashMap.
     */
    public void setAccountMap(HashMap<String, UserAccount> accountMap){
        this.accountMap = accountMap;
    }

    /**
     * Add score of a user based on how many moves he/she made.
     *
     * @param moves number of moves user made.
     * @param board board user is playing on.
     */
    void addScore(int moves, Board board) {
        int score = accountMap.get(currentUser).getScores().get(getCurrentGame());
        if (1000 * board.numTiles() / moves > score && moves != 1) {
            accountMap.get(currentUser).setScore(getCurrentGame(),
                    1000 * board.numTiles() / moves);
        }
    }

    /**
     * Return number of undo times user chose to have.
     *
     * @return number of undo times user chose to have.
     */
    int getUserUndoTime() {
        return accountMap.get(currentUser).getMaxUndo();
    }

    /**
     * Update the number of undo times user chose to have.
     *
     * @param undoTime numbers of undo
     */
    public void updateUndoTime(int undoTime) {
        accountMap.get(currentUser).setMaxUndo(undoTime);
    }

}
