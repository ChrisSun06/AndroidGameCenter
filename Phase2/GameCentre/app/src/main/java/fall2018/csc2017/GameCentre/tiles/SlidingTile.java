package fall2018.csc2017.GameCentre.tiles;

import android.graphics.Bitmap;

import fall2018.csc2017.GameCentre.R;

/**
 * A Tile in a sliding tiles game.
 */
public class SlidingTile extends Tile {

    private int gridSize;

    public int getGridSize() {
        return gridSize;
    }

    /**
     * A tile with a background id and the grid size; look up and set the id.
     *
     * @param id background id
     * @param gridSize grid size
     */
    public SlidingTile(int id, int gridSize) {
        setId(id);
        this.gridSize = gridSize;
    }
}
