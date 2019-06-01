package avans.shooter.Client.UIScenes;

import avans.shooter.Client.ClientMain;
import avans.shooter.Client.Game.Game;
import avans.shooter.Client.Game.GameObject;
import avans.shooter.Client.Game.MultyKeyLisner;
import avans.shooter.Client.ShooterClient;
import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import org.jfree.fx.FXGraphics2D;
import org.jfree.fx.ResizableCanvas;

import java.awt.*;
import java.awt.geom.Point2D;
import java.util.ArrayList;

public class GameScreen implements LoadableScene {

    private ClientMain parent;
    private ShooterClient client;
    private Game game;

    private BorderPane mainpane;
    private ResizableCanvas canvas;
    public static MultyKeyLisner keyLisner;

    public GameScreen(ShooterClient client, ClientMain clientMain) {
        init(client);
        this.parent = clientMain;
        this.client = client;
    }

    @Override
    public void loadScene(Stage stage) {
        stage.setFullScreen(true);
        this.mainpane = new BorderPane();
        canvas = new ResizableCanvas(g -> draw(g), mainpane);
        mainpane.setCenter(canvas);
        canvas.resize(1000, 1000);
        FXGraphics2D g2d = new FXGraphics2D(canvas.getGraphicsContext2D());
        new AnimationTimer() {
            long last = -1;

            @Override
            public void handle(long now) {
                if (last == -1)
                    last = now;
                update((now - last) / 1000.0);
                last = now;
                draw(g2d);
            }
        }.start();

        // Mouse Events
        this.keyLisner = new MultyKeyLisner();
        canvas.setOnKeyPressed(e -> {
            keyLisner.keyPressed(e);
//            keyLisner.getKeys().forEach(keyCode -> System.out.println(keyCode.getName()));
        });
        canvas.setOnKeyReleased(event -> {
            keyLisner.keyReleased(event);
//            keyLisner.getKeys().forEach(keyCode -> System.out.println(keyCode.getName()));
        });
        canvas.setFocusTraversable(true); // make sure we have focus for key events
        canvas.setOnMouseMoved(event -> {
            game.getPlayer().setMousePos(new Point2D.Double(event.getX(), event.getY()));
        });

        Scene scene = new Scene(mainpane);
        stage.setScene(scene);
        stage.setTitle("Player: " + client.getName() + " Port: " + client.getPort());
        stage.show();
        stage.setResizable(true);
        //draw(g2d);

    }



    public void init(ShooterClient client) {
        this.game = new Game(client);
    }

    private void draw(FXGraphics2D g2d) {
        g2d.clearRect(0,0,2000,2000);
        g2d.setColor(Color.WHITE);
        this.game.draw(g2d);
    }

    private void update(double deltaTime) {
        this.game.setGameObjects(this.client.getServerData());
        this.game.update(deltaTime);
    }
}
