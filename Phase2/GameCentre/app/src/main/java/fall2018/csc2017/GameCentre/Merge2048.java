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
     * The role of the inputArray, row or column.
     */
    private String identity;

    /**
     * The whether the inputArray is inverted before merging.
     */
    private boolean inverted;

    /**
     * The row/column number of the inputArray in the board to be merged.
     */
    private int numRC;

    /**
     * Initialize the list to be merged.
     * Precondition: the length of the inputArray should be 4.
     * Precondition: identity can only be "row" or "column".
     *
     * @param inputArray the array to be merged.
     */
    Merge2048(TofeTile[] inputArray, String identity, int numRC, boolean inverted){
        this.inputArray = removingBlank(inputArray);
        this.identity = identity;
        this.inverted = inverted;
        this.numRC = numRC;
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
                resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue()*2, 0);
                currentPosition += 2;
                posInResult += 1;
            }else{
                resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue(), 0);
                currentPosition += 1;
                posInResult += 1;
            }
        }
        this.addingToResultingList();
        this.setResultingId();
        return resultingList;
    }

    /**
     * Adding tiles to the resultingList if the posInResult is less than 4. Also add the last tile
     * in inputArray to the resultingList if needed.
     */
    private void addingToResultingList(){
        if (currentPosition == inputArray.length - 1){
            resultingList[posInResult] = new TofeTile(inputArray[currentPosition].getValue(), 0);
            posInResult += 1;
        }
        while (posInResult < 4){
            resultingList[posInResult] = new TofeTile(0, 0);
            posInResult += 1;
        }
    }

    /**
     * Setting the id of tiles in the resultingList, based on its identity, its position
     * in the board and whether it is inverted
     */
    private void setResultingId(){
        if(identity == "row" && !inverted){
            for(int i = 0; i < 4; i++)
                resultingList[i].setId(numRC * 4 + i);
        }else if(identity == "row" && inverted){
            for(int i = 3; i > 0; i--)
                resultingList[i].setId(numRC * 4 + i);
        }else if(identity == "column" && !inverted){
            for(int i = 0; i < 4; i++)
                resultingList[i].setId(4 * i + numRC);
        }else if(identity == "column" && inverted){
            for(int i = 3; i > 0; i--)
                resultingList[i].setId(4 * i + numRC);
        }
    }
}
