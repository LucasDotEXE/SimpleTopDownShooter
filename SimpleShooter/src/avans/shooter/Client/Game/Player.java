package avans.shooter.Client.Game;

import avans.shooter.Client.ShooterClient;
import avans.shooter.Client.UIScenes.GameScreen;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.ConnectionTools.Responce.ResponceType;
import javafx.scene.input.KeyCode;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements GameObject, Serializable {

    private transient ShooterClient shooterClient;

    private Point2D position;
    private double diameter;
    private int speed;


    public Player(int x, int y, int d, int s, ShooterClient client){
        this.shooterClient = client;
        this.position = new Point2D.Double(x,y);
        this.diameter = d;
        this.speed = s;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = new AffineTransform();
        af.setToTranslation(this.position.getX(), this.position.getY());
        Ellipse2D player = new Ellipse2D.Double(this.position.getX() - this.diameter/2, this.position.getY() - this.diameter/2, this.diameter, this.diameter);
        g2d.fill(player);
    }

    @Override
    public void update(double deltatime) {
        GameScreen.keyLisner.getKeys().forEach(keyCode -> {
            moveInDirection(keyCode, deltatime);
            shooterClient.sentDataPacket(new Responce<Player>(new Player((int)this.position.getX(), (int)this.position.getY(),
                    (int)this.diameter, this.speed, this.shooterClient), ResponceType.player));
        });
    }

    private void moveInDirection(KeyCode direction, double deltatime) {
        if (direction == KeyCode.D && this.position.getX() < 1000) {
            setPosition((int) this.position.getX() + (int) (this.speed* (deltatime / 10000)), (int) this.position.getY());
        } else if (direction == KeyCode.A && this.position.getX() > 10) {
            setPosition((int) this.position.getX() - (int) (this.speed * (deltatime / 10000)), (int) this.position.getY());
        } else  if (direction == KeyCode.W && this.position.getY() > 10){
            setPosition((int) this.position.getX(), (int) this.position.getY() - (int)(this.speed*(deltatime/10000)));
        } else  if (direction == KeyCode.S && this.position.getY() < 1000) {
            setPosition((int) this.position.getX(), (int) this.position.getY() + (int)(this.speed*(deltatime/10000)));
        } else setPosition((int) this.position.getX(), (int) this.position.getY());
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public Point2D getPos() {
        return this.position;
    }

//    public void setDirection(String di) {
//        this.direction = di;
//    }

//    public void keyTyped(KeyEvent e) {
//        if(e.getCode() == KeyCode.A) {
//            setDirection("a");
//        } else if (e.getCode() == KeyCode.D) {
//            setDirection("d");
//        } else if (e.getCode() == KeyCode.W) {
//            setDirection("w");
//        } else if (e.getCode() == KeyCode.S) {
//            setDirection("s");
//        } else {setDirection("o"); }
//    }
   /*public Player(int x, int y, int d, int s , String u, String t, String l, String r){

        this.diameter = d;
        this.position = new Point2D.Double(x,y);
        this.speed = s;
        this.upKey = u;
        this.downKey = t;
        this.leftKey= l;
        this.rightKey = r;
        this.ball = new Ellipse2D.Double(x-5, y-5, 1000, 1000);

    }

    public void draw(Graphics2D g2d) {
        AffineTransform af = new AffineTransform();
        af.setToTranslation(this.position.getX(), this.position.getY());
        g2d.fill(this.ball);
    }


    public void update(double deltatime) {

        this.position = new Point2D.Double(this.position.getX() + this.point2Ddirection.getX(), this.position.getY() + this.point2Ddirection.getY());
        this.point2Ddirection = new Point2D.Double(this.speed, 0);

        if(this.direction.equals(this.upKey) && this.position.getY() > 0) {
            setPosition((int) this.position.getX(), (int) this.position.getY() - (int)(this.speed*(deltatime/5000)));
        } else if(this.direction.equals(this.downKey) && this.position.getY() < 910) {
            setPosition((int) this.position.getX(), (int) this.position.getY() + (int)(this.speed*(deltatime/5000)));
        } else if(this.direction.equals(this.leftKey) && this.position.getX()<0 ){
            setPosition((int)this.position.getX(), (int) this.position.getY() - (int)(this.speed*(deltatime/5000)));
        } else if(this.direction.equals(this.rightKey) && this.position.getX()< 910) {
            setPosition((int) this.position.getX(), (int) this.position.getY() - (int)(this.speed *(deltatime / 5000)));
        }
        else this.direction.equals("o");
    }

    private void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    private void setDirection(String di) {
        this.direction = di;
    }

<<<<<<< HEAD
    public void keyTyped(KeyEvent e) {
        if (e.getCode().getName().equals(this.upKey)){
            setDirection(this.upKey);
        } else if (e.getCode().getName().equals(this.downKey)){
            setDirection(this.downKey);
        } else if (e.getCode().getName().equals(this.leftKey)) {
            setDirection(this.leftKey);
        } else if (e.getCode().getName().equals(this.rightKey)) {
            setDirection(this.rightKey);
        }
    }
    */
}
