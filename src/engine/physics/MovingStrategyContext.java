package src.engine.physics;

import src.entities.moving.Ghost;

/**
 * Created by Vincent on 18/11/2019.
 */
public class MovingStrategyContext {

    private MovingStrategy movingStrategy;
    private Ghost ghost;

    public MovingStrategyContext(MovingStrategy movingStrategy, Ghost ghost) {
        this.movingStrategy = movingStrategy;
        this.ghost = ghost;
    }

    public void setMovingStrategy(MovingStrategy movingStrategy) {
        this.movingStrategy = movingStrategy;
    }

    public void executeStrategy() {
        movingStrategy.computeInputList(ghost);
    }
}
