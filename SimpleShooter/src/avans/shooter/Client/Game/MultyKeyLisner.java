package avans.shooter.Client.Game;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.ArrayList;
import java.util.HashSet;

public class MultyKeyLisner {

    // Set of currently pressed keys
    private final HashSet<KeyCode> pressed = new HashSet<>();


    public synchronized void keyPressed(javafx.scene.input.KeyEvent e) {
        pressed.add(e.getCode());
    }

    public synchronized void keyReleased(KeyEvent e) {
        pressed.remove(e.getCode());
    }

    public HashSet<KeyCode> getKeys() {
        return this.pressed;
    }
}
