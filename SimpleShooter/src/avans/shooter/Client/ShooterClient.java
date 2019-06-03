package avans.shooter.Client;

import avans.shooter.Client.Game.Bullet;
import avans.shooter.Client.Game.GameObject;
import avans.shooter.Client.Game.Player;
import avans.shooter.Client.UIScenes.LobbyScreen;
import avans.shooter.ConnectionTools.ClientSide.RequestHandler;
import avans.shooter.ConnectionTools.ClientSide.ResponceHandler;
import avans.shooter.ConnectionTools.DataPacket;
import avans.shooter.ConnectionTools.Request.Request;
import avans.shooter.ConnectionTools.Responce.Responce;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ShooterClient implements Serializable {

    private transient int port;
    private transient Socket socket;
    private transient String host;
    private String name;

    private transient ObjectOutputStream out;
    private transient ObjectInputStream in;

    private transient ArrayList<GameObject> serverData;
    private transient  ArrayList<Player> otherPlayers;
    private transient  ArrayList<Bullet> otherBullets;

    private transient LobbyScreen lobby;

    public ShooterClient(int port, String host, String name) {
        this.serverData = new ArrayList<>();
        this.otherPlayers = new ArrayList<>();
        this.otherBullets = new ArrayList<>();
        this.port = port;
        this.host = host;
        this.name = name;
        this.lobby = null;
    }

    public boolean connect () {
        try {
            this.socket = new Socket(this.host, this.port);

            this.out = new ObjectOutputStream( this.socket.getOutputStream() );
            this.in = new ObjectInputStream(this.socket.getInputStream());

            String server = (String) in.readObject();
            System.out.println(server);

            this.out.writeObject(this.name);

            startDataRecieverTread();

        } catch (IOException e) {
            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return true;
    }

    public void closeSocket() {
        try {
            this.socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getPort() {
        return this.port;
    }

    public void sentDataPacket(DataPacket dataPacket) {
        try {
            out.writeObject(dataPacket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void startDataRecieverTread() {
        new Thread ( () -> {
            while ( true ) {
                try {
                    //Input from server
                    DataPacket data = (DataPacket) in.readObject();
                    if (data.isRequest()) {
                        RequestHandler.handle((Request) data, this);
                    } else {
                        if (data.isResponce()) {
                            ResponceHandler.handle((Responce) data, this);
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Error ### IO Exception");
                    e.printStackTrace();
                } catch (ClassNotFoundException e) {
                    System.out.println("Sent class was not a DataPacket");
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void setLobby(LobbyScreen lobby) {
        this.lobby = lobby;
    }

    public LobbyScreen getLobby() {
        return lobby;
    }

    public String getName() {
        return name;
    }

    public synchronized ArrayList<GameObject> getServerData() {
        ArrayList<GameObject> serverData = new ArrayList<>();
        if (this.otherPlayers != null) {
            serverData.addAll(this.otherPlayers);
        }
        return serverData;
    }

    public synchronized void setOtherPlayers(ArrayList<Player> players) {
        this.otherPlayers = players;
    }

    public synchronized void setOtherBullets(ArrayList<Bullet> bullets) {
        this.otherBullets = bullets;
    }

}
