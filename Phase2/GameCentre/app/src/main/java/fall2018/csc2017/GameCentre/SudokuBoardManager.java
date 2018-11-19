package fall2018.csc2017.GameCentre;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class SudokuBoardManager implements Serializable {

    private SudokuBoard board;

    private ArrayList<Integer> NUMBERS = new ArrayList<>();

    SudokuBoardManager(){
        for (int i = 1; i <= 6; i++) {
            NUMBERS.add(i);
        }
        ArrayList<Integer> tiles = new ArrayList<>(Collections.nCopies(30, 0));
        randomAdd(tiles);
        this.board = new SudokuBoard(tiles);
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
            int col = randomInt % board.getCols();
            int row = randomInt / board.getRows();
            board.setTiles(row, col, 0);
        }
    }

    private void randomAdd(ArrayList<Integer> tiles){
        for (Integer i: NUMBERS){
            tiles.add(i);
        }
        Collections.shuffle(tiles);
    }

    boolean solve() {
        for (int row = 0; row < board.getNumRows(); row++) {
            for (int col = 0; col < board.getNumCols(); col++) {
                if (board.getTiles(row, col) == 0) {
                    for (Integer k : NUMBERS) {
                        board.setTiles(row, col, k);
                        if (isValid() && solve()) {
                            return true;
                        }
                        board.setTiles(row, col, 0);
                    }
                    return false;
                }
            }
        }
        return true;
    }

    boolean isValid() {
        return isRowValid() && isColValid() && isSectionValid();
    }

    boolean isRowValid() {
        int count = 0;
        int totalCount = 0;
        Set<Integer> tempSet = new HashSet<>();
        for (Integer i: board.horizontal()){
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
    }

    boolean isColValid() {
        int count = 0;
        int totalCount = 0;
        Set<Integer> tempSet = new HashSet<>();
        for (Integer i: board.vertical()){
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
    }

    boolean puzzleSolved(){
        boolean filled = true;
        for (Integer i: board.horizontal()){
            if (i == 0){filled = false;}
        }
        return isValid() && filled;
    }

    void touchMove(int position){
        int row = position / board.getRows();
        int col = position % board.getCols();
        board.incrementTile(row, col);
    }

    SudokuBoard getBoard(){
        return board;
    }



}
