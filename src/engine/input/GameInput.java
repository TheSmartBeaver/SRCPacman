package src.engine.input;

/**
 * Created by Vincent on 02/11/2019.
 */
public class GameInput {

    private static Input input = Input.NONE;
    private static boolean pauseKeyDown;

    public static Input getInput() {
        return input;
    }

    public static void setInput(Input input) {
        GameInput.input = input;
    }

    public static boolean isPauseKeyDown() {
        return pauseKeyDown;
    }

    public static void setPauseKeyDown(boolean pauseKeyDown) {
        GameInput.pauseKeyDown = pauseKeyDown;
    }
}
