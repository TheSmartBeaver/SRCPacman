package src.engine.physics;

import src.GameState;
import src.Level;
import src.engine.input.GameInput;
import src.engine.input.InputGetter;
import src.entities.moving.Direction;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.moving.Pacman;
import src.entities.space.TileMap;

import java.util.List;

public class MovementPhysics {

    private static Pacman findPacman(List<MovingEntity> entities) {
        for (MovingEntity entity : entities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                return (Pacman)entity;
            }
        }
        return null;
    }

    private static Integer absoluteToRelativePosX(float posX, Integer tileWidth, Integer offsetLeft) {
        return (int)(posX - offsetLeft) / tileWidth;
    }

    private static Integer absoluteToRelativePosY(float posY, Integer tileHeight, Integer offsetUp) {
        return (int)(posY - offsetUp) / tileHeight;
    }

    private static void updatePacmanPosition(double deltaTime, Integer tileWidth, Integer tileHeight, Integer offsetLeft, Integer offsetUp, TileMap tileMap) {
        Pacman pacman = findPacman(GameState.currentEntities);
        if (pacman == null) {
            System.err.println("Pas de pacman sur la map");
            return;
        }

        Direction oldDirection = pacman.getCurrentDirection();
        if (pacman.isInMiddleOfTile()) {
            //TODO : pour mettre en place la généricité pour toutes les MovingEntity, remplacer switch (GameInput.getInput())
            //TODO : par un switch(movingEntity.getInput()) ===> la prochaine direction souhaitée de l'entité = l'input clavier
            //TODO : pour le joueur et la prochaine valeur du tableau d'inputs pour le fantôme (tableau rempli par l'algo)
            int pacmanTileY = pacman.getTileY();
            int pacmanTileX = pacman.getTileX();
            switch (GameInput.getInput()) {
                case UP:
                {
                    if (!tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) {
                        pacman.setMoving(true);
                        pacman.setCurrentDirection(Direction.UP);
                    } else {
                        if (
                                (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                        (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                        (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()))
                            pacman.setMoving(false);
                    }
                    break;
                }
                case DOWN:
                {
                    if (!tileMap.get(pacmanTileY + 1, pacmanTileX).isWall()) {
                        pacman.setMoving(true);
                        pacman.setCurrentDirection(Direction.DOWN);
                    } else {
                        if (
                                (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                        (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                        (oldDirection == Direction.DOWN && tileMap.get(pacmanTileY + 1, pacmanTileX).isWall())) {
                            pacman.setMoving(false);
                        }

                    }
                    break;
                }
                case LEFT:
                {
                    if (!tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) {
                        pacman.setMoving(true);
                        pacman.setCurrentDirection(Direction.LEFT);
                    } else {
                        if (
                                (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                        (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) ||
                                        (oldDirection == Direction.DOWN && tileMap.get(pacmanTileY + 1, pacmanTileX).isWall())) {
                            pacman.setMoving(false);
                        }

                    }
                    break;
                }
                case RIGHT:
                {
                    if (!tileMap.get(pacmanTileY, pacman.getTileX() + 1).isWall()) {
                        pacman.setMoving(true);
                        pacman.setCurrentDirection(Direction.RIGHT);
                    } else {
                        if (
                                (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                        (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) ||
                                        (oldDirection == Direction.DOWN && tileMap.get(pacmanTileY + 1, pacmanTileX).isWall()))
                            pacman.setMoving(false);
                    }
                    break;
                }
            }
            //TODO : a changer de place, ceci n'a rien a faire dans le moteur physique, c'est juste pour tester
            tileMap.get(pacmanTileY,pacmanTileX).setContent(null);
        }

        //TODO : si on veut qu'on puisse faire demi-tour instantanément, mon idée est de séparer chaque case un par un du switch
        //TODO : ci-dessous car il y a 4 demi-tours différents possibles et mettre PacMan dans un nouvel état qui fait
        //TODO : que son attribut nbPixelsMoved diminue jusqu'à 0 ou moins pour le recaler sur la case d'avant
        //TODO : PROBLEMES DE CETTE SOLUTION : ça complique le code et ça risque de mettre en péril la généricité
        //TODO : même si comme ça je vois pas trop pourquoi ni comment
        float nbPixelsMoved = pacman.getNbPixelsMoved();
        Direction currentDirection = pacman.getCurrentDirection();
        if (pacman.isMoving()) {
            switch (currentDirection) {
                case UP:
                case DOWN:
                {
                    float nbPixelsToMove = (float)((deltaTime / pacman.tileTravelTime) * tileHeight);
                    float newNbPixelsMoved = nbPixelsMoved + nbPixelsToMove;
                    if (newNbPixelsMoved >= tileHeight) {
                        pacman.setInMiddleOfTile(true);
                        pacman.setNbPixelsMoved(0);
                        if (currentDirection == Direction.UP) {
                            pacman.setPosY(Math.round(pacman.getPosY() - (tileHeight - nbPixelsMoved)));
                        } else {
                            pacman.setPosY(Math.round(pacman.getPosY() + (tileHeight - nbPixelsMoved)));
                        }
                        pacman.setTileY(absoluteToRelativePosY(pacman.getPosY(), tileHeight, offsetUp));

                    } else {
                        pacman.setInMiddleOfTile(false);
                        pacman.setNbPixelsMoved(nbPixelsMoved + nbPixelsToMove);
                        if (currentDirection == Direction.UP) {
                            pacman.setPosY(pacman.getPosY() - nbPixelsToMove);
                        } else {
                            pacman.setPosY(pacman.getPosY() + nbPixelsToMove);
                        }
                    }

                    break;
                }
                case LEFT:
                case RIGHT:
                {
                    float nbPixelsToMove = (float)((deltaTime / pacman.tileTravelTime) * tileWidth);
                    float newNbPixelsMoved = nbPixelsMoved + nbPixelsToMove;
                    if (newNbPixelsMoved >= tileWidth) {
                        pacman.setInMiddleOfTile(true);
                        pacman.setNbPixelsMoved(0);
                        //on recale la position de PacMan au centre de la case
                        if (currentDirection == Direction.LEFT) {
                            pacman.setPosX(Math.round(pacman.getPosX() - (tileWidth - nbPixelsMoved)));
                        } else {
                            pacman.setPosX(Math.round(pacman.getPosX() + (tileWidth - nbPixelsMoved)));
                        }
                        pacman.setTileX(absoluteToRelativePosX(pacman.getPosX(), tileWidth, offsetLeft));

                    } else {
                        pacman.setInMiddleOfTile(false);
                        pacman.setNbPixelsMoved(nbPixelsMoved + nbPixelsToMove);
                        //on recale la position de PacMan au centre de la case
                        if (currentDirection == Direction.LEFT) {
                            pacman.setPosX(pacman.getPosX() - nbPixelsToMove);
                        } else {
                            pacman.setPosX(pacman.getPosX() + nbPixelsToMove);
                        }
                    }

                    break;
                }
            }
        }

        System.out.println(pacman.getPosX() + " " + pacman.getPosY() + " " + pacman.getTileX() + " " + pacman.getTileY() + " " + pacman.getNbPixelsMoved() + " " + pacman.isInMiddleOfTile() + " " + deltaTime);
    }

    private static void updateGhostsPositions(double deltaTime) {

    }

    private static void ghostCollisionChecking() {

    }

    private static void wallCollisionChecking(List<MovingEntity> entities, TileMap tileMap, Integer offsetLeft, Integer offsetUp, Integer tileWidth, Integer tileHeight) {

    }

    public static void updateEntitiesPositions(double deltaTime, List<MovingEntity> entities, Level levelPlayed) {
        //CODE DE TEST
        updatePacmanPosition(deltaTime, levelPlayed.getTileWidth(), levelPlayed.getTileHeight(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileMap());
        updateGhostsPositions(deltaTime);
        ghostCollisionChecking();
        //wallCollisionChecking(entities, levelPlayed.getTileMap(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileWidth(), levelPlayed.getTileHeight());
    }
}
