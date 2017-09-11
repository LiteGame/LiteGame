package src.main.java.physics;

/**
 * Created by JoeyH on 2017-09-11.
 */
public class Environment {
    public double getGravity() {
        return gravity;
    }

    public void setGravity(double gravity) {
        this.gravity = gravity;
    }

    private double gravity = 9.81;

}
