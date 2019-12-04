package src.entities.moving.specific;

import src.engine.ai.MovingReturningHome;

/**
 * Created by Vincent on 19/11/2019.
 */
public class GhostStateReturningHome extends GhostState {

    public GhostStateReturningHome(int[][] tilesForA) {
        super(new MovingReturningHome(tilesForA));
    }

    @Override
    public void executeStrategy(Ghost ghost) {
        getMovingStrategy().computeInputList(ghost);
    }
}
