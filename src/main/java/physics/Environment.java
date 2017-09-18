package physics;

/**
 * Represents a physics environment.
 */
public class Environment {
    /**
     * The amount of ticks per second.
     */
    public final int TICKRATE = 100;
    /**
     * The maximum speed possible in this environment.
     */
    public final int SPEEDOFLIGHT = 100;

    /**
     * The Gravity value of this environment
     */
    private double gravity = 9.81;
    /**
     * The friction coefficient in this environment.
     */
    private double friction = 1;

    /**
     * The drag coefficient in this environment.
     */
    private double drag = 1;

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getDrag() {
        return drag;
    }

    public void setDrag(double drag) {
        this.drag = drag;
    }

}
