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

    private static void updatePacmanPosition(Pacman pacman, double deltaTime) {
        float newPacmanPosX = pacman.getPosX();
        float newPacmanPosY = pacman.getPosY();
        switch (pacman.getCurrentDirection()) {
            case UP :
            {
                newPacmanPosY -= ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
            case DOWN :
            {
                newPacmanPosY += ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
            case LEFT :
            {
                newPacmanPosX -= ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
            case RIGHT :
            {
                newPacmanPosX += ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
        }
        pacman.setPosX(newPacmanPosX);
        pacman.setPosY(newPacmanPosY);
    }

    private static void updatePacmanPosition(double deltaTime, TileMap tileMap) {
        InputGetter.getInputs();
        Pacman pacman = findPacman(GameState.currentEntities);
        if (pacman == null) {
            System.err.println("Pas de pacman sur la map");
        }
        int pacmanPosTileX = pacman.getTileX();
        int pacmanPosTileY = pacman.getTileY();

        switch(GameInput.getInput()) {
            case UP:
            {
                pacman.setNextDirection(Direction.UP);
                if (!tileMap.get(pacmanPosTileY - 1, pacmanPosTileX).isWall()) {
                    pacman.setCurrentDirection(Direction.UP);
                }
                break;
            }
            case DOWN:
            {
                pacman.setNextDirection(Direction.DOWN);
                if (!tileMap.get(pacmanPosTileY + 1, pacmanPosTileX).isWall()) {
                    pacman.setCurrentDirection(Direction.DOWN);
                }
                break;
            }
            case LEFT:
            {
                pacman.setNextDirection(Direction.LEFT);
                if (!tileMap.get(pacmanPosTileY, pacmanPosTileX - 1).isWall()) {
                    pacman.setCurrentDirection(Direction.LEFT);
                }
                break;
            }
            case RIGHT:
            {
                pacman.setNextDirection(Direction.RIGHT);
                if (!tileMap.get(pacmanPosTileY, pacmanPosTileX + 1).isWall()) {
                    pacman.setCurrentDirection(Direction.RIGHT);
                }
                break;
            }
        }
        updatePacmanPosition(pacman, deltaTime);
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
        updatePacmanPosition(deltaTime, levelPlayed.getTileMap());
        updateGhostsPositions(deltaTime);
        ghostCollisionChecking();
        wallCollisionChecking(entities);
    }
}
