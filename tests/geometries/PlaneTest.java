package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PlaneTest {

    @Test
    void constructor()
    {
        // ============ Equivalence Partitions Tests ==============
        try {
            new Plane(new Point3D(0,0,-1),new Point3D(2,0,3),new Point3D(0,-1,2));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }

        // =============== Boundary Values Tests ==================
        // test 2 points equal
        try {
            new Plane(new Point3D(2,0,3),new Point3D(2,0,3),new Point3D(0,-1,2));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct plane");
        }
    }

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Plane p=new Plane(new Point3D(4,2,-1),new Point3D(1,3,1),new Point3D(-3,0,3));
        Vector N=p.getNormal(new Point3D(8,-2,13));
        assertEquals(N,new Vector(0.5196558419693047,-0.12991396049232617,0.8444407432001202));
    }


    @Test
    void findIntersections() {
        //test 1 point of intersection
        Plane p=new Plane(new Point3D(0,2,0),new Point3D(0,0,2),new Point3D(0,-2,0));
        List<Point3D> result1 =p.findIntersections(new Ray(new Point3D(-1.5, 0, 0),
                new Vector(0, 1, 0.5)));
        assertEquals(1, result1.size(),"Wrong number of points");

        //test 0 point of intersection
        assertNull(p.findIntersections(new Ray(new Point3D(-2,1,0),
                        new Vector(-3,0,0))),
                "Ray's line out of triangle");


        //test 1 point of intersection and p0 of ray in on the limit of the triangle
        List<Point3D> result2 =p.findIntersections(new Ray(new Point3D(0, 0, 0),
                new Vector(-3,0,0)));
        assertEquals(1, result1.size(),"Wrong number of points");


    }

}