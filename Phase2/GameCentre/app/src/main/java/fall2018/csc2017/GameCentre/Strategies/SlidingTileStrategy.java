package fall2018.csc2017.GameCentre.Strategies;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.SlidingTileBoard;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

public class SlidingTileStrategy implements ScoringStrategy {

    private Map<String, UserAccount> accountMap;

    private String currentUser;

    private String currentGame;

    public SlidingTileStrategy(UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = accManager.getCurrentGame();
    }

    public void addScore(int moves, AbstractBoard board) {
        int score = accountMap.get(currentUser).getScores().get(currentGame);
        SlidingTileBoard tempBoard = (SlidingTileBoard) board;
        if (1000 * tempBoard.numTiles() / moves > score && moves != 1) {
            accountMap.get(currentUser).setScore(currentGame,
                    1000 * tempBoard.numTiles() / moves);
        }
    }
}
