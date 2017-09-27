package physics;
import items.BallSprite;
import map.Board;
import java.awt.geom.Line2D;

public class Collisions{

    private static Vec2d normalVector;
    private static double wallDrag = 0.9;
    private static String collisionBetween = "";
    private static Vec2d lineVector;

    /**
     * This function calculates if there is a collision between a bumper and a ball and calculates the speed after
     * impact accordingly.
     */
    /**
     private String checkCollisionBallBumper(Ball ball, Bumper bumper){
     // Normal vector is the vector perpendicular to the surface of the bumper. Simply the vector from the center of
     // the ball to the center of the bumper.
     normalVector = ball.getPosition() - bumper.getPosition();
     incomingVelocity = ball.getVelocity();
     if (normalVector < (ball.getRadius() + bumper.getRadius())){
     bounce(ball, normalVector);
     collisionBetween = "BallBumper";
     return collisionBetween;
     }
     }
     */
    /**
     * This function check collision between the ball and a line. This line can we a wall or a part of the flipper.
     */


    public static boolean checkCollisionBallLine(Ball ball, Line2D line){
        // Check collision and mirror velocity.
        // Assuming that line.getPoints returns a double list = [x1, y1, x2, y2]
        lineVector = new Vec2d(line.getX1() - line.getX2(), line.getY1() - line.getY2());
        // projected = ball * line / |line|
        Vec2d a = ball.getPosition();
        Vec2d b = lineVector;
        Vec2d bUnit = b.scale(1 / b.getMagnitude());
        double a1 = a.dot(bUnit);
        Vec2d a1Vector = bUnit.scale(a1);
        Vec2d distanceBallLine = a.minus(a1Vector);
        normalVector = distanceBallLine.scale(1/distanceBallLine.getMagnitude());
        if (distanceBallLine.getMagnitude() < 10){
            // Collision
            bounce(ball, normalVector);
            collisionBetween = "BallLine";
            return true;
        }
        else{
            return false;
        }
    }

    /**
     * This function bounces the ball given a normal vector
     */

    private static void bounce(Ball ball, Vec2d normalVector){
        // r = d - 2(d*n))n where r = outgoingVelocity, d = incomingVelocity and n = normalVector
        Vec2d d = ball.getVelocity();
        Vec2d n = normalVector;
        double dn = d.dot(n);
        Vec2d outgoingVelocity = d.minus(n.scale(dn*2));
        //System.out.println(n);
        //Vec2d outgoingVelocity = new Vec2d(10,-10);
        ball.setVelocity(outgoingVelocity);
    }
}
