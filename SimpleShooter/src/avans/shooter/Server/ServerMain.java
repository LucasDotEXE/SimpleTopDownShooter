package avans.shooter.Server;

import java.util.Scanner;

// Before each message in the console there are () with a letter
//(Q) means Query
//(S) means Status
//(E) means Event

public class ServerMain {
    public static void main(String[] args) {
        int portPin = askPin();
        System.out.println("(S) Selecting a map...");

        //add a map saving and selecting protocols

        ShooterServer server = new ShooterServer(portPin);
        server.start();
        while ( true ) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Thread.yield();
        }
    }

    private static int askPin() {
        System.out.println("(Q) Enter port pin:");
        System.out.print("(Q) > ");
        int pin;
        Scanner reader = new Scanner(System.in);
        try {
            pin = Integer.parseInt(reader.next());
        } catch (NumberFormatException e) {
            System.out.println("(Q) That was not a valid port pin, pleas enter a number. (preferably 4 digits or higher)");
            pin = askPin();
        }
        return pin;
    }
}
