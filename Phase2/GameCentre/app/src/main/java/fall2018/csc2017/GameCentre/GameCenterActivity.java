package fall2018.csc2017.GameCentre;

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

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

/**
 * The initial activity for the sliding puzzle tile game.
 */
public class GameCenterActivity extends ImageOperationActivity {

    /**
     * The main save file.
     */
    public static final String SAVE_FILENAME = "save_file.ser";

    /**
     * A temporary save file.
     */
    public static final String TEMP_SAVE_FILENAME = "save_file_tmp.ser";

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private HashMap<String, BoardManager> gameStateMap = new HashMap<>();

    /**
     * Current Game
     */
    static String CURRENT_GAME;


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
        CURRENT_GAME = b.getStringExtra("GAME");

        loadFromFile(SAVE_FILENAME);
        saveToFile(TEMP_SAVE_FILENAME);

        setupDefault();

        setContentView(R.layout.activity_game_center);

        changeFragment2(CURRENT_GAME);

        goToScoreBoard();

    }



    private void changeFragment2(String game){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        switch (game) {
            case GameSelectionActivity.GameSlidingTile:
                Fragment fragment0 = new SlidingTileFragment();
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
                startActivity(new Intent(GameCenterActivity.this, InGameSettings.class));
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
                loadFromFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
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
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFromFile(SAVE_FILENAME);
                saveToFile(SAVE_FILENAME);
                saveToFile(TEMP_SAVE_FILENAME);
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
        loadFromFile(TEMP_SAVE_FILENAME);
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(this, GameActivity.class);
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Load the game state from fileName.
     *
     * @param fileName the name of the file
     */
    private void loadFromFile(String fileName) {

        try {
            InputStream inputStream = this.openFileInput(fileName);
            if (inputStream != null) {
                loadGameState(inputStream);
            }
        } catch (FileNotFoundException e) {
            Log.e("login activity", "File not found: " + e.toString());
        } catch (IOException e) {
            Log.e("login activity", "Can not read file: " + e.toString());
        } catch (ClassNotFoundException e) {
            Log.e("login activity", "File contained unexpected data type: " + e.toString());
        }
    }

    /**
     * Load the game state from inputStream.
     *
     * @param inputStream the file input stream
     * @throws IOException in/output exception
     * @throws ClassNotFoundException class not found exception
     */
    private void loadGameState(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(inputStream);
        gameStateMap = (HashMap<String, BoardManager>) input.readObject();
        if (gameStateMap.containsKey(UserAccManager.getInstance().getCurrentUser())){
            boardManager = gameStateMap.get(UserAccManager.getInstance().getCurrentUser());
            boardManager.getBoard().setMaxUndoTime(boardManager.getBoard().getMaxUndoTime());
        } else {
            Toast.makeText(this, "Game saves not found!", Toast.LENGTH_LONG).show();
        }
        inputStream.close();
    }

    /**
     * Save the game state to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        gameStateMap.put(UserAccManager.getInstance().getCurrentUser(), boardManager);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(gameStateMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    /**
     * Activate button to go to score board activity.
     */
    private void goToScoreBoard(){
        final ImageButton scoreboard = findViewById(R.id.scoreboard);
        scoreboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GameCenterActivity.this, ScoreBoardActivity.class));
                overridePendingTransition(R.anim.slide_inright, R.anim.slide_outleft);
            }
        });
    }


}
