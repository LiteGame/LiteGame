package physics;
import java.awt.geom.*;
import java.util.Set;

public class Collisions {

    /**
     * This function calculates if there is a collision between a ellipse and a prop/ball. First we create a outer
     * ellipse that is slightly larger such that it intersects the middle of the prop/ball at collision. This point of
     * collision at the outer ellipse is then scaled back onto the original ellipse. Then at this point we calculate
     * the normal vector later used for bouncing.
     * @param prop This prop is now assumed to be the pinball
     * @param ellipse This is the ellipse the pinball can collide with
     */
    private static void checkCollisionPropEllipse(Prop prop, Ellipse2D ellipse) {
        // Is the vector from the corner of the screen to the middle of the ball.
        Vec2d zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
        // This is the position vector of the bumper.
        Vec2d zeroToEllipse = new Vec2d(ellipse.getCenterX(), ellipse.getCenterY());
        // Calculate the distance between the bumper and the ball.
        Vec2d distanceBallEllipse = zeroToBall.minus(zeroToEllipse);

        // Calculate the radius of the ellipse.
        double angle = distanceBallEllipse.getPhase();
        double a = ellipse.getWidth() / 2;
        double b = ellipse.getHeight() / 2;
        double radiusEllipse = a * b / Math.sqrt(a * a * Math.sin(angle) * Math.sin(angle) + b * b * Math.cos(angle) * Math.cos(angle));

        // Outside collision bound
        double aOutside = (ellipse.getWidth() + 5) / 2;
        double bOutside = (ellipse.getHeight() + 5) / 2;

        // If the ellipse and prop intersect then we have a collision.
        if (distanceBallEllipse.getMagnitude() < (5 + radiusEllipse)) {

            // Calculate the normal vector
            Vec2d normalVectorOutside = new Vec2d(distanceBallEllipse.x * bOutside/aOutside, distanceBallEllipse.y * aOutside/bOutside);
            normalVectorOutside = normalVectorOutside.scale(1/normalVectorOutside.getMagnitude());

            bounce(prop, normalVectorOutside);
            // Here we move the ball in the direction of the normal vector until the prop doesn't intersect the ellipse anymore.
            while (distanceBallEllipse.getMagnitude() <= (5 + radiusEllipse)) {
                zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
                distanceBallEllipse = zeroToBall.minus(zeroToEllipse);
                prop.setPosition(prop.getPosition().plus(normalVectorOutside));
            }
        }
    }

    /**
     * This function check collision between the ball and any line.
     * @param prop This prop is now assumed to be the pinball
     * @param line This is the line the pinball can collide with
     */
    private static void checkCollisionPropLine(Prop prop, Line2D line) {
        // Is the vector from the corner of the screen to the middle of the ball.
        Vec2d zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
        // The vector from the corner of the screen to the edge of the line.
        Vec2d zeroToLine = new Vec2d(line.getX1(), line.getY1());
        // Vector a is the vector from the corner if the line to the center of the ball.
        Vec2d a = zeroToLine.minus(zeroToBall);
        // Vector b is the vector that has the same size and direction as Line2D line.
        Vec2d b = new Vec2d(line.getX1() - line.getX2(), line.getY1() - line.getY2());
        // bUnit is the unit vector of b.
        Vec2d bUnit = b.scale(1 / b.getMagnitude());
        // This is distance between the line and the ball calculated using vector calculus.
        Vec2d distanceBallLine = a.minus(bUnit.scale(a.dot(bUnit)));
        // Now we normalize this distance for later use in the bounce function.
        Vec2d normalVector = distanceBallLine.scale(-1 / distanceBallLine.getMagnitude());
        // Calculate the angle between a and b.
        double angle = Math.acos(a.dot(b) / (a.getMagnitude() * b.getMagnitude()));
        // If the distance between the line and the ball is smaller than the radius, we get a collision.
        if (distanceBallLine.getMagnitude() < 5 && bUnit.scale(a.dot(bUnit)).getMagnitude() < b.getMagnitude() && angle < Math.PI * 0.25) {

            bounce(prop, normalVector);
            // Keep the prop moving until it doesn't intersect the line anymore.
            while (distanceBallLine.getMagnitude() <= 5) {
                zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
                distanceBallLine = zeroToBall.minus(zeroToLine);
                prop.setPosition(prop.getPosition().plus(normalVector));
            }
        }
    }

