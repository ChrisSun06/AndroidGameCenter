package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard5x5 extends ScoreBoard {



    /**
     * Return the view for the 5x5 ScoreBoard
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String gametype = "5X5sliding";
        ArrayList<UserAccount> tempAccList = super.loadAllAccountInfo();
        ArrayList<UserAccount> accList = super.sortingAccountsScores(gametype, tempAccList);
        ArrayList<String> accountNames = super.accountsGetNamesScores(accList, gametype).get(0);
        ArrayList<String> accountScores = super.accountsGetNamesScores(accList, gametype).get(1);
        View rootView = inflater.inflate(R.layout.score_board_5x5, container, false);


        castToView(accountNames, true, rootView);
        castToView(accountScores, false, rootView);

        return rootView;
    }


    /**
     * Either cast the ArrayList lis to the  name listView or the score listView based on isName
     */
    @Override
    public void castToView (ArrayList<String> lis, boolean isName, View rootview){
        if(isName) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    lis);
            ListView list = rootview.findViewById(R.id.by5listViewScores);
            list.setAdapter(adapter);
        }
        else{
            ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1,
                    lis);
            ListView list = rootview.findViewById(R.id.by5listViewScores);
            list.setAdapter(adapter);
        }
    }
}
