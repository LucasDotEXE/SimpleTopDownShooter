package avans.shooter.Server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class ShooterServer {

    private int port;
    private ServerSocket server;
    private Thread serverThread;
    private ArrayList<Client> clients;
    private ArrayList<Thread> threads;

    public ShooterServer(int port) {
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
}
