package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Observable;
import java.util.Observer;

/**
 * The game activity.
 */
public class GameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private BoardManager boardManager;

    private String currentGame;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The buttons to display.
     */
    private HashMap<String, BoardManager> gameStateMap;

    /**
     * The user account manager
     */
    private UserAccManager userAccManager;

    // Grid View and calculated column height and width based on device size
    private SlidingTileGestureDetectGridView gridView;
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
        currentGame = getIntent().getExtras().getString("currentGame");
        makeToastSavedText();
        userAccManager = (UserAccManager) FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        userAccManager.setCurrentGame(currentGame);
        boardManager = (BoardManager) FileSaver.loadFromFile(getApplicationContext(),
                GameCenterActivity.TEMP_SAVE_FILENAME);
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
     *
     */
    private void setCurrentGameName(){
        String gridSize = String.valueOf(boardManager.getBoard().getNumCols());
        currentGame = gridSize + "X" + gridSize + currentGame;
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
        FileSaver.saveToFile(getApplicationContext(), userAccManager,
                LoginActivity.ACC_INFO);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
    }

    /**
     * Save the user's game state according to the current game, to local storage.
     */
    private void saveToFile(){
        userAccManager.setCurrentGameState(boardManager);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
    }

    /**
     * Update the score of the current user if puzzle is solved.
     */
    public void onSolved(){
        if (boardManager.puzzleSolved()){
            userAccManager.addScore(boardManager.getBoard().getNumOfMoves()+1,
                    boardManager.getBoard());
            FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
        }
    }

    /**
     * Save the game state and account state when game activity is about to close.
     */
    @Override
    protected void onStop(){
        super.onStop();
        saveToFile();
    }


    @Override
    public void update(Observable o, Object arg) {
        saveToFile();
        display();
        onSolved();
    }

    private void makeToastSavedText() {
        Toast.makeText(this, currentGame, Toast.LENGTH_SHORT).show();
    }
}
