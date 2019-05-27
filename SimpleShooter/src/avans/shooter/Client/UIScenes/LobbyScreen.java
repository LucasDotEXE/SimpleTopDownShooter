package avans.shooter.Client.UIScenes;

import avans.shooter.Client.ClientMain;
import avans.shooter.Client.ShooterClient;
import avans.shooter.ConnectionTools.Request.Request;
import avans.shooter.ConnectionTools.Request.RequestType;
import avans.shooter.Server.Client;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;

public class LobbyScreen implements LoadableScene {

    private ClientMain parent;
    private ShooterClient client;

    private BorderPane layout;
    private VBox nameList;
    private VBox leftList;
    private Button play_Button;

    private final Font FONT = new Font(20);


    @Override
    public void loadScene(Stage primaryStage) {
        primaryStage.setScene(new Scene(layout, 400, 400));
    }

    public LobbyScreen(ShooterClient client, ClientMain parent) {
        this.client = client;
        this.parent = parent;
        initLayout();
        formatLayout();
        setFunction();
    }

    private void setFunction() {
        this.play_Button.setOnAction(event -> {
            this.parent.startGame(this.client);
        });
    }

    private void formatLayout() {
        this.nameList.setAlignment(Pos.TOP_CENTER);
        this.nameList.setSpacing(10);

        this.leftList.setSpacing(150);
        this.leftList.setAlignment(Pos.TOP_CENTER);

    }

    private void initLayout() {
        this.layout = new BorderPane();
        layout.setCenter(new Label(" "));

        this.nameList = new VBox();
        this.leftList = new VBox();

        this.play_Button = new Button("PLAY");
        play_Button.setFont(new Font(40));
        play_Button.setPrefSize(180, 30);

        Label leftTop = new Label("Port Nr:" + this.client.getPort());
        leftTop.setFont(new Font(30));
        this.leftList.getChildren().addAll(leftTop, this.play_Button);


        this.layout.setLeft(this.leftList);
        this.layout.setRight(this.nameList);
        reloadPlayerList();
    }



    public void reloadPlayerList() {
        Request request = new Request(RequestType.lobbyStatus, client);
        client.setLobby(this);
        client.sentDataPacket(request);
    }

    public void setPlayers(ArrayList<String> players) {
        this.nameList.getChildren().clear();
        Label playerList = new Label("Player List");
        playerList.setFont(new Font(30));
        this.nameList.getChildren().addAll(playerList, new Label(" "));

        for (String player : players) {
            Label name = new Label(player);
            name.setFont(this.FONT);
            if (this.client.getName().equals(player)) {
                name.setStyle("-fx-font-weight: bold");
            }
            this.nameList.getChildren().add(name);
        }
    }
}
