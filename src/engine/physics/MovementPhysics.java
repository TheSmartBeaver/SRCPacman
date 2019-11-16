package src.engine.physics;

import javafx.util.Pair;
import src.Level;
import src.entities.moving.Direction;
import src.entities.moving.MovingEntity;
import src.entities.space.TileMap;
import src.entities.space.TileTeleport;

import java.util.List;

public class MovementPhysics {

    private static Integer absoluteToRelativePosX(float posX, Integer tileWidth, Integer offsetLeft) {
        return (int)(posX - offsetLeft) / tileWidth;
    }

    private static Integer absoluteToRelativePosY(float posY, Integer tileHeight, Integer offsetUp) {
        return (int)(posY - offsetUp) / tileHeight;
    }

    //TODO : voir si on sépare cette fonction en 2 sous-fonctions : le 1er switch = détection collision, le 2e = update position
    private static void updateEntityPosition(MovingEntity entity, double deltaTime, Integer tileWidth, Integer tileHeight, Integer offsetLeft, Integer offsetUp, TileMap tileMap) {

        Direction oldDirection = entity.getCurrentDirection();
        if (entity.isInMiddleOfTile()) {
            int entityTileY = entity.getTileY();
            int entityTileX = entity.getTileX();
            if (tileMap.get(entityTileY, entityTileX).isTeleportTile()) {
                System.out.println(entityTileX + " " + entityTileY);
                TileTeleport tileSrc = (TileTeleport)tileMap.get(entityTileY, entityTileX);
                TileTeleport tileDest = tileSrc.getTileDest();
                Pair<Integer, Integer> tileDestIndexes = tileMap.findTilePos(tileDest);
                entity.setTileY(tileDestIndexes.getKey());
                entity.setTileX(tileDestIndexes.getValue());
                entity.setPosY(offsetUp + tileHeight * tileDestIndexes.getKey() + tileHeight / 2);
                entity.setPosX(offsetLeft + tileWidth * tileDestIndexes.getValue() + tileWidth / 2);
                //entity.setNbPixelsMoved(0);
            }
            else {
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
            //TODO : a changer de place, ceci n'a rien a faire dans le moteur physique, c'est juste pour tester
            tileMap.get(entityTileY,entityTileX).setContent(null);
        }

        //TODO : si on veut qu'on puisse faire demi-tour instantanément, mon idée est de séparer chaque case un par un du switch
        //TODO : ci-dessous car il y a 4 demi-tours différents possibles et mettre PacMan dans un nouvel état qui fait
        //TODO : que son attribut nbPixelsMoved diminue jusqu'à 0 ou moins pour le recaler sur la case d'avant
        //TODO : PROBLEMES DE CETTE SOLUTION : ça complique le code et ça risque de mettre en péril la généricité
        //TODO : même si comme ça je vois pas trop pourquoi ni comment
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

    private static void updateGhostsPositions(double deltaTime) {

    }

    private static void ghostCollisionChecking() {

    }

    private static void wallCollisionChecking(List<MovingEntity> entities, TileMap tileMap, Integer offsetLeft, Integer offsetUp, Integer tileWidth, Integer tileHeight) {

    }

    public static void updateEntitiesPositions(double deltaTime, List<MovingEntity> entities, Level levelPlayed) {
        //CODE DE TEST
        for (MovingEntity entity : entities) {
            updateEntityPosition(entity, deltaTime, levelPlayed.getTileWidth(), levelPlayed.getTileHeight(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileMap());
        }
        updateGhostsPositions(deltaTime);
        ghostCollisionChecking();
        //wallCollisionChecking(entities, levelPlayed.getTileMap(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileWidth(), levelPlayed.getTileHeight());
    }
}
