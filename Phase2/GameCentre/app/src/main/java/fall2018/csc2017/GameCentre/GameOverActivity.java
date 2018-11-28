package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class GameOverActivity extends AppCompatActivity {

    static final String GameOverMessageName = "GameOverMessage";
    private String current_game;
    private String displayMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_over);
        current_game = getIntent().getStringExtra("GAME");
        displayMessage = getIntent().getStringExtra(GameOverMessageName);
        displayMessage();
        //gController = new GameOverController();
        addGameCenterButtonListener();
    }

    /**
     * Go back to game center Button.
     */
    private void addGameCenterButtonListener() {
        Button gameCenterButton = findViewById(R.id.goBackGameCenter);
        gameCenterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(GameOverActivity.this,
                        GameCenterActivity.class);
                i.putExtra("GAME", current_game);
                startActivity(i);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }

    private void displayMessage(){
        TextView msg= (TextView) findViewById(R.id.displayMsg);
        msg.setText(displayMessage);
    }

}
