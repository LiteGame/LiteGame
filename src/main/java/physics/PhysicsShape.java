package physics;

import java.awt.*;

/**
 * Created by JoeyH on 2017-10-05.
 */
public class PhysicsShape implements Collidable {
    Shape shape;

    public PhysicsShape(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void tick() {
        //TODO: Implement tick method
    }
}
