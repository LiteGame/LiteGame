package items;

import java.awt.Image;
import java.awt.geom.Rectangle2D;
import javax.swing.ImageIcon;

public class Sprite {

    protected float x;
    protected float y;
    protected int width;
    protected int height;
    protected boolean vis;
    protected Image image;

    public Sprite(float x, float y) {

        this.x = x;
        this.y = y;
        vis = true;
    }

    protected void getImageDimensions() {

        width = image.getWidth(null);
        height = image.getHeight(null);
    }

    protected void loadImage(String imageName) {

        ImageIcon ii = new ImageIcon(imageName);
        image = ii.getImage();
    }

    public Image getImage() {
        return image;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public boolean isVisible() {
        return vis;
    }

    public void setVisible(Boolean visible) {
        vis = visible;
    }

    public Rectangle2D getBounds() {
        return new Rectangle2D.Float(x, y, width, height);
    }
}