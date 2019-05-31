package avans.shooter.ConnectionTools.ServerSide;

import avans.shooter.ConnectionTools.Request.Request;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.ConnectionTools.Responce.ResponceType;
import avans.shooter.Server.ShooterServer;

import java.util.ArrayList;

public class RequestHandler {

    public static void handle(Request data, ShooterServer server) {
        switch (data.getRequestType()) {
            case lobbyStatus:{
                responceLobby(server);
                break;
            }

        }
    }

    private static void responceLobby(ShooterServer server) {
        ArrayList<String> names = new ArrayList<>();
        server.getClients().forEach(client -> names.add(client.getName()));
        Responce<ArrayList<String>> responce = new Responce<>(names, ResponceType.lobbyStatus);
        server.getClients().forEach(client -> {
            client.sentDataPacket(responce);
        });
    }
}
