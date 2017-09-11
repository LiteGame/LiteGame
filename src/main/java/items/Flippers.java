package items;

public class Flippers extends Sprite {
    private int dx;
    private int dy;

    public Flippers(int x, int y) {
        super(x, y);

        initBall();
    }

    private void initBall() {

        loadImage("resources/dot.png");
        getImageDimensions();
    }

    public void move() {

        x += dx;
        y += dy;

        if (x < 1) {
            x = 1;
        }

        if (y < 1) {
            y = 1;
        }
    }

}
