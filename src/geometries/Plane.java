package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Plane extends Geometry {
    final Point3D _q0;
    final Vector _normal;

    //return the point _q0 of the plane
    public Point3D get_q0() {
        return _q0;
    }

    /**
     * getter of the normal vector field
     *
     * @return
     * @deprecated use {@link Plane#getNormal(Point3D)} with null as parameter.
     */
    @Deprecated
    public Vector getNormal() {
        return _normal;
    }

    /**
     * constructor that receives point that belongs to the plane and normal of the plane
     * @param q0
     * @param normal
     */
    public Plane(Point3D q0, Vector normal) {
        _q0 = q0;
        _normal = normal.normalized();
    }

    /**
     * constructor that receives 3 points and do a plane from these 3 points
     * @param p0
     * @param p1
     * @param p2
     */
    public Plane(Point3D p0, Point3D p1, Point3D p2) {
        if ((p0.equals(p1) || (p1.equals(p2)) || (p0.equals(p2))))
            throw new IllegalArgumentException();
        _q0 = p0;
        Vector U = p1.subtract(p0);
        Vector V = p2.subtract(p0);
        Vector N = U.crossProduct(V);
        N.normalize();
        _normal = N;
    }

    /**
     * implementation of getNormal from Geometry interface
     *
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


    /**
     * this function receives a ray and a double and returns a list of GeoPoint with the received ray
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
   public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        Point3D p0 = ray.get_p0();// p0 of the received ray
        Vector v = ray.get_dir();// director vector of the received ray
        if (isZero(_normal.dotProduct(v))) { //if the director vector ray is on the plane
            return null;
        }
        if ((p0.equals(_q0)) && !(isZero(_normal.dotProduct(v)))) {//if the received ray is on the plane
            return null;
        }
        double t = (_normal.dotProduct(_q0.subtract(p0))) / (_normal.dotProduct(v));//t is the distance between the received ray and the plane


        if (t > 0&&(alignZero(t-maxDistance)<=0))
        {
            Point3D p = ray.getPoint(t);// p is the intersection point on the plane

            return List.of(new GeoPoint(this, p));
        }
        return null;
    }
}