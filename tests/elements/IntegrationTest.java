package elements;

import geometries.Intersectable;
import geometries.Sphere;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -3), new Vector(0, -1, 0)).setDistance(1);

//    public int countIntersection(Intersectable inter){
//        camera.setViewPlaneSize(3, 3);
//        int intersection=0;
//        for(int i=0;i<3;i++)
//            for(int j=0;j<3;j++)
//                intersection=inter.findIntersections(camera.constructRayThroughPixel());
//
//
//    }
@Test
private int CountIntersections(Camera cam, Intersectable geo) {
    int count = 0;

    List<Point3D> allpoints = null;

    cam.setViewPlaneSize(3, 3);
    cam.setDistance(1);
    int nX =3;
    int nY =3;
    //view plane 3X3 (WxH 3X3 & nx,ny =3 => Rx,Ry =1)
    for (int i = 0; i < nY; ++i) {
        for (int j = 0; j < nX; ++j) {
            var intersections = geo.findIntersections(cam.constructRayThroughPixel(nX, nY, j, i));
            if (intersections != null) {
                if (allpoints == null) {
                    allpoints = new LinkedList<>();
                }
                allpoints.addAll(intersections);
            }
            count += intersections == null ? 0 : intersections.size();
        }
    }
    return count;
//    System.out.format("there is %d points:%n", count);
//    if (allpoints != null) {
//        for (var item : allpoints) {
//            System.out.println(item);
//        }
//    }
//    System.out.println();

  //  assertEquals(expected, count, "Wrong amount of intersections");
}

    @Test
    public void SphereIntegrationTest(){

        //sphere first test case Two intersection points
        Sphere s1=new Sphere(1,new Point3D(0,0,-3));
        Intersectable inter=s1;
        assertEquals(2,CountIntersections(camera,inter),"error");
    }



}
