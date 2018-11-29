package fall2018.csc2017.GameCentre.Strategies;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

public class SudokuStrategy implements ScoringStrategy{

    private Map<String, UserAccount> accountMap;

    private String currentUser;

    private String currentGame;

    private int score;

    /**
     * Set up the strategy for Sudoku
     *
     * @param accManager
     */
    public SudokuStrategy(UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = accManager.getCurrentGame();
    }

    @Override
    public void addScore(int moves, AbstractBoard board) {
        score = accountMap.get(currentUser).getScores().get(currentGame) + 1;
        accountMap.get(currentUser).setScore(currentGame, score);
    }

    @Override
    public int getScore(){
        return score;
    }
}
