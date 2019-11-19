package src.engine.physics;

import javafx.util.Pair;
import src.Level;
import src.engine.input.Input;
import src.entities.moving.Direction;
import src.entities.moving.Ghost;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.space.TileMap;
import src.entities.space.TileTeleport;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;

public class MovementPhysics {

    /*A quoi correspond relative et absolute*/
    static Integer absoluteToRelativePosX(float posX, Integer tileWidth, Integer offsetLeft) {
        return (int)(posX - offsetLeft) / tileWidth;
    }

    static Integer absoluteToRelativePosY(float posY, Integer tileHeight, Integer offsetUp) {
        return (int)(posY - offsetUp) / tileHeight;
    }

    //TODO:FIXER entités passant sur case de téléportation

    static List<Input> determinePossibleDirections(Ghost ghost, TileMap tileMap) {
        List<Input> possibleInputs = new ArrayList<>();
        /*Récup coordonnées de la Tile sur laquelle est le fantôme*/
        int tileXGhost = ghost.getTileX();
        int tileYGhost = ghost.getTileY();
        switch (ghost.getCurrentDirection()) {
            case UP:
            {
                /*Pour chaque direction prise par fantôme, test si case direct dans autre direction est vide pour ajouter une possible autre dirction*/
                if (!tileMap.get(tileYGhost - 1, tileXGhost).isWall()) {
                    possibleInputs.add(Input.UP);
                }
                if (!tileMap.get(tileYGhost, tileXGhost - 1).isWall()) {
                    possibleInputs.add(Input.LEFT);
                }
                if (!tileMap.get(tileYGhost, tileXGhost + 1).isWall()) {
                    possibleInputs.add(Input.RIGHT);
                }
                break;
            }
            case DOWN:
            {
                if (!tileMap.get(tileYGhost + 1, tileXGhost).isWall()) {
                    possibleInputs.add(Input.DOWN);
                }
                if (!tileMap.get(tileYGhost, tileXGhost - 1).isWall()) {
                    possibleInputs.add(Input.LEFT);
                }
                if (!tileMap.get(tileYGhost, tileXGhost + 1).isWall()) {
                    possibleInputs.add(Input.RIGHT);
                }
                break;
            }
            case LEFT:
            {
                if (!tileMap.get(tileYGhost + 1, tileXGhost).isWall()) {
                    possibleInputs.add(Input.DOWN);
                }
                if (!tileMap.get(tileYGhost, tileXGhost - 1).isWall()) {
                    possibleInputs.add(Input.LEFT);
                }
                if (!tileMap.get(tileYGhost - 1, tileXGhost).isWall()) {
                    possibleInputs.add(Input.UP);
                }
                break;
            }
            case RIGHT:
            {
                if (!tileMap.get(tileYGhost - 1, tileXGhost).isWall()) {
                    possibleInputs.add(Input.UP);
                }
                if (!tileMap.get(tileYGhost, tileXGhost + 1).isWall()) {
                    possibleInputs.add(Input.RIGHT);
                }
                if (!tileMap.get(tileYGhost + 1, tileXGhost).isWall()) {
                    possibleInputs.add(Input.DOWN);
                }
                break;
            }
        }
        return possibleInputs;
    }

    /*Input très similaire à Direction*/
    static Direction convertInputToDirection(Input input) {
        switch (input) {
            case UP:
            {
                return Direction.UP;
            }
            case DOWN:
            {
                return Direction.DOWN;
            }
            case LEFT:
            {
                return Direction.LEFT;
            }
            case RIGHT:
            {
                return Direction.RIGHT;
            }
            default:
                return null;
        }
    }

    //TODO:Pourquoi up et left vide ? TileX,TileY
    /*Renvoie true si l'entité se trouve à une intersection*/
    static boolean isEntityAtIntersection(MovingEntity entity, TileMap tileMap) {
        int entityTileX = entity.getTileX();
        int entityTileY = entity.getTileY();
        switch (entity.getCurrentDirection()) {
            case UP:
            case DOWN:
            {
                if (!tileMap.get(entityTileY, entityTileX - 1).isWall() || !tileMap.get(entityTileY, entityTileX + 1).isWall()) {
                    return true;
                }
                break;
            }
            case LEFT:
            case RIGHT:
            {
                if (!tileMap.get(entityTileY - 1, entityTileX).isWall() || !tileMap.get(entityTileY + 1, entityTileX).isWall()) {
                    return true;
                }
                break;
            }
        }
        return false;
    }

    private static void determineGhostPath(Ghost ghost, List<Input> possibleInputs) {
        //pas besoin de recalculer l'algo s'il n'y a qu'un seul chemin à prendre
        if (possibleInputs.size() == 1) { /*Possibles directions prise par fantômes*/
            ArrayDeque<Input> nextMandatoryInput = new ArrayDeque<>();
            nextMandatoryInput.push(possibleInputs.get(0));
            ghost.setInputs(nextMandatoryInput); /*Insert direction OBLIGATOIRE à prendre pour fantôme ?*/
        }
        //s'il y a plusieurs chemins à cette intersection, on refait l'algo
        else {
            ghost.getContext().executeStrategy(); /*On lance cet algo jusqu'à ce possibleInputs.size() == 1 ??????*/
        }
    }

