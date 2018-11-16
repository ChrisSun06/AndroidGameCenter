package fall2018.csc2017.GameCentre;

import android.view.View;

import java.util.ArrayList;

interface ScoresBoardFragmentInterface {
    void castNameScoresToView (ArrayList<String> lis, boolean isName, View rootview);
}
