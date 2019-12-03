package src.engine.input;

import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 02/11/2019.
 */
public class InputGetter {

    static boolean keyFirstUpPressed = false;
    static boolean keyFirstDownPressed = false;
    static boolean keyFirstLeftPressed = false;
    static boolean keyFirstRightPressed = false;

    static boolean keySecondUpPressed = false;
    static boolean keySecondDownPressed = false;
    static boolean keySecondLeftPressed = false;
    static boolean keySecondRightPressed = false;

    public static void getInputs() {
        //First et second : imputs pour 2 joueurs

        keyFirstUpPressed = false;
        keyFirstDownPressed = false;
        keyFirstLeftPressed = false;
        keyFirstRightPressed = false;
        keySecondUpPressed = false;
        keySecondDownPressed = false;
        keySecondLeftPressed = false;
        keySecondRightPressed = false;

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == InputKeys.keyUpFirst) {
                    keyFirstUpPressed = true;
                }
                else if (Keyboard.getEventKey() == InputKeys.keyDownFirst) {
                    keyFirstDownPressed = true;
                }
                else if (Keyboard.getEventKey() == InputKeys.keyLeftFirst) {
                    keyFirstLeftPressed = true;
                }
                else if (Keyboard.getEventKey() == InputKeys.keyRightFirst) {
                    keyFirstRightPressed = true;
                }

                if (Keyboard.getEventKey() == InputKeys.keyUpSecond) {
                    keySecondUpPressed = true;
                }
                else if (Keyboard.getEventKey() == InputKeys.keyDownSecond) {
                    keySecondDownPressed = true;
                }
                else if (Keyboard.getEventKey() == InputKeys.keyLeftSecond) {
                    keySecondLeftPressed = true;
                }
                else if (Keyboard.getEventKey() == InputKeys.keyRightSecond) {
                    keySecondRightPressed = true;
                }
            }
        }
    }
}
