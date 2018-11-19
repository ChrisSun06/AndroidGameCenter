package fall2018.csc2017.GameCentre.tiles;

import java.nio.file.Files;

import fall2018.csc2017.GameCentre.R;

/**
 * A Tile in a 2048 game.
 */
public class TofeTile extends Tile{
    /**
     * The id of the first 2048 tile image
     */
    public static final int FirstTofeTileDrawableId = R.drawable.tofe_01;

    /**
     * The background image id to find the tile image.
     */
    private int drawableId;

    /**
     * The value on the tile image.
     */
    private int value;

    /**
     * Return the value of the tile
     *
     * @return the value of the tile
     */
    public int getValue(){return value;}

    /**
     * Set the drawable id of the background image.
     *
     * @param drawableId drawable id
     */
    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
    }

    /**
     * Set the value of the tile.
     *
     * @param value the value
     */
    public void setValue(int value) {
        this.value = value;
    }

    /**
     * Return the background image id.
     *
     * @return the background id
     */
    public int getDrawableId() {
        return drawableId;
    }

    /**
     * A tile with a background id and image id; look up and set the id.
     *
     * @param value current tile's displayed value such as 1024, 2048...
     */
    public TofeTile(int value) {
        //setId(id + 1);
        if(value != 0)
            this.drawableId = FirstTofeTileDrawableId + powerOfTwo(value) - 1;
        else
            this.drawableId = R.drawable.white;
        this.value = value;
    }

    /**
     * A helper that returns the power of 2 of a given value.
     *
     * @param value a value which is 2^x for positive integer x
     * @return power of two of the given value
     */
    private int powerOfTwo(int value) {
        return value / 2 == 1 ? 1 : 1 + powerOfTwo(value / 2);
    }
}