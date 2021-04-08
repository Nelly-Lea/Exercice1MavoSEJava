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
            new Tube(4, ray);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

        // =============== Boundary Values Tests ==================
        // Test radius ZERO
        try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,1,0));
            new Tube(0, ray);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

        // Test Vector ZERO
        try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,0,0));
            new Tube(3, ray);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

    }

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============

        Ray ray=new Ray(new Point3D(0,1,0),new Vector(0,1,0));
        Tube tb=new Tube(2, ray);
        assertEquals(tb.getNormal(new Point3D(0,0,0)),new Vector(0,-1,0));
    }
}