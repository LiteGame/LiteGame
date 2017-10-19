package physics;

import java.awt.*;

/**
 * Created by JoeyH on 2017-10-05.
 */
public interface Collidable {
    void tick();

    Shape getShape();
    Integer getID();
}
