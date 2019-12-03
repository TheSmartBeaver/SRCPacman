package src.entities.moving.specific;

import src.engine.ai.MovingInGhostArea;
import src.entities.space.generic.TileMap;

/**
 * Created by Vincent on 19/11/2019.
 */
public class GhostStateWaiting extends GhostState {

    public GhostStateWaiting(TileMap tileMap) {
        super(new MovingInGhostArea(tileMap));
    }

    @Override
    public void executeStrategy(Ghost ghost) {
        getMovingStrategy().computeInputList(ghost);
    }
}
