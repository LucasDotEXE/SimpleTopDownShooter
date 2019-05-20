package avans.shooter.Client.Game;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;

public class GameController extends Application{

    private ResizableCanvas canvas;
    private Player player;

    @Override
    public void start(Stage stage) {

        BorderPane mainPane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainPane);
        mainPane.setCenter(canvas);
        canvas.resize(500, 820);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000.0, g2d);
                last = now;
                draw(g2d);
            }
        }.start();

        // Mouse Events
        canvas.setOnKeyPressed(e -> keyTyped(e));
        canvas.setFocusTraversable(true); // make sure we have focus for key events

        Scene scene = new Scene(mainPane, 1000, 1000);
        stage.setScene(scene);
        stage.setTitle("Spelletje");
        stage.show();
        stage.setResizable(false);
        //draw(g2d);

        scene.setOnKeyPressed(event -> {
            keyTyped(event);
        });
    }

    private void keyTyped(KeyEvent e) {
        this.player.keyTyped(e);
    }


    public void init() {
        player = new Player(10,40,  10, 2);
    }

    private void draw(FXGraphics2D g2d) {
        g2d.clearRect(0,0,2000,2000);
        g2d.setColor(Color.WHITE);
        player.draw(g2d);
    }

    private void update(double deltaTime, FXGraphics2D g2d) {
        player.update(deltaTime);
        draw(g2d);
    }

    public static void main(String[] args) {
        Application.launch(GameController.class);
    }

}
