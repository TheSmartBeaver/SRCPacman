package src;

import src.entities.space.TileMap;

/**
 * Created by Vincent on 01/11/2019.
 */
public class Level {

    private TileMap tileMap;
    private int numeroLevel;

    public Level(TileMap tileMap, int numeroLevel) {
        this.tileMap = tileMap;
        this.numeroLevel = numeroLevel;
    }

    public TileMap getTileMap() {
        return tileMap;
    }
}
