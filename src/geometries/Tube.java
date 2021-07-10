package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

public class Tube extends RadialGeometry {
    /* protected */final Ray _axisRay;

    public Ray getAxisRay() {
        return _axisRay;
    }


    /**
     * constructor that receives Ray and radius
     * @param radius
     * @param axisRay
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        _axisRay = axisRay;


    }

    /**
     * this function returns the normal to the Tube according to the received point
     * @param p0
     * @return
     */
    public Vector getNormal(Point3D p0) {

        Vector P_P0 = p0.subtract(_axisRay.get_p0());//vector between p0 and p0 of the axis ray
        double t = _axisRay.get_dir().dotProduct(P_P0);// t is the dotproduct between the axis and p_p0
        Point3D O = _axisRay.get_p0().add(P_P0.scale(t));
        Vector N = p0.subtract(O);
        N.normalize();
        return N;//return the normal
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {///
        return null;
    }


}
