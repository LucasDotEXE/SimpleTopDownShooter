package avans.shooter.Server;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Client implements Runnable {

    private Socket socket;
    private ShooterServer server;
    private DataOutputStream out;
    private DataInputStream in;
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
            this.in  = new DataInputStream( this.socket.getInputStream() );
            this.out = new DataOutputStream( this.socket.getOutputStream() );

            out.writeUTF("Avans ChatServer 1.2.3.4");

            this.name = in.readUTF();
            System.out.println("(E) " + this.name + " joined the Server!");

            String message = "";
            while ( !message.equals("stop") ) {

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

