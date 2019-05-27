package avans.shooter.Client.Game;


import java.awt.*;
import java.util.ArrayList;

public class Game implements GameObject{

    private ArrayList<GameObject> gameObjects;
    private Player player;

    public Game() {
        this.player = new Player(10,40,  10, 2);
        this.gameObjects = new ArrayList<>();
    }

    public void draw(Graphics2D graphics2D) {
        this.gameObjects.forEach(gameObject -> gameObject.draw(graphics2D));
        this.player.draw(graphics2D);
    }

    public void update(double deltatime) {
        this.gameObjects.forEach(gameObject -> gameObject.update(deltatime));
        this.player.update(deltatime);
    }
}
