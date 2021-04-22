package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane implements Geometry{
    final Point3D _q0;
    final Vector _normal ;

    //return the point _q0 of the plane
    public Point3D get_q0() {
        return _q0;
    }

    /**
     * getter of the normal vector field
     * @deprecated use {@link Plane#getNormal(Point3D)} with null as parameter.
     * @return
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
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
        if((p0.equals(p1)||(p1.equals(p2))||(p0.equals(p2))))
            throw new IllegalArgumentException();
        _q0 = p0;
        Vector U=p1.subtract(p0);
        Vector V=p2.subtract(p0);
        Vector N= U.crossProduct(V);
        N.normalize();
        _normal = N;
    }

    /**
     * implementation of getNormal from Geometry interface
     * @param point reference point
     * @return normal to the plane
     */

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

    @Override
    public List<Point3D> findIntersections(Ray ray) {
//        Point3D p0=ray.get_p0();
//        Vector v=ray.get_dir();
//        if(isZero(_normal.dotProduct(v))){
//            return null;
//        }
//        double t=(_normal.dotProduct(_q0.subtract(p0)))/(_normal.dotProduct(v));
//        if((p0.equals(_q0)) && !(isZero(_normal.dotProduct(v)))){
////            Point3D p= _q0;
////
////            return List.of(p);
//            return null;
//        }
//
//        if(t>0){
//            Point3D p= ray.getPoint(t);
//
//            return List.of(p);
//        }
//        return null;
        Point3D P0 = ray.get_p0();
        Vector v = ray.get_dir();

        Vector n = _normal;

        if(_q0.equals(P0)){
            return  null;
        }

        Vector P0_Q0 = _q0.subtract(P0);

        //numerator
        double nP0Q0  = alignZero(n.dotProduct(P0_Q0));

        //
        if (isZero(nP0Q0 )){
            return null;
        }

        //denominator
        double nv = alignZero(n.dotProduct(v));

        // ray is lying in the plane axis
        if(isZero(nv)){
            return null;
        }

        double  t = alignZero(nP0Q0  / nv);

        if (t <=0){
            return  null;
        }

        Point3D point = ray.getPoint(t);

        return List.of(point);
    }
}
