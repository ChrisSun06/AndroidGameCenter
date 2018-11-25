package fall2018.csc2017.GameCentre.tiles;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;

import java.io.Serializable;

import fall2018.csc2017.GameCentre.R;
import fall2018.csc2017.GameCentre.SerializableBitmap;

/**
 * An abstract tile for polymorphism implementation.
 */
public abstract class Tile implements Comparable<Tile>, Serializable {
    /**
     * All the drawable id's of the background images.
     */
    public static final int FirstSlidingTileDefaultId = R.drawable.tile_01;
    public static final int FirstSudokuTileId = R.drawable.sudoku_01;
    public static final int FirstSudokuNumberId = R.drawable.sudoku_i_01;
    public static final int FirstSudokuEditNumberId = R.drawable.sudoku_i_11;
    public static final int FirstTofeTileDrawableId = R.drawable.tofe_01;
    /**
     * Tile's background id on the board.
     */
    private int id;

    private SerializableBitmap imageBitmap;

    public void setImageBitmap(Bitmap imageBitmap) {
        this.imageBitmap = new SerializableBitmap(imageBitmap);
    }

    public Bitmap getImageBitmap() {
        try {
            return imageBitmap.byteArrayToBitmap();
        }
        catch (Exception e) {
            return null;
        }
    }

    /**
     * Set tile's background id.
     *
     * @param id background id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Return tile's background id.
     *
     * @return tile's background id
     */
    public int getId() {
        return this.id;
    }

    /**
     * Default constructor.
     */
    Tile() {}

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
