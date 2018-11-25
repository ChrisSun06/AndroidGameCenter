package fall2018.csc2017.GameCentre;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import fall2018.csc2017.GameCentre.tiles.SudokuTile;

public class SudokuBoardManager extends AbstractBoardManager {

    private SudokuBoard board;

    private ArrayList<Integer> NUMBERS = new ArrayList<>();

    SudokuBoard getBoard(){
        return board;
    }

    SudokuBoardManager(){
        for (int i = 1; i <= 9; i++) {
            NUMBERS.add(i);
        }
        List<SudokuTile> numbers = new ArrayList<>();
        randomAdd(numbers);
        this.board = new SudokuBoard(numbers);
        solve();
        randomRemove();
    }

    SudokuBoardManager(SudokuBoard board){
        this.board = board;
    }

    private void randomRemove(){
        Random random = new Random();
        for (int i = 0; i < this.board.numTiles()/2; i++){
            int randomInt = random.nextInt(board.numTiles());
            int col = randomInt % board.getNumCols();
            int row = randomInt / board.getNumRows();
            board.setTile(row, col, 0);
            board.getTile(row, col).setIsMutable(true);
        }
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

    boolean solve() {
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
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
            }
        }
        return true;
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

    boolean isValid() {
        return isPartialValid(board.horizontal()) &&
                isPartialValid(board.vertical()) &&
                isPartialValid(board.sectional());
    }

    boolean isPartialValid(Iterable<SudokuTile> part) {
        int count = 0;
        int totalCount = 0;
        Set<Integer> tempSet = new HashSet<>();

        for (SudokuTile i: part){
            totalCount ++;
            if (i.getNumber() != 0) {
                count++;
                tempSet.add(i.getNumber());
                if (tempSet.size() != count){
                    return false;
                }
            }
            if (totalCount == board.getNumRows()){
                totalCount = 0;
                count = 0;
                tempSet.clear();
            }
        }
        return true;
    }

    /*boolean isRowValid() {
        int count = 0;
        int totalCount = 0;
        Set<SudokuTile> tempSet = new HashSet<>();
        for (SudokuTile i: board.horizontal()){
            totalCount ++;
            if (i.getNumber() != 0) {
                count++;
                tempSet.add(i);
                if (tempSet.size() != count){
                    return false;
                }
            }
            if (totalCount == board.getRows()){
                totalCount = 0;
                count = 0;
                tempSet.clear();
            }
        }
        return true;
    }

    boolean isColValid() {
        int count = 0;
        int totalCount = 0;
        Set<SudokuTile> tempSet = new HashSet<>();
        for (SudokuTile i: board.vertical()){
            totalCount ++;
            if (i.getNumber() != 0) {
                count++;
                tempSet.add(i);
                if (tempSet.size() != count){
                    return false;
                }
            }
            if (totalCount == board.getRows()){
                totalCount = 0;
                count = 0;
                tempSet.clear();
            }
        }
        return true;
    }

    boolean isSectionValid() {
        int count = 0;
        int totalCount = 0;
        Set<Integer> tempSet = new HashSet<>();
        for (Integer i: board.sectional()){
            totalCount ++;
            if (i != 0) {
                count++;
                tempSet.add(i);
                if (tempSet.size() != count){
                    return false;
                }
            }
            if (totalCount == board.getRows()){
                totalCount = 0;
                count = 0;
                tempSet.clear();
            }
        }
        return true;
    }*/

    boolean puzzleSolved(){
        boolean filled = true;
        for (SudokuTile i: board.horizontal()){
            if (i.getNumber() == 0){filled = false;}
        }
        return isValid() && filled;
    }

    void touchMove(int position){
        int row = position / board.getNumRows();
        int col = position % board.getNumCols();
        board.incrementTile(row, col);
    }
}
