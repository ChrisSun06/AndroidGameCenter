package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * A serializable user account.
 */
public class UserAccount implements Serializable {

    public static final String[] games = {"3X3sliding", "4X4sliding", "5X5sliding", "Sudoku", "2048"};

    /**
     * The username of user.
     */
    private String name;

    /**
     * The password of user.
     */
    private String password;

    /**
     * The maximum number of undo user choose.
     */
    private int maxUndo = 3;

    /**
     * The HashMap of game map to user's corresponding score for each game.
     */
    private HashMap<String, Integer> scores;

    /**
     * Return the HashMap of the scores of the user.
     *
     * @return scores
     */
    HashMap<String, Integer> getScores(){
        return scores;
    }

    /**
     * Return the password of the user.
     *
     * @return password
     */
    String getPassword() {
        return this.password;
    }

    /**
     * Set the max number of undo of this user.
     */
    void setMaxUndo(int maxUndo) {
        this.maxUndo = maxUndo;
    }

    /**
     * Get the max number of undo of this user.
     */
    int getMaxUndo() {
        return this.maxUndo;
    }

    /**
     * Get the username of this user.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Get the score of the user for certain game type.
     *
     * @param gameType type of the game
     */
    Integer getScores(String gameType) {
        return scores.get(gameType);
    }

    /**
     * Initialize a new user with username and password.
     *
     * @param n username
     * @param p password
     */
    UserAccount(String n, String p) {
        this.name = n;
        this.password = p;
        this.scores = new HashMap<>();

        //setting all scores to -1
        for (String game : games) {
            scores.put(game, -1);
        }
    }

    /**
     * Set the score of this user of current game.
     *
     * @param currentGame current game
     * @param score score for this game
     */
    void setScore(String currentGame, int score) {
        scores.put(currentGame, score);
    }

}
