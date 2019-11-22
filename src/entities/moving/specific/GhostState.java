package src.entities.moving.specific;

import src.engine.ai.MovingStrategy;

/**
 * Created by Vincent on 19/11/2019.
 */
public abstract class GhostState {

    private MovingStrategy movingStrategy;

    public GhostState(MovingStrategy movingStrategy) {
        this.movingStrategy = movingStrategy;
    }

    public abstract void executeStrategy(Ghost ghost);

    public MovingStrategy getMovingStrategy() {
        return movingStrategy;
    }
}
