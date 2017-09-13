package items;

import java.awt.event.KeyEvent;

public class TempBall extends Sprite {

    private float dx;
    private float dy;
    private float speed;
    private boolean in_start_position;
    private int ticks = 0;
    private String state = "";

    public TempBall(float x, float y) {
        super(x, y);

        initBall();
    }

    private void initBall() {

        loadImage("resources/dot.png");
        getImageDimensions();
    }

    public void move() {

        // Speed limits, suf!

        if(dx > 10){
            dx = 10;
        }
        if(dy > 10){
            dy = 10;
        }

        // Actual move

        x += dx;
        y += dy;

        // Boundary box, Jeeeey!

        switch (state) {
            case "Game":
                if (x < 201) {
                    x = 201;
                }

                if (y < 51) {
                    y = 51;
                }

                if (x > 539) {
                    x = 539;
                }

                if (y > 489) {
                    y = 489;
                }
                break;
            case "Launch":
                if (x < 199) {
                    x = 199;
                }

                if (y < 49) {
                    y = 49;
                }

                if (x > 589) {
                    x = 589;
                }

                if (y > 539) {
                    y = 539;
                }
                break;
            default:
                if (x < 1) {
                    x = 1;
                }

                if (y < 1) {
                    y = 1;
                }

                if (x > 789) {
                    x = 789;
                }

                if (y > 589) {
                    y = 589;
                }
                break;
        }

    }

    public void set_in_start_position(Boolean enable) {
        in_start_position = enable;
    }

    public void set_state(String state) {
        this.state = state;
    }

    public float getMoveAngle() {
        return (float)Math.toDegrees(Math.atan2(-dx, dy));
    }

    public float getSpeed() {
        return (float)Math.sqrt(dx * dx + dy * dy);
    }

    public void gravity(Boolean enable) {
        ticks++;

        if(enable && ticks == 8){
            dy += 1;
            ticks = 0;
        }

    }

    public void bounce(String angle){
        if(angle.equals("90")){
            float temp = dx;
            dx = dy;
            dy = temp;
        }
        else if(angle.equals("Left wall")){
            dx = (float)(-dx * 0.9);
        }
        else if(angle.equals("Right wall")){
            dx = (float)(-dx * 0.9);
        }
        else if(angle.equals("Bottom wall")){
            dy = (float)(-dy * 0.9);
        }
        else if(angle.equals("Top wall")){
            dy = (float)(-dy * 0.9);
        }

    }

    public void keyPressed(KeyEvent e) {

        int key = e.getKeyCode();

        if (key == KeyEvent.VK_SPACE && in_start_position) {
            dy = -8;
        }

    }
}