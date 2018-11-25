package fall2018.csc2017.GameCentre;

import android.content.Context;
import android.widget.Toast;


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
            processUndo(context);
        } else {
            Toast.makeText(context, context.getDataDir().getPath(), Toast.LENGTH_SHORT).show();
        }
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
        boardManager.touchMove(position, false);
        boardManager.getBoard().increaseNumOfMoves();
        if (boardManager.puzzleSolved()) {
            Toast.makeText(context, "YOU WIN!", Toast.LENGTH_SHORT).show();
        }
    }


}
