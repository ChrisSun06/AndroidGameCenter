package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.GameCentre.Utility.FileSaver;


public class GameScoresFragment extends Fragment implements ScoresBoardFragmentInterface{

    private String gameType;
    TextView gameName;

    /**
     * Return the view for the ScoreBoard
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        UserAccManager userAccManager = (UserAccManager) FileSaver.
                loadFromFile(getActivity(), LoginActivity.ACC_INFO);
        Scores scores = new Scores(gameType, userAccManager);
        View rootView = inflater.inflate(R.layout.fragment_game_scores, container, false);

        //ScoreBoardActivity activity = (ScoreBoardActivity) getActivity();

        gameName = rootView.findViewById(R.id.gametype);
        gameName.setText(gameType);

        castNameToView((ArrayList<String>) scores.getAccountNames(), rootView);
        castScoresToView((ArrayList<String>) scores.getAccountScores(), rootView);

        return rootView;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }


    /**
     * It will cast the ArrayList lis to the  name listView or the score listView
     */
    public void castNameToView(ArrayList<String> lis, View rootview){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, lis);
        ListView list = rootview.findViewById(R.id.gameViewName);
        list.setAdapter(adapter);
    }

    public void castScoresToView (ArrayList<String> lis, View rootview) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, lis);
        ListView list = rootview.findViewById(R.id.gameViewScores);
        list.setAdapter(adapter);
    }


}

