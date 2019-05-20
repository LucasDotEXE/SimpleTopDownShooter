package avans.shooter.Server;


import avans.shooter.ConnectionTools.DataPacket;
import avans.shooter.ConnectionTools.Request.Request;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.ConnectionTools.ServerSide.RequestHandler;
import avans.shooter.ConnectionTools.ServerSide.ResponceHandler;

import java.io.*;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private ShooterServer server;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private String name;

    public Client ( Socket socket, ShooterServer server ) {
        this.socket = socket;
        this.server = server;
    }

    public void writeUTF ( String text ) {
        //This is to write data on command
//        System.out.println("Got message for client");
//        try {
//            this.out.writeUTF(text);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public void run() {
        try {
            this.in  = new ObjectInputStream( this.socket.getInputStream() );
            this.out = new ObjectOutputStream( this.socket.getOutputStream() );

            out.writeUTF("ShooterServer 1.0.0");
            System.out.println("(E) " + this.name + " joined the Server!");

            String message = "";
            while ( !message.equals("stop") ) {
                try {
                    DataPacket data = (DataPacket) this.in.readObject();
                    if (data.isRequest()) {
                        RequestHandler.handle((Request) data, this.server);
                    } else {
                        if (data.isResponce()) {
                            ResponceHandler.handle((Responce) data, this.server);
                        }
                    }

                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                }

                //The update's/data-transfer should go here
//                message = in.readUTF();   //incoming data
//                out.writeUTF(message);
//                System.out.println("Client send: " + message);
//                this.server.sendToAllClients("(" + this.name + "): " + message);
            }

            this.socket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

