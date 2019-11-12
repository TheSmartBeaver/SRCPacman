package src.engine.input;

import org.lwjgl.input.Keyboard;

/**
 * Created by Vincent on 02/11/2019.
 */
public class InputGetter {

    public static void getInputs() {

        while (Keyboard.next()) {
            if (Keyboard.getEventKeyState()) {
                if (Keyboard.getEventKey() == InputKeys.keyUp) {
                    GameInput.setInput(Input.UP);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyDown) {
                    GameInput.setInput(Input.DOWN);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyLeft) {
                    GameInput.setInput(Input.LEFT);
                }
                else if (Keyboard.getEventKey() == InputKeys.keyRight) {
                    GameInput.setInput(Input.RIGHT);
                }

            }
        }
    }

}
