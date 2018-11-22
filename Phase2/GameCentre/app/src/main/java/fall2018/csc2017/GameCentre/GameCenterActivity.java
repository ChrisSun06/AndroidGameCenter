package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class GameCenterActivity extends ImageOperationActivity {

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * Current Game
     */
    private String currentGame;

    /**
     * User account manager
     */
    private UserAccManager userAccManager;



    /**
     * Activate all the Buttons in game center and set up the board,
     * loadFile, and saveFile
     *
     * @param savedInstanceState current state
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = new BoardManager(4);
        Intent b = getIntent();
        currentGame = b.getStringExtra("GAME");
        userAccManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);

        FileSaver.saveToFile(getApplicationContext(), boardManager, TEMP_SAVE_FILENAME);
        setupDefault();

        setContentView(R.layout.activity_game_center);

        changeFragment2(currentGame);

        goToScoreBoard();
        addLoadButtonListener();
        addSaveButtonListener();
    }



    private void changeFragment2(String game){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (game) {
            case GameSelectionActivity.GameSlidingTile:
                Fragment fragment0 = new SlidingTileFragment();
                ((SlidingTileFragment) fragment0).setCurrentGame(game);
                transaction.replace(R.id.gameButtonFrame, fragment0);
                break;

            case GameSelectionActivity.Game2048:
                Fragment fragment1 = new The2048Fragment();
                transaction.replace(R.id.gameButtonFrame, fragment1);
                break;

            case GameSelectionActivity.GameSudoku:
                Fragment fragment2 = new SudokuFragment();
                transaction.replace(R.id.gameButtonFrame, fragment2);
                break;

            default:
                break;
        }

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
                        InGameSettings.class);
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
                makeToastLoadedText();
                switchToGame();
            }
        });
    }

    /**
     * Display that a game was loaded successfully.
     */
    private void makeToastLoadedText() {
        Toast.makeText(this, "Loaded Game", Toast.LENGTH_SHORT).show();
    }

    /**
     * Activate the save button.
     */
    private void addSaveButtonListener() {
        Button saveButton = findViewById(R.id.SaveButton);
        userAccManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveToFile();
                FileSaver.saveToFile(getApplicationContext(), boardManager, TEMP_SAVE_FILENAME);
                makeToastSavedText();
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
     * Read the temporary board from disk.
     */
    @Override
    protected void onResume() {
        super.onResume();
        FileSaver.loadFromFile(getApplicationContext(), TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        tmp.putExtra("accManager", userAccManager);
        tmp.putExtra("currentGame", currentGame);
        saveToFile();
        startActivity(tmp);
    }

    /**
     * Load the game state from fileName.
     *
     */
    private void loadFromFile() {
        userAccManager = (UserAccManager)FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        if (userAccManager != null) {
            boardManager = userAccManager.getCurrentGameStateMap(currentGame);
        }
    }

    /**
     * Save the game state to fileName.
     *
     */
    public void saveToFile() {
        boardManager = (BoardManager)FileSaver.loadFromFile(getApplicationContext(),
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
                scoreBoard.putExtra("accManager", userAccManager);
                startActivity(scoreBoard);
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }


}
