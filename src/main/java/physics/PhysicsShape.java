package physics;

import java.awt.*;

public class PhysicsShape implements Collidable {
    private Shape shape;
    private Integer id;

    public PhysicsShape(Integer id, Shape shape) {
        this.shape = shape;
        this.id = id;
    }

    public Shape getShape() {
        return shape;
    }

    public Integer getID() { return id; }

    public void setShape(Shape shape) {
        this.shape = shape;
    }

    @Override
    public void tick() {
        //TODO: Implement tick method
    }
}
