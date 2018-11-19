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



public class GameScoresFragment extends Fragment implements ScoresBoardFragmentInterface{

    private String game_type;
    TextView game_name;

    /**
     * Return the view for the 3x3 ScoreBoard
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Scores scores = new Scores(game_type);
        View rootView = inflater.inflate(R.layout.fragment_game_scores, container, false);

        //ScoreBoardActivity activity = (ScoreBoardActivity) getActivity();

        game_name = rootView.findViewById(R.id.gametype);
        game_name.setText(game_type);

        castNameScoresToView(scores.getAccountNames(), true, rootView);
        castNameScoresToView(scores.getAccountScores(), false, rootView);

        return rootView;
    }

    public void setGame_type(String game_type) {
        this.game_type = game_type;
    }

    /**
     * It will cast the ArrayList lis to the  name listView or the score listView
     */
    public void castNameScoresToView (ArrayList<String> lis, boolean isName, View rootview){
        if(isName) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    lis);
            ListView list = rootview.findViewById(R.id.gameViewName);
            list.setAdapter(adapter);
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    lis);
            ListView list = rootview.findViewById(R.id.gameViewScores);
            list.setAdapter(adapter);
        }
    }



}
