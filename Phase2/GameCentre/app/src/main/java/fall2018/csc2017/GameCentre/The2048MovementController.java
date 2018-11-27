package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;


class The2048MovementController {

    /**
     * The boardManager.
     */
    private The2048BoardManager boardManager = null;

    /**
     * Set the boardManager.
     *
     * @param boardManager the board manager
     */
    void setBoardManager(The2048BoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process the movement for each slides.
     *
     * @param context the current context
     * @param direction either "row" or "col", "row" means horizontal movenment, "col" means vertical movement
     * @param directionValue deciding vector if "row" is left or right, and if "col" is up or down.
     */
    void processMovement(Context context, String direction, boolean directionValue) {
        //ToDo: process a slide movement
        boardManager.move(direction, directionValue);
        Toast.makeText(context, "Your score: " + boardManager.getBoard().getScore(), Toast.LENGTH_SHORT).show();
    }

    /**
     * Process the undo movement for each tap.
     *
     * @param context the current context
     */
    private void processUndo(Context context) {
        if (boardManager.getBoard().getMaxUndoTime() <= 0) {
            Toast.makeText(context, "Cannot Undo anymore", Toast.LENGTH_SHORT).show();
        } else {
            boardManager.undo();
            Toast.makeText(context, boardManager.getBoard().getMaxUndoTime() +
                    " Undo chance(s) left", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * process the valid tap.
     *
     * @param context the current context
     * @param position the position tapped
     */
    private void processValidTap(Context context, int position) {
        //ToDo: is this even necessary?
    }


}
