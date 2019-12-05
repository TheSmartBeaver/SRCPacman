package src.engine.physics.generic;

import src.entities.moving.generic.Direction;
import src.entities.moving.generic.MovingEntity;
import src.entities.space.generic.TileMap;

import java.util.List;

/**
 * Created by Vincent on 22/11/2019.
 */
public class MovementPhysics {

    public static Integer absoluteToRelativePosX(float posX, Integer tileWidth, Integer offsetLeft) {
        return (int)(posX - offsetLeft) / tileWidth;
    }

    public static Integer absoluteToRelativePosY(float posY, Integer tileHeight, Integer offsetUp) {
        return (int)(posY - offsetUp) / tileHeight;
    }

    public static void outOfBoundsCheck(MovingEntity entity, TileMap tileMap) {
        int entityTileY = entity.getTileY();
        int entityTileX = entity.getTileX();
        switch (entity.getInput()) {
            case UP:
            {
                if (entityTileY == 0) {
                    entity.setMoving(false);
                }
                break;
            }
            case DOWN:
            {
                if (entityTileY == tileMap.getRowCount() - 1) {
                    entity.setMoving(false);
                }
                break;
            }
            case LEFT:
            {
                if (entityTileX == 0) {
                    entity.setMoving(false);
                }
                break;
            }
            case RIGHT:
            {
                if (entityTileX == tileMap.getColumnCount() - 1) {
                    entity.setMoving(false);
                }
                break;
            }
        }
    }

    public static void updateEntityPosition(MovingEntity entity, double deltaTime, Integer tileWidth, Integer tileHeight, Integer offsetLeft, Integer offsetUp) {
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
    }

    public static boolean checkCollision(MovingEntity entity, MovingEntity entity2, double hitbox) {
        float entityPosX = entity.getPosX();
        float entityPosY = entity.getPosY();
        float entity2PosX = entity2.getPosX();
        float entity2PosY = entity2.getPosY();
        double distance = Math.sqrt(Math.pow(entityPosX - entity2PosX, 2) + Math.pow(entityPosY - entity2PosY, 2));
        if(distance < hitbox){
            return true;
        }
        return false;
    }
}