    /**
     * This function checks for collision between ball and arc. It does this by making a inner ellipse and outer ellipse
     * these ellipses are such that they intersect the middle of the prop/ball at collision. The normal vectors locations
     * are calculated on these inner and outer ellipses and then scaled back onto the original ellipse. Finally the
     * normal vector is calculated at this point on the actual ellipse.
     * @param prop This prop is now assumed to be the pinball
     * @param arc This is the arc the pinball can collide with
     */
    private static void checkCollisionPropArc(Prop prop, Arc2D arc) {
        Vec2d zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
        Vec2d zeroToCenterArc = new Vec2d(arc.getCenterX(), arc.getCenterY());
        Vec2d distanceBallArc = zeroToBall.minus(zeroToCenterArc);

        // Calculate the radius of the arc.
        double angle = -((Math.toDegrees(distanceBallArc.getPhase()) - 360) % 360);
        double a = arc.getWidth() / 2;
        double b = arc.getHeight() / 2;

        // Inside collision bound.
        double aInside = (arc.getWidth() - 5) / 2;
        double bInside = (arc.getHeight() - 5) / 2;

        // Outside collision bound.
        double aOutside = (arc.getWidth() + 5) / 2;
        double bOutside = (arc.getHeight() + 5) / 2;

        // Normal of a ellipse
        double theta = distanceBallArc.getPhase();
        double radiusEllipse = a * b / Math.sqrt(a * a * Math.sin(theta) * Math.sin(theta) + b * b * Math.cos(theta) * Math.cos(theta));
        double radiusInsideEllipse = aInside * bInside / Math.sqrt(aInside * aInside * Math.sin(theta) * Math.sin(theta) + bInside * bInside * Math.cos(theta) * Math.cos(theta));
        double radiusOutsideEllipse = aOutside * bOutside / Math.sqrt(aOutside * aOutside * Math.sin(theta) * Math.sin(theta) + bOutside * bOutside * Math.cos(theta) * Math.cos(theta));

        // Check if inside the arc.
        if (distanceBallArc.getMagnitude() < radiusEllipse && arc.containsAngle(angle)) {
            if (distanceBallArc.getMagnitude() > radiusInsideEllipse) {

                // Calculate the normal vector
                Vec2d normalVectorInside = new Vec2d(distanceBallArc.x * bInside/aInside, distanceBallArc.y * aInside/bInside);
                normalVectorInside = normalVectorInside.scale(-1/normalVectorInside.getMagnitude());
                Vec2d normalVectorLocation = distanceBallArc.minus(normalVectorInside.scale(5));
                Vec2d normalVectorInwards = new Vec2d(normalVectorLocation.x * b/a, normalVectorLocation.y * a/b);
                normalVectorInwards = normalVectorInwards.scale(-1/normalVectorInwards.getMagnitude());

                // Collision on the inside.
                bounce(prop, normalVectorInwards);
            }
            // Else if its on the outside.
        } else if (distanceBallArc.getMagnitude() > radiusEllipse && arc.containsAngle(angle)) {
            if (distanceBallArc.getMagnitude() < radiusOutsideEllipse) {

                // Calculate the normal vector
                Vec2d normalVectorOutside = new Vec2d(distanceBallArc.x * bOutside/aOutside, distanceBallArc.y * aOutside/bOutside);
                normalVectorOutside = normalVectorOutside.scale(-1/normalVectorOutside.getMagnitude());
                Vec2d normalVectorLocation = distanceBallArc.minus(normalVectorOutside.scale(5));
                Vec2d normalVectorOutwards = new Vec2d(normalVectorLocation.x * b/a, normalVectorLocation.y * a/b);
                normalVectorOutwards = normalVectorOutwards.scale(1/normalVectorOutwards.getMagnitude());

                // Collision on the outside.
                bounce(prop, normalVectorOutwards);
            }
        }
    }

    /**
     * This function bounces the prop like a mirror.
     * @param prop This prop is now assumed to be the pinball
     * @param normalVector This is the normal vector that is used to determine what direction to bounce to
     */
    private static void bounce(Prop prop, Vec2d normalVector) {
        // Calculate the deceleration with a collision duration of 1 tick.
        Vec2d deceleration = prop.getVelocity().scale(-100);
        // F = m*a
        Vec2d reactionForce = deceleration.scale(prop.getMass());
        // Normal force is the reaction force projected onto the normal vector of the object we bounce from.
        Vec2d normalForce = normalVector.scale(reactionForce.dot(normalVector));
        // Apply the force and let the physics do its magic.
        prop.applyForce(normalForce.scale(2));
    }


    /**
     * This function iterates through the sets of collidables, we check what shape it has and apply the correct function accordingly.
     * @param prop This prop is now assumed to be the pinball
     * @param collidables This is a set of all the shapes(Line2D, Ellipse2D, Arc2D) the prop can collide with
     */
    public static void checkCollision(Prop prop, Set<Collidable> collidables) {
        for (Collidable collidable : collidables) {
            String objectName = collidable.getShape().toString().split("\\$")[0];
            switch (objectName) {
                // Every case has its own collision method.
                case "java.awt.geom.Line2D":
                    Line2D line = (Line2D) collidable.getShape();
                    checkCollisionPropLine(prop, line);
                    break;
                case "java.awt.geom.Ellipse2D":
                    Ellipse2D ellipse = (Ellipse2D) collidable.getShape();
                    if ((int)prop.getID() != (int)collidable.getID()){
                        System.out.println(prop.getID().toString() + collidable.getID().toString());
                        checkCollisionPropEllipse(prop, ellipse);}
                    break;
                case "java.awt.geom.Arc2D":
                    Arc2D arc = (Arc2D) collidable.getShape();
                    checkCollisionPropArc(prop, arc);
                    break;
            }
        }
    }
}
