package avans.shooter.Client.Game;


import avans.shooter.Client.ShooterClient;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.ConnectionTools.Responce.ResponceType;

import java.awt.*;
import java.util.ArrayList;

public class Game implements GameObject{

    private ArrayList<GameObject> gameObjects;
//    private Map map;
    private Player player;
    private ArrayList<Bullet> bullets;
    private ShooterClient client;

    public Game(ShooterClient client) {
        this.player = new Player(10,40,  10, 2, client);
        this.gameObjects = new ArrayList<>();
        this.bullets = new ArrayList<>();
        this.client = client;
    }

    public void draw(Graphics2D graphics2D) {
        this.gameObjects.forEach(gameObject -> gameObject.draw(graphics2D));
        this.bullets.forEach(bullet -> bullet.draw(graphics2D));
        this.player.draw(graphics2D);
    }

    public void update(double deltatime) {
       this.gameObjects.forEach(gameObject -> {
           if (gameObject.getClass() == Bullet.class) {
               gameObject.update(deltatime);
           }
       });
        this.bullets.forEach(bullet -> bullet.update(deltatime));
        this.client.sentDataPacket(new Responce<ArrayList<Bullet>>(new ArrayList<>(this.bullets), ResponceType.bullet));
        this.player.update(deltatime);
    }

    public void setGameObjects(ArrayList<GameObject> gameObjects) {
        this.gameObjects = gameObjects;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void shoot() {
        this.bullets.add(this.player.shoot());
    }
}
