package physics;

import java.awt.geom.Rectangle2D;

public class Collisions{

    /**
     * This array of wall coordinates should be in the environment class
     * {left, right, top, bottom} where left: x = 0
     */
    private int[] Walls = {0,100,100,0};
    private double[] ballLine = new double[4];
    private int[] scaledVector = new int[2];
    private Vec2d normLeftWall = new Vec2d(1,0);
    private Vec2d normRightWall = new Vec2d(-1,0);
    private Vec2d normTopWall = new Vec2d(0,-1);
    private double wallDrag = 0.9;

    public Vec2d checkWalls(Ball ball, int[] Walls){
        if(ball.getPosition().x - ball.getRadius() <= Walls[0]){
            return ball.getVelocity().minus(normLeftWall.scale(ball.getVelocity().dot(normLeftWall) * 2)).scale(wallDrag);
        }
        if(ball.getPosition().x + ball.getRadius() >= Walls[1]){
            return ball.getVelocity().minus(normLeftWall.scale(ball.getVelocity().dot(normLeftWall) * 2)).scale(wallDrag);
        }
        if(ball.getPosition().y + ball.getRadius() >= Walls[2]) {
            return ball.getVelocity().minus(normLeftWall.scale(ball.getVelocity().dot(normLeftWall) * 2)).scale(wallDrag);
        }
    }

    public boolean checkFlipper(Rectangle2D flipper, Ball ball){
        ballLine[0] = ball.getPosition().x;
        ballLine[1] = ball.getPosition().y;

        scaledVector = (ball.getVelocity()*ball.getRadius())/ball.getVelocity().getMagnitude();

        ballLine[2] = scaledVector[0];
        ballLine[3] = scaledVector[1];

        return flipper.intersectsLine(ballLine[0], ballLine[1],ballLine[2],ballLine[3]);
    }
}