    /*UP <--> DOWN    LEFT <--> RIGHT*/
    private static void reverseEntityPosition(MovingEntity movingEntity) {
        switch (movingEntity.getCurrentDirection()) {
            case UP:
            {
                movingEntity.setCurrentDirection(Direction.DOWN);
                break;
            }
            case DOWN:
            {
                movingEntity.setCurrentDirection(Direction.UP);
                break;
            }
            case LEFT:
            {
                movingEntity.setCurrentDirection(Direction.RIGHT);
                break;
            }
            case RIGHT:
            {
                movingEntity.setCurrentDirection(Direction.LEFT);
                break;
            }
        }
    }

    private static void determineNextPacmanDirection(MovingEntity pacman, TileMap tileMap) {
        Direction oldDirection = pacman.getCurrentDirection();
        int pacmanTileY = pacman.getTileY();
        int pacmanTileX = pacman.getTileX();
        switch (pacman.getInput()) {
            case UP:
            {
                /*si case du haut vide, aller directement en haut ????*/
                if (!tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()) {
                    pacman.setMoving(true); /*Pacman peut bouger ??*/
                    pacman.setCurrentDirection(Direction.UP);
                } else {
                    if (
                            (oldDirection == Direction.LEFT && tileMap.get(pacmanTileY, pacmanTileX - 1).isWall()) ||
                                    (oldDirection == Direction.RIGHT && tileMap.get(pacmanTileY, pacmanTileX + 1).isWall()) ||
                                    (oldDirection == Direction.UP && tileMap.get(pacmanTileY - 1, pacmanTileX).isWall()))
                        pacman.setMoving(false);/*Si Pacman ne peut pas tourner à cause d'un mur, set Moving à false*/
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
    }

    //TODO : voir si on sépare cette fonction en 2 sous-fonctions : le 1er switch = détection collision, le 2e = update position
    //TODO : Régler problème téléportation entité, crash
    private static void updateEntityPosition(MovingEntity entity, double deltaTime, Integer tileWidth, Integer tileHeight, Integer offsetLeft, Integer offsetUp, TileMap tileMap) {

        if (entity.isInMiddleOfTile()) { /*Test si entité au centre de la tile*/
            int entityTileY = entity.getTileY();
            int entityTileX = entity.getTileX();
            //si l'entité est sur une case de TELEPORTATION
            if (tileMap.get(entityTileY, entityTileX).isTeleportTile()) {
                TileTeleport tileSrc = (TileTeleport)tileMap.get(entityTileY, entityTileX);
                TileTeleport tileDest = tileSrc.getTileDest();
                Pair<Integer, Integer> tileDestIndexes = tileMap.findTilePos(tileDest);
                entity.setTileY(tileDestIndexes.getKey()); /*getKey ET/OU getValue*/
                entity.setTileX(tileDestIndexes.getValue()); /*On set nouvelle tile après téléportation*/
                entity.setPosY(offsetUp + tileHeight * tileDestIndexes.getKey() + tileHeight / 2); /*On set nouvelle pos XY de l'entité*/
                entity.setPosX(offsetLeft + tileWidth * tileDestIndexes.getValue() + tileWidth / 2);
                //entity.setNbPixelsMoved(0);
            }
            else {
                //si l'entité est pacman
                if (entity.getEntityType() == MovingEntityType.PACMAN) {
                    determineNextPacmanDirection(entity, tileMap);
                //si l'entité est un fantôme
                } else if (entity.getEntityType() == MovingEntityType.GHOST) {
                    Ghost ghost = (Ghost)entity;
                    List<Input> possibleInputs = determinePossibleDirections(ghost, tileMap);
                    if (possibleInputs.size() == 0) {
                        reverseEntityPosition(ghost); /*Si fantôme bloqué ??*/
                    }
                    //si le fantôme est à une intersection
                    else if (isEntityAtIntersection(ghost, tileMap)) {
                        //si il a encore des inputs en mémoire, il va dans la direction adéquate
                        if (!ghost.getInputs().isEmpty()) {
                            ghost.setCurrentDirection(convertInputToDirection(ghost.getInputs().poll()));
                        }
                        //si sa liste d'inputs est vide, on la recalcule en réexécutant son algo
                        else {
                            determineGhostPath(ghost, possibleInputs);
                            ghost.setCurrentDirection(convertInputToDirection(ghost.getInputs().poll()));
                        }
                    }

                } else {
                    System.err.println("MAIS WTF LES AMIS");
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
                        entity.setInMiddleOfTile(true); /*L'entité est considéré comme entré dans la tile si newNbPixelsMoved >= taille de la Tile précédente ??????*/
                        entity.setNbPixelsMoved(0);
                        if (currentDirection == Direction.UP) {
                            /*CONDITION INATEIGNABLE toujours DOWN ? ???*/
                            entity.setPosY(Math.round(entity.getPosY() - (tileHeight - nbPixelsMoved)));
                        } else {
                            entity.setPosY(Math.round(entity.getPosY() + (tileHeight - nbPixelsMoved))); /*Calcule new Y*/
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
