package src.engine.physics.specific;

import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.space.generic.TileMap;

/**
 * Created by Vincent on 26/11/2019.
 */
public class NoRestrictions implements MovementRestrictions{

    @Override
    public void determineNextPlayerDirection(MovingEntity entity, TileMap tileMap) {
        int entityTileX = entity.getTileX();
        int entityTileY = entity.getTileY();
        switch (entity.getInput()) {
            case UP:
            {
                if (entityTileY > 0) {
                    entity.setCurrentDirection(Direction.UP);
                    entity.setMoving(true);
                }
                break;
            }
            case DOWN:
            {
                if (entityTileY < tileMap.getRowCount() - 1) {
                    entity.setCurrentDirection(Direction.DOWN);
                    entity.setMoving(true);
                }
                break;
            }
            case LEFT:
            {
                if (entityTileX > 0) {
                    entity.setCurrentDirection(Direction.LEFT);
                    entity.setMoving(true);
                }
                break;
            }
            case RIGHT:
            {
                if (entityTileX < tileMap.getColumnCount() - 1) {
                    entity.setCurrentDirection(Direction.RIGHT);
                    entity.setMoving(true);
                }
                break;
            }
        }
    }
}
