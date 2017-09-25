package items;

import java.awt.event.KeyEvent;
import java.security.Key;

public class Flipper_Right extends FlipperSprite {
    private double dx;
    private double dy;
    private double rotation;

    public Flipper_Right(float x, float y, double rotation) {
        super(x, y);
        this.rotation = rotation;
        initBall();
    }

    private void initBall() {

        loadImage("resources/flipper_right.png");
    }

    public double getAngle() {
        return this.rotation;
    }

    public void move() {

//        x += dx;
//        y += dy;
//
//        if (x < 1) {
//            x = 1;
//        }
//
//        if (y < 1) {
//            y = 1;
//        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT) {
            rotation = -165;
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_RIGHT) {
            rotation = -195;
        }

    }

}
