package physics;

import items.BallSprite;

/**
 * Factory for creating Balls
 */
public class BallFactory {
    private Environment e;
    private Vec2d startingPos;
    private int id = 3999;

    private String imagePath = "resources/dot.png";
    private double defaultMass = 10.0;
    private double defaultRadius = 5.0;

    public BallFactory(Environment e, Vec2d startingPos) {
        this.e = e;
        this.startingPos = startingPos;
    }

    public BallFactory(Environment e, Vec2d startingPos, String imagePath) {
        this.e = e;
        this.startingPos = startingPos;
        this.imagePath = imagePath;
    }

    public Ball create()
    {
        return create(this.startingPos, defaultMass, defaultRadius, this.imagePath);
    }

    public Ball create(Vec2d position, double mass, double radius)
    {
        return create(position, mass, radius, this.imagePath);
    }

    public Ball create(Vec2d position, double mass, double radius, String imagePath)
    {
        id += 1;
        Ball b = new Ball(this.e, position, mass, radius, id);
        BallSprite sprite = new BallSprite(b);
        sprite.loadImage(imagePath);
        b.setSprite(sprite);
        this.e.spawnObject(b);
        return b;
    }
}
