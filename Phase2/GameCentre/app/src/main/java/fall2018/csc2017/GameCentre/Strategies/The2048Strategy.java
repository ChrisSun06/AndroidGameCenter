package fall2018.csc2017.GameCentre.Strategies;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.The2048Board;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

public class The2048Strategy implements ScoringStrategy {
    private Map<String, UserAccount> accountMap;

    private String currentUser;

    private String currentGame;

    private int score;

    public The2048Strategy(UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = accManager.getCurrentGame();
    }

    public void addScore(int moves, AbstractBoard board) {
        The2048Board tempBoard = (The2048Board) board;
        score = tempBoard.getScore();
        accountMap.get(currentUser).setScore(currentGame, score);
    }

    public int getScore(){
        return score;
    }
}
