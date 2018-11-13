package fall2018.csc2017.GameCentre;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoard4x4 extends ScoreBoard {



    /**
     * Return the view for the 4x4 ScoreBoard
     *
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String gametype = "4X4sliding";
        ArrayList<UserAccount> tempAccList = super.loadAllAccountInfo();
        ArrayList<UserAccount> accList = super.sortingAccountsScores(gametype, tempAccList);
        ArrayList<String> accountNames = super.accountsGetNamesScores(accList, gametype).get(0);
        ArrayList<String> accountScores = super.accountsGetNamesScores(accList, gametype).get(1);
        View rootView = inflater.inflate(R.layout.score_board_4x4, container, false);


        castToView(accountNames, true, rootView);
        castToView(accountScores, false, rootView);

        return rootView;
    }


    /**
     * It will cast the ArrayList lis to the  name listView or the score listView
     */
    @Override
    public void castToView (ArrayList<String> lis, boolean isName, View rootview){
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

