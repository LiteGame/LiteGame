package src.main.java.physics;

/**
 * Created by JoeyH on 2017-09-11.
 */
public class Vec2d {
    public double x = 0;
    public double y = 0;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getPhase() {
        return Math.atan2(this.y,this.x);
    }

    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }

    public Vec2d plus(Vec2d b) {
        Vec2d a = this;
        return new Vec2d(a.x+b.x,a.y+b.y);
    }

    public Vec2d minus(Vec2d b) {
        Vec2d a = this;
        return new Vec2d(a.x-b.x,a.y-b.y);
    }

    public double dot(Vec2d b) {
        Vec2d a = this;
        return a.x*b.x + a.y*b.y;
    }

    public Vec2d scale(double factor) {
        return new Vec2d(this.x*factor, this.y*factor);
    }

    public String toString() {
        return "(" + x + "," + ")";
    }
}
