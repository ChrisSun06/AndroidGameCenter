package fall2018.csc2017.GameCentre;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;

/**
 * The serializable user account manager.
 */
public class UserAccManager implements Serializable {

    /**
     * The HashMap that stores all counts.
     */
    private Map<String, UserAccount> accountMap = new HashMap<>();

    /**
     * The String that stores the current user.
     */
    private String currentUser;

    /**
     * The boolean that keeps track of whether last game saves load is successful or not.
     */
    private boolean gameLoaded;

    /**
     * The String that stores the current game.
     */
    private String currentGame;

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
        return (HashMap<String, UserAccount>) accountMap;
    }

    /**
     *  Write the accounts into accountMap.
     *
     * @param email    the email/username
     * @param password the password
     * @param context the current context
     * @return the string for which toast to display
     */
    public String writeAcc(String email, String password, Context context) {
        if (accountMap.containsKey(email)){
            return "Username already taken!";
        } else if (password.equals("") || email.equals("")){
            return "Field cannot be empty!";
        } else {
            accountMap.put(email, new UserAccount(email, password));
            return "Registered!";
        }
    }

    /**
     * Return the current game user is playing.
     *
     * @return the current game user is playing.
     */
    public String getCurrentGame() {
        return currentGame;
    }

    /**
     * Set the current game user is playing.
     *
     * @param game the current game
     */
    public void setCurrentGame(String game) {
        if (game != null){
            currentGame = game;
        }
    }

    /**
     * Return the current game user is playing.
     *
     * @param accountMap the account hashMap.
     */
    public void setAccountMap(Map<String, UserAccount> accountMap){
        this.accountMap = accountMap;
    }

    /**
     * Add score of a user based on how many moves he/she made.
     *
     * @param scoringStrategy the strategy to calculate score.
     * @param moves number of moves user made.
     * @param boardManager board manager user is playing on.
     */
    void addScore(ScoringStrategy scoringStrategy, int moves, AbstractBoardManager boardManager) {
        if (boardManager.toString().equals("2048 Board Manager")){
            scoringStrategy.addScore(moves, boardManager.getBoard());
        }
        else {
            if (boardManager.puzzleSolved()) {
                scoringStrategy.addScore(moves, boardManager.getBoard());
            }
        }
    }

    /**
     * Set the user's game save to be the current board manager.
     *
     * @param boardManager the board manager
     */
    void setCurrentGameState(AbstractBoardManager boardManager){
        if (currentGame != null && accountMap.containsKey(currentUser)) {
            accountMap.get(currentUser).setSaves(currentGame, boardManager);
        }
    }

    /**
     * Set the user's game save to be the current board manager.
     *
     * @param game the game name that user wants to load.
     */
    AbstractBoardManager getCurrentGameStateMap(String game){
        Map<String, AbstractBoardManager> tempGameSaves = accountMap.get(currentUser).getSaves();
        if (tempGameSaves.containsKey(game) && tempGameSaves.get(game) != null) {
            currentGame = game;
            gameLoaded = true;
            return tempGameSaves.get(game);
        } else {
            currentGame = null;
            gameLoaded = false;
            return null;
        }
    }

    /**
     * Make a toast message of whether game board is initialized successfully or not.
     * Combined with getCurrentGameStateMap method above, to clarify for user.
     *
     * @param context current activity context.
     */
    void makeToastGameState(Context context){
        if (gameLoaded) {
            Toast.makeText(context, "Game save successfully loaded! Enjoy your game.",
                    Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(context, "Game save not found!", Toast.LENGTH_LONG).show();
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
