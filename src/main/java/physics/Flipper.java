package physics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

public class Flipper extends PhysicsShape {
    public enum FlipperPosition {
        LEFT, RIGHT
    }
    FlipperPosition position = FlipperPosition.LEFT;

    int flipperSpeed;
    Environment e;
    private double angle;
    private boolean keyPressed = false;

    public Flipper(Integer id, Line2D flipperLine, int flipperSpeed) {
        super(flipperLine);
        this.flipperSpeed = flipperSpeed;
    }

    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent e) {

    }

    @Override
    public Shape getShape() {
        return super.getShape();
    }

    @Override
    public void tick() {
        if (keyPressed) {
            angle += flipperSpeed;
            if (angle >= 30) {
                angle = 30;
            }
        } else {
            angle -= 2 * flipperSpeed;
            if (-20 >= angle) {
                angle = -20;
            }
        }
        switch(position) {
            case LEFT:
                // Left flipper
                break;
            case RIGHT:
                // Right flipper.
                break;
        }
    }
}
