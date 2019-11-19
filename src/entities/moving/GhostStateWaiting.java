package src.entities.moving;

import src.engine.ai.MovingInGhostArea;
import src.engine.ai.MovingStrategy;
import src.entities.space.TileMap;

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
