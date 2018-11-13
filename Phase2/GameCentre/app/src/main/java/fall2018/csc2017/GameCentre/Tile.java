package fall2018.csc2017.GameCentre;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * A Tile in a sliding tiles puzzle.
 */
public class Tile extends ImageOperationActivity implements Comparable<Tile>, Serializable {

    /**
     * The background id to find the tile image.
     */
    private String background;

    /**
     * The unique id.
     */
    private int id;

    /**
     * Return the background id.
     *
     * @return the background id
     */
    public String getBackground() {
        return background;
    }

    /**
     * Return the tile id.
     *
     * @return the tile id
     */
    public int getId() {
        return id;
    }

    /**
     * A tile with a background id; look up and set the id.
     *
     * @param backgroundId background id
     * @param gridSize grid size
     */
    public Tile(int backgroundId, int gridSize) {
        id = backgroundId + 1;
        // This looks not so ugly.
        background = getTilePath(gridSize, gridSize, id);
    }

    @Override
    public int compareTo(@NonNull Tile o) {
        return o.id - this.id;
    }
}
