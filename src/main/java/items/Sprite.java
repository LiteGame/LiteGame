package items;

import physics.Vec2d;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public interface Sprite {

    void loadImage(String imageName);

    Image getImage();

    boolean isVisible();

    Vec2d getPosition();

    //double getX();

    //double getY();

    void setVisible(Boolean visible);

    Ellipse2D getBounds();

}