package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.util.Log;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
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
     * Load and return UserAccManager from local storage.
     *
     * @param c current context
     * @throws IOException            the in/output exception
     * @throws ClassNotFoundException the class not found exception
     */
    private UserAccManager loadAcc(Context c) throws IOException,
            ClassNotFoundException {
        UserAccManager temp = new UserAccManager();
        InputStream inputStream = c.openFileInput(LoginActivity.ACC_INFO);
        if (inputStream != null) {
            ObjectInputStream input = new ObjectInputStream(inputStream);
            temp = (UserAccManager) input.readObject();
            inputStream.close();
        }
        return temp;
    }

    /**
     * Load/populate the accountMap from the accountMap of UserAccManager in local storage.
     *
     * @param context the current context
     */
    public void loadAccManager(Context context) {
        try {
            accountMap = loadAcc(context).getAccountMap();
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException ioe) {
            Log.e("login activity", "Can not read file: " + ioe.toString());
        } catch (ClassNotFoundException cne) {
            Log.e("login activity", "File contained unexpected data type: " + cne.toString());
        }
    }

    /**
     * Write the account manager itself into local storage.
     *
     * @param context the current context
     */
    public void writeAccManager(Context context) {
        try {
            ObjectOutputStream os =
                    new ObjectOutputStream(context.openFileOutput
                            (LoginActivity.ACC_INFO, MODE_PRIVATE));
            os.writeObject(this);
            os.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * Add score of a user based on how many moves he/she made.
     *
     * @param moves number of moves user made.
     * @param board board user is playing on.
     */
    void addScore(int moves, Board board) {
        int score = accountMap.get(LoginActivity.currentUser).getScores().get(getCurrentGame());
        if (1000 * board.numTiles() / moves > score && moves != 1) {
            accountMap.get(LoginActivity.currentUser).setScore(getCurrentGame(),
                    1000 * board.numTiles() / moves);
        }
    }

    /**
     * Return number of undo times user chose to have.
     *
     * @param c the current context
     * @return number of undo times user chose to have.
     */
    int getUserUndoTime(Context c) {
        loadAccManager(c);
        return accountMap.get(LoginActivity.currentUser).getMaxUndo();
    }

    /**
     * Update the number of undo times user chose to have.
     *
     * @param undoTime numbers of undo
     */
    public void updateUndoTime(int undoTime) {
        accountMap.get(LoginActivity.currentUser).setMaxUndo(undoTime);
    }

}
