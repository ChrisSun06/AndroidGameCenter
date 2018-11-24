package fall2018.csc2017.GameCentre.tiles;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * An abstract tile for polymorphism implementation.
 */
public abstract class Tile implements Comparable<Tile>, Serializable {
    /**
     * Tile's background id on the board.
     */
    private int id;

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
