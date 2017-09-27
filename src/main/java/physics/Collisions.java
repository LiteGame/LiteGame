package physics;
import items.BallSprite;
import map.Board;
import java.awt.geom.Line2D;

public class Collisions{

    private static double boost;

    /**
     * This function calculates if there is a collision between a bumper and a ball and calculates the speed after
     * impact accordingly.
     */
    /**
     private void checkCollisionBallBumper(Ball ball, Bumper bumper) {
         // Set the boost
         boost = 1.3;
         // Calculate the distance between the bumper and the ball.
         Vec2d distanceBallBumper = ball.getPosition() - bumper.getPosition();
         // Normalize this vector for the bounce function.
         Vec2d normalVector = distanceBallBumper.scale(1/distanceBallBumper.getMagnitude());
         // If the bumper and ball intersect then we have a collision.
         if (distanceBallBumper < (ball.getRadius() + bumper.getRadius())) {
             // Bounce the ball because of collision.
             bounce(ball, normalVector, boost);
         }
     }
    */

    /**
     * This function check collision between the ball and a line. This line can we a wall or a part of the flipper.
     */
    public static void checkCollisionBallLine(Ball ball, Line2D line){
        // Set the boost
        boost = 3;
        // Is the vector from the corner of the screen to the middle of the ball.
        Vec2d zerotoBall = ball.getPosition();
        // The vector from the corner of the screen to the edge of the line.
        Vec2d zerotoLine = new Vec2d(line.getX1(), line.getY1());
        // Vector a is the vector from the corner if the line to the center of the ball.
        Vec2d a = zerotoBall.minus(zerotoLine);
        // Vector b is the vector that has the same size and direction as the Line2D.
        Vec2d b = new Vec2d(line.getX1() - line.getX2(), line.getY1() - line.getY2());
        System.out.println(b);
        // bUnit is the unit vector of b.
        Vec2d bUnit = b.scale(1 / b.getMagnitude());
        // This is distance between the line and the ball calculated using vector calculus.
        Vec2d distanceBallLine = a.minus(bUnit.scale(a.dot(bUnit)));
        // Now we normalize this distance for later use in the bounce function.
        Vec2d normalVector = distanceBallLine.scale(1/distanceBallLine.getMagnitude());

        // If the distance between the line and middle of the ball is smaller than the radius, we get a collision.
        // The 10 should be replaced by a getter as this is the radius of the ball.
        if (distanceBallLine.getMagnitude() < 10) {
            // Bounce the ball because of collision.
            bounce(ball, normalVector, boost);
        }
    }

    /**
     * This function bounces the ball given a normal vector
     */
    private static void bounce(Ball ball, Vec2d normalVector, double boost){
        // The following formula is used to mirror the velocity of the ball after impact.
        // r = d - 2(d*n))n where r = outgoingVelocity, d = incomingVelocity and n = normalVector.
        Vec2d d = ball.getVelocity();
        double dn = d.dot(normalVector);
        Vec2d outgoingVelocity = d.minus(normalVector.scale(dn*2));
        // Set the new velocity of the ball.
        ball.setVelocity(outgoingVelocity.scale(boost));
    }
}
