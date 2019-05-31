package avans.shooter.Client;

import avans.shooter.Client.UIScenes.GameScreen;
import avans.shooter.Client.UIScenes.LobbyScreen;
import avans.shooter.Client.UIScenes.LoginScreen;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;


public class ClientMain extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        LoginScreen loginScreen = new LoginScreen(this);
        loginScreen.loadScene(primaryStage);
        primaryStage.show();
    }


    public void startGame(ShooterClient client) {
        System.out.println("game oppened");
        new GameScreen(client, this).loadScene(primaryStage);
    }

    public void openLobby(ShooterClient client) {
        System.out.println("Lobby oppened");
        new LobbyScreen(client, this).loadScene(primaryStage);
    }

    //    private void loadLogin(Stage primaryStage) {
//        Parent root = null;
//        try {
//            root = FXMLLoader.load(getClass().getResource("../Deprecated/LoginScreen.fxml"));
//            primaryStage.setScene(new Scene(root, 175, 155));
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
}