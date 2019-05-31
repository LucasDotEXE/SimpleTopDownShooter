package avans.shooter.Server;

import avans.shooter.Client.Game.Player;

import java.util.HashMap;

public class ServerGameData {
    private ShooterServer parent;

    private HashMap<String, Player> playerHashMap;


    public ServerGameData(ShooterServer server) {
        this.parent = server;
        this.playerHashMap = new HashMap<>();
    }

    public void putPlayer(String clientName, Player player) {
        this.playerHashMap.put(clientName, player);
//        System.out.println("put Player: " + clientName + " pos: " + player.getPos());
        this.parent.updatePlayerPos(this.playerHashMap);
    }
}
