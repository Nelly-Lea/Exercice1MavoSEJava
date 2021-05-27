package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


class SphereTest {
    @Test
    void constructor()
    {
        // ============ Equivalence Partitions Tests ==============
        try {
            new Sphere(2, new Point3D(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }

        // =============== Boundary Values Tests ==================

        // Test radius ZERO
        try {
            new Sphere(7, new Point3D(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }

        // Test negative radius
        try {
            new Sphere(-4, new Point3D(0, 1, 0));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct sphere");
        }
    }
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============;
        Sphere sp=new Sphere(4, new Point3D(0,0,0));
        Vector N=sp.getNormal(new Point3D(2,2,2));
        Vector V=new Vector(0.5773502691896258,0.5773502691896258,0.5773502691896258);
        assertEquals(N,V);
    }

    @Test
    void testFindIntersections(){
        Sphere sphere = new Sphere(1d, new Point3D(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============

        // TC01: Ray's line is outside the sphere (0 points)
        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                        new Vector(1, 1, 0))),
                "Ray's line out of sphere");

        // TC02: Ray starts before and crosses the sphere (2 points)
        Point3D p1 = new Point3D(0.0651530771650466, 0.355051025721682, 0);
        Point3D p2 = new Point3D(1.53484692283495, 0.844948974278318, 0);
        List<Point3D> result = sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                new Vector(3, 1, 0)));
        assertEquals(2, result.size(),"Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        assertEquals( List.of(p1, p2), result,"Ray crosses sphere");

        // TC03: Ray starts inside the sphere (1 point)
        List<Point3D> result1 = sphere.findIntersections(new Ray(new Point3D(1.5, 0, 0),
                new Vector(0, 1, 0)));
        assertEquals(1, result1.size(),"Wrong number of points");

        // TC04: Ray starts after the sphere (0 points)

        assertNull(sphere.findIntersections(new Ray(new Point3D(-1, 0, 0),
                        new Vector(0, 1, 0))),
                "Ray's line out of sphere");

        // =============== Boundary Values Tests ==================

        // **** Group: Ray's line crosses the sphere (but not the center)
        // TC11: Ray starts at sphere and goes inside (1 points)
        // TC12: Ray starts at sphere and goes outside (0 points)

        // **** Group: Ray's line goes through the center
        // TC13: Ray starts before the sphere (2 points)
        // TC14: Ray starts at sphere and goes inside (1 points)
        // TC15: Ray starts inside (1 points)
        // TC16: Ray starts at the center (1 points)
        // TC17: Ray starts at sphere and goes outside (0 points)
        // TC18: Ray starts after sphere (0 points)

        // **** Group: Ray's line is tangent to the sphere (all tests 0 points)
        // TC19: Ray starts before the tangent point
        // TC20: Ray starts at the tangent point
        // TC21: Ray starts after the tangent point

        // **** Group: Special cases
        // TC19: Ray's line is outside, ray is orthogonal to ray start to sphere's center line


    }
}