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

    public Vec2d getPosition() {
        return position;
    }

    public void tick() {
        double v = this.velocity.getMagnitude();
        if(v > 0) {
            applyDrag();
            applyFriction();
        }
        applyVelocity();
    }

    public void applyGravity() {
        double gravity = environment.getGravity();
        this.applyForce(new Vec2d(0,gravity*mass));
    }

    public void applyDrag() {
        double dragForce = environment.getDrag() * Math.pow(this.velocity.getMagnitude(),2)/2;
        applyForce(this.velocity.scale(-dragForce));
    }

    public void applyFriction() {
        double frictionForce = environment.getFriction() * this.mass;
        applyForce(this.velocity.scale(-frictionForce));
    }

    public void applyVelocity() {
        this.position = this.position.plus(this.velocity);
    }

    public void applyForce(Vec2d force) {
        // a = F / m
        this.velocity.plus(force.scale(1.0/this.mass).scale(1.0/environment.TICKRATE));
    }
}
