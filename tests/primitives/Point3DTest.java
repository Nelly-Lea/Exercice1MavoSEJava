package primitives;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class Point3DTest {

    Point3D p1=new Point3D(0.0d, 0.5d, 1.0d);
    Point3D p2=new Point3D(0.000000000001d, 0.499999999999d,1.d);
    @Test
    void testEquals() {
        assertEquals(p1,p2);
    }

    @Test
    void testToString() {
        System.out.println("the first point is: "+p1);
        System.out.println("the second point is: "+p2);

    }

    @Test
    void testSubstract() {
        //Test substract with points and vectors
        Point3D p1=new Point3D(1,1,1);
        Vector result=new Point3D(2,2,2).subtract(p1);
        assertEquals(new Vector(1,1,1),result,"ERROR in substract function");
    }
    @Test
    void distance() {
        Point3D p3= new Point3D(0,0,2.4);
        double result=p3.distance(p2);
        assertEquals(1.5,p3.distance(p2),0.1);
    }
    @Test
    void squaredDistance(){

        assertEquals(0, p1.distanceSquared(p2));
    }
}