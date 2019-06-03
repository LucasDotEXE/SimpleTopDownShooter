package avans.shooter.Client.Game;

import avans.shooter.Client.ShooterClient;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Bullet implements GameObject, Serializable {

    private transient ShooterClient shooterClient;
    private Point2D position;
    private Boolean hit = false;
    private double size;
    private double angle;

    public Bullet(int x, int y, double angle, ShooterClient client) {
        this.position = new Point2D.Double(x, y);
        this.shooterClient = client;
        this.size = 4;
        this.angle = angle + Math.PI/2;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = new AffineTransform();
        af.translate(0, 0);
        af.translate(-size /2, -size /2);

        Shape bullet = af.createTransformedShape(new Rectangle2D.Double(this.size /2, this.size /2, this.size, this.size));
        AffineTransform pos = new AffineTransform();
        pos.translate(this.position.getX(), this.position.getY());
        g2d.fill(pos.createTransformedShape(bullet));
    }

    @Override
    public void update(double deltatime) {
        if (!hit) {
            this.position = new Point2D.Double(this.position.getX() + Math.cos(this.angle)  * (deltatime / 5000.0D), this.position.getY() + Math.sin(this.angle) * (deltatime / 5000.0D));
//            shooterClient.sentDataPacket(new Responce<Bullet>(new Bullet((int) this.position.getX(), (int) this.position.getY(),
//                     this.dirVect, this.shooterClient),ResponceType.bullet));
        }
    }

    public int getX() {
        return (int)this.position.getX();
    }

    public int getY() {
        return (int)this.position.getY();
    }

    public void hit() {
        this.hit = true;
    }
}
