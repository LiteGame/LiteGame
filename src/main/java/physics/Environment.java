package physics;

/**
 * Created by JoeyH on 2017-09-11.
 */
public class Environment {
    public final int TICKRATE = 100;

    private double gravity = 9.81;
    private double friction = 1;
    private double drag = 1;

    public double getFriction() {
        return friction;
    }

    public void setFriction(double friction) {
        this.friction = friction;
    }

    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    public double getDrag() {
        return drag;
    }

    public void setDrag(double drag) {
        this.drag = drag;
    }

    public Environment() {

    }

}