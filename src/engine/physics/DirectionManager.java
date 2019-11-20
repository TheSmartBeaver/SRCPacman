package src.engine.physics;

import src.engine.ai.GhostAI;
import src.entities.moving.Direction;
import src.entities.moving.Ghost;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.space.TileMap;

/**
 * Created by Vincent on 19/11/2019.
 */
public class DirectionManager {

    private static void determineNextPlayerDirection(MovingEntity entity, TileMap tileMap) {
        Direction oldDirection = entity.getCurrentDirection();
        int pacmanTileY = entity.getTileY();
        int pacmanTileX = entity.getTileX();
        switch (entity.getInput()) {
            case UP:
            {
                if (!tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.UP);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                    (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()))
                        entity.setMoving(false);
                }
                break;
            }
            case DOWN:
            {
                if (!tileMap.get(pacmanTileY + 1, pacmanTileX).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.DOWN);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                    (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                    (oldDirection == Direction.DOWN && tileMap.get(pacmanTileY + 1, pacmanTileX).isWall())) {
                        entity.setMoving(false);
                    }

                }
                break;
            }
            case LEFT:
            {
                if (!tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.LEFT);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) ||
                                    (oldDirection == Direction.DOWN && tileMap.get(pacmanTileY + 1, pacmanTileX).isWall())) {
                        entity.setMoving(false);
                    }

                }
                break;
            }
            case RIGHT:
            {
                if (!tileMap.get(pacmanTileY, entity.getTileX() + 1).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.RIGHT);
                } else {
                    if (
                            (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) ||
                                    (oldDirection == Direction.DOWN && tileMap.get(pacmanTileY + 1, pacmanTileX).isWall()))
                        entity.setMoving(false);
                }
                break;
            }
        }
    }

    static void determineEntityDirection(MovingEntity entity, TileMap tileMap) {
        //si l'entit� est pacman
        if (entity.getEntityType() == MovingEntityType.PACMAN) {
            determineNextPlayerDirection(entity, tileMap);
            //si l'entit� est un fant�me
        } else if (entity.getEntityType() == MovingEntityType.GHOST) {
            GhostAI.ghostAIStart((Ghost)entity, tileMap);

        } else {
            System.err.println("MAIS WTF LES AMIS");
        }
    }
}
