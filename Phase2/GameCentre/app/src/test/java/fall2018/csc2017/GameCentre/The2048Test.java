package fall2018.csc2017.GameCentre;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

import static org.junit.Assert.*;

public class The2048Test {
    The2048BoardManager boardManager;
    The2048Board board1;
    The2048Board board2;
    The2048Board board1Right;
    The2048Board board1Left;
    The2048Board board1Up;
    The2048Board board1Down;
/*
    my history

    private Stack<The2048Board> historyStack = new Stack<>();

    void undo() {
        this.board = historyStack.pop();
    }
 */

    private void listToBoard(int[] list, List<TofeTile> tiles){
        for (int tileNum = 0; tileNum != 16; tileNum++) {
            if (list[tileNum] == 1) {
                tiles.add(new TofeTile(1, tileNum));
            } else if (list[tileNum] == 2) {
                tiles.add(new TofeTile(2, tileNum));
            } else {
                tiles.add(new TofeTile(0, tileNum));
            }
        }
    }

    /**
     * Make a empty Board, filled with all 0s.
     */
    private void setUp(){
        int[] boardNumber1 = new int[16];
        boardNumber1[0] = 1;
        boardNumber1[4] = 1;
        int[] boardNumber2 = new int[16];
        boardNumber2[0] = 1;
        boardNumber2[5] = 2;
        boardNumber2[6] = 2;
        boardNumber2[7] = 2;
        boardNumber2[8] = 1;
        boardNumber2[10] = 3;
        boardNumber2[13] = 3;
        List<TofeTile> tiles1 = new ArrayList<>();
        List<TofeTile> tiles2 = new ArrayList<>();
        listToBoard(boardNumber1, tiles1);
        listToBoard(boardNumber2, tiles2);
        board1 = new The2048Board(tiles1);
        board2 = new The2048Board(tiles2);
    }

    private void endResult(){
        int[] boardNumber1Right = new int[16];
        boardNumber1Right[3] = 1;
        boardNumber1Right[7] = 1;
        int[] boardNumber1Left = new int[16];
        boardNumber1Left[0] = 1;
        boardNumber1Left[4] = 1;
        int[] boardNumber1Up = new int[16];
        boardNumber1Up[0] = 2;
        int[] boardNumber1Down = new int[16];
        boardNumber1Down[12] = 2;

        List<TofeTile> tiles1Right = new ArrayList<>();
        listToBoard(boardNumber1Right, tiles1Right);
        board1Right = new The2048Board(tiles1Right);
        List<TofeTile> tiles1Left = new ArrayList<>();
        listToBoard(boardNumber1Left, tiles1Left);
        board1Left = new The2048Board(tiles1Left);
        List<TofeTile> tiles1Up = new ArrayList<>();
        listToBoard(boardNumber1Up, tiles1Up);
        board1Up = new The2048Board(tiles1Up);
        List<TofeTile> tiles1Down= new ArrayList<>();
        listToBoard(boardNumber1Down, tiles1Down);
        board1Down = new The2048Board(tiles1Down);


        int[] boardNumber2Right = new int[16];
        boardNumber2Right[0] = 1;
        boardNumber2Right[5] = 2;
        boardNumber2Right[6] = 2;
        boardNumber2Right[7] = 2;
        boardNumber2Right[8] = 1;
        boardNumber2Right[10] = 3;
        boardNumber2Right[13] = 3;

        int[] boardNumber2 = new int[16];
        boardNumber2[0] = 1;
        boardNumber2[5] = 2;
        boardNumber2[6] = 2;
        boardNumber2[7] = 2;
        boardNumber2[8] = 1;
        List<TofeTile> tiles2 = new ArrayList<>();
        listToBoard(boardNumber2, tiles2);
        board2 = new The2048Board(tiles2);
    }

    @Test
    public void testMove(){
        setUp();

    }


}

