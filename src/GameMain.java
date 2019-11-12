package src;

import src.engine.graphics.Color;
import src.engine.graphics.Drawer;
import src.engine.graphics.MovingEntityRenderer;
import src.engine.graphics.GameRenderer;
import src.engine.input.GameInput;
import src.engine.input.InputGetter;
import src.entities.moving.MovingEntity;
import src.entities.moving.MovingEntityType;
import src.entities.moving.SquareTest;
import src.entities.space.Tile;
import src.entities.space.TileMap;
import src.loaders.LevelLoader;

import java.util.ArrayList;
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
            for (int columnIndex = 0 ; columnIndex < columnCount ; ++columnCount) {
                if (tileMap.get(rowIndex, columnIndex).isPacmanSpawnTile()) {
                    for (MovingEntity movingEntity : GameState.currentEntities) {
                        if (movingEntity.getEntityType() == MovingEntityType.TEST) {
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
                        }
                    }
                }
            }
        }
    }

    public static void update(double deltaTime, SquareTest squareTest) {

        if (newLevel) {

            GameState.currentLevelPlayed = LevelLoader.levels.get(1);
            GameState.currentEntities.add(new SquareTest());
            newLevel = false;
        }

        //TODO : faire un objet GameState au lieu d'un truc statique pour passer en paramètre les entités à cette fonction ?
        //initEntitiesPosition();


        //CODE DE TEST
        InputGetter.getInputs();
        float lastSquarePosX = squareTest.getPosX();
        float lastSquarePosY = squareTest.getPosY();

        float newSquarePosX = lastSquarePosX;
        float newSquarePosY = lastSquarePosY;

        switch(GameInput.getInput()) {
            case UP:
            {
                newSquarePosY -= ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
            case DOWN:
            {
                newSquarePosY += ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
            case LEFT:
            {
                newSquarePosX -= ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
            case RIGHT:
            {
                newSquarePosX += ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
        }

        squareTest.setPosX(newSquarePosX);
        squareTest.setPosY(newSquarePosY);
    }

    public static void render(SquareTest squareTest){

        //TODO : idée : faire une classe GlobalRenderer qui prend tous les paramètres qu'il faut et appeler dedans les sous-renderers ?
        GameRenderer.renderLevel(GameState.currentLevelPlayed, GameState.currentEntities);

        float x = squareTest.getPosX();
        float y = squareTest.getPosY();
        int size = squareTest.getLength();

        //TODO : ceci est du code de test: tous les appels � Drawer doivent se faire normalement dans le package engine.graphics
        Drawer.drawRect((int)x, (int)y, size, size, new Color(0.5f, 0.2f, 0.9f));
    }
}