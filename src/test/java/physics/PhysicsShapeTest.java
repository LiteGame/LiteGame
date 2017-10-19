package physics;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.awt.geom.Line2D;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.assertj.core.api.Assertions.assertThat;

public class PhysicsShapeTest {
    @Test
    public void testGetShape() {
        Line2D shape = new Line2D.Double(0.0,0.0,1.0,1.0);
        PhysicsShape pshape = new PhysicsShape(shape);
        Assert.assertEquals(shape,pshape.getShape());
    }

    @Test
    public void testSetShape() {
        Line2D shape1 = new Line2D.Double(0.0,0.0,1.0,1.0);
        PhysicsShape pshape = new PhysicsShape(shape1);
        Line2D shape2 = new Line2D.Double(0.0,0.0,2.0,2.0);
        pshape.setShape(shape2);
        Assert.assertEquals(shape2,pshape.getShape());
    }
}
