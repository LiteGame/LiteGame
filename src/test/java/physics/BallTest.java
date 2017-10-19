package physics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.geom.Ellipse2D;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;
public class BallTest {
    @Test
    public void testGetShape() {
        Ball ball = new Ball(null, new Vec2d(0.0,0.0), 10.0, 5.0);
        Ellipse2D ballShape = new Ellipse2D.Double(-5.0,-5.0,10.0,10.0);
        Assert.assertEquals(ballShape,ball.getShape());
    }
}
