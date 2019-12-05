package src.engine.physics.specific;

import src.GameState;
import src.Level;
import src.Pair;
import src.engine.ai.GhostAI;
import src.engine.input.Input;
import src.engine.physics.generic.MovementPhysics;
import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.moving.specific.Ghost;
import src.entities.moving.specific.MovingEntityType;
import src.entities.moving.specific.Pacman;
import src.entities.space.generic.TileMap;
import src.entities.space.specific.TileTeleport;

import java.util.List;

public class GamePhysicsManager {

    private static void determineEntityDirection(MovingEntity entity, TileMap tileMap) {
        MovementPhysics.outOfBoundsCheck(entity, tileMap);
        //si l'entit� est pacman
        if (entity.getEntityType() == MovingEntityType.PACMAN) {
            ((Pacman)entity).getMovementRestrictions().determineNextPlayerDirection(entity, tileMap);
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

            if( entity.getEntityType() == MovingEntityType.PACMAN){
                Pacman pacman = (Pacman)entity;
                for (MovingEntity entity2 : entities) {
                    if (entity2.getEntityType() == MovingEntityType.GHOST) {
                        boolean hasCollided = MovementPhysics.checkCollision(pacman, entity2, tileHeight / 2);
                        if (hasCollided) {
                            pacman.getGhostCollision().ghostCollision(pacman, (Ghost) entity2);
                        }
                    }
                }
            }
        }
    }
}
