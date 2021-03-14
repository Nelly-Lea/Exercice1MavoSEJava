package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube {
   /* protected */final Ray _axisRay;
   /* protected */final double _radius;

    public Ray getAxisRay() {
        return _axisRay;
    }

    public double getRadius() {
        return _radius;
    }

    //constructor that receives Ray and radius
    public Tube(Ray axisRay, double radius) {
        _axisRay = axisRay;
        _radius = radius;
    }

    public Vector getNormal(Point3D p0){

        Vector P_P0 = p0.subtract(_axisRay.get_p0());
        double t=_axisRay.get_dir().dotProduct(P_P0);
        Point3D O=_axisRay.get_p0().add(P_P0.scale(t));
        Vector N=p0.subtract(O);
        N.normalize();
        return N;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
