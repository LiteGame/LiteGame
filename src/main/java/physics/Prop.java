package physics;

/**
 * Represents a physics point object.
 */
public class Prop {
    /**
     * The environment to handle physics in.
     */
    private Environment environment;

    /**
     * The position of this prop.
     */
    private Vec2d position;
    /**
     * The velocity of this prop.
     */
    private Vec2d velocity;
    /**
     * The mass of this prop.
     */
    private double mass = 1;

    public Prop(Environment e, Vec2d position, double mass) {
        this.environment = e;
        this.velocity = new Vec2d(0,0);
        this.position = position;
        this.mass = mass;
    }

    public Vec2d getPosition() {
        return position;
    }

    public Vec2d getVelocity() {
        return velocity;
    }

    public void setVelocity(Vec2d velicity) { this.velocity = velicity;}

    public void setPosition(Vec2d position) { this.position = position;}

    /**
     * Handles a 'tick'.
     *
     * A tick applies Gravity, Drag, Friction and finally calculates the next position according to the velocity.
     */
    public void tick() {
        applyGravity();
        double v = this.velocity.getMagnitude();
        if(v > 0) {
            //Drag and Friction are only applied with a non-zero velocity.
            applyDrag();
            applyFriction();
        }
        applyVelocity();
    }

    /**
     * Apply gravity to this prop.
     */
    public void applyGravity() {
        double gravity = environment.getGravity();
        this.applyForce(new Vec2d(0,gravity*mass));
    }

    /**
     * Apply drag to this prop.
     */
    public void applyDrag() {
        double dragForce = environment.getDrag() * Math.pow(this.velocity.getMagnitude(),2)/2;
        applyForce(this.velocity.scale(-dragForce));
    }

    /**
     * Apply friction to this prop.
     */
    public void applyFriction() {
        double frictionForce = environment.getFriction() * this.mass;
        applyForce(this.velocity.scale(-frictionForce));
    }

    /**
     * Calculate the next position for this prop by adding the velocity to the position.
     */
    public void applyVelocity() {
        this.position = this.position.plus(this.velocity);
    }

    /**
     * Apply a force upon this prop.
     * @param force The force in Newtons to apply.
     */
    public void applyForce(Vec2d force) {
        // a = F / m
        this.velocity = this.velocity.plus(force.scale(1.0/this.mass).scale(1.0/environment.TICKRATE)).round(4);
    }
}
