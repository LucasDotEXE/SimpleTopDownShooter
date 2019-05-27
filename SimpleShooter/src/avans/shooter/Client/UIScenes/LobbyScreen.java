package avans.shooter.Client.UIScenes;

import avans.shooter.Client.ShooterClient;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class LobbyScreen implements LoadableScene{

    private ShooterClient client;

    private BorderPane layout;
    private VBox nameList;
    private VBox leftList;
    private Button play_Button;


    @Override
    public void loadScene(Stage primaryStage) {
        primaryStage.setScene(new Scene(layout));
    }

    public LobbyScreen(ShooterClient client) {
        this.client = client;
        initLayout();
    }

    private void initLayout() {
        this.layout = new BorderPane();

        this.nameList = new VBox();
        this.leftList = new VBox();

        this.play_Button = new Button();
        this.leftList.getChildren().addAll(new Label("Port Nr:" + this.client.getPort()), this.play_Button);


        this.layout.setLeft(this.leftList);
        this.layout.setRight(this.nameList);
        loadNameList();
    }

    private void loadNameList() {
        this.nameList.getChildren().add(new HBox(new Label("Player List")));
    }
}
