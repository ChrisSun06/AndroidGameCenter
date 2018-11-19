package fall2018.csc2017.GameCentre;

import java.util.ArrayList;

import fall2018.csc2017.GameCentre.tiles.TofeTile;

/**
 * Class responsible for algorithm of adding numbers together and changing positions of tiles on
 * the board, i.e., merging rows or columns.
 */
class Merge2048 {

    /**
     * The resulting list after merging the original row/column.
     */
    private TofeTile[] resultingList = new TofeTile[4];

    /**
     * The input list for merging.
     */
    private TofeTile[] inputArray;

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
    Merge2048(TofeTile[] inputArray){
        this.inputArray = removingBlank(inputArray);
    }

    /**
     * Return an array of tile that contains all non-blank tile of the input array of tiles.
     *
     * @param inputArray input array of tiles
     * @return an array of tile without blank tile.
     */
    private static TofeTile[] removingBlank(TofeTile[] inputArray){
        ArrayList<TofeTile> temResult = new ArrayList<>();
        for (TofeTile t: inputArray){
            if(t.getValue() != 0)
                temResult.add(t);
        }
        return (TofeTile[])temResult.toArray();
    }

    /**
     * Return the merged version of inputArray, which is resultingList after series of operation.
     *
     * @return the merged version of inputArray.
     */
    TofeTile[] merge(){
        while (currentPosition < inputArray.length - 1){
            if(inputArray[currentPosition].getValue() == inputArray[currentPosition+1].getValue()){
                resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue()*2);
                currentPosition += 2;
                posInResult += 1;
            }else{
                resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue());
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
            resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue());
            posInResult += 1;
        }
        while (posInResult < 4){
            resultingList[posInResult] = new TofeTile(0);
            posInResult += 1;
        }
    }
}
