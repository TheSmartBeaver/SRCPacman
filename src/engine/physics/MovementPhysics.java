package src.engine.physics;

import src.GameState;
import src.Level;
import src.engine.input.GameInput;
import src.engine.input.InputGetter;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.space.Tile;
import src.entities.space.TileMap;

import java.util.List;

public class MovementPhysics {

    private static MovingEntity findPacman(List<MovingEntity> entities) {
        for (MovingEntity entity : entities) {
            if (entity.getEntityType() == MovingEntityType.PACMAN) {
                return entity;
            }
        }
        return null;
    }

    private static void updatePacmanPosition(double deltaTime, TileMap tileMap) {
        InputGetter.getInputs();
        MovingEntity pacman = findPacman(GameState.currentEntities);
        if (pacman == null) {
            System.err.println("Pas de pacman sur la map");
        }
        float lastSquarePosX = pacman.getPosX();
        float lastSquarePosY = pacman.getPosY();
        int pacmanPosTileX = pacman.getTileX();
        int pacmanPosTileY = pacman.getTileY();

        float newPacmanPosX = lastSquarePosX;
        float newPacmanPosY = lastSquarePosY;

        switch(GameInput.getInput()) {
            case UP:
            {
                if (tileMap.get(pacmanPosTileX, pacmanPosTileY - 1).isWall())
                    break;
                newPacmanPosY -= ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
            case DOWN:
            {
                if (tileMap.get(pacmanPosTileX, pacmanPosTileY + 1).isWall())
                    break;
                newPacmanPosY += ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
            case LEFT:
            {
                if (tileMap.get(pacmanPosTileX - 1, pacmanPosTileY ).isWall())
                    break;
                newPacmanPosX -= ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
            case RIGHT:
            {
                if (tileMap.get(pacmanPosTileX + 1, pacmanPosTileY ).isWall())
                    break;
                newPacmanPosX += ((deltaTime / 1000.0) * pacman.getSpeed());
                break;
            }
        }

        pacman.setPosX(newPacmanPosX);
        pacman.setPosY(newPacmanPosY);



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
