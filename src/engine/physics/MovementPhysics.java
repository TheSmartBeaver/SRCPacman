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
        InputGetter.getInputs();
        Pacman pacman = findPacman(GameState.currentEntities);
        if (pacman == null) {
            System.err.println("Pas de pacman sur la map");
            return;
        }

        Integer nbPixelsMoved = pacman.getNbPixelsMoved();
        Direction currentDirection = pacman.getCurrentDirection();
        switch (currentDirection) {
            case UP:
            case DOWN:
            {
                int nbPixelsToMove = (int)((deltaTime / pacman.tileTravelTime) * tileHeight);
                //TODO : ce if juste dessous est une aberration si par exemple on joue à 120FPS, on ira 2 fois plus vite à la vitesse minimale !
                //TODO : idée pour corriger ça : sauf pour la première frame, on lui dit de se déplacer de la valeur en int de (nbPixelsDeplaces
                //TODO : de la frame courante EN FLOAT + nbPixelsDeplaces de l'ancienne EN FLOAT) - l'ancienne valeur en int de nbPixelsDeplaces
                //TODO : Pourquoi ? parce que si le calcul ci-dessus avant le cast donne 1.99 à chaque frame, ca se castera en 1 à chaque fois.
                //TODO : Donc, ce qu'on peut faire, pour minimiser ça, c'est (int)(1.99 + 1.99) = 3. Cumulé sur beaucoup de frames, je pense que ça passe.
                if (nbPixelsToMove == 0) {
                    nbPixelsToMove = 1;
                }
                int newNbPixelsMoved = nbPixelsMoved + nbPixelsToMove;
                if (newNbPixelsMoved >= tileHeight) {
                    pacman.setInMiddleOfTile(true);
                    pacman.setNbPixelsMoved(0);
                    if (currentDirection == Direction.UP) {
                        pacman.setPosY(pacman.getPosY() - (tileHeight - nbPixelsMoved));
                    } else {
                        pacman.setPosY(pacman.getPosY() + (tileHeight - nbPixelsMoved));
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
                int nbPixelsToMove = (int)((deltaTime / pacman.tileTravelTime) * tileWidth);
                if (nbPixelsToMove == 0) {
                    nbPixelsToMove = 1;
                }
                int newNbPixelsMoved = nbPixelsMoved + nbPixelsToMove;
                if (newNbPixelsMoved >= tileWidth) {
                    pacman.setInMiddleOfTile(true);
                    pacman.setNbPixelsMoved(0);
                    //on recale la position de PacMan au centre de la case
                    if (currentDirection == Direction.LEFT) {
                        pacman.setPosX(pacman.getPosX() - (tileWidth - nbPixelsMoved));
                    } else {
                        pacman.setPosX(pacman.getPosX() + (tileWidth - nbPixelsMoved));
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

        if (pacman.isInMiddleOfTile()) {
            switch (GameInput.getInput()) {
                case UP:
                {
                    if (!tileMap.get(pacman.getTileY() - 1, pacman.getTileX()).isWall()) {
                        pacman.setCurrentDirection(Direction.UP);
                    }
                    break;
                }
                case DOWN:
                {
                    if (!tileMap.get(pacman.getTileY() + 1, pacman.getTileX()).isWall()) {
                        pacman.setCurrentDirection(Direction.DOWN);
                    }
                    break;
                }
                case LEFT:
                {
                    if (!tileMap.get(pacman.getTileY(), pacman.getTileX() - 1).isWall()) {
                        pacman.setCurrentDirection(Direction.LEFT);
                    }
                    break;
                }
                case RIGHT:
                {
                    if (!tileMap.get(pacman.getTileY(), pacman.getTileX() + 1).isWall()) {
                        pacman.setCurrentDirection(Direction.RIGHT);
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
