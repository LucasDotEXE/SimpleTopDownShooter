package avans.shooter.Client.Game;

import java.awt.*;

public interface GameObject {
    void draw(Graphics2D g2d);
    void update(double deltatime);
}
