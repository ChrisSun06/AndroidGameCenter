package fall2018.csc2017.GameCentre;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Strategies.SlidingTileStrategy;

public class GameActivityOverController {


    void startOverControl(AbstractBoardManager boardManager, Context context, ScoringStrategy
            strategy, String game){
        if (boardManager.puzzleSolved()){
            Intent i = new Intent(context, GameOverActivity.class);
            i.putExtra("GAME", game);
            i.putExtra(GameOverActivity.GameOverMessageName,
                    "Your Score is: " + strategy.getScore());
            context.startActivity(i);
        }
    }
}
