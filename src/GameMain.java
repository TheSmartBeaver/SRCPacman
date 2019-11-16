package src;

import src.engine.graphics.Color;
import src.engine.graphics.Drawer;
import src.engine.graphics.GameRenderer;
import src.engine.input.GameInput;
import src.engine.physics.MovementPhysics;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.moving.Pacman;
import src.entities.moving.SquareTest;
import src.entities.space.Tile;
import src.entities.space.TileMap;
import src.entities.space.TileTeleport;
import src.loaders.LevelLoader;

import java.util.List;

/**
 * Created by Vincent on 02/11/2019.
 */
public class GameMain {

    private static boolean newLevel = true;

    //TODO : méthode de test, à mettre autre part ou à enlever
    private static void initEntitiesPosition() {
        TileMap tileMap = GameState.currentLevelPlayed.getTileMap();
        int rowCount = tileMap.getRowCount();
        int columnCount = tileMap.getColumnCount();
        for (int rowIndex = 0 ; rowIndex < rowCount ; ++rowIndex) {
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnIndex) {
                if (tileMap.get(rowIndex, columnIndex).isPacmanSpawnTile()) {
                    for (MovingEntity movingEntity : GameState.currentEntities) {
                        if (movingEntity.getEntityType() == MovingEntityType.PACMAN) {
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            movingEntity.setPosX(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetLeft() +
                                    GameState.currentLevelPlayed.getTileWidth() * columnIndex +
                                    GameState.currentLevelPlayed.getTileWidth() / 2
                            );
                            movingEntity.setPosY(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetUp() +
                                    GameState.currentLevelPlayed.getTileHeight() * rowIndex +
                                    GameState.currentLevelPlayed.getTileHeight() / 2
                            );
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            System.out.println(movingEntity.getPosX() + " " + movingEntity.getPosY());
                        }
                    }
                }
                else if (tileMap.get(rowIndex, columnIndex).isGhostSpawnTile()) {
                    for (MovingEntity movingEntity : GameState.currentEntities) {
                        if (movingEntity.getEntityType() == MovingEntityType.GHOST) {
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            movingEntity.setPosX(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetLeft() +
                                            GameState.currentLevelPlayed.getTileWidth() * columnIndex +
                                            GameState.currentLevelPlayed.getTileWidth() / 2
                            );
                            movingEntity.setPosY(
                                    GameState.currentLevelPlayed.getLevelScreenOffsetUp() +
                                            GameState.currentLevelPlayed.getTileHeight() * rowIndex +
                                            GameState.currentLevelPlayed.getTileHeight() / 2
                            );
                            movingEntity.setTileX(columnIndex);
                            movingEntity.setTileY(rowIndex);
                            System.out.println(movingEntity.getPosX() + " " + movingEntity.getPosY());
                        }
                    }
                }
            }
        }
    }

    public static void update(double deltaTime) {

        if (newLevel) {
            GameState.currentLevelPlayed = LevelLoader.levels.get(0);
            //DEBUG DEBUT
            TileMap tileMap = GameState.currentLevelPlayed.getTileMap();
            for (int i = 0 ; i < tileMap.getRowCount() ; ++i) {
                for (int j = 0 ; j < tileMap.getColumnCount() ; ++j) {
                    if (tileMap.get(i,j).isTeleportTile()) {
                        TileTeleport tile = (TileTeleport)tileMap.get(i,j);
                        System.out.println(tile.hashCode() + " " + i + " " + j + " " + tile.getTileDest().hashCode());
                    }
                }
            }
            //DEBUG FIN
            GameState.currentEntities.add(new Pacman(1,3.0f));
            GameState.currentEntities.add(new Pacman(2,3.0f));
            initEntitiesPosition();
            newLevel = false;
        }

        List<Pacman> pacmans = MovingEntity.findPacmanEntities(GameState.currentEntities);
        for (Pacman pacman : pacmans) {
            if (pacman.getId() == 1) {
                pacman.setInput(GameInput.getInputFirst());
            }
            else if (pacman.getId() == 2) {
                pacman.setInput(GameInput.getInputSecond());
            }
        }

        MovementPhysics.updateEntitiesPositions(deltaTime, GameState.currentEntities, GameState.currentLevelPlayed);
    }

    public static void render(){

        //TODO : idée : faire une classe GlobalRenderer qui prend tous les paramètres qu'il faut et appeler dedans les sous-renderers ?
        GameRenderer.renderLevel(GameState.currentLevelPlayed, GameState.currentEntities);

    }
}
