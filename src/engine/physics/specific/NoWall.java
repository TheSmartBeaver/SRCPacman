package src.engine.physics.specific;

import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.space.generic.TileMap;

/**
 * Created by Vincent on 26/11/2019.
 */
public class NoWall implements MovementRestrictions {

    @Override
    public void determineNextPlayerDirection(MovingEntity entity, TileMap tileMap) {
        Direction oldDirection = entity.getCurrentDirection();
        int entityTileY = entity.getTileY();
        int entityTileX = entity.getTileX();
        int rowCount = tileMap.getRowCount();
        int columnCount = tileMap.getColumnCount();
        switch (entity.getInput()) {
            case UP:
            {
                if (entityTileY > 0 && !tileMap.get(entityTileY - 1, entityTileX).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.UP);
                } else {
                    if (
                            (entityTileX > 0 && oldDirection == Direction.LEFT && tileMap.get(entityTileY, entityTileX - 1).isWall()) ||
                                    (entityTileX < columnCount - 1 && oldDirection == Direction.RIGHT && tileMap.get(entityTileY, entityTileX + 1).isWall()) ||
                                    (entityTileY > 0 && oldDirection == Direction.UP && tileMap.get(entityTileY - 1, entityTileX).isWall()))
                        entity.setMoving(false);
                }
                break;
            }
            case DOWN:
            {
                if (entityTileY < rowCount - 1 && !tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.DOWN);
                } else {
                    if (
                            (entityTileX > 0 && oldDirection == Direction.LEFT && tileMap.get(entityTileY, entityTileX - 1).isWall()) ||
                                    (entityTileX < columnCount - 1 && oldDirection == Direction.RIGHT && tileMap.get(entityTileY, entityTileX + 1).isWall()) ||
                                    (entityTileY < rowCount - 1 && oldDirection == Direction.DOWN && tileMap.get(entityTileY + 1, entityTileX).isWall())) {
                        entity.setMoving(false);
                    }

                }
                break;
            }
            case LEFT:
            {
                if (entityTileX > 0 && !tileMap.get(entityTileY, entityTileX - 1).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.LEFT);
                } else {
                    if (
                            (entityTileX > 0 && oldDirection == Direction.LEFT && tileMap.get(entityTileY, entityTileX - 1).isWall()) ||
                                    (entityTileY > 0 && oldDirection == Direction.UP && tileMap.get(entityTileY - 1, entityTileX).isWall()) ||
                                    (entityTileY < rowCount - 1 && oldDirection == Direction.DOWN && tileMap.get(entityTileY + 1, entityTileX).isWall())) {
                        entity.setMoving(false);
                    }

                }
                break;
            }
            case RIGHT:
            {
                if (entityTileX < columnCount - 1 && !tileMap.get(entityTileY, entity.getTileX() + 1).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.RIGHT);
                } else {
                    if (
                            (entityTileX < columnCount - 1 && oldDirection == Direction.RIGHT && tileMap.get(entityTileY, entityTileX + 1).isWall()) ||
                                    (entityTileY > 0 && oldDirection == Direction.UP && tileMap.get(entityTileY - 1, entityTileX).isWall()) ||
                                    (entityTileY < rowCount - 1 && oldDirection == Direction.DOWN && tileMap.get(entityTileY + 1, entityTileX).isWall()))
                        entity.setMoving(false);
                }
                break;
            }
        }
    }
}
