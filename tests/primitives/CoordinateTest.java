package primitives;

import geometries.Sphere;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CoordinateTest {

    @Test
    void constructor()
    {
        try {
            new Coordinate(2);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct coordinate");
        }
    }

    @Test
    void testEquals() {
        Coordinate c1=new Coordinate(3);
        Coordinate c2=new Coordinate(3);
        assertEquals(c1,c2,"Not equal");

    }
}