package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import fall2018.csc2017.GameCentre.Utility.FileSaver;
import pl.droidsonroids.gif.GifImageButton;

class ScoreAdapter extends PagerAdapter{
    Context context;
    LayoutInflater layoutInflater;
    String gameName;

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public ScoreAdapter(Context context){
        this.context = context;
    }

    String[] games = UserAccount.GAMES;

    @Override
    public int getCount(){return UserAccount.GAMES.length;}

    @Override
    public boolean isViewFromObject(View view, Object o){
        return view == (ConstraintLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.score_selection_pager, container, false);
        UserAccManager userAccManager = (UserAccManager) FileSaver.
                loadFromFile(context, LoginActivity.ACC_INFO);
        Scores scores = new Scores(games[position], userAccManager);
        TextView slideHeader = (TextView) view.findViewById(R.id.gametype);
        slideHeader.setText(games[position]);
        castNameToView((ArrayList<String>) scores.getAccountNames(), view);
        castScoresToView((ArrayList<String>) scores.getAccountScores(), view);

        container.addView(view);

        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView((ConstraintLayout)object);
    }

    /**
     * It will cast the ArrayList listName to the  name listView or the score listView
     * @param listName list of user names.
     */
    public void castNameToView(ArrayList<String> listName, View view){
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, listName);
        ListView list = view.findViewById(R.id.gameViewName);
        list.setAdapter(adapter);
    }

    /**
     *
     * @param listScores
     */
    public void castScoresToView (ArrayList<String> listScores, View view) {
        ArrayAdapter<String> adapter = new ArrayAdapter<>(context,
                android.R.layout.simple_list_item_1, listScores);
        ListView list = view.findViewById(R.id.gameViewScores);
        list.setAdapter(adapter);
    }

}
