package avans.shooter.Server;

import avans.shooter.Client.Game.Bullet;
import avans.shooter.Client.Game.Player;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.ConnectionTools.Responce.ResponceType;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;

public class ShooterServer {

    private int port;
    private ServerSocket server;
    private Thread serverThread;
    private ArrayList<Client> clients;
    private ArrayList<Thread> threads;
    private ServerGameData gameData;

    public ShooterServer(int port) {
        this.gameData = new ServerGameData(this);
        this.port = port;
        this.clients = new ArrayList<>();
        this.threads = new ArrayList<>();
    }

    public boolean start ( ) {
        try {
            this.server = new ServerSocket(port);

            this.serverThread = new Thread ( () -> {
                while ( true ) {
                    System.out.println("(S) Waiting for clients to connect.");
                    try {
                        Socket socket = this.server.accept();
                        System.out.println("(E) Client connected from " + socket.getInetAddress().getHostAddress() + ".");

                        Client client = new Client(socket, this);
                        Thread threadClient = new Thread(client);
                        threadClient.start();
                        this.clients.add(client);
                        this.threads.add(threadClient);

                        System.out.println("(S) Total clients connected: " + this.clients.size());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        Thread.sleep(100);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    Thread.yield();
                }

            });

            this.serverThread.start();
            System.out.println("(E) Server started on port " + this.port);

        } catch (IOException e) {
            System.out.println("(E) Could not connect: " + e.getMessage());
            return false;
        }

        return true;
    }

    public void removeClient(Client client) {
        this.clients.remove(client);
    }

    public ArrayList<Client> getClients() {
        return clients;
    }

    public ServerGameData getGameData() {
        return this.gameData;
    }

    public void updatePlayerPos(HashMap<String, Player> playerHashMap) {
//        System.out.println("update player Pos: ");
//        playerHashMap.forEach((s, player) -> System.out.println(s + " pos: " + player.getPos()));
        for (Client client: this.clients) {
            ArrayList<Player> players = new ArrayList();
            playerHashMap.forEach((s, player) -> {
                if (s != client.getName()){
                    players.add(player);
                }
            });
            client.sentDataPacket(new Responce<ArrayList<Player>>(players, ResponceType.player));
        }
    }

    public void updatePlayerBullets(HashMap<String, ArrayList<Bullet>> bulletHashMap) {
        for (Client client: this.clients) {
            ArrayList<ArrayList<Bullet>> players = new ArrayList();
            bulletHashMap.forEach((s, Bullet) -> {
                if (s != client.getName()){
                    players.add(Bullet);
                }
            });
            client.sentDataPacket(new Responce<ArrayList<ArrayList<Bullet>>>(players, ResponceType.bullet));
        }
    }
}
