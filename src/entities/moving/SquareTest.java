package src.entities.moving;

/**
 * Created by Vincent on 02/11/2019.
 */
public class SquareTest extends Entity{

    public SquareTest() {}

    @Override
    public EntityType getEntityType() {
        return EntityType.TEST;
    }

    public SquareTest(float posX, float posY, int length, float speed) {
        super(posX, posY, length, speed);
    }
}
