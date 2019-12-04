package src.entities.moving.specific;

import src.entities.moving.generic.MovingEntity;

/**
 * Created by Vincent on 02/11/2019.
 */
public class SquareTest extends MovingEntity {

    public SquareTest() {}

    @Override
    public MovingEntityType getEntityType() {
        return MovingEntityType.TEST;
    }

    public SquareTest(float posX, float posY, int length, float speed) {
        super(posX, posY, length, speed);
    }
}
