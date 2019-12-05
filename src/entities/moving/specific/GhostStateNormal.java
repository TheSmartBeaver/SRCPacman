package src.entities.moving.specific;

import src.engine.ai.MovingStrategy;

/**
 * Created by Vincent on 19/11/2019.
 */
public class GhostStateNormal extends GhostState{

    public GhostStateNormal(MovingStrategy movingStrat) {
        super(movingStrat);
    }

    @Override
    public void executeStrategy(Ghost ghost) {
        getMovingStrategy().computeInputList(ghost);
    }
}
