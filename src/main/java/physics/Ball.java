package physics;

import items.BallSprite;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends Prop {
    double radius;

    public BallSprite getSprite() {
        return sprite;
    }

    public void setSprite(BallSprite sprite) {
        this.sprite = sprite;
    }

    private BallSprite sprite;

    public Ball(Environment e, Vec2d position, double mass, double radius) {
        super(e, position, mass);
        this.radius = radius;
    }

    public Shape getShape() {
        Vec2d pos = this.getPosition();
        Ellipse2D ballShape = new Ellipse2D.Double(pos.x-radius,pos.y-radius,radius*2,radius*2);
        return ballShape;
    }
}
