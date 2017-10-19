package physics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(MockitoJUnitRunner.class)
public class EnvironmentTest {
    @Mock
    Ball ball;

    @Mock
    PhysicsShape pshape;

    Environment environment = new Environment();

    @Before
    public void setup() {
        environment.spawnDynamicObject(ball);
        environment.spawnStaticObject(ball);
    }

    @Test
    public void testTick() {
        environment.tick();
        verify(ball,times(1)).tick();
        verify(pshape,times(0)).tick();
    }

    @Test
    public void testSetGravity() {
        double newGravity = 5.0;
        environment.setGravity(newGravity);
        assertThat(newGravity).isEqualTo(environment.getGravity());
    }

    @Test
    public void testSetFriction() {
        double newFriction = 5.0;
        environment.setFriction(newFriction);
        assertThat(newFriction).isEqualTo(environment.getFriction());
    }

    @Test
    public void testSetDrag() {
        double newDrag = 5.0;
        environment.setDrag(newDrag);
        assertThat(newDrag).isEqualTo(environment.getDrag());
    }
}
