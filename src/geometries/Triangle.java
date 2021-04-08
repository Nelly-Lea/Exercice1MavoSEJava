package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/*
 * The class is based on Point3D and an extends of Polygon
 */

public class Triangle extends Polygon{
    //constructor that receives 3 points
    public Triangle(Point3D p0,Point3D p1,Point3D p2) {

        super(p0,p1,p2);
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    @Override
    public List<Point3D> findIntersections(Ray ray) {
//        Point3D p0=ray.get_p0();
//        Vector v=ray.get_dir();
//        List<Point3D> point3DList=new LinkedList<>();
//        double t=(plane._normal.dotProduct(plane._q0.subtract(p0)))/(plane._normal.dotProduct(v));
//        if((p0.equals(plane._q0)) && !(isZero(plane._normal.dotProduct(v)))){
//            Point3D p= plane._q0;
//
//           point3DList.add(p);
//
//        }
//
//        if(t>0){
//            Point3D p= p0.add(v.scale(t));
//
//            point3DList.add(p);
//        }
//
//        if(point3DList.isEmpty()) {
//            return null;
//        }
//
//        List<Point3D> l=vertices;
//        Vector v1=l.get(0).subtract(p0);
//        Vector v2=l.get(1).subtract(p0);
//        Vector v3=l.get(2).subtract(p0);
//
//        Vector n1=v1.crossProduct(v2).normalize();
//        Vector n2=v2.crossProduct(v3).normalize();
//        Vector n3=v3.crossProduct(v1).normalize();
//
//        if((v.dotProduct(n1)>0 && v.dotProduct(n2)>0 && v.dotProduct(n2)>0)||((v.dotProduct(n1)<0 && v.dotProduct(n2)<0 && v.dotProduct(n2)<0))){
//            return point3DList;
//        }
//        else {
//            if (isZero(alignZero(v.dotProduct(n1))) || (isZero(alignZero(v.dotProduct(n2))) || (isZero(alignZero(v.dotProduct(n3)))))) {
//                return null;
//            }
//        }
//
//        return null;
        return findIntersections(ray);

    }
}
