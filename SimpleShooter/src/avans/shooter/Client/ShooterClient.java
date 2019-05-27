package avans.shooter.Client;

import avans.shooter.ConnectionTools.ClientSide.RequestHandler;
import avans.shooter.ConnectionTools.ClientSide.ResponceHandler;
import avans.shooter.ConnectionTools.DataPacket;
import avans.shooter.ConnectionTools.Request.Request;
import avans.shooter.ConnectionTools.Responce.Responce;

import java.io.*;
import java.net.Socket;

public class ShooterClient {

    private int port;
    private Socket socket;
    private String host;
    private String name;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    public ShooterClient(int port, String host, String name) {
        this.port = port;
        this.host = host;
        this.name = name;
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
            this.out.writeObject(dataPacket);
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
}
