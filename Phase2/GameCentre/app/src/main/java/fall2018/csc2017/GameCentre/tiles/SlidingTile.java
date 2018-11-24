package fall2018.csc2017.GameCentre.tiles;

import android.graphics.Bitmap;

import fall2018.csc2017.GameCentre.R;

/**
 * A Tile in a sliding tiles game.
 */
public class SlidingTile extends Tile {
    /**
     * Bitmap of the background image.
     */
    private Bitmap imageBitmap;

    /**
     * Set the bitmap of the background image.
     *
     * @param imageBitmap bitmap of the background image
     */
    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = imageBitmap;
    }

    /**
     * Return the bitmap of the background image.
     *
     * @return the bitmap of the background image
     */
    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    /**
     * A tile with a background id and background image bitmap; look up and set the id.
     *
     * @param id background id
     * @param imageBitmap bitmap of the background image
     */
    public SlidingTile(int id, Bitmap imageBitmap) {
        setId(id);
        this.imageBitmap = imageBitmap;
    }
}
