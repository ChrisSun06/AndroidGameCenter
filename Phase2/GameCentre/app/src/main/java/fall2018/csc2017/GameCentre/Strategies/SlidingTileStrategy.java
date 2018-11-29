package fall2018.csc2017.GameCentre.Strategies;

import java.util.Map;

import fall2018.csc2017.GameCentre.AbstractBoard;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileBoard;
import fall2018.csc2017.GameCentre.UserAccManager;
import fall2018.csc2017.GameCentre.UserAccount;

public class SlidingTileStrategy implements ScoringStrategy {

    private Map<String, UserAccount> accountMap;

    private String currentUser;

    private String currentGame;

    private int currScore;

    private int numMoves;
    private SlidingTileBoard tempBoard;

    public SlidingTileStrategy(UserAccManager accManager) {
        this.accountMap = accManager.getAccountMap();
        this.currentUser = accManager.getCurrentUser();
        this.currentGame = accManager.getCurrentGame();
    }

    public void addScore(int moves, AbstractBoard board) {
        int score = accountMap.get(currentUser).getScores().get(currentGame);
        numMoves = moves;
        tempBoard = (SlidingTileBoard) board;
        tempBoard.setNumRows(board.getNumRows());
        tempBoard.setNumCols(board.getNumCols());
        if (getScore()/ moves > score && moves != 1) {
            accountMap.get(currentUser).setScore(currentGame,
                    currScore);
        }
    }

    public int getScore(){
        currScore =  1000 * tempBoard.numTiles() / numMoves;
        return currScore;
    }
}

