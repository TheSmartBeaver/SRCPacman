package src;

import src.engine.graphics.Color;
import src.engine.graphics.Drawer;
import src.engine.graphics.MovingEntityRenderer;
import src.engine.graphics.LevelRenderer;
import src.engine.input.GameInput;
import src.engine.input.InputGetter;
import src.entities.moving.MovingEntity;
import src.entities.moving.SquareTest;
import src.loaders.LevelLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vincent on 02/11/2019.
 */
public class GameMain {

    private static Level oldLevel = null;
    private static Level currentLevel = null;
    //TODO : booléen de test, à enlever plus tard certainement
    private static boolean newLevel = true;

    //TODO : idée : la liste d'entités serait dans la classe Level (chaque level a ses propres entités)
    private static List<MovingEntity> entities = new ArrayList<>();

    public static void update(double deltaTime, SquareTest squareTest) {
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

        if (newLevel) {
            entities.add(squareTest);
            newLevel = false;
        }

        //TODO: idée : la classe Level possède une liste d'entités propres à elle.
        currentLevel = LevelLoader.levels.get(1);
        //TODO : idée : faire une classe GlobalRenderer qui prend tous les paramètres qu'il faut et appeler dedans les sous-renderers ?
        LevelRenderer.renderLevel(currentLevel);
        //oldLevel = currentLevel;
        MovingEntityRenderer.renderEntities(currentLevel);

        float x = squareTest.getPosX();
        float y = squareTest.getPosY();
        int size = squareTest.getLength();

        //TODO : ceci est du code de test: tous les appels � Drawer doivent se faire normalement dans le package engine.graphics
        Drawer.drawRect((int)x, (int)y, size, size, new Color(0.5f, 0.2f, 0.9f));
    }
}
