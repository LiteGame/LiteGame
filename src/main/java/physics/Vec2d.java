package physics;

/**
 * Represents a 2-Dimensional vector
 */
public class Vec2d {
    /**
     * The x component
     */
    public double x = 0;
    /**
     * The y component
     */
    public double y = 0;

    public Vec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Get the phase of the vector, in radians.
     * @return The phase, bound between -Pi and Pi.
     */
    public double getPhase() {
        return Math.atan2(this.y,this.x);
    }

    /**
     * Get the magnitude of the vector.
     * @return The magnitude per the pythagorean theorem.
     */
    public double getMagnitude() {
        return Math.sqrt(x*x + y*y);
    }

    /**
     * Returns the sum of this vector plus another vector.
     * @param b The vector to add.
     * @return This vector + b.
     */
    public Vec2d plus(Vec2d b) {
        Vec2d a = this;
        return new Vec2d(a.x+b.x,a.y+b.y);
    }

    /**
     * Returns the result of this vector minus another vector.
     * @param b The vector to subtract.
     * @return This vector - b.
     */
    public Vec2d minus(Vec2d b) {
        Vec2d a = this;
        return new Vec2d(a.x-b.x,a.y-b.y);
    }

    /**
     * Returns the result of the dot product of this vector with another vector.
     * @param b The vector to calculate the dot product with.
     * @return This vector dot b.
     */
    public double dot(Vec2d b) {
        Vec2d a = this;
        return a.x*b.x + a.y*b.y;
    }

    /**
     * Returns a vector with rounded components.
     * @param decimals The precision to which to round.
     * @return The rounded vector.
     */
    public Vec2d round(int decimals) {
        double shift = Math.pow(10.0,decimals);
        double rx = Math.round(this.x * shift) / shift;
        double ry = Math.round(this.y * shift) / shift;
        return new Vec2d(rx,ry);
    }

    /**
     * Returns a vector scaled with a factor.
     * @param factor The factor to scale this vector with.
     * @return The scaled vector.
     */
    public Vec2d scale(double factor) {
        return new Vec2d(this.x*factor, this.y*factor);
    }

    /**
     * Rotates a vector around the point of origin.
     * @param angle The angle to rotate with.
     * @return The rotated vector.
     */
    public Vec2d rotate(double angle) {
        double newX = (this.x * Math.cos(angle) - this.y * Math.sin(angle));
        double newY = (this.x * Math.sin(angle) - this.y * Math.cos(angle));
        return new Vec2d(newX,newY);
    }

    /**
     * Returns a {@link String} representation of this vector.
     * @return The representation.
     */
    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }

    @Override
    public int hashCode() {
        int a = (int)Math.round(x*10.0);
        int b = (int)Math.round(y*10.0);
        return a * 3 + b * 5;
    }

    /**
     * Checks for equality between this vector and another.
     * @param other The vector to check equality with.
     * @return True iff the other object is a vector and the x and y of the other object are equal to this vector.
     */
    @Override
    public boolean equals(Object other) {
        if(other instanceof Vec2d) {
           Vec2d that = (Vec2d)other;
           return (this.x==that.x && this.y == that.y);
        }
        return false;
    }
}
