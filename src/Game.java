package src;

import src.engine.input.GameInput;
import src.engine.input.InputGetter;

/**
 * Created by Vincent on 02/11/2019.
 */
public class Game {

    public static void update(long deltaTime, SquareTest squareTest) {
        //CODE DE TEST
        InputGetter.getInputs();
        float lastSquarePosX = squareTest.getPosX();
        float lastSquarePosY = squareTest.getPosY();

        float newSquarePosX = lastSquarePosX;
        float newSquarePosY = lastSquarePosY;

        switch(GameInput.getInput()) {
            case UP:
            {
                newSquarePosY -= (float) ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
            case DOWN:
            {
                newSquarePosY += (float) ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
            case LEFT:
            {
                newSquarePosX -= (float) ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
            case RIGHT:
            {
                newSquarePosX += (float) ((deltaTime / 1000.0) * squareTest.getSpeed());
                break;
            }
        }

        squareTest.setPosX(newSquarePosX);
        squareTest.setPosY(newSquarePosY);
    }
}
