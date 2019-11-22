package src.engine.physics.specific;

import src.Level;
import src.Pair;
import src.engine.ai.GhostAI;
import src.engine.physics.generic.MovementPhysics;
import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.MovingEntityType;
import src.entities.space.generic.TileMap;
import src.entities.space.specific.TileTeleport;

import java.util.List;

public class GamePhysicsManager {

    private static void determineNextPlayerDirection(MovingEntity entity, TileMap tileMap) {
        Direction oldDirection = entity.getCurrentDirection();
        int entityTileY = entity.getTileY();
        int entityTileX = entity.getTileX();
        switch (entity.getInput()) {
            case UP:
            {
                if (!tileMap.get(entityTileY - 1, entityTileX).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.UP);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(entityTileY, entityTileX - 1).isWall()) ||
                                    (oldDirection == Direction.RIGHT && tileMap.get(entityTileY, entityTileX + 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(entityTileY - 1, entityTileX).isWall()))
                        entity.setMoving(false);
                }
                break;
            }
            case DOWN:
            {
                if (!tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.DOWN);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(entityTileY, entityTileX - 1).isWall()) ||
                                    (oldDirection == Direction.RIGHT && tileMap.get(entityTileY, entityTileX + 1).isWall()) ||
                                    (oldDirection == Direction.DOWN && tileMap.get(entityTileY + 1, entityTileX).isWall())) {
                        entity.setMoving(false);
                    }

                }
                break;
            }
            case LEFT:
            {
                if (!tileMap.get(entityTileY, entityTileX - 1).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.LEFT);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(entityTileY, entityTileX - 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(entityTileY - 1, entityTileX).isWall()) ||
                                    (oldDirection == Direction.DOWN && tileMap.get(entityTileY + 1, entityTileX).isWall())) {
                        entity.setMoving(false);
                    }

                }
                break;
            }
            case RIGHT:
            {
                if (!tileMap.get(entityTileY, entity.getTileX() + 1).isWall()) {
                    entity.setMoving(true);
                    entity.setCurrentDirection(Direction.RIGHT);
                } else {
                    if (
                            (oldDirection == Direction.RIGHT && tileMap.get(entityTileY, entityTileX + 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(entityTileY - 1, entityTileX).isWall()) ||
                                    (oldDirection == Direction.DOWN && tileMap.get(entityTileY + 1, entityTileX).isWall()))
                        entity.setMoving(false);
                }
                break;
            }
        }
    }

    private static void determineEntityDirection(MovingEntity entity, TileMap tileMap) {
        //si l'entit� est pacman
        if (entity.getEntityType() == MovingEntityType.PACMAN) {
            determineNextPlayerDirection(entity, tileMap);
            //si l'entit� est un fant�me
        } else if (entity.getEntityType() == MovingEntityType.GHOST) {
            GhostAI.ghostAIStart((Ghost)entity, tileMap);

        }
    }

    public static void updateEntitiesPositions(double deltaTime, List<MovingEntity> entities, Level levelPlayed) {

        TileMap tileMap = levelPlayed.getTileMap();
        int offsetUp = levelPlayed.getLevelScreenOffsetUp();
        int offsetLeft = levelPlayed.getLevelScreenOffsetLeft();
        int tileHeight = levelPlayed.getTileHeight();
        int tileWidth = levelPlayed.getTileWidth();

        for (MovingEntity entity : entities) {
            if (entity.isInMiddleOfTile()) {
                int entityTileY = entity.getTileY();
                int entityTileX = entity.getTileX();
                //si l'entité est sur une case de téléportation
                if (tileMap.get(entityTileY, entityTileX).isTeleportTile()) {
                    TileTeleport tileSrc = (TileTeleport)tileMap.get(entityTileY, entityTileX);
                    TileTeleport tileDest = tileSrc.getTileDest();
                    Pair tileDestIndexes = tileMap.findTilePos(tileDest);
                    entity.setTileY(tileDestIndexes.getX());
                    entity.setTileX(tileDestIndexes.getY());
                    entity.setPosY(offsetUp + tileHeight * tileDestIndexes.getX() + tileHeight / 2);
                    entity.setPosX(offsetLeft + tileWidth * tileDestIndexes.getY() + tileWidth / 2);
                }
                else {
                    determineEntityDirection(entity, tileMap);
                }
            }
            MovementPhysics.updateEntityPosition(entity, deltaTime, levelPlayed.getTileWidth(), levelPlayed.getTileHeight(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp());
        }
    }
}
