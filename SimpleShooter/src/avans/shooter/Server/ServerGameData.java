package avans.shooter.Server;

import avans.shooter.Client.Game.Bullet;
import avans.shooter.Client.Game.Player;

import java.util.ArrayList;
import java.util.HashMap;

public class ServerGameData {
    private ShooterServer parent;

    private HashMap<String, Player> playerHashMap;
    private HashMap<String, ArrayList<Bullet>> bulletHashmap;


    public ServerGameData(ShooterServer server) {
        this.parent = server;
        this.playerHashMap = new HashMap<>();
        this.bulletHashmap = new HashMap<>();
    }

    public void putPlayer(String clientName, Player player) {
        this.playerHashMap.put(clientName, player);
//        System.out.println("put Player: " + clientName + " pos: " + player.getPos());
        this.parent.updatePlayerPos(this.playerHashMap);
    }

    public void putBullet(String name, ArrayList<Bullet> data) {
        this.bulletHashmap.put(name, data);
        this.parent.updatePlayerBullets(this.bulletHashmap);
    }
}
