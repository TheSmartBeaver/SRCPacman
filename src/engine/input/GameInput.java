package src.engine.input;

/**
 * Created by Vincent on 02/11/2019.
 */
public class GameInput {

    private static Input inputFirst = Input.NONE; /*1er imput*/
    private static Input inputSecond = Input.NONE; /*2ème imput*/
    private static boolean pauseKeyDown;

    public static Input getInputFirst() {
        return inputFirst;
    }

    public static void setInputFirst(Input inputFirst) {
        GameInput.inputFirst = inputFirst;
    }

    public static Input getInputSecond() {
        return inputSecond;
    }

    public static void setInputSecond(Input inputSecond) {
        GameInput.inputSecond = inputSecond;
    }

    public static boolean isPauseKeyDown() {
        return pauseKeyDown;
    }

    public static void setPauseKeyDown(boolean pauseKeyDown) {
        GameInput.pauseKeyDown = pauseKeyDown;
    }
}
