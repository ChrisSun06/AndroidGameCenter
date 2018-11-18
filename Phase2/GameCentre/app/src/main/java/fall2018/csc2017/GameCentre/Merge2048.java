package fall2018.csc2017.GameCentre;

import java.util.ArrayList;

/**
 * Class responsible for algorithm of adding numbers together and changing positions of tiles on
 * the board, i.e., merging rows or columns.
 */
public class Merge2048 {

    /**
     * The resulting list after merging the original row/column.
     */
    private Tile[] resultingList = new Tile[4];

    /**
     * The input list for merging.
     */
    private Tile[] inputArray;

    /**
     * The position the next tile to be put in the resulting list
     */
    private int posInResult = 0;

    /**
     * The position the tile in the inputArray to be checked.
     */
    private int currentPosition = 0;

    /**
     * Initialize the list to be merged.
     * Precondition: the length of the inputArray should be 4.
     *
     * @param inputArray the array to be merged.
     */
    public Merge2048(Tile[] inputArray){
        this.inputArray = removingBlank(inputArray);
    }

    /**
     * Return an array of tile that contains all non-blank tile of the input array of tiles.
     *
     * @param inputArray input array of tiles
     * @return an array of tile without blank tile.
     */
    private static Tile[] removingBlank(Tile[] inputArray){
        ArrayList<Tile> temResult = new ArrayList<>();
        for (Tile t: inputArray){
            if(t.getId() != 15)//Todo: 不确定blank的id是什么。
                temResult.add(t);
        }
        return (Tile[])temResult.toArray();
    }

    /**
     * Return the merged version of inputArray, which is resultingList after series of operation.
     *
     * @return the merged version of inputArray.
     */
    public Tile[] merge(){
        while (currentPosition < inputArray.length - 1){
            if(inputArray[currentPosition].getId() == inputArray[currentPosition+1].getId()){
                //Todo: 不确定Tile如何建立
                resultingList[posInResult] = new Tile(inputArray[currentPosition].getId()+1, 4);
                currentPosition += 2;
                posInResult += 1;
            }else{
                resultingList[posInResult] = new Tile(inputArray[currentPosition].getId(), 4);
                currentPosition += 1;
                posInResult += 1;
            }
        }
        this.addingToResultingList();
        return resultingList;
    }

    /**
     * Adding tiles to the resultingList if the posInResult is less than 4. Also add the last tile
     * in inputArray to the resultingList if needed.
     */
    private void addingToResultingList(){
        if (currentPosition == inputArray.length - 1){
            resultingList[posInResult] = new Tile(inputArray[currentPosition].getId(), 4);
            posInResult += 1;
        }
        while (posInResult < 4){
            resultingList[posInResult] = new Tile(15, 4);
            posInResult += 1;
        }
    }
}
