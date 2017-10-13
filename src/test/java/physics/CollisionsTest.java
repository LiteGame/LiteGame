package physics;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.geom.Arc2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CollisionsTest {

    private Line2D line;
    private Ellipse2D ellipse;
    private Arc2D arc;
    private Environment environment;
    private Ball prop;
    private Vec2d normalVector;
    private Set<Collidable> collidables = new HashSet<>();

    @BeforeEach
    void setUp() {
        environment = new Environment();
        line = new Line2D.Double(0, 0, 100, 0);
        ellipse = new Ellipse2D.Double(0,0,20,20);
        arc = new Arc2D.Double(-50,-50,100,100, 0, 90, Arc2D.OPEN);
        normalVector = new Vec2d(0,1);

        collidables.add(new PhysicsShape(line));
        collidables.add(new PhysicsShape(ellipse));
        collidables.add(new PhysicsShape(arc));

        prop = new Ball(environment, new Vec2d(50,50), 10, 5);
        prop.setVelocity(new Vec2d(0,-5));
    }

    @Test
    void checkCollisionPropLine() {
        Vec2d zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
        Vec2d zeroToLine = new Vec2d(line.getX1(), line.getY1());
        Vec2d a = zeroToLine.minus(zeroToBall);
        Vec2d b = new Vec2d(line.getX1() - line.getX2(), line.getY1() - line.getY2());
        Vec2d bUnit = b.scale(1 / b.getMagnitude());
        Vec2d distanceBallLine = a.minus(bUnit.scale(a.dot(bUnit)));
        Vec2d normalVector = distanceBallLine.scale(-1 / distanceBallLine.getMagnitude());
        Assertions.assertEquals(normalVector, new Vec2d(0,1));
    }

    @Test
    void checkCollisionPropEllipse() {
        Vec2d zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
        Vec2d zeroToEllipse = new Vec2d(ellipse.getCenterX(), ellipse.getCenterY());
        Vec2d distanceBallEllipse = zeroToBall.minus(zeroToEllipse);

        double angle = distanceBallEllipse.getPhase();
        double a = ellipse.getWidth() / 2;
        double b = ellipse.getHeight() / 2;
        double radiusEllipse = a * b / Math.sqrt(a * a * Math.sin(angle) * Math.sin(angle) + b * b * Math.cos(angle) * Math.cos(angle));

        double aOutside = (ellipse.getWidth() + 5) / 2;
        double bOutside = (ellipse.getHeight() + 5) / 2;


        Vec2d normalVectorOutside = new Vec2d(distanceBallEllipse.x * bOutside/aOutside, distanceBallEllipse.y * aOutside/bOutside);
        normalVectorOutside = normalVectorOutside.scale(1/normalVectorOutside.getMagnitude());
        Assertions.assertEquals(normalVectorOutside, new Vec2d(0.5*Math.sqrt(2), 0.5*Math.sqrt(2)));
    }

    @ Test
    void checkCollisionPropArc() {
        Vec2d zeroToBall = prop.getPosition().plus(new Vec2d(5, 5));
        Vec2d zeroToCenterArc = new Vec2d(arc.getCenterX(), arc.getCenterY());
        Vec2d distanceBallArc = zeroToBall.minus(zeroToCenterArc);

        double a = arc.getWidth() / 2;
        double b = arc.getHeight() / 2;

        double aInside = (arc.getWidth() - 5) / 2;
        double bInside = (arc.getHeight() - 5) / 2;

        Vec2d normalVectorInside = new Vec2d(distanceBallArc.x * bInside/aInside, distanceBallArc.y * aInside/bInside);
        normalVectorInside = normalVectorInside.scale(-1/normalVectorInside.getMagnitude());
        Vec2d normalVectorLocation = distanceBallArc.minus(normalVectorInside.scale(5));
        Vec2d normalVectorInwards = new Vec2d(normalVectorLocation.x * b/a, normalVectorLocation.y * a/b);
        normalVectorInwards = normalVectorInwards.scale(-1/normalVectorInwards.getMagnitude());
        Assertions.assertEquals(normalVectorInwards, new Vec2d(-0.7071067811865475,-0.7071067811865475));
    }

    @Test
    void bounce() {
        Vec2d deceleration = prop.getVelocity().scale(-100);
        Vec2d reactionForce = deceleration.scale(prop.getMass());
        Vec2d normalForce = normalVector.scale(reactionForce.dot(normalVector));
        Assertions.assertEquals(normalForce.scale(2), new Vec2d(0,10000));
    }

    @Test
    void checkCollision() {
        for (Collidable collidable : collidables) {
            String objectName = collidable.getShape().toString().split("\\$")[0];
            switch (objectName) {
                // Every case has its own collision method.
                case "java.awt.geom.Line2D":
                    Assertions.assertEquals(line, (Line2D) collidable.getShape());
                    break;
                case "java.awt.geom.Ellipse2D":
                    Assertions.assertEquals(ellipse, (Ellipse2D) collidable.getShape());

                    break;
                case "java.awt.geom.Arc2D":
                    Assertions.assertEquals(arc, (Arc2D) collidable.getShape());
                    break;
            }
        }
    }

}