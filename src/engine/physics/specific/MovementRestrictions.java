package src.engine.physics.specific;

import src.entities.moving.generic.MovingEntity;
import src.entities.space.generic.TileMap;

/**
 * Created by Vincent on 26/11/2019.
 */
public interface MovementRestrictions {

    public void determineNextPlayerDirection(MovingEntity entity, TileMap tileMap);

}
