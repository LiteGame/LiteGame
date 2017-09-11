package items;

import java.awt.event.KeyEvent;

public class TempBall extends Sprite {

    private int dx;
    private int dy;
    private boolean in_start_position;

    public TempBall(int x, int y) {
        super(x, y);

        initBall();
    }

    private void initBall() {

        loadImage("resources/dot.png");
        getImageDimensions();
    }

    public void move() {

        if(dx > 5){
            dx = 5;
        }
        if(dy > 5){
            dy = 5;
        }

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }

    }

    public void set_in_start_position(Boolean enable) {
        in_start_position = enable;
    }

    public void gravity(Boolean enable) {
        if(enable){
            dy += 1;
        }
        else{
            dy = 0;
        }
    }

    public void bounce(int angle){
        if(angle == 90){
            int temp = dx;
            dx = dy;
            dy = temp;
        }
    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && in_start_position) {
            dy = -3;
            dx = 0;
        }

    }
}