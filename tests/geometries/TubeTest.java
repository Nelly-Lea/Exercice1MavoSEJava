package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;

class TubeTest {

    @Test
    void constructor()
    {
        // ============ Equivalence Partitions Tests ==============
        try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,1,0));
            new Tube(ray,4);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

        // =============== Boundary Values Tests ==================
        // Test radius ZERO
        try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,1,0));
            new Tube(ray,0);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

        // Test Vector ZERO
        try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,0,0));
            new Tube(ray,3);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

    }

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        Ray ray=new Ray(new Point3D(0,1,0),new Vector(0,1,0));
        Tube tb=new Tube(ray,2);
        assertEquals(tb.getNormal(new Point3D(0,0,0)),new Vector(0,-1,0));
    }
}