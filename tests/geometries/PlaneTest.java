package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    void constructor()
    {
        try {
            new Plane(new Point3D(0,0,-1),new Point3D(2,0,3),new Point3D(0,-1,2));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }

    @Test
    void getNormal() {
        Plane p=new Plane(new Point3D(0,0,-1),new Point3D(2,0,3),new Point3D(0,-1,2));
        Vector N=p.getNormal(new Point3D(0,0,-1));
        assertEquals(N,new Vector(2,-3,-1));
    }
}