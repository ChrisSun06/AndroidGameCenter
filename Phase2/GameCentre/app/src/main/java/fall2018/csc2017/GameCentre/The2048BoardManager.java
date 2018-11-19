package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * Manage a board, including swapping tiles, checking for a win, and managing taps.
 */
class The2048BoardManager implements Serializable {

    /**
     * The board being managed.
     */
    private The2048Board board;

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
                tiles.add(new TofeTile(2));
            } else if (boardNumber[tileNum] == 4) {
                tiles.add(new TofeTile(4));
            } else {
                tiles.add(new TofeTile(0));
            }
        }
        this.board = new The2048Board(tiles);
    }

    void movingVertical(boolean down){
        The2048Board mergedBoard = mergeVertical(down);
        Integer[] blankPositions = mergedBoard.getBlankPosition();
        if (blankPositions.length != 0){
            int randomPos = blankPositions[(int) (Math.random() * blankPositions.length)];
            TofeTile randomTile = new TofeTile(((int) (Math.random() * 1) + 1) * 2);
            mergedBoard.setTile(randomPos/The2048Board.numRows, randomPos%The2048Board.numCols, randomTile);
        }
        this.board = mergedBoard;
    }

    private The2048Board mergeVertical(boolean down) {
        TofeTile[] resultingTiles = new TofeTile[16];
        for (int column = 0; column < 4; column++) {
            TofeTile[] mergedTiles = new Merge2048(board.getCol(column, down)).merge();
            if (!down) {
                for (int row = 1; row < 4; row++) {
                    resultingTiles[row * 4 + column] = mergedTiles[row];
                }
            } else {
                for (int row = 1; row < 4; row++) {
                    resultingTiles[row * 4 + column] = mergedTiles[3 - row];
                }
            }
        }
        return new The2048Board(Arrays.asList(resultingTiles));

    }

//    void appearNewTile(){
//        int appearPosition = (int)(Math.random()*15);
//        int appearNumber = (int)(Math.random());
//        while (boardNumber[appearPosition] != 0){
//            appearPosition = (int)(Math.random()*15);
//        }
//        boardNumber[appearPosition] = appearNumber;
//    }
//
//    void recreateBoard(){
//        List<Tile> tiles = new ArrayList<>();
//        for (int i = 0; i!=16; i++){
//            tiles.add(new Tile(boardNumber[i]-1, 4));
//            //要保证boardNumber[i]－1 对应png的数字 重点需解决！！
//        }
//    }

    /**
     * Return whether the tiles are in row-major order.
     *
     * @return whether the tiles are in row-major order
     */
    boolean puzzleSolved() {
        boolean solved = true;
        for (TofeTile tile : this.board) {
            solved = (tile.getValue() != 0);
            if (!solved) break;
        }
        return solved;
    }

    /**
     * the method will be used in Class MovementController.
     */
    void undo() {

    }

//    /**
//     * every time when user makes a move, historyStack will add the blankTile position.
//     *
//     */
//    private void moveHistory(){
//        board.historyStack.push(blankTilePosition());
//    }
}