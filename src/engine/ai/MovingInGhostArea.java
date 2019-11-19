package src.engine.ai;

import src.entities.moving.Ghost;
import src.entities.space.TileMap;

/**
 * Created by Vincent on 19/11/2019.
 */
public class MovingInGhostArea extends MovingStrategy {

    public MovingInGhostArea(TileMap tileMap) {
        super(tileMap);
    }

    @Override
    public void computeInputList(Ghost ghost) {

    }
}
