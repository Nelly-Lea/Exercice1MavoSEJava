package primitives;

import org.junit.jupiter.api.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class RayTest {
    @Test
    void testFindClosestPoint(){
        Ray ray=new Ray(new Point3D(0,0,5),new Vector(10,10,15));
        List<Point3D> point3DList=null;
        assertNull(ray.findClosestPoint(point3DList),"error");
        point3DList=new LinkedList<>();
        point3DList.add(new Point3D(2,2,8));
        point3DList.add(new Point3D(5,5,12.5));
        point3DList.add(new Point3D(20,20,30));
        assertEquals(new Point3D(2,2,8),ray.findClosestPoint(point3DList),"error");
        List<Point3D> point3DList1=new LinkedList<>();
        point3DList.add(new Point3D(5,5,12.5));
        point3DList.add(new Point3D(20,20,30));
        point3DList.add(new Point3D(2,2,8));
        assertEquals(new Point3D(2,2,8),ray.findClosestPoint(point3DList),"error");
    }

}