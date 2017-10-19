package physics;

import items.BallSprite;

import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Ball extends Prop {
    double radius;
    int id;
    private boolean frozen;

    public BallSprite getSprite() {
        return sprite;
    }

    public void setSprite(BallSprite sprite) {
        this.sprite = sprite;
    }

    private BallSprite sprite;

    public Ball(Environment e, Vec2d position, double mass, double radius, int id) {
        super(e, position, mass, id);
        this.radius = radius;
        this.id = id;
    }

    public Shape getShape() {
        Vec2d pos = this.getPosition();
        Ellipse2D ballShape = new Ellipse2D.Double(pos.x-radius,pos.y-radius,radius*2,radius*2);
        return ballShape;
    }

    public void tick() {
        if(!frozen) {
            super.tick();
        }
    }

    public void freeze() {
        frozen = true;
    }

    public void unfreeze() {
        frozen = false;
    }

    public boolean isFrozen() {
        return frozen;
    }
}
