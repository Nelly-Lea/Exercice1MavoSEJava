package elements;

import primitives.Point3D;
import primitives.Vector;

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

//    public void SphereIntegrationTest(){
//        //sphere first test case Two intersection points
//        Sphere s1=new Sphere(1,new Point3D(0,0,-3));
//        assertEquals(2,)
//    }



}
