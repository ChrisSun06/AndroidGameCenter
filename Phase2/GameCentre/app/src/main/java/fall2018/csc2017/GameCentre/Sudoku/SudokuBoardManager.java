package fall2018.csc2017.GameCentre.Sudoku;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fall2018.csc2017.GameCentre.AbstractBoardManager;
import fall2018.csc2017.GameCentre.ImageOperation;
import fall2018.csc2017.GameCentre.tiles.SudokuTile;
import fall2018.csc2017.GameCentre.tiles.Tile;

public class SudokuBoardManager extends AbstractBoardManager {

    private SudokuBoard board;

    private ArrayList<Integer> NUMBERS = new ArrayList<>();

    public SudokuBoard getBoard(){
        return board;
    }

    public SudokuBoardManager(){
        for (int i = 1; i <= 9; i++) {
            NUMBERS.add(i);
        }
        List<SudokuTile> numbers = new ArrayList<>();
        randomAdd(numbers);
        this.board = new SudokuBoard(numbers);
        solve();
        randomRemove();
    }

    public SudokuBoardManager(SudokuBoard board){
        this.board = board;
    }

    /**
     * Return the string representation
     *
     * @return string representation
     */
    @Override
    public String toString(){
        return "Sudoku Board Manager";
    }

    private void randomRemove(){
        Random random = new Random();
        for (int i = 0; i < 2; i++){
            int randomInt = random.nextInt(board.numTiles());
            int col = randomInt % board.getNumCols();
            int row = randomInt / board.getNumRows();
            board.setTile(row, col, 0);
            board.getTile(row, col).setIsMutable(true);
        }
    }

    Bitmap getUpdatedBitmap(Context context, SudokuBoard board, int row, int col, Bitmap tile) {
        Bitmap temp = tile;
        if (!board.getTile(row, col).getIsMutable()) {
            Bitmap number = BitmapFactory.decodeResource(context.getResources(),
                    Tile.FirstSudokuNumberId + board.getTile(row, col).getNumber() - 1);
            temp = ImageOperation.superpose(tile, number);
        }
        else {
            if (board.getTile(row, col).getNumber() != 0) {
                Bitmap number = BitmapFactory.decodeResource(context.getResources(),
                        Tile.FirstSudokuEditNumberId + board.getTile(row, col).getNumber() - 1);
                temp = ImageOperation.superpose(tile, number);
            }
        }
        return temp;
    }

    private void randomAdd(List<SudokuTile> tiles){
        for (int i = 0; i < 72; i++) {
            tiles.add(new SudokuTile(0, false));
        }
        for (int i = 0; i < NUMBERS.size(); i++) {
            tiles.add(new SudokuTile(NUMBERS.get(i), false));
        }
        Collections.shuffle(tiles);
    }

    public boolean solve() {
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                Boolean x = checkSolveAndValid(row, col);
                if (x != null) return x;
            }
        }
        return true;
    }

    @Nullable
    private Boolean checkSolveAndValid(int row, int col) {
        if (board.getTile(row, col).getNumber() == 0) {
            for (Integer k : NUMBERS) {
                board.setTile(row, col, k);
                if (isValid() && solve()) {
                    return true;
                }
                board.setTile(row, col, 0);
            }
            return false;
        }
        return null;
    }

    /**
     * Return whether the tap position is valid by checking if the change is available at such
     * position.
     *
     * @param position the tile to check
     * @return whether the tile is open to modify
     */
    boolean isValidTap(int position) {
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        return board.getTile(row, col).getIsMutable();
    }

    public boolean isValid() {
        return isPartialValid(board.horizontal()) &&
                isPartialValid(board.vertical()) &&
                isPartialValid(board.sectional());
    }

    private boolean isPartialValid(Iterable<SudokuTile> part) {
        int count = 0;
        int totalCount = 0;
        Set<Integer> tempSet = new HashSet<>();

        if (checkValid(part, count, totalCount, tempSet)) return false;
        return true;
    }

    private boolean checkValid(Iterable<SudokuTile> part, int count, int totalCount, Set<Integer> tempSet) {
        for (SudokuTile i: part){
            totalCount ++;
            if (i.getNumber() != 0) {
                count++;
                tempSet.add(i.getNumber());
                if (tempSet.size() != count){
                    return true;
                }
            }
            if (totalCount == board.getNumRows()){
                totalCount = 0;
                count = 0;
                tempSet.clear();
            }
        }
        return false;
    }

    public boolean puzzleSolved(){
        boolean filled = true;
        for (SudokuTile i: board.horizontal()){
            if (i.getNumber() == 0){filled = false;}
        }
        return isValid() && filled;
    }

    public void touchMove(int position){
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        board.incrementTile(row, col);
    }
}
