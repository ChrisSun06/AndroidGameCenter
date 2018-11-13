package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The buttons to display.
     */
    private HashMap<String, BoardManager> gameStateMap;

    // Grid View and calculated column height and width based on device size
    private GestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, columnWidth, columnHeight));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromFile(GameCenterActivity.TEMP_SAVE_FILENAME);
        createTileButtons(this);
        setContentView(R.layout.activity_main);

        // Add View to activity
        gridView = findViewById(R.id.grid);
        gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().getNumCols();
                        columnHeight = displayHeight / boardManager.getBoard().getNumRows();

                        display();
                    }
                });
    }

    /**
     * create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        Board board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                Button tmp = new Button(context);
                tmp.setBackground(Drawable.createFromPath(board.getTile(row, col).getBackground()));
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        Board board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            b.setBackground(Drawable.createFromPath(board.getTile(row, col).getBackground()));
            nextPos++;
        }
    }

    /**
     * Dispatch onPause() to fragments.
     */
    @Override
    protected void onPause() {
        super.onPause();
        LoginActivity.accManager.writeAccManager(getApplicationContext());
        saveToFile(GameCenterActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Load the board manager from fileName.
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
     * Update the score of the current user if puzzle is solved.
     */
    public void onSolved(){
        if (boardManager.puzzleSolved()){
            LoginActivity.accManager.addScore(boardManager.getBoard().getNumOfMoves()+1,
                    boardManager.getBoard());
        }
    }

    /**
     * Save the game state and account state when game activity is about to close.
     */
    @Override
    protected void onStop(){
        super.onStop();
        LoginActivity.accManager.writeAccManager(getApplicationContext());
        saveToFile(GameCenterActivity.SAVE_FILENAME);
    }

    /**
     * load the game state.
     *
     * @param inputStream the file input stream
     *
     * @throws IOException in/output exception
     * @throws ClassNotFoundException class not found exception
     */
    private void loadGameState(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(inputStream);
        gameStateMap = (HashMap<String, BoardManager>) input.readObject();
        if (gameStateMap.containsKey(LoginActivity.currentUser)){
            boardManager = gameStateMap.get(LoginActivity.currentUser);
            boardManager.getBoard().setMaxUndoTime(boardManager.getBoard().getMaxUndoTime());
        } else {
            Toast.makeText(this, "Game saves not found!", Toast.LENGTH_LONG).show();
        }
        inputStream.close();
    }


    /**
     * Save the board manager to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        gameStateMap.put(LoginActivity.currentUser, boardManager);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(gameStateMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }

    @Override
    public void update(Observable o, Object arg) {
        saveToFile(GameCenterActivity.SAVE_FILENAME);
        display();
        onSolved();
    }
}
