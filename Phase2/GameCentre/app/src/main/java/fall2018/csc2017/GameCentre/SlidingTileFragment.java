package fall2018.csc2017.GameCentre;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SlidingTileFragment extends Fragment {

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
    public static int CURRENT_GAME;

    public SlidingTileFragment() {
        // Required empty public constructor

    }

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_sliding_tile, container, false);
        add3x3ButtonListener();
        add4x4ButtonListener();
        add5x5ButtonListener();
        return view;

    }


    /**
     * Activate the 3x3 game.
     */
    private void add3x3ButtonListener() {
        Button by3sliding = view.findViewById(R.id.by3sliding);
        by3sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGame(3);
            }
        });
    }


    /**
     * Activate the 4x4 game.
     */
    private void add4x4ButtonListener() {
        Button by4sliding = view.findViewById(R.id.by4sliding);
        by4sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGame(4);
            }
        });
    }

    /**
     * Activate the 5x5 game.
     */
    private void add5x5ButtonListener() {
        Button by5sliding = view.findViewById(R.id.by5sliding);
        by5sliding.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGame(5);
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    private void switchToGame() {
        Intent tmp = new Intent(getActivity(), GameActivity.class);
        saveToFile(TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Activate the ixi game.
     *
     * @param i the complexity of game
     */
    private void activateGame(int i) {
        boardManager = new BoardManager(i);
        boardManager.getBoard().setMaxUndoTime(LoginActivity.accManager.
                getUserUndoTime(getActivity().getApplicationContext()));
        CURRENT_GAME = i;
        switchToGame();
    }


    /**
     * Save the game state to fileName.
     *
     * @param fileName the name of the file
     */
    public void saveToFile(String fileName) {
        gameStateMap.put(LoginActivity.currentUser, boardManager);
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(
                    getActivity().openFileOutput(fileName, MODE_PRIVATE));
            outputStream.writeObject(gameStateMap);
            outputStream.close();
        } catch (IOException e) {
            Log.e("Exception", "File write failed: " + e.toString());
        }
    }
}
