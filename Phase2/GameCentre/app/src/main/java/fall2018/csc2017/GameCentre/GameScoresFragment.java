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

/**The score fragment that takes care of displaying user's score.**/
public class GameScoresFragment extends Fragment implements ScoresBoardFragmentInterface{

    /**
     * The game type
     */
    private String gameType;

    /**
     * The game name.
     */
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
        gameName = rootView.findViewById(R.id.gametype);
        gameName.setText(gameType);
        castNameToView((ArrayList<String>) scores.getAccountNames(), rootView);
        castScoresToView((ArrayList<String>) scores.getAccountScores(), rootView);
        return rootView;
    }

    /**
     * Set the game type.
     * @param gameType the game type.
     */
    public void setGameType(String gameType) {
        this.gameType = gameType;
    }


    /**
     * It will cast the ArrayList listName to the  name listView or the score listView
     * @param listName list of user names.
     * @param rootview root view.
     */
    public void castNameToView(ArrayList<String> listName, View rootview){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, listName);
        ListView list = rootview.findViewById(R.id.gameViewName);
        list.setAdapter(adapter);
    }

    /**
     *
     * @param listScores
     * @param rootview
     */
    public void castScoresToView (ArrayList<String> listScores, View rootview) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, listScores);
        ListView list = rootview.findViewById(R.id.gameViewScores);
        list.setAdapter(adapter);
    }


}

