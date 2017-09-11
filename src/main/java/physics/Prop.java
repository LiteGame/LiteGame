package src.main.java.physics;

/**
 * Created by JoeyH on 2017-09-11.
 */
public class Prop {
    private Environment environment;

    private Vec2d position;
    private Vec2d velocity;
    private double mass = 0;

    public Prop(Environment e, Vec2d position, double mass) {
        this.environment = e;
        this.velocity = new Vec2d(0,0);
        this.position = position;
    }

    public void tick() {
        // TODO: Gravity calculations

        // TODO: Drag calculations

        // TODO: Friction calculations

    }

    public void applyForce(Vec2d force) {
        // a = F / m
        this.velocity.plus(force.scale(1.0/this.mass));
    }
}
