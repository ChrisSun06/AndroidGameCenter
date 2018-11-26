package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class The2048BoardManager extends AbstractBoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private The2048Board board;

    /**
     * The stack which keeps track of the previous board.
     */

    private Stack<TofeTile[]> historyStack = new Stack<>();

    /**
     * Return the current board.
     */
    The2048Board getBoard() {
        return board;
    }

    /**
     * Manage a new shuffled board.
     */
    private int[] beginBoardList() {
        int[] boardNumber = new int[16];
        int set1 = (int) (Math.random() * 16);
        int set2 = (int) (Math.random() * 16);
        while (set1 == set2) {
            set2 = (int) (Math.random() * 16);
        }
        boardNumber[set1] = ((int) (Math.random() * 2) + 1) * 2;
        boardNumber[set2] = ((int) (Math.random() * 2) + 1) * 2;
        return boardNumber;
    }

    The2048BoardManager() {
        int[] boardNumber = beginBoardList();
        List<TofeTile> tiles = new ArrayList<>();
        final int numTiles = The2048Board.numTiles;
        for (int tileNum = 0; tileNum != numTiles; tileNum++) {
            if (boardNumber[tileNum] == 2) {
                tiles.add(new TofeTile(2, tileNum));
            } else if (boardNumber[tileNum] == 4) {
                tiles.add(new TofeTile(4, tileNum));
            } else {
                tiles.add(new TofeTile(0, tileNum));
            }
        }
        this.board = new The2048Board(tiles);
    }

    void move(String rOrC, boolean inverted){
        historyStack.push(board.getAllTiles());
        TofeTile[] mergedList = this.board.merge(rOrC, inverted);
        this.board.setAllTiles(mergedList);
        this.board.generateNewTiles();
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    boolean puzzleSolved() {
        TofeTile[] currentTiles = this.board.getAllTiles();
        return Arrays.equals(board.merge("row", true), currentTiles) &&
                Arrays.equals(board.merge("row", false), currentTiles) &&
                Arrays.equals(board.merge("column", true), currentTiles) &&
                Arrays.equals(board.merge("column", false), currentTiles);
    }

    /**
     * the method will be used in Class MovementController.
     */

    void undo() {
        if(!historyStack.empty()) {
            this.board.setAllTiles(historyStack.pop());
        }
    }
}