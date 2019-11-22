package src.engine.ai;

import src.entities.moving.specific.Ghost;
import src.entities.space.generic.TileMap;

/**
 * Created by Vincent on 18/11/2019.
 */
public abstract class MovingStrategy {

    private TileMap tileMap;

    public MovingStrategy(TileMap tileMap) {
        this.tileMap = tileMap;
    }

    public TileMap getTileMap() {
        return tileMap;
    }

    public abstract void computeInputList(Ghost ghost);

}
