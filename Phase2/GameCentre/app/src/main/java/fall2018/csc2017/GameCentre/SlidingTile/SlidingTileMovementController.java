package fall2018.csc2017.GameCentre.SlidingTile;

import fall2018.csc2017.GameCentre.AbstractBoardManager;
import fall2018.csc2017.GameCentre.MovementController;


class SlidingTileMovementController implements MovementController {

    /**
     * The boardManager.
     */
    private SlidingTileBoardManager boardManager;

    /**
     * Set the boardManager.
     *
     * @param boardManager the board manager
     */
    public void setBoardManager(AbstractBoardManager boardManager) {
        this.boardManager = (SlidingTileBoardManager) boardManager;
    }

    /**
     * Process the movement for each tap on position.
     *
     * @param position the tapped position
     */
    void processTapMovement(int position) {
        if (boardManager.isValidTap(position)) {
            processValidTap(position);

        } else if (!boardManager.getBoard().historyStack.isEmpty() && position ==
                boardManager.blankTilePosition()) {
            processUndo();

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
     * @param position the position tapped
     */
    private void processValidTap(int position) {
        boardManager.touchMove(position, false);
    }




}
