package src.engine.input;

import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 02/11/2019.
 */
public class InputKeys {

    static int keyUp = Keyboard.KEY_Z;
    static int keyDown = Keyboard.KEY_S;
    static int keyLeft = Keyboard.KEY_Q;
    static int keyRight = Keyboard.KEY_D;

    private static void setKeyUp(int keyUp) {
        InputKeys.keyUp = keyUp;
    }

    private static void setKeyDown(int keyDown) {
        InputKeys.keyDown = keyDown;
    }

    private static void setKeyLeft(int keyLeft) {
        InputKeys.keyLeft = keyLeft;
    }

    private static void setKeyRight(int keyRight) {
        InputKeys.keyRight = keyRight;
    }
}
