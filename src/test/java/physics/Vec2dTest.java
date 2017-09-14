package physics;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

public class Vec2dTest {
    @Test
    public void testGetPhase() {
        Vec2d v = new Vec2d(0.0, 1.0);
        Assertions.assertEquals(Math.PI/2.0, v.getPhase());
    }

    @Test
    public void testGetMagnitude() {
        Vec2d v = new Vec2d(3.0,4.0);
        Assertions.assertEquals(5.0,v.getMagnitude());
    }

    @Test
    public void testPlus() {
        Vec2d v1 = new Vec2d(1.0,0.0);
        Vec2d v2 = new Vec2d(0.0,1.0);
        Vec2d res = new Vec2d(1.0,1.0);
        Assertions.assertEquals(res,v1.plus(v2));
    }
}
