package avans.shooter.Client.Game;

import avans.shooter.Client.ShooterClient;
import avans.shooter.ConnectionTools.Responce.Responce;
import avans.shooter.ConnectionTools.Responce.ResponceType;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.io.Serializable;

public class Bullet implements GameObject, Serializable {

    private transient ShooterClient shooterClient;
    private Point2D position;
    private Boolean hit = false;
    private double diameter;
    private Point2D dirVect;

    public Bullet(int x, int y, Point2D dirVect, ShooterClient client) {
        this.position = new Point2D.Double(x, y);
        this.shooterClient = client;
        this.diameter = 10;
        this.dirVect = dirVect;
    }

    @Override
    public void draw(Graphics2D g2d) {
        AffineTransform af = new AffineTransform();
        af.translate(0, 0);
        af.translate(-diameter/2, -diameter/2);

        Shape bullet = af.createTransformedShape(new Rectangle2D.Double(this.diameter/3, this.diameter/2, this.diameter/3, this.diameter));
        AffineTransform pos = new AffineTransform();
        pos.translate(this.position.getX(), this.position.getY());
        g2d.fill(pos.createTransformedShape(bullet));
    }

    @Override
    public void update(double deltatime) {
        if (!hit) {
            this.position = new Point2D.Double(this.position.getX(), this.position.getY() - 5.0D * (deltatime / 5000.0D));
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
