package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TriangleTest {


    @Test
    void findIntersections() {
        //test 1 point of intersection
        Triangle t=new Triangle(new Point3D(0,2,0),new Point3D(0,0,2),new Point3D(0,-2,0));
        List<Point3D> result1 =t.findIntersections(new Ray(new Point3D(-1.5, 0, 0),
                new Vector(0, 1, 0.5)));
        assertEquals(1, result1.size(),"Wrong number of points");

        //test 0 point of intersection
        assertNull(t.findIntersections(new Ray(new Point3D(-2,1,0),
                        new Vector(-3,0,0))),
                "Ray's line out of triangle");


        //test 1 point of intersection and p0 of ray in on the limit of the triangle
        List<Point3D> result2 =t.findIntersections(new Ray(new Point3D(0, 0, 0),
                new Vector(-3,0,0)));
        assertEquals(1, result1.size(),"Wrong number of points");


    }
}