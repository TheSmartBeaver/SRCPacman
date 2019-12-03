package src.entities.moving.specific;

import src.engine.ai.MovingRandom;
import src.entities.space.generic.TileMap;

/**
 * Created by Vincent on 19/11/2019.
 */
public class GhostStateFleeing extends GhostState {

    public GhostStateFleeing(TileMap tileMap) {
        super(new MovingRandom(tileMap));
    }

    @Override
    public void executeStrategy(Ghost ghost) {
        getMovingStrategy().computeInputList(ghost);
    }
}
