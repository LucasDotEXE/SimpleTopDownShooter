package avans.shooter.Server;

import java.util.Scanner;

public class ServerMain {
    public static void main(String[] args) {
        int portPin = askPin();
        System.out.println("Selecting a map...");


    }

    private static int askPin() {
        System.out.println("Enter port pin:");
        System.out.print("> ");
        int pin;
        Scanner reader = new Scanner(System.in);
        try {
            pin = Integer.parseInt(reader.next());
        } catch (NumberFormatException e) {
            System.out.println("That was not a valid port pin, pleas enter a number. (preferably 4 digits or higher)");
            pin = askPin();
        }
        return pin;
    }
}
