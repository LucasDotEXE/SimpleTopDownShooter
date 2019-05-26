package avans.shooter.Client.UIScenes;

import avans.shooter.Client.ClientMain;
import avans.shooter.Client.ShooterClient;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.StageStyle;


public class LoginScreen implements LoadableScene {

    private BorderPane layout;
    private VBox centerBox;
    private VBox topBox;
    private HBox botBox;
    private Label title;
    private HBox portRow;
    private HBox nameRow;
    private Label portLabel;
    private Label nameLabel;
    private TextField nameField;
    private TextField portField;
    private Button close;
    private Button connect;

    private ClientMain parent;

    public LoginScreen(ClientMain clientMain) {
        intitComponents();
        formatAll();
        setFunction();
        this.parent = clientMain;
    }

    private void setFunction() {
        this.connect.setOnAction(event -> {
            try {
            int port = Integer.parseInt(this.portField.getCharacters().toString());
            ShooterClient client = new ShooterClient(port, "localhost", nameField.getCharacters().toString());
            if (client.connect()) {
                this.parent.openLobby(client);
            }
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Dialog");
            alert.setHeaderText("Oh no, an Error Has occurred");
            alert.setContentText("Ooops, you used an invalid port number please try again!");
            this.portField.setText("");
            alert.showAndWait();
        }
        });
    }

    private void formatAll() {

        this.centerBox.setAlignment(Pos.CENTER);
        this.centerBox.setSpacing(5);

        this.topBox.setAlignment(Pos.CENTER);

        this.botBox.setAlignment(Pos.CENTER);
        this.botBox.setSpacing(5);

        this.nameField.setPrefWidth(100);
        this.portField.setPrefWidth(100);

    }

    private void intitComponents() {
        this.layout = new BorderPane();
        this.centerBox = new VBox();
        this.topBox = new VBox();
        this.botBox = new HBox();
        this.portRow = new HBox();
        this.nameRow = new HBox();
        this.nameField = new TextField();
        this.portField = new TextField();
        this.portLabel = new Label("Port Nr: ");
        this.nameLabel = new Label("Name: ");
        this.title = new Label("Log in");
        this.close = new Button("close");
        this.connect = new Button("connect");

        this.layout.setTop(this.topBox);
        this.layout.setCenter(this.centerBox);
        this.layout.setBottom(this.botBox);

        this.nameRow.getChildren().addAll(this.nameLabel, this.nameField);
        this.portRow.getChildren().addAll(this.portLabel, this.portField);

        this.topBox.getChildren().addAll(this.title);
        this.centerBox.getChildren().addAll(this.nameRow, portRow);
        this.botBox.getChildren().addAll(this.close, this.connect);
    }



    @Override
    public void loadScene(Stage primaryStage) {
        primaryStage.initStyle(StageStyle.UNDECORATED);
        primaryStage.setScene(new Scene(layout, 175, 155));
    }


}
