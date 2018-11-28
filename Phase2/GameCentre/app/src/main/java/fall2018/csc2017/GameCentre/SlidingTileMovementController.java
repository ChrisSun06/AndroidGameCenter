package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.TextView;
import android.widget.Toast;

import static java.security.AccessController.getContext;


class SlidingTileMovementController {

    /**
     * The boardManager.
     */
    private SlidingTileBoardManager boardManager = null;

    /**
     * Set the boardManager.
     *
     * @param boardManager the board manager
     */
    void setBoardManager(SlidingTileBoardManager boardManager) {
        this.boardManager = boardManager;
    }

    /**
     * Process the movement for each tap on position.
     *
     * @param context the current context
     * @param position the tapped position
     */
    void processTapMovement(Context context, int position) {
        if (boardManager.isValidTap(position)) {
            processValidTap(context, position);

        } else if (!boardManager.getBoard().historyStack.isEmpty() && position ==
                boardManager.blankTilePosition()) {
            processUndo();

        } else {
            //Toast.makeText(context, context.getDataDir().getPath(), Toast.LENGTH_SHORT).show();

        }
    }

    /**
     * Process the undo movement for each tap.
     */
    public void processUndo() {
        if (boardManager.getBoard().getMaxUndoTime() > 0) {
            boardManager.undo();
        }


    }

    /**
     * process the valid tap.
     *
     * @param context the current context
     * @param position the position tapped
     */
    private void processValidTap(Context context, int position) {
        boardManager.touchMove(position, false);
    }




}
