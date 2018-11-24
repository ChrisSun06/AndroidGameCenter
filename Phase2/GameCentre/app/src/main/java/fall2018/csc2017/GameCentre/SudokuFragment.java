package fall2018.csc2017.GameCentre;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


/**
 * A simple {@link Fragment} subclass.
 */
public class SudokuFragment extends GameCenterButtonFragment {

    private SudokuBoardManager boardManager;
    private UserAccManager accManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        accManager = (UserAccManager) FileSaver.loadFromFile(getActivity(),
                LoginActivity.ACC_INFO);
        view = inflater.inflate(R.layout.fragment_sudoku, container, false);
        addSudokuButtonListener();
        return view;

    }


    public SudokuFragment() {
        // Required empty public constructor
    }

    /**
     * Activate the Sudoku game.
     */
    private void addSudokuButtonListener() {
        Button start2048Game = view.findViewById(R.id.start2048);
        start2048Game.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                activateGame();
            }
        });
    }

    /**
     * Switch to the GameActivity view to play the game.
     */
    @Override
    void switchToGame() {
        Intent tmp = new Intent(getActivity(), SudokuGameActivity.class);
        tmp.putExtra("accManager", accManager);
        FileSaver.saveToFile(getActivity(), boardManager,
                GameCenterActivity.TEMP_SAVE_FILENAME);
        startActivity(tmp);
    }

    /**
     * Activate the Sudoku game.
     */
    @Override
    void activateGame() {
         boardManager = new SudokuBoardManager();
        //boardManager.getBoard().setMaxUndoTime(accManager.getUserUndoTime());
        switchToGame();
    }


    private View view;



}
