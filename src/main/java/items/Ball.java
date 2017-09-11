package items;

import java.awt.*;

/**
 * Created by JoeyH on 2017-09-11.
 */
public class Ball {

    private int ballRadius;
    static Color ballColor;
    private int x;
    private int y;
    private double vx;
    private double vy;
    private double dir;

    public Ball(int ballRadius, int x, int y) {
        this.ballRadius = ballRadius;
        this.x = x;
        this.y = y;
    }

    /**
    public void draw(Graphics g){
        g.setColor(ballColor);
        g.drawOval(x, y, ballRadius, ballRadius);
    }
     */

    public void moveBall(int dx, int dy){
        x += dx;
        y += dy;
    }

    public void setVelocity(double vx, double vy){
        this.vx = vx;
        this.vy = vy;
    }

    /**
     * Create getPosition that return the vector(x, y)
     */

    /**
     * ballDirection function is present in the Vector Class.
     */

    public double ballDirection(double dy, double dx){
        dir = Math.atan2(dy, dx);
        return dir;
    }

    public static void main(String[] args){
        Ball b = new Ball(10, 200, 200);
        System.out.print(b.ballDirection(1000, 1));
    }

}
