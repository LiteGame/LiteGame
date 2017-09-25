package items;

import java.awt.Image;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public interface Sprite {

    void loadImage(String imageName);

    Image getImage();

    boolean isVisible();

    double getX();

    double getY();

    void setVisible(Boolean visible);

    Ellipse2D getBounds();

}