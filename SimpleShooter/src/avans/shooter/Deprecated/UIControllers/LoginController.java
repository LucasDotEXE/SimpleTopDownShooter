package avans.shooter.Deprecated.UIControllers;

import avans.shooter.Client.ShooterClient;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class LoginController {

    @FXML
    private Button connect;
    @FXML
    private Button close;
    @FXML
    private TextField nameInput;
    @FXML
    private TextField portInput;

    public void initialize() {
        close.setOnAction(event -> {System.exit(0);});
        connect.setOnAction(event -> {
            try {
                int port = Integer.parseInt(this.portInput.getCharacters().toString());
                ShooterClient client = new ShooterClient(port, "localhost", nameInput.getCharacters().toString());
                if (client.connect()) {

                }
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Dialog");
                alert.setHeaderText("Oh no, an Error Has occurred");
                alert.setContentText("Ooops, you used an invalid port number please try again!");
                this.portInput.setText("");
                alert.showAndWait();
            }
        });

    }
}
