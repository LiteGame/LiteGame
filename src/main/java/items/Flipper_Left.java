package items;

import java.awt.event.KeyEvent;
import nl.tu.delft.defpro.api.APIProvider;

public class Flipper_Left extends FlipperSprite {
    private float dx;
    private float dy;
    private float rotation;

    public Flipper_Left(float x, float y, float rotation) {
        super(x, y);
        this.rotation = rotation;
        initBall();
    }

    private void initBall() {

        loadImage("resources/flipper_left.png");
    }

    public float getAngle() {
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

        if (key == KeyEvent.VK_LEFT) {
            rotation = -15;
        }

    }

    public void keyReleased(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_LEFT) {
            rotation = 15;
        }

    }


}
