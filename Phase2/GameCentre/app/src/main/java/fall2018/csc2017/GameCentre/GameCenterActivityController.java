package fall2018.csc2017.GameCentre;

import android.app.Activity;
import android.support.v4.app.Fragment;

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

    Class<? extends Activity> getIntent(String game){
        if (game == null){
            return GameCenterActivity.class;
        } else {
            switch (game) {
                case "sliding":
                    return SlidingTileGameActivity.class;
                case "Sudoku":
                    return SudokuGameActivity.class;
                case "2048":
                    return The2048GameActivity.class;
                default:
                    return GameCenterActivity.class;
            }
        }
    }
}
