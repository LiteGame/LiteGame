package physics;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Line2D;

public class Flipper extends PhysicsShape {
    Environment e;
    private double angle;
    private boolean keyPressed = false;

    public Flipper(Integer id, Line2D flipperLine) {
        super(id, flipperLine);
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
    public Integer getID() {
        return super.getID();
    }

    @Override
    public void tick() {
        super.tick();
    }
}
