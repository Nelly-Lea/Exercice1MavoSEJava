package elements;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class IntegrationTest {
    Camera camera = new Camera(Point3D.ZERO, new Vector(0, 0, -1), new Vector(0, -1, 0)).setDistance(1);
    Camera camera2 = new Camera(new Point3D(0, 0, 0.5), new Vector(0, 0, -1), new Vector(0, -1, 0)).setDistance(1);

//    public int countIntersection(Intersectable inter) {
//        camera.setViewPlaneSize(3, 3);
//        int intersection = 0;
//        for (int i = 0; i < 3; i++)
//            for (int j = 0; j < 3; j++)
//                intersection = inter.findIntersections(camera.constructRayThroughPixel());
//
//    }


    @Test
    private int CountIntersections(Camera cam, Intersectable geo) {
        int count = 0;

        List<Point3D> allpoints = null;

        cam.setViewPlaneSize(3, 3);
        cam.setDistance(1);
        int nX = 3;
        int nY = 3;
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
    public void SphereIntegrationTest() {

        //sphere first test case Two intersection points
        Sphere s1 = new Sphere(1, new Point3D(0, 0, -3));
        Intersectable inter = s1;
        assertEquals(2, CountIntersections(camera, inter), "error");

        //sphere test case 18 intersection points
        Sphere s2 = new Sphere(2.5, new Point3D(0, 0, -2.5));
        Intersectable inter2 = s2;
        assertEquals(18, CountIntersections(camera2, inter2), "error");

        //sphere test case 10 intersection points
        Sphere s3 = new Sphere(2, new Point3D(0, 0, -2));
        Intersectable inter3 = s3;
        assertEquals(10, CountIntersections(camera2, inter3), "error");

        //sphere test case 9 intersection points
        Sphere s4 = new Sphere(4, new Point3D(0, 0, -1));
        Intersectable inter4 = s4;
        assertEquals(9, CountIntersections(camera2, inter4), "error");

        //sphere test case 0 intersection points
        Sphere s5 = new Sphere(0.5, new Point3D(0, 0, 1));
        Intersectable inter5 = s5;
        assertEquals(0, CountIntersections(camera2, inter5), "error");

    }

    @Test
    public void PlaneIntegrationTest() {
        //plane test case 9 intersections
        Plane pl1 = new Plane(new Point3D(0, 0, -5), new Vector(0, 0, 1));
        Intersectable inter1 = pl1;
        assertEquals(9, CountIntersections(camera, inter1), "error");

        //plane test case 9 intersections
        Plane pl2 = new Plane(new Point3D(0, 0, -5), new Vector(-2, -1.5, 4));
        Intersectable inter2 = pl2;
        assertEquals(9, CountIntersections(camera, inter2), "error");

        //plane test case 6 intersections
        Plane pl3 = new Plane(new Point3D(0, 0, -5), new Vector(-2, -1.5, 1.5));
        Intersectable inter3 = pl3;
        assertEquals(6, CountIntersections(camera, inter3), "error");

    }

    @Test
    public void TriangleIntegrationTest() {
        //triangle test case 1 intersection
        Triangle tr1=new Triangle(new Point3D(0,1,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        Intersectable inter1=tr1;
        assertEquals(1,CountIntersections(camera,inter1),"error");

        //triangle test case 2 intersection
        Triangle tr2=new Triangle(new Point3D(0,20,-2),new Point3D(1,-1,-2),new Point3D(-1,-1,-2));
        Intersectable inter2=tr2;
        assertEquals(2,CountIntersections(camera,inter2),"error");

    }

    }
