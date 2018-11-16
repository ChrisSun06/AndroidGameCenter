package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;



public class SlidingTile4x4ScoresFragment extends Fragment implements ScoresBoardFragmentInterface{
    /**
     * Return the view for the 4x4 ScoreBoard
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Scores scores = new Scores("4X4sliding");
        View rootView = inflater.inflate(R.layout.fragment_sliding_tile4x4_scores, container, false);


        castNameScoresToView(scores.getAccountNames(), true, rootView);
        castNameScoresToView(scores.getAccountScores(), false, rootView);

        return rootView;
    }


    /**
     * It will cast the ArrayList lis to the  name listView or the score listView
     */
    @Override
    public void castNameScoresToView (ArrayList<String> lis, boolean isName, View rootview){
        if(isName) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    lis);
            ListView list = rootview.findViewById(R.id.by4listViewName);
            list.setAdapter(adapter);
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    lis);
            ListView list = rootview.findViewById(R.id.by4listViewScores);
            list.setAdapter(adapter);
        }
    }

}
