package avans.shooter.Client;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class ShooterClient {

    private int port;
    private Socket socket;
    private String host;
    private String name;

    public ShooterClient(int port, String host, String name) {
        this.port = port;
        this.host = host;
        this.name = name;
    }

    public boolean connect () {
        try {
            this.socket = new Socket(this.host, this.port);

            DataInputStream in = new DataInputStream( this.socket.getInputStream() );
            DataOutputStream out = new DataOutputStream( this.socket.getOutputStream() );

            Scanner scanner = new Scanner( System.in );

            String server = in.readUTF();
            System.out.println(server);

            out.writeUTF(this.name);

            new Thread ( () -> {
                while ( true ) {
                    try {
                        System.out.println(in.readUTF());

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

            String message = "";
            while ( !message.equals("stop" ) ) {
                System.out.print("> ");
                message = scanner.nextLine();
                out.writeUTF(message);

                //System.out.println("Server response: " + in.readUTF());
            }

            this.socket.close();

        } catch (IOException e) {
            System.out.println("Could not connect with the server on " + this.host + " with port " + this.port + ": " + e.getMessage());
            return false;
        }

        return true;
    }
}
