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
     * Set the drawable id of the background image.
     *
     * @param drawableId drawable id
     */
    public void setDrawableId(int drawableId) {
        this.drawableId = drawableId;
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
     * @param id background id
     * @param value current tile's displayed value such as 1024, 2048...
     */
    public TofeTile(int id, int value) {
        setId(id + 1);
        this.drawableId = FirstTofeTileDrawableId + powerOfTwo(value) - 1;
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
