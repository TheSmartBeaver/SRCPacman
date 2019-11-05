package src;

import src.engine.graphics.Color;
import src.engine.graphics.Drawer;
import src.engine.graphics.LevelRenderer;
import src.engine.input.GameInput;
import src.engine.input.InputGetter;
import src.loaders.LevelLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2f;

/**
 * Created by Vincent on 02/11/2019.
 */
public class Game {

    private static Level oldLevel = null;
    private static Level currentLevel = null;

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

        currentLevel = LevelLoader.levels.get(0);
        //if (oldLevel != currentLevel) {
            //render le currentLevel
            LevelRenderer.renderLevel(currentLevel);
            oldLevel = currentLevel;
            //System.out.println(LevelRenderer.dump());
       // }

        float x = squareTest.getPosX();
        float y = squareTest.getPosY();
        int size = squareTest.getLength();

        //TODO : ceci est du code de test: tous les appels à Drawer doivent se faire normalement dans le package engine.graphics
        Drawer.drawRect((int)x, (int)y, size, size, new Color(0.5f, 0.2f, 0.9f));
    }
}
