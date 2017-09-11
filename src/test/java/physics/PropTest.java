package physics;
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
/**
 * Created by JoeyJo0 on 2017-09-11.
 */
@RunWith(MockitoJUnitRunner.class)
public class PropTest {

    private final double GRAVITY = 5;
    private final double FRICTION = 1;
    private final double DRAG = 1;
    private int tickRate = 0;

    @Mock
    private Environment environment;

    @Before
    public void setup() {
        when(environment.getDrag()).thenReturn(DRAG);
        when(environment.getGravity()).thenReturn(GRAVITY);
        when(environment.getFriction()).thenReturn(FRICTION);
        this.tickRate = environment.TICKRATE;
    }

    @Test
    public void testApplyUnresistedForce() {
        when(environment.getDrag()).thenReturn(0.0);
        when(environment.getGravity()).thenReturn(0.0);
        when(environment.getFriction()).thenReturn(0.0);
        Vec2d initialPos = new Vec2d(100,100);
        Prop p = new Prop(environment,initialPos,1);
        //1 kg prop gets pushed downward with 1 newton for 1 second.
        for(int i = 0; i<tickRate; i++) {
            p.applyForce(new Vec2d(0, 1));
            p.tick();
        }
        Vec2d v = p.getVelocity();
        Assertions.assertEquals(1.0,v.y);
    }

    @Test
    public void testApplyUnresistedGravity() {
        when(environment.getDrag()).thenReturn(0.0);
        when(environment.getGravity()).thenReturn(GRAVITY);
        when(environment.getFriction()).thenReturn(0.0);
        Vec2d initialPos = new Vec2d(100,100);
        Prop p = new Prop(environment,initialPos,1);
        //1 kg prop gets pushed downward with 1 newton for 1 second.
        for(int i = 0; i<tickRate; i++) {
            p.tick();
        }
        Vec2d v = p.getVelocity();
        Assertions.assertEquals(5.0,v.y);
    }

    @Test
    public void testTicks() {
        Vec2d initialPos = new Vec2d(100,100);
        Prop p = new Prop(environment,initialPos,0.001);
        for(int i = 0; i < tickRate; i++) {
            p.tick();
        }
        verify(environment,times(tickRate)).getGravity();
        verify(environment,times(tickRate)).getDrag();
        verify(environment,times(tickRate)).getFriction();
    }

    @Test
    public void testCounterGravityForce() {
        Vec2d initialPos = new Vec2d(100,100);
        Prop p = new Prop(environment,initialPos,0.001);

        Vec2d counterGravityForce = new Vec2d(0,-0.001 * GRAVITY);
        for(int i = 0; i < tickRate; i++) {
            p.applyForce(counterGravityForce);
            p.tick();
        }
        assertThat(p.getPosition().equals(initialPos)).isTrue();
    }
}
