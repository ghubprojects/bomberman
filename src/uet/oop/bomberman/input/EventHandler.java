package uet.oop.bomberman.input;

import javafx.scene.Scene;
import javafx.scene.input.KeyCode;

import java.util.ArrayList;
import java.util.List;

public class EventHandler {
    public static List<KeyCode> keyboardInputs = new ArrayList<>();
    public static List<KeyCode> getKeyboardInputs() {
        return keyboardInputs;
    }

    public static void handle(Scene scene) {
        keyboardInputs.clear();
        scene.setOnKeyPressed(event-> {
            KeyCode keyCode = event.getCode();
            if (!keyboardInputs.contains(keyCode)) {
                keyboardInputs.add(keyCode);
            }
        });
        scene.setOnKeyReleased(event -> {
            KeyCode keyCode = event.getCode();
            keyboardInputs.remove(keyCode);
        });
    }
}
