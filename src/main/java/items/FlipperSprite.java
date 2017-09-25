package items;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

/**
 * Created by JoeyH on 2017-09-25.
 */
public class FlipperSprite implements Sprite {
    private double x;
    private double y;
    public int width;
    public int height;
    private boolean vis;
    private Image image;

    @Override
    public double getX() {
        return x;
    }

    @Override
    public double getY() {
        return y;
    }

    public FlipperSprite(float x, float y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public void loadImage(String imageName) {
        this.image = new ImageIcon(imageName).getImage();
        this.width = image.getWidth(null);
        this.height = image.getHeight(null);
    }

    @Override
    public Image getImage() {
        return image;
    }

    @Override
    public boolean isVisible() {
        return vis;
    }

    @Override
    public void setVisible(Boolean visible) {
        vis = visible;
    }

    @Override
    public Ellipse2D getBounds() {
        return new Ellipse2D.Double(this.getX(),this.getY(),width,height);
    }
}
