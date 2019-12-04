package src.engine.input;

import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 02/11/2019.
 */
public class InputKeys {

    static int keyUpFirst = Keyboard.KEY_Z;
    static int keyDownFirst = Keyboard.KEY_S;
    static int keyLeftFirst = Keyboard.KEY_Q;
    static int keyRightFirst = Keyboard.KEY_D;

    static int keyUpSecond = Keyboard.KEY_UP;
    static int keyDownSecond = Keyboard.KEY_DOWN;
    static int keyLeftSecond = Keyboard.KEY_LEFT;
    static int keyRightSecond = Keyboard.KEY_RIGHT;

    private static void setKeyUpFirst(int keyUpFirst) {
        InputKeys.keyUpFirst = keyUpFirst;
    }

    private static void setKeyDownFirst(int keyDownFirst) {
        InputKeys.keyDownFirst = keyDownFirst;
    }

    private static void setKeyLeftFirst(int keyLeftFirst) {
        InputKeys.keyLeftFirst = keyLeftFirst;
    }

    private static void setKeyRightFirst(int keyRightFirst) {
        InputKeys.keyRightFirst = keyRightFirst;
    }

    public static void setKeyUpSecond(int keyUpSecond) {
        InputKeys.keyUpSecond = keyUpSecond;
    }

    public static void setKeyDownSecond(int keyDownSecond) {
        InputKeys.keyDownSecond = keyDownSecond;
    }

    public static void setKeyLeftSecond(int keyLeftSecond) {
        InputKeys.keyLeftSecond = keyLeftSecond;
    }

    public static void setKeyRightSecond(int keyRightSecond) {
        InputKeys.keyRightSecond = keyRightSecond;
    }
}
