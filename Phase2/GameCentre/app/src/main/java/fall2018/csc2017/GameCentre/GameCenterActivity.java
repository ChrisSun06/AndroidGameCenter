package fall2018.csc2017.GameCentre;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import fall2018.csc2017.GameCentre.SlidingTile.SlidingTileGameSettings;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class GameCenterActivity extends AppCompatActivity implements FragmentBasedInterface{

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board manager.
     */
    private AbstractBoardManager boardManager;

    /**
     * Current Game
     */
    private String currentGame;

    /**
     * User account manager
     */
    private UserAccManager userAccManager;

    private GameCenterActivityController gController;



    /**
     * Activate all the Buttons in game center and set up the board,
     * loadFile, and saveFile
     *
     * @param savedInstanceState current state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gController = new GameCenterActivityController();
        currentGameInfoSetup();

        setContentView(R.layout.activity_game_center);

        changeFragment(currentGame);

        goToScoreBoard();
        addLoadButtonListener();
        addSaveButtonListener();
        addSettingButtonListener();
        addBackButtonListener();
    }

    private void currentGameInfoSetup(){
        currentGame = getIntent().getStringExtra("GAME");
        userAccManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
    }

    public void changeFragment(String game){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        transaction.replace(R.id.gameButtonFrame, gController.switchFragment(currentGame));
        transaction.commit();
    }

    /**
     * Activate the start button.
     */
    private void addSettingButtonListener() {
        Button startButton = findViewById(R.id.SettingButton);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settings = new Intent(GameCenterActivity.this,
                        SlidingTileGameSettings.class);
                settings.putExtra("accManager", userAccManager);
                startActivity(settings);
            }
        });
    }

    /**
     * Activate the load button.
     */
    private void addLoadButtonListener() {
        Button loadButton = findViewById(R.id.LoadButton);
        loadButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile();
                FileSaver.saveToFile(getApplicationContext(), boardManager, TEMP_SAVE_FILENAME);
                switchToLoadedGame();
            }
        });
    }


    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAccManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(),
                        LoginActivity.ACC_INFO);
                saveToFile();
                FileSaver.saveToFile(getApplicationContext(), boardManager, TEMP_SAVE_FILENAME);
                makeToastSavedText();
            }
        });
    }

    /**
     * Activate the back button
     */
    private void addBackButtonListener(){
        Button backButton = findViewById(R.id.Back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GameCenterActivity.this,
                        GameSelectionActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * Display that a game was saved successfully.
     */
    private void makeToastSavedText() {
        Toast.makeText(this, "Game Saved", Toast.LENGTH_SHORT).show();
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToLoadedGame() {
        //from loaded slot, activate the game by current_game
        saveToFile();
        gController.startGame(currentGame, getApplicationContext(), boardManager);
    }

    /**
     * Load the game state from file.
     */
    private void loadFromFile() {
        userAccManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        boardManager = userAccManager.getCurrentGameStateMap(currentGame);
        userAccManager.makeToastGameState(getApplicationContext());
    }

    /**
     * Save the game state to fileName.
     */
    public void saveToFile() {
        boardManager = (AbstractBoardManager)FileSaver.loadFromFile(getApplicationContext(),
                TEMP_SAVE_FILENAME);
        userAccManager.setCurrentGameState(boardManager);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
    }

    /**
     * Activate button to go to score board activity.
     */
    private void goToScoreBoard(){
        final ImageButton scoreboard = findViewById(R.id.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent scoreBoard = new Intent(GameCenterActivity.this,
                        ScoreBoardActivity.class);
                startActivity(scoreBoard);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }


}
