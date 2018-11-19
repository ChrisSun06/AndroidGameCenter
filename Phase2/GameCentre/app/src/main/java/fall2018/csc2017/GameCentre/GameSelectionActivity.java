package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class GameSelectionActivity extends AppCompatActivity {

    //private String slidingtilegame;

    public static final String GameSudoku = "sudoku";
    public static final String GameSlidingTile = "slidingtile";
    public static final String Game2048 = "2048";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_selection);

        goToSlidingTile();
        goToSudoku();
        goTo2048();
    }

    private void goToSlidingTile(){
        Button slidingTileButton = findViewById(R.id.SlidingGameCenter);
        slidingTileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GameSelectionActivity.this, GameCenterActivity.class);
                i.putExtra("GAME", GameSlidingTile);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }

    private void goToSudoku(){
        Button SudokuButton = findViewById(R.id.SudokuGameCenter);
        SudokuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GameSelectionActivity.this, GameCenterActivity.class);
                i.putExtra("GAME", GameSudoku);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }

    private void goTo2048(){
        final Button Game2048Button = findViewById(R.id.The2048GameCenter);
        Game2048Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(GameSelectionActivity.this, GameCenterActivity.class);
                i.putExtra("GAME", Game2048);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }

}