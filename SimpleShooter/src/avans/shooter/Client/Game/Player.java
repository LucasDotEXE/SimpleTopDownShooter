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
import java.awt.geom.Rectangle2D;
import java.io.Serializable;
import java.util.ArrayList;

public class Player implements GameObject, Serializable {

    private transient ShooterClient shooterClient;

    private Point2D.Double position;
    private double diameter;
    private int speed;
    private double rotation;
    private Point2D.Double mouse;
    private ArrayList<Bullet> bullets;
    private Boolean hit = false;


    public Player(int x, int y, int d, int s, ShooterClient client){
        this.mouse = new Point2D.Double(x, y);
        this.rotation = 0;
        this.shooterClient = client;
        this.position = new Point2D.Double(x, y);
        this.diameter = d;
        this.speed = s;
        this.bullets = new ArrayList<>();
    }

    public Player(int x, int y, int d, int s, double rotation, ShooterClient client) {
        this.shooterClient = client;
        this.position = new Point2D.Double(x, y);
        this.diameter = d;
        this.speed = s;
        this.rotation = rotation;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = new AffineTransform();
        af.translate(0, 0);
        af.rotate(this.getRotation());
        af.translate(-diameter/2, -diameter/2);

        Shape player = af.createTransformedShape(new Ellipse2D.Double(0, 0, this.diameter, this.diameter));
        Shape gun = af.createTransformedShape(new Rectangle2D.Double(this.diameter/3, this.diameter/2, this.diameter/3, this.diameter));
        AffineTransform pos = new AffineTransform();
        pos.translate(this.position.getX(), this.position.getY());

        g2d.fill(pos.createTransformedShape(player));
        g2d.fill(pos.createTransformedShape(gun));

        this.bullets.forEach(bullet -> bullet.draw(g2d));
    }

    @Override
    public void update(double deltatime) {
        GameScreen.keyLisner.getKeys().forEach(keyCode -> {
            moveInDirection(keyCode, deltatime);
        });
        this.rotation = angleOf(this.position, this.mouse);
        shooterClient.sentDataPacket(new Responce<Player>(new Player((int)this.position.getX(), (int)this.position.getY(),
                (int)this.diameter, this.speed, this.rotation, this.shooterClient), ResponceType.player));
        if (this.bullets.size() > 20) {
            this.bullets.remove(0);
        }
        this.bullets.forEach(bullet -> bullet.update(deltatime));
    }

    public double angleOf(Point2D target, Point2D mouse) {

        Point2D diff = new Point2D.Double(mouse.getX() - target.getX(), mouse.getY() - target.getY());
        double targetAngle = Math.atan2(diff.getY(), diff.getX()) - Math.PI/2;

        while (targetAngle > Math.PI)
            targetAngle -= 2 * Math.PI;

        while (targetAngle < -Math.PI)
            targetAngle += 2 * Math.PI;

        return targetAngle;
    }

    private void moveInDirection(KeyCode direction, double deltatime) {
        if (direction == KeyCode.D && this.position.getX() < 1000) {
            setPosition((int) this.position.getX() + (int) (this.speed* (deltatime / 10000)), (int) this.position.getY());
        } else if (direction == KeyCode.A && this.position.getX() > 10) {
            setPosition((int) this.position.getX() - (int) (this.speed * (deltatime / 10000)), (int) this.position.getY());
        } else  if (direction == KeyCode.W && this.position.getY() > 10){
            setPosition((int) this.position.getX(), (int) this.position.getY() - (int)(this.speed*(deltatime/10000)));
        } else  if (direction == KeyCode.S && this.position.getY() < 1000) {
            setPosition((int) this.position.getX(), (int) this.position.getY() + (int) (this.speed * (deltatime / 10000)));
        } else if(direction == KeyCode.SPACE ){
            this.shoot();
        } else setPosition((int) this.position.getX(), (int) this.position.getY());
    }

    public void shoot() {
        Bullet bullet = new Bullet((int)this.position.getX(), (int)this.position.getY() , angleOf(this.position, this.mouse), shooterClient);
        this.bullets.add(bullet);
    }

    private Point2D getBulletMath() {
        double xDiff = this.mouse.getX() - this.position.getX();
        double yDiff = this.mouse.getY() - this.position.getY();


        System.out.println(xDiff + "x   y" + yDiff);
        if (xDiff < 0) {
            while (xDiff < -1) {
                xDiff+=1.0;
            }
        } else {
            while (xDiff > 1) {
                xDiff -=1.0;
            }
        }
        if (yDiff < 0) {
            while (yDiff < -1) {
                yDiff+=1.0;
            }
        } else {
            while (yDiff > 1) {
                yDiff -=1.0;
            }
        }

        System.out.println(xDiff + "x   y" + yDiff);

        return new Point2D.Double(xDiff, yDiff);
    }

    public void hit(Player p, Bullet b){
        if (p.getX() == b.getX() && p.getY() == b.getY()){
            this.position = null;
        }
    }



    public ArrayList<Bullet> getBullets() {
        return this.bullets;
    }

    public void setPosition(int x, int y) {
        this.position.setLocation(x, y);
    }

    public Point2D getPos() {
        return this.position;
    }

    public double getRotation() {
        return rotation;
    }

    public void setMousePos(Point2D.Double point2D) {
        this.mouse = point2D;
    }

    public int getX() {
        return (int)this.position.getX();
    }

    public int getY() {
        return (int)this.position.getY();
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
