package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.SlidingTile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class SlidingTileBoardManager extends AbstractBoardManager implements Serializable{

    /**
     * The board being managed.
     */
    private SlidingTileBoard slidingTileBoard;

    /**
     * Manage a board that has been pre-populated.
     * @param slidingTileBoard the board
     */
    SlidingTileBoardManager(SlidingTileBoard slidingTileBoard) {
        this.slidingTileBoard = slidingTileBoard;
    }

    /**
     * Return the current board.
     */
    SlidingTileBoard getBoard() {
        return slidingTileBoard;
    }

    /**
     * Manage a new shuffled board.
     */
    SlidingTileBoardManager(int gridSize) {
        List<SlidingTile> tiles = new ArrayList<>();
        final int numTiles = gridSize * gridSize;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            tiles.add(new SlidingTile(tileNum + 1, gridSize));
        }

        Collections.shuffle(tiles);
        while (!solvable(tiles, gridSize)){
            Collections.shuffle(tiles);
        }
        this.slidingTileBoard = new SlidingTileBoard(tiles, gridSize);
    }

    /**
     * Return an boolean to check if the game could be solved.
     *
     * @param tile the list to check
     * @return true if it could; otherwise, false
     */
    private boolean solvable(List<SlidingTile> tile, int gridSize){
        if (gridSize % 2 == 1){
            return  (inversion(tile) % 2 == 0);
        }else{
            return (inversion(tile) % 2 + blankFromBottom(tile, gridSize) % 2 == 1);
        }
    }

    /**
     * Return the blank Tile position from bottom (need by solvable).
     *
     * @param tile the list to check
     * @param gridSize the row and col number
     * @return the int blank Tile position from bottom which need by solvable
     */
    private int blankFromBottom(List<SlidingTile> tile, int gridSize) {
        int blankId = tile.size();
        for (int r = 0; r != gridSize; r++) {
            for (int c = 0; c != gridSize; c++) {
                if (tile.get(r * gridSize + c).getId() == blankId){
                    return gridSize - r;
                }
            }
        }
        return -1;
    }

    /**
     * Return an inversion (need by solvable).
     *
     * @param tile the list to check
     * @return the int named inversion which need by solvable
     */
    private int inversion(List<SlidingTile> tile){
        int blankId = tile.size();
        int totalTile = tile.size();
        int i = 0;
        for (int r = 0; r != totalTile; r++) {
            if (tile.get(r).getId() != blankId){
                for (int rr = r+1; rr != totalTile; rr++) {
                    if (tile.get(r).getId() > tile.get(rr).getId()) {
                        i = i + 1;
                    }
                }
            }
        }
        return i;
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;

        int tileId = 1;
        for (SlidingTile tile: this.slidingTileBoard) {
            solved = (tile.getId() == tileId);
            tileId++;
            if (!solved) break;
        }
        return solved;
    }

    /**
     * Return whether any of the four surrounding tiles is the blank tile.
     *
     * @param position the tile to check
     * @return whether the tile at position is surrounded by a blank tile
     */
    boolean isValidTap(int position) {

        int row = position / slidingTileBoard.getNumRows();
        int col = position % slidingTileBoard.getNumCols();
        int blankId = slidingTileBoard.numTiles();
        SlidingTile above = row == 0 ? null : slidingTileBoard.getTile(row - 1, col);
        SlidingTile below = row == slidingTileBoard.getNumRows() - 1 ? null : slidingTileBoard.getTile(row + 1, col);
        SlidingTile left = col == 0 ? null : slidingTileBoard.getTile(row, col - 1);
        SlidingTile right = col == slidingTileBoard.getNumCols() - 1 ? null : slidingTileBoard.getTile(row, col + 1);
        return (below != null && below.getId() == blankId)
                || (above != null && above.getId() == blankId)
                || (left != null && left.getId() == blankId)
                || (right != null && right.getId() == blankId);
    }

    /**
     * Find and return an array of the row and col of blankTile.
     *
     * @return An int array of the row and col of blankTile.
     */
    private int[] blankTilePos(){
        int blankId = slidingTileBoard.numTiles();
        int row1 = 0;
        int col1 = 0;
        for (int r = 0; r != slidingTileBoard.getNumRows(); r++) {
            for (int c = 0; c != slidingTileBoard.getNumCols(); c++) {
                if (slidingTileBoard.getTile(r, c).getId() == blankId) {
                    row1 = r;
                    col1 = c;
                }
            }
        }
        return new int[]{row1, col1};
    }

    /**
     * Upon receiving a valid tap, switch the position of the
     * blank tile and the tile at position.
     *
     * @param position the position
     */
    void touchMove(int position, boolean ifUndo) {
            int row = position / slidingTileBoard.getNumRows();
            int col = position % slidingTileBoard.getNumCols();

            int[] position2 = blankTilePos();
            if (!ifUndo) {
                moveHistory();
                slidingTileBoard.increaseNumOfMoves();
            }
            slidingTileBoard.swapTiles(row, col, position2[0], position2[1]);
    }

    /**
     * get the exactly position of method blankTilePos().
     *
     * @return An int blankTile position.
     */
    int blankTilePosition(){
        return blankTilePos()[0] * slidingTileBoard.getNumRows() + blankTilePos()[1];
    }

    /**
     * the method will be used in Class MovementController.
     *
     */
    void undo(){
        touchMove(slidingTileBoard.historyStack.pop(), true);
        slidingTileBoard.setMaxUndoTime(slidingTileBoard.getMaxUndoTime()-1);
    }

    /**
     * every time when user makes a move, historyStack will add the blankTile position.
     *
     */
    private void moveHistory(){
        slidingTileBoard.historyStack.push(blankTilePosition());
    }
}