package avans.shooter.ConnectionTools.ServerSide;

import avans.shooter.Client.Game.Bullet;
import avans.shooter.Client.Game.Player;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.Server.Client;
import avans.shooter.Server.ShooterServer;

import java.util.ArrayList;

public class ResponceHandler {
    public static void handle(Responce data, ShooterServer server, Client client) {
        switch (data.getResponceType()) {
            case player: {
//                Player player = (Player) data.getData();
//                System.out.println("handle: " + client.getName() + " pos: " + player.getPos());
                server.getGameData().putPlayer(client.getName(), (Player) data.getData());

                break;
            }
            case bullet: {
                server.getGameData().putBullet(client.getName(), (ArrayList<Bullet>) data.getData());
            }
        }
    }
}
