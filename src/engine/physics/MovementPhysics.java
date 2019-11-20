package src.engine.physics;

import src.Level;
import src.Pair;
import src.engine.ai.GhostAI;
import src.entities.moving.Direction;
import src.entities.moving.Ghost;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.space.TileMap;
import src.entities.space.TileTeleport;

import java.util.List;

public class MovementPhysics {

    static Integer absoluteToRelativePosX(float posX, Integer tileWidth, Integer offsetLeft) {
        return (int)(posX - offsetLeft) / tileWidth;
    }

    static Integer absoluteToRelativePosY(float posY, Integer tileHeight, Integer offsetUp) {
        return (int)(posY - offsetUp) / tileHeight;
    }

    private static void updateEntityPosition(MovingEntity entity, double deltaTime, Integer tileWidth, Integer tileHeight, Integer offsetLeft, Integer offsetUp, TileMap tileMap) {

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
                //entity.setNbPixelsMoved(0);
            }
            else {
                DirectionManager.determineEntityDirection(entity, tileMap);
            }
            //TODO : a changer de place, ceci n'a rien a faire dans le moteur physique, c'est juste pour tester
            //tileMap.get(entityTileY,entityTileX).setContent(null);
        }

        float nbPixelsMoved = entity.getNbPixelsMoved();
        Direction currentDirection = entity.getCurrentDirection();
        if (entity.isMoving()) {
            switch (currentDirection) {
                case UP:
                case DOWN:
                {
                    float nbPixelsToMove = (float)((deltaTime / entity.tileTravelTime) * tileHeight);
                    float newNbPixelsMoved = nbPixelsMoved + nbPixelsToMove;
                    if (newNbPixelsMoved >= tileHeight) {
                        entity.setInMiddleOfTile(true);
                        entity.setNbPixelsMoved(0);
                        if (currentDirection == Direction.UP) {
                            entity.setPosY(Math.round(entity.getPosY() - (tileHeight - nbPixelsMoved)));
                        } else {
                            entity.setPosY(Math.round(entity.getPosY() + (tileHeight - nbPixelsMoved)));
                        }
                        entity.setTileY(absoluteToRelativePosY(entity.getPosY(), tileHeight, offsetUp));

                    } else {
                        entity.setInMiddleOfTile(false);
                        entity.setNbPixelsMoved(nbPixelsMoved + nbPixelsToMove);
                        if (currentDirection == Direction.UP) {
                            entity.setPosY(entity.getPosY() - nbPixelsToMove);
                        } else {
                            entity.setPosY(entity.getPosY() + nbPixelsToMove);
                        }
                    }

                    break;
                }
                case LEFT:
                case RIGHT:
                {
                    float nbPixelsToMove = (float)((deltaTime / entity.tileTravelTime) * tileWidth);
                    float newNbPixelsMoved = nbPixelsMoved + nbPixelsToMove;
                    if (newNbPixelsMoved >= tileWidth) {
                        entity.setInMiddleOfTile(true);
                        entity.setNbPixelsMoved(0);
                        //on recale la position de entity au centre de la case
                        if (currentDirection == Direction.LEFT) {
                            entity.setPosX(Math.round(entity.getPosX() - (tileWidth - nbPixelsMoved)));
                        } else {
                            entity.setPosX(Math.round(entity.getPosX() + (tileWidth - nbPixelsMoved)));
                        }
                        entity.setTileX(absoluteToRelativePosX(entity.getPosX(), tileWidth, offsetLeft));

                    } else {
                        entity.setInMiddleOfTile(false);
                        entity.setNbPixelsMoved(nbPixelsMoved + nbPixelsToMove);
                        //on recale la position de entity au centre de la case
                        if (currentDirection == Direction.LEFT) {
                            entity.setPosX(entity.getPosX() - nbPixelsToMove);
                        } else {
                            entity.setPosX(entity.getPosX() + nbPixelsToMove);
                        }
                    }

                    break;
                }
            }
        }

        //System.out.println(entity.getPosX() + " " + entity.getPosY() + " " + entity.getTileX() + " " + entity.getTileY() + " " + entity.getNbPixelsMoved() + " " + entity.isInMiddleOfTile() + " " + deltaTime);
    }

    public static void updateEntitiesPositions(double deltaTime, List<MovingEntity> entities, Level levelPlayed) {
        //CODE DE TEST
        for (MovingEntity entity : entities) {
            updateEntityPosition(entity, deltaTime, levelPlayed.getTileWidth(), levelPlayed.getTileHeight(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileMap());
        }
    }
}
