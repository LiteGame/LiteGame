package physics;
import items.BallSprite;
import map.Board;

import javax.sound.sampled.Line;
import java.awt.geom.*;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class Collisions {

    /**
     * This function calculates if there is a collision between a bumper and a ball and calculates the speed after
     * impact accordingly.
     */

    private static void checkCollisionBallEllipse(Prop prop, Ellipse2D ellipse) {
        // Is the vector from the corner of the screen to the middle of the ball.
        Vec2d zerotoBall = prop.getPosition().plus(new Vec2d(5, 5));
        // This is the position vector of the bumper.
        Vec2d zerotoEllipse = new Vec2d(ellipse.getCenterX(), ellipse.getCenterY());
        // Calculate the distance between the bumper and the ball.
        Vec2d distanceBallEllipse = zerotoBall.minus(zerotoEllipse);
        // Calculate the radius of the ellipse.
        double angle = Math.atan2(distanceBallEllipse.y, distanceBallEllipse.x);
        double a = ellipse.getWidth() / 2;
        double b = ellipse.getHeight() / 2;
        double radiusEllipse = a * b / Math.sqrt(a * a * Math.sin(angle) * Math.sin(angle) + b * b * Math.cos(angle) * Math.cos(angle));
        // Normal of a ellipse
        Vec2d normalVector = new Vec2d(b * Math.cos(angle), a * Math.sin(angle)).scale(1 / Math.sqrt(a * a * Math.pow(Math.sin(angle), 2) + b * b * Math.pow(Math.cos(angle), 2)));
        // If the bumper and ball intersect then we have a collision.
        if (distanceBallEllipse.getMagnitude() < (5 + radiusEllipse)) {
            // Bounce the ball because of collision.
            bounce(prop, normalVector);
        }
    }

    /**
     * This function check collision between the ball and a line. This line can we a wall or a part of the flipper.
     */

    private static void checkCollisionBallLine(Prop prop, Line2D line) {
        // Is the vector from the corner of the screen to the middle of the ball.
        Vec2d zerotoBall = prop.getPosition().plus(new Vec2d(5, 5));
        // The vector from the corner of the screen to the edge of the line.dwwa
        Vec2d zerotoLine = new Vec2d(line.getX1(), line.getY1());
        // Vector a is the vector from the corner if the line to the center of the ball.
        Vec2d a = zerotoLine.minus(zerotoBall);
        // Vector b is the vector that has the same size and direction as the Line2D.
        Vec2d b = new Vec2d(line.getX1() - line.getX2(), line.getY1() - line.getY2());
        // bUnit is the unit vector of b.
        Vec2d bUnit = b.scale(1 / b.getMagnitude());
        // This is distance between the line and the ball calculated using vector calculus.
        Vec2d distanceBallLine = a.minus(bUnit.scale(a.dot(bUnit)));
        // Now we normalize this distance for later use in the bounce function.
        Vec2d normalVector = distanceBallLine.scale(1 / distanceBallLine.getMagnitude());
        // Calculate the angle between a and b.
        double angle = Math.acos(a.dot(b) / (a.getMagnitude() * b.getMagnitude()));
        // If the distance between the line and middle of the ball is smaller than the radius, we get a collision.
        // The 10 should be replaced by a getter as this is the radius of the ball.
        if (distanceBallLine.getMagnitude() < 5 && bUnit.scale(a.dot(bUnit)).getMagnitude() < b.getMagnitude() && angle < Math.PI * 0.25) {
            // Bounce the ball because of collision.
            bounce(prop, normalVector);
        }
    }

    /**
     * This function checks for collision between ball and arc.
     */

    private static void checkCollisionBallArc(Prop prop, Arc2D arc) {
        Vec2d zerotoBall = prop.getPosition().plus(new Vec2d(5, 5));
        Vec2d zerotoCenterArc = new Vec2d(arc.getCenterX(), arc.getCenterY());
        Vec2d distanceBallArc = zerotoBall.minus(zerotoCenterArc);

        // Calculate the radius of the arc.
        double angle = -((Math.toDegrees(distanceBallArc.getPhase()) - 360) % 360);
        double a = arc.getWidth() / 2;
        double b = arc.getHeight() / 2;

        double startAngle = arc.getAngleStart();
        double endAngle = arc.getAngleStart() + arc.getAngleExtent();

        // Normal of a ellipse
        double thetha = distanceBallArc.getPhase();
        double radiusEllipse = a * b / Math.sqrt(a * a * Math.sin(thetha) * Math.sin(thetha) + b * b * Math.cos(thetha) * Math.cos(thetha));
        Vec2d normalVector = new Vec2d(b * Math.cos(thetha), a * Math.sin(thetha)).scale(1 / Math.sqrt(a * a * Math.pow(Math.sin(thetha), 2) + b * b * Math.pow(Math.cos(thetha), 2)));
        // Check if inside the arc.
        if (distanceBallArc.getMagnitude() < radiusEllipse && arc.containsAngle(angle)) {
            if (distanceBallArc.getMagnitude() + 5 > radiusEllipse) {
                // Collision on the inside.
                bounce(prop, normalVector);
            }
            // Else if its on the outside.
        } else if (distanceBallArc.getMagnitude() > radiusEllipse && arc.containsAngle(angle)) {
            if (distanceBallArc.getMagnitude() - 5 < radiusEllipse) {
                // Collision on the outside.
                bounce(prop, normalVector);
            }
        }
    }

    /**
     * This function bounces the ball given a normal vector
     */
    private static void bounce(Prop prop, Vec2d normalVector) {
        // Calculate the deacceleration with a collision duration of 1 tick.
        Vec2d deacceleration = prop.getVelocity().scale(-100);
        // F = m*a
        Vec2d reactionForce = deacceleration.scale(prop.getMass());
        // Normal force is the reaction force projected onto the normal vector of the object we bounce from.
        Vec2d normalForce = normalVector.scale(reactionForce.dot(normalVector));
        // Apply the force and let the physics do its magic.
        prop.applyForce(normalForce.scale(2));
    }

    /**
     * This function iterates through the sets of lines, ellipses and arcs and checks collision.
     */
    public static void checkCollision(Prop prop, Set<Collidable> collidables) {
        for (Collidable collidable : collidables) {
            String objectName = collidable.getShape().toString().split("\\$")[0];
            switch (objectName) {
                // Every case has its own collision method.
                case "java.awt.geom.Line2D":
                    Line2D line = (Line2D) collidable.getShape();
                    checkCollisionBallLine(prop, line);
                    break;
                case "java.awt.geom.Ellipse2D":
                    Ellipse2D ellipse = (Ellipse2D) collidable.getShape();
                    checkCollisionBallEllipse(prop, ellipse);
                    break;
                case "java.awt.geom.Arc2D":
                    Arc2D arc = (Arc2D) collidable.getShape();
                    checkCollisionBallArc(prop, arc);
                    break;
            }
        }
    }
}
