package items;

import items.Sprite;
import physics.Environment;
import physics.Ball;
import physics.Vec2d;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;

public class BallSprite implements Sprite {
    public int width;
    public int height;
    private boolean vis = true;
    private Image image;
    private Ball ball;

    public BallSprite(Ball ball) {
        this.ball = ball;
    }

    @Override
    public void loadImage(String imageName) {
        this.image = new ImageIcon(imageName).getImage();
        this.width = this.image.getWidth(null);
        this.height = this.image.getHeight(null);
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
    public double getX() {
        return ball.getPosition().x;
    }

    @Override
    public double getY() {
        return ball.getPosition().y;
    }

    @Override
    public void setVisible(Boolean visible) {
        vis = visible;
    }

    @Override
    public Ellipse2D getBounds() {
        return null;
    }

    public Ball getBall() { return ball; }

}
