package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
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

import fall2018.csc2017.GameCentre.Strategies.ScoringStrategy;
import fall2018.csc2017.GameCentre.Strategies.SudokuStrategy;
import fall2018.csc2017.GameCentre.tiles.Tile;

/**
 * The game activity.
 */
public class SudokuGameActivity extends AppCompatActivity implements Observer {

    /**
     * The board manager.
     */
    private SudokuBoardManager boardManager;

    /**
     * The buttons to display.
     */
    private ArrayList<Button> tileButtons;

    /**
     * The current sliding tile game name.
     */
    private String currentGame;

    /**
     * The buttons to display.
     */
    private HashMap<String, SudokuBoardManager> gameStateMap;

    // Grid View and calculated column height and width based on device size
    private SudokuGestureDetectGridView gridView;
    private static int columnWidth, columnHeight;

    /**
     * The user account manager
     */
    private UserAccManager userAccManager;

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
        boardManager = (SudokuBoardManager) FileSaver.loadFromFile(getApplicationContext(),
                GameCenterActivity.TEMP_SAVE_FILENAME);
        setUpBoard();
        createTileButtons(this);
        setContentView(R.layout.activity_sudoku_main);
        // Add View to activity
        gridView = findViewById(R.id.sudoku_grid);
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
     * Set up the board and account manager.
     */
    private void setUpBoard() {
        setCurrentGameName();
        userAccManager = (UserAccManager) FileSaver.loadFromFile(getApplicationContext(),
                LoginActivity.ACC_INFO);
        userAccManager.setCurrentGame(currentGame);
    }

    /**
     * A method that set the game name based on gridSize.
     */
    private void setCurrentGameName(){
        currentGame = "Sudoku";
    }

    /**
     * create the buttons for displaying the tiles.
     *
     * @param context the context
     */
    private void createTileButtons(Context context) {
        SudokuBoard board = boardManager.getBoard();
        tileButtons = new ArrayList<>();
        for (int row = 0; row != boardManager.getBoard().getNumRows(); row++) {
            for (int col = 0; col != boardManager.getBoard().getNumCols(); col++) {
                Button tmp = new Button(context);
                int position = board.getNumCols() * row + col + 1;
                // set up default background image
                Bitmap tile = BitmapFactory.decodeResource(getResources(),
                        Tile.FirstSudokuTileId + position - 1);
                tile = getUpdatedBitmap(board, row, col, tile);
                tmp.setBackground(new BitmapDrawable(getResources(), tile));
                this.tileButtons.add(tmp);
            }
        }
    }

    /**
     * Update the backgrounds on the buttons to match the tiles.
     */
    private void updateTileButtons() {
        SudokuBoard board = boardManager.getBoard();
        int nextPos = 0;
        for (Button b : tileButtons) {
            int row = nextPos / boardManager.getBoard().getNumRows();
            int col = nextPos % boardManager.getBoard().getNumCols();
            Bitmap tile = ((BitmapDrawable) b.getBackground()).getBitmap();
            tile = getUpdatedBitmap(board, row, col, tile);
            b.setBackground(new BitmapDrawable(getResources(), tile));
            nextPos++;
        }
    }

    private Bitmap getUpdatedBitmap(SudokuBoard board, int row, int col, Bitmap tile) {
        Bitmap temp = tile;
        if (!board.getTile(row, col).getIsMutable()) {
            Bitmap number = BitmapFactory.decodeResource(getResources(),
                    Tile.FirstSudokuNumberId + board.getTile(row, col).getNumber() - 1);
            temp = superpose(tile, number);
        }
        else {
            if (board.getTile(row, col).getNumber() != 0) {
                Bitmap number = BitmapFactory.decodeResource(getResources(),
                        Tile.FirstSudokuEditNumberId + board.getTile(row, col).getNumber() - 1);
                temp = superpose(tile, number);
            }
        }
        return temp;
    }

    /**
     * Superpose one bitmap above the centre of one another.
     *
     * @param back back image
     * @param front front image
     * @return the superposed image
     */
    private Bitmap superpose(Bitmap back, Bitmap front) {
        Bitmap superposed= Bitmap.createBitmap(back.getWidth(), back.getHeight(), back.getConfig());
        Canvas canvas = new Canvas(superposed);
        canvas.drawBitmap(back, new Matrix(), null);
        canvas.drawBitmap(front,
                (back.getWidth() - front.getWidth()) / 2,
                (back.getHeight() - front.getHeight()) / 2,
                null);
        return superposed;
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
        /*LoginActivity.accManager.writeAccManager(getApplicationContext());
        saveToFile(GameCenterActivity.TEMP_SAVE_FILENAME);*/
    }

    /**
     * Load the board manager from fileName.
     *
     * @param fileName the name of the file
     */
    /*private void loadFromFile(String fileName) {

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
    }*/

    /**
     * Update the score of the current user if puzzle is solved.
     */
    public void onSolved(){
        ScoringStrategy strategy = new SudokuStrategy(userAccManager);
        userAccManager.addScore(strategy, 0, boardManager);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
    }

    /**
     * Save the game state and account state when game activity is about to close.
     */
    @Override
    protected void onStop(){
        super.onStop();
        saveToFile();
        /*LoginActivity.accManager.writeAccManager(getApplicationContext());
        saveToFile(GameCenterActivity.SAVE_FILENAME);*/
    }

    /**
     * load the game state.
     *
     * @param inputStream the file input stream
     *
     * @throws IOException in/output exception
     * @throws ClassNotFoundException class not found exception
     */
    /*private void loadGameState(InputStream inputStream) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(inputStream);
        gameStateMap = (HashMap<String, SudokuBoardManager>) input.readObject();
        if (gameStateMap.containsKey(LoginActivity.currentUser)){
            boardManager = gameStateMap.get(LoginActivity.currentUser);
            // boardManager.getBoard().setMaxUndoTime(boardManager.getBoard().getMaxUndoTime());
        } else {
            Toast.makeText(this, "Game saves not found!", Toast.LENGTH_LONG).show();
        }
        inputStream.close();
    }*/


    /**
     * Save the board manager to fileName.
     */
    public void saveToFile() {
        userAccManager.setCurrentGameState(boardManager);
        FileSaver.saveToFile(getApplicationContext(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        FileSaver.saveToFile(getApplicationContext(), userAccManager, LoginActivity.ACC_INFO);
        /*gameStateMap.put(LoginActivity.currentUser, boardManager);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    this.openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(gameStateMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }*/
    }

    @Override
    public void update(Observable o, Object arg) {
        //saveToFile(GameCenterActivity.SAVE_FILENAME);
        saveToFile();
        display();
        onSolved();
    }
}
