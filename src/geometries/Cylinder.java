package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

/*
* The class is based on Point3D,Ray and Vector and an extends of Tube
 */
public class Cylinder extends Tube {
    final double _height;
// constructor
    public Cylinder(double height, Ray ray,double radius) {
        super(ray,radius);
        _height = height;
    }
    //return the normal of the cylinder at point p0
    public Vector getNormal(Point3D p0)
    {
        return null;
    }

    // return the height of the cylinder
    public double get_height() {
        return _height;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axisRay=" + _axisRay +
                ", radius=" + _radius +
                '}';
    }
}
