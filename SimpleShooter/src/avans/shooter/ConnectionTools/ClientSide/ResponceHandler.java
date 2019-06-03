package avans.shooter.ConnectionTools.ClientSide;

import avans.shooter.Client.Game.Bullet;
import avans.shooter.Client.Game.Player;
import avans.shooter.Client.ShooterClient;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.Server.ShooterServer;
import javafx.application.Platform;

import java.util.ArrayList;

public class ResponceHandler {
    public static void handle(Responce data, ShooterClient client) {
        switch (data.getResponceType()) {
            case lobbyStatus: {
                lobbyResponce(data, client);
                break;
            }
            case player: {
                playerResponce(data, client);
                break;
            }
            case bullet: {
                bulletResponce(data, client);
                break;
            }

        }
    }

    private static void playerResponce(Responce data, ShooterClient client) {
        client.setOtherPlayers((ArrayList<Player>) data.getData());
    }

    private static void bulletResponce(Responce data, ShooterClient client) {
        ArrayList<Bullet> bulets = new ArrayList<>();
        ArrayList<ArrayList<Bullet>> responce = (ArrayList<ArrayList<Bullet>>) data.getData();
        responce.forEach(bullets -> {
            bullets.forEach(bullet -> bulets.add(bullet));
        });
//        System.out.println(bulets + "array       data" + responce);
        client.setOtherBullets(bulets);
    }

    private static void lobbyResponce(Responce data, ShooterClient client) {
        if (client.getLobby() != null) {
            Platform.runLater(() -> {
                client.getLobby().setPlayers((ArrayList<String>)data.getData());
            });
        } else {
            System.out.println("no lobby");
        }
    }
}
