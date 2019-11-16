/*package src.engine.physics;

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

public class MovementPhysicsOld {

    static boolean tileSwitched = false;

    private static Pacman findPacman(List<MovingEntity> entities) {
        for (MovingEntity entity : entities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                return (Pacman)entity;
            }
        }
        return null;
    }

    private static Integer absoluteToRelativePosX(float newPacmanPosX, int offsetLeft, int tileWidth, Direction direction) {
        if (direction == Direction.LEFT) {
            return (int)(newPacmanPosX - offsetLeft + tileWidth / 2) / tileWidth;
        } else if (direction == Direction.RIGHT){
            return (int)(newPacmanPosX - offsetLeft - tileWidth / 2) / tileWidth;
        }
        return null;
    }

    private static Integer absoluteToRelativePosY(float newPacmanPosY, int offsetUp, int tileHeight, Direction direction) {
        if (direction == Direction.UP) {
            return (int)(newPacmanPosY - offsetUp + tileHeight / 2) / tileHeight;
        } else if (direction == Direction.DOWN) {
            return (int)(newPacmanPosY - offsetUp - tileHeight / 2) / tileHeight;
        }
        return null;
    }

    //TODO : voir si on peut pas utiliser la même méthode pour toutes les entités mouvantes ?
    private static void updatePosition(Pacman pacman, double deltaTime, Integer offsetLeft, Integer offsetUp, Integer tileWidth, Integer tileHeight) {
        float newPacmanPosX = pacman.getPosX();
        float newPacmanPosY = pacman.getPosY();
        int newTileX = pacman.getTileX();
        int newTileY = pacman.getTileY();
        switch (pacman.getCurrentDirection()) {
            case UP :
            {
                newPacmanPosY -= ((deltaTime / 1000.0) * pacman.getSpeed());
                //newTileY = absoluteToRelativePosY(newPacmanPosY, offsetUp, tileHeight, Direction.UP);
                newTileY = (int)(newPacmanPosY - offsetUp + tileHeight / 2) / tileHeight;
                break;
            }
            case DOWN :
            {
                newPacmanPosY += ((deltaTime / 1000.0) * pacman.getSpeed());
                //newTileY = absoluteToRelativePosY(newPacmanPosY, offsetUp, tileHeight, Direction.DOWN);
                newTileY = (int)(newPacmanPosY - offsetUp - tileHeight / 2) / tileHeight;
                break;
            }
            case LEFT :
            {
                newPacmanPosX -= ((deltaTime / 1000.0) * pacman.getSpeed());
                //newTileX = absoluteToRelativePosX(newPacmanPosX, offsetLeft, tileWidth, Direction.LEFT);
                newTileX = (int)(newPacmanPosX - offsetLeft + tileWidth / 2) / tileWidth;
                break;
            }
            case RIGHT :
            {
                newPacmanPosX += ((deltaTime / 1000.0) * pacman.getSpeed());
                //newTileX = absoluteToRelativePosX(newPacmanPosX, offsetLeft, tileWidth, Direction.RIGHT);
                newTileX = (int)(newPacmanPosX - offsetLeft - tileWidth / 2) / tileWidth;
                break;
            }
        }
        pacman.setPosX(newPacmanPosX);
        pacman.setPosY(newPacmanPosY);
        if (pacman.isHasChangedDirection() && newTileX == pacman.getTileX() && newTileY == pacman.getTileY()) {
            pacman.setHasChangedDirection(false);
        }
        if (!pacman.isHasChangedDirection()) {
            pacman.setTileX(newTileX);
            pacman.setTileY(newTileY);
            tileSwitched = true;
        } else {
            tileSwitched = false;
        }
    }

    private static void updatePacmanPosition(double deltaTime, TileMap tileMap, Integer offsetLeft, Integer offsetUp, Integer tileWidth, Integer tileHeight) {
        InputGetter.getInputs();
        Pacman pacman = findPacman(GameState.currentEntities);
        if (pacman == null) {
            System.err.println("Pas de pacman sur la map");
        }
        int pacmanPosTileX = pacman.getTileX();
        int pacmanPosTileY = pacman.getTileY();
        //System.out.println(pacman.getPosX() + " " + pacman.getPosY() + " " + pacmanPosTileX + " " + pacmanPosTileY);

        System.out.println(tileSwitched);
        Direction oldDirection = pacman.getCurrentDirection();
        switch(GameInput.getInput()) {
            case UP:
            {
                if (!pacman.isHasChangedDirection() || oldDirection == Direction.DOWN) {
                    if (!tileMap.get(pacmanPosTileY - 1, pacmanPosTileX).isWall()) {
                        if (oldDirection != Direction.UP) {
                            if (!tileSwitched) {
                                if (!(oldDirection == Direction.RIGHT && tileMap.get(pacmanPosTileY - 1, pacmanPosTileX + 1).isWall())) {
                                    if (!(oldDirection == Direction.LEFT && tileMap.get(pacmanPosTileY - 1, pacmanPosTileX - 1).isWall())) {
                                        pacman.setHasChangedDirection(true);
                                        pacman.setTileY(pacmanPosTileY - 1);
                                        tileSwitched = true;
                                        pacman.setCurrentDirection(Direction.UP);
                                    }
                                }
                            } else {
                                pacman.setHasChangedDirection(true);
                                pacman.setTileY(pacmanPosTileY - 1);
                                pacman.setCurrentDirection(Direction.UP);
                                tileSwitched = false;
                            }
                        }
                    }
                }
                break;
            }
            case DOWN:
            {
                if (!pacman.isHasChangedDirection() || oldDirection == Direction.UP) {
                    if (!tileMap.get(pacmanPosTileY + 1, pacmanPosTileX).isWall()) {
                        if (oldDirection != Direction.DOWN) {
                            pacman.setHasChangedDirection(true);
                            pacman.setTileY(pacmanPosTileY + 1);
                            pacman.setCurrentDirection(Direction.DOWN);
                        }
                    }
                }
                break;
            }
            case LEFT:
            {
                if (!pacman.isHasChangedDirection() || oldDirection == Direction.RIGHT) {
                    if (!tileMap.get(pacmanPosTileY, pacmanPosTileX - 1).isWall()) {
                        if (oldDirection != Direction.LEFT) {
                            pacman.setHasChangedDirection(true);
                            pacman.setTileX(pacmanPosTileX - 1);
                            pacman.setCurrentDirection(Direction.LEFT);
                        }
                    }
                }
                break;
            }
            case RIGHT:
            {
                if (!pacman.isHasChangedDirection() || oldDirection == Direction.LEFT) {
                    if (!tileMap.get(pacmanPosTileY, pacmanPosTileX + 1).isWall()) {
                        if (oldDirection != Direction.RIGHT) {
                            pacman.setHasChangedDirection(true);
                            pacman.setTileX(pacmanPosTileX + 1);
                            pacman.setCurrentDirection(Direction.RIGHT);
                        }
                    }
                }
                break;
            }
        }

        updatePosition(pacman, deltaTime, offsetLeft, offsetUp, tileWidth, tileHeight);
    }

    private static void updateGhostsPositions(double deltaTime) {

    }

    private static void ghostCollisionChecking() {

    }

    private static void wallCollisionChecking(List<MovingEntity> entities, TileMap tileMap, Integer offsetLeft, Integer offsetUp, Integer tileWidth, Integer tileHeight) {
        //TODO : idée : faire 2 sous fonctions pour les collisions de pacman et des fantomes
        MovingEntity pacman = findPacman(entities);
        int pacmanTileX = (int)(pacman.getPosX() - offsetLeft) / tileWidth;
        int pacmanTileY = (int)(pacman.getPosY() - offsetUp) / tileHeight;;
        System.out.println(pacmanTileX + " " + pacmanTileY);
        switch (pacman.getCurrentDirection()) {
            case UP :
            {
                if (tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) {
                    pacman.setSpeed(0);
                } else {
                    pacman.setSpeed(60.0f);
                }
                break;
            }
            case DOWN :
            {
                if (tileMap.get(pacmanTileY + 1, pacmanTileX).isWall()) {
                    pacman.setSpeed(0);
                } else {
                    pacman.setSpeed(60.0f);
                }
                break;
            }
            case LEFT :
            {
                if (tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) {
                    pacman.setSpeed(0);
                } else {
                    pacman.setSpeed(60.0f);
                }
                break;
            }
            case RIGHT :
            {
                if (tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) {
                    pacman.setSpeed(0);
                } else {
                    pacman.setSpeed(60.0f);
                }
                break;
            }
        }
    }

    public static void updateEntitiesPositions(double deltaTime, List<MovingEntity> entities, Level levelPlayed) {
        //CODE DE TEST
        updatePacmanPosition(deltaTime, levelPlayed.getTileMap(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileWidth(), levelPlayed.getTileHeight());
        updateGhostsPositions(deltaTime);
        ghostCollisionChecking();
        //wallCollisionChecking(entities, levelPlayed.getTileMap(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileWidth(), levelPlayed.getTileHeight());
    }
}*/
