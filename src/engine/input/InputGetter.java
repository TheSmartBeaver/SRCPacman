package src.engine.input;

import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 02/11/2019.
 */
public class InputGetter {

    public static void getInputs() {

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == InputKeys.keyUpFirst) {
                    GameInput.setInputFirst(Input.UP);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyDownFirst) {
                    GameInput.setInputFirst(Input.DOWN);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyLeftFirst) {
                    GameInput.setInputFirst(Input.LEFT);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyRightFirst) {
                    GameInput.setInputFirst(Input.RIGHT);
                }

                if (Keyboard.getEventKey() == InputKeys.keyUpSecond) {
                    GameInput.setInputSecond(Input.UP);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyDownSecond) {
                    GameInput.setInputSecond(Input.DOWN);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyLeftSecond) {
                    GameInput.setInputSecond(Input.LEFT);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyRightSecond) {
                    GameInput.setInputSecond(Input.RIGHT);
                }
            }
        }
    }
}
