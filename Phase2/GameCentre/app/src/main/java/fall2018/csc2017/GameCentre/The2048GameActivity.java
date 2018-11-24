package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;
import android.widget.Button;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class The2048GameActivity extends AppCompatActivity implements Observer{

    /**
     * The board manager.
     */
    private The2048BoardManager boardManager;

    /**
     * The current sliding tile game name.
     */
    private String currentGame;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The user account manager
     */
    private UserAccManager userAccManager;

    // Grid View and calculated column height and width based on device size
    private The2048GestureDetectGridView gridView;

    private static int columnWidth, columnHeight;

    /**
     * Set up the background image for each button based on the master list
     * of positions, and then call the adapter to set the view.
     */
    // Display
    //todo: not sure how to display() --- Jin
    public void display() {
        updateTileButtons();
        gridView.setAdapter(new CustomAdapter(tileButtons, 4, 4));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        boardManager = (The2048BoardManager) FileSaver.loadFromFile(getApplicationContext(),
                GameCenterActivity.TEMP_SAVE_FILENAME);
        setUpBoard();
        createTileButtons(this);
        setContentView(R.layout.activity_the2048_game);
        // Add View to activity

        gridView = findViewById(R.id.grid);
        //gridView.setNumColumns(boardManager.getBoard().getNumCols());
        gridView.setBoardManager(boardManager);
        boardManager.getBoard().addObserver(this);
        // Observer sets up desired dimensions as well as calls our display function
        //TODO this has to be changed
        gridView.getViewTreeObserver().addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        gridView.getViewTreeObserver().removeOnGlobalLayoutListener(
                                this);
                        int displayWidth = gridView.getMeasuredWidth();
                        int displayHeight = gridView.getMeasuredHeight();

                        columnWidth = displayWidth / boardManager.getBoard().numCols;
                        columnHeight = displayHeight / boardManager.getBoard().numRows;

                        display();
                    }
                });
    }

    /**
     * Set up the board and account manager.
     */
    private void setUpBoard() {
        userAccManager = (UserAccManager) FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        userAccManager.setCurrentGame(currentGame);
    }

    /**
     * create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    //todo: needs to rewrite into a 2048 version
    private void createTileButtons(Context context) {
        The2048Board the2048Board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().numRows; row++) {
            for (int col = 0; col != boardManager.getBoard().numCols; col++) {
                Button tmp = new Button(context);
                //tmp.setBackground(Drawable.createFromPath(the2048Board.getTile(row, col).getBackground()));
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    //todo: update 2048 tile buttons
    private void updateTileButtons() {
        The2048Board the2048Board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / The2048Board.numRows;
            int col = nextPos % The2048Board.numCols;
            //b.setBackground(Drawable.createFromPath(the2048Board.getTile(row, col).getBackground()));
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
        //todo: currently userAccManager add score can only be apply to SlidinTile, needs to change
        if (boardManager.puzzleSolved()){
            //userAccManager.addScore(boardManager.getBoard().getNumOfMoves()+1,
              //      boardManager.getBoard());
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
}