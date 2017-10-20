package physics;

import java.awt.*;

/**
 * General interface for anything that needs to collide.
 */
public interface Collidable {
    void tick();

    Shape getShape();
}
