package src.engine.physics;

import src.GameState;
import src.Level;
import src.UserParams;
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
                newTileY = (int)(newPacmanPosY - offsetUp + tileHeight / 2) / tileHeight;
                break;
            }
            case DOWN :
            {
                newPacmanPosY += ((deltaTime / 1000.0) * pacman.getSpeed());
                newTileY = (int)(newPacmanPosY - offsetUp - tileHeight / 2) / tileHeight;
                break;
            }
            case LEFT :
            {
                newPacmanPosX -= ((deltaTime / 1000.0) * pacman.getSpeed());
                newTileX = (int)(newPacmanPosX - offsetLeft + tileWidth / 2) / tileWidth;
                break;
            }
            case RIGHT :
            {
                newPacmanPosX += ((deltaTime / 1000.0) * pacman.getSpeed());
                newTileX = (int)(newPacmanPosX - offsetLeft - tileWidth / 2) / tileWidth;
                break;
            }
        }
        pacman.setPosX(newPacmanPosX);
        pacman.setPosY(newPacmanPosY);
        pacman.setTileX(newTileX);
        pacman.setTileY(newTileY);
    }

    private static void updatePacmanPosition(double deltaTime, TileMap tileMap, Integer offsetLeft, Integer offsetUp, Integer tileWidth, Integer tileHeight) {
        InputGetter.getInputs();
        Pacman pacman = findPacman(GameState.currentEntities);
        if (pacman == null) {
            System.err.println("Pas de pacman sur la map");
        }
        int pacmanPosTileX = pacman.getTileX();
        int pacmanPosTileY = pacman.getTileY();
        System.out.println(pacman.getPosX() + " " + pacman.getPosY() + " " + pacmanPosTileX + " " + pacmanPosTileY);

        Direction oldDirection = pacman.getCurrentDirection();
        switch(GameInput.getInput()) {
            case UP:
            {
                if (!tileMap.get(pacmanPosTileY - 1, pacmanPosTileX).isWall()) {
                    pacman.setCurrentDirection(Direction.UP);
                }
                break;
            }
            case DOWN:
            {
                if (!tileMap.get(pacmanPosTileY + 1, pacmanPosTileX).isWall()) {
                    pacman.setCurrentDirection(Direction.DOWN);
                }
                break;
            }
            case LEFT:
            {
                if (!tileMap.get(pacmanPosTileY, pacmanPosTileX - 1).isWall()) {
                    pacman.setCurrentDirection(Direction.LEFT);
                }
                break;
            }
            case RIGHT:
            {
                if (!tileMap.get(pacmanPosTileY, pacmanPosTileX + 1).isWall()) {
                    pacman.setCurrentDirection(Direction.RIGHT);
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

    private static void wallCollisionChecking(List<MovingEntity> entities) {
        //TODO : idée : faire 2 sous fonctions pour les collisions de pacman et des fantomes
        MovingEntity pacman = findPacman(GameState.currentEntities);


    }

    public static void updateEntitiesPositions(double deltaTime, List<MovingEntity> entities, Level levelPlayed) {
        //CODE DE TEST
        updatePacmanPosition(deltaTime, levelPlayed.getTileMap(), levelPlayed.getLevelScreenOffsetLeft(), levelPlayed.getLevelScreenOffsetUp(), levelPlayed.getTileWidth(), levelPlayed.getTileHeight());
        updateGhostsPositions(deltaTime);
        ghostCollisionChecking();
        wallCollisionChecking(entities);
    }
}
