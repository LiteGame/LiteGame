package physics;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

public class Vec2dTest {
    @Test
    public void testEqualsFalseX() {
        Vec2d v1 = new Vec2d(1.0,1.0);
        Vec2d v2 = new Vec2d(2.0,1.0);
        Assertions.assertNotEquals(v1,v2);
    }

    @Test
    public void testEqualsFalseY() {
        Vec2d v1 = new Vec2d(1.0,1.0);
        Vec2d v2 = new Vec2d(1.0,2.0);
        Assertions.assertNotEquals(v1,v2);
    }

    @Test
    public void testEqualsFalse() {
        Vec2d v1 = new Vec2d(1.0,1.0);
        Vec2d v2 = new Vec2d(1.0,1.0);
        Assertions.assertEquals(v1,v2);
    }

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
        Vec2d ans = v1.plus(v2);
        Vec2d res = new Vec2d(1.0,1.0);
        Assertions.assertEquals(res,ans);
    }

    @Test
    public void testScale() {
        Vec2d v1 = new Vec2d(1.0,1.0);
        Vec2d ans = v1.scale(0.5);
        Vec2d res = new Vec2d(0.5,0.5);
        Assertions.assertEquals(res,ans);
    }

    @Test
    public void testRotate() {
        Vec2d v1 = new Vec2d(1.0,0.0);
        Vec2d ans = v1.rotate((Math.PI/2));
        Vec2d res = new Vec2d(0,1.0);
        Assertions.assertEquals(res,ans);
    }
}
