package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

public class ScoreBoardPublicActivity extends AppCompatActivity {

    private ViewPager mSlideViewPager;
    private ScoreAdapter selectionAdapter;
    private String currentGame;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_score_board_public);

        mSlideViewPager = (ViewPager) findViewById(R.id.scorePage);

        selectionAdapter = new ScoreAdapter(this);

        mSlideViewPager.setAdapter(selectionAdapter);
        currentGame = getIntent().getStringExtra("GAME");
        backGameCenter();
    }

    private void backGameCenter(){
        ImageButton logOutButton =findViewById(R.id.backGameCenter);
        logOutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(ScoreBoardPublicActivity.this,
                        GameCenterActivity.class);
                i.putExtra("GAME", currentGame);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inleft, R.anim.slide_outright);
            }
        });
    }
}
