package fall2018.csc2017.GameCentre.Strategies;

import fall2018.csc2017.GameCentre.AbstractBoard;

public interface ScoringStrategy {

    void addScore(int moves, AbstractBoard board);
}
