package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;


class SphereTest {
    @Test
    void constructor()
    {
        try {
            new Sphere(new Point3D(0, 1, 0), 2);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }
    }
        @Test
    void getNormal() {
        Sphere sp=new Sphere(new Point3D(0,0,0),1);
        Vector N=sp.getNormal(new Point3D(2,2,2));
        assertEquals(N,new Vector(0,1,0));
    }
}