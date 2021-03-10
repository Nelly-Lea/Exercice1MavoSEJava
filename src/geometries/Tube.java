package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube {
    protected Ray _axisRay;
    protected double _radius;

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
        return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
