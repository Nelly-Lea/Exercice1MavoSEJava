package geometries;

import primitives.Point3D;
import primitives.Vector;
public class Sphere {
    final Point3D _center;
    final double _radius;

    public Point3D get_center() {
        return _center;
    }

    public double get_radius() {
        return _radius;
    }
//constructor that receives the center point and the radius
    public Sphere(Point3D center, double radius) {
        _center = center;
        if(radius<=0)
            throw new IllegalArgumentException("radius can't be smaller than 0");
        _radius = radius;
    }
    public Vector getNormal(Point3D p0){
        Vector N=p0.subtract(_center);
        N.normalize();
        return N;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}
