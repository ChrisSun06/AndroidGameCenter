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

    private Stack<The2048Board> historyStack = new Stack<>();

    /**
     * Manage a board that has been pre-populated.
     *
     * @param board the board
     */
    The2048BoardManager(The2048Board board) {
        this.board = board;
    }

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
        int set1 = (int) (Math.random() * 15);
        int set2 = (int) (Math.random() * 15);
        while (set1 == set2) {
            set2 = (int) (Math.random() * 15);
        }
        boardNumber[set1] = ((int) (Math.random() * 1) + 1) * 2;
        boardNumber[set2] = ((int) (Math.random() * 1) + 1) * 2;
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
        historyStack.push(this.board);
    }

    void move(String rOrC, boolean inverted){
        The2048Board mergedBoard = this.merge(rOrC, inverted);
        Integer[] blankPositions = mergedBoard.getBlankPosition();
        if (blankPositions.length != 0){
            int randomPos = blankPositions[(int) (Math.random() * blankPositions.length)];
            TofeTile randomTile = new TofeTile(((int) (Math.random() * 1) + 1) * 2, randomPos);
            mergedBoard.setTile(randomPos/The2048Board.numRows, randomPos%The2048Board.numCols, randomTile);
        }
        this.board = mergedBoard;
        historyStack.push(mergedBoard);
    }

    private The2048Board merge(String rOrC, boolean inverted) {
        TofeTile[] resultingTiles = new TofeTile[16];
        for (int i = 0; i < 4; i++) {
            TofeTile[] mergedTiles = new Merge2048(board.getRowOrColumn(rOrC, i, inverted)).merge();
            for(int j = 0; j < 4; j++)
                resultingTiles[mergedTiles[j].getId()] = mergedTiles[j];
        }
        return new The2048Board(Arrays.asList(resultingTiles));
    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    @Override
    boolean puzzleSolved() {
        return merge("row", true).equals(board) && merge("column", true).equals(board) &&
                merge("row", false).equals(board) && merge("column", false).equals(board) ;
    }

    /**
     * the method will be used in Class MovementController.
     */

    void undo() {
        this.board = historyStack.pop();
    }
}