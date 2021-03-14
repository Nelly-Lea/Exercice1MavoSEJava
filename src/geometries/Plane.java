package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Plane {
    final Point3D _q0;
    final Vector _normal ;

    //return the point _q0 of the plane
    public Point3D get_q0() {
        return _q0;
    }

    //return the normal of the plane
//    public Vector get_normal() {
//        return _normal;
//    }

    // constructor that receives point that belongs to the plane and normal of the plane
    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal;
     }

    //constructor that receives 3 points and do a plane from these 3 points
    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        _q0 = p0;
        Vector U=p1.subtract(p0);
        Vector V=p2.subtract(p0);
        Vector N= U.crossProduct(V);
        N.normalize();
        _normal = N;
    }

    public Vector getNormal(Point3D point) {

        return _normal;
    }

    @Override
    public String toString() {
        return "Plane{" +
                "q0=" + _q0 +
                ", normal=" + _normal +
                '}';
    }
}
