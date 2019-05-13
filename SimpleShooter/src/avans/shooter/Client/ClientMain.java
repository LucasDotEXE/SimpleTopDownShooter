package avans.shooter.Client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;


public class ClientMain extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        setUserAgentStylesheet(STYLESHEET_CASPIAN);
        primaryStage.initStyle(StageStyle.TRANSPARENT);

        openLogin(primaryStage);
        primaryStage.show();
    }

    private void openLogin(Stage primaryStage) {
        Parent root = null;
        try {
            root = FXMLLoader.load(getClass().getResource("LoginScreen.fxml"));

            primaryStage.setScene(new Scene(root, 175, 155));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}