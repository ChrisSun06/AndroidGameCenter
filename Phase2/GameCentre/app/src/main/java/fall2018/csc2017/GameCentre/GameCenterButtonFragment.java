package fall2018.csc2017.GameCentre;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public abstract class GameCenterButtonFragment extends Fragment {

    //AbstractBoardManager boardManager;


    public GameCenterButtonFragment() {
        // Required empty public constructor
    }

    abstract void activateGame();

    /**
     * Switch to the GameActivity view to play the game.
     */
    abstract void switchToGame();


    @Override
    public abstract View onCreateView(LayoutInflater inflater, ViewGroup container,
                                      Bundle savedInstanceState);

}