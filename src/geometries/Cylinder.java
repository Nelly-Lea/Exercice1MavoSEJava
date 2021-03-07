package geometries;

import primitives.Point3D;
import primitives.Vector;

public class Cylinder extends Tube {
    final double _height;

    public Cylinder(double height) {
        _height = height;
    }
    public Vector getNormal(Point3D p0)
    {
        return null;
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "height=" + _height +
                ", axixRay=" + axixRay +
                ", radius=" + radius +
                '}';
    }
}
