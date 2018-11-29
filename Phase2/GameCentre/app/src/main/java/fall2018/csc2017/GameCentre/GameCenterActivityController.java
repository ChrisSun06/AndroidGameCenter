package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;

import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileFragment;
import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileGameActivity;
import fall2018.csc2017.GameCentre.Sudoku.SudokuFragment;
import fall2018.csc2017.GameCentre.Sudoku.SudokuGameActivity;
import fall2018.csc2017.GameCentre.The2048.The2048Fragment;
import fall2018.csc2017.GameCentre.The2048.The2048GameActivity;

public class GameCenterActivityController {

    GameCenterButtonFragment switchFragment(String game) {
        if (game == null) {return null;}
        else {
            switch (game) {
                case GameSelectionActivity.GameSlidingTile:
                    GameCenterButtonFragment fragment0 = new SlidingTileFragment();
                    return fragment0;
                case GameSelectionActivity.Game2048:
                    GameCenterButtonFragment fragment1 = new The2048Fragment();
                    return fragment1;
                case GameSelectionActivity.GameSudoku:
                    GameCenterButtonFragment fragment2 = new SudokuFragment();
                    return fragment2;
                default:
                    GameCenterButtonFragment fragment4 = new SlidingTileFragment();
                    return fragment4;
            }
        }
    }

    void startGame(String game, Context context, AbstractBoardManager manager){
        if (game != null && manager != null){
            switch (game) {
                case "sliding":
                    context.startActivity(new Intent(context, SlidingTileGameActivity.class));
                    break;
                case "Sudoku":
                    context.startActivity(new Intent(context, SudokuGameActivity.class));
                    break;
                case "2048":
                    context.startActivity(new Intent(context, The2048GameActivity.class));
                    break;
                default:
                    break;
            }
        }
    }
}
