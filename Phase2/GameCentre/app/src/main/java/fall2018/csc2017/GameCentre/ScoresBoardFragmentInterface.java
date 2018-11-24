package fall2018.csc2017.GameCentre;

import android.view.View;

import java.util.ArrayList;

interface ScoresBoardFragmentInterface {
    void castNameToView (ArrayList<String> lis, View rootview);

    void castScoresToView (ArrayList<String> lis, View rootview);
}
