package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

public class Tube {
    protected Ray axixRay;
    protected double radius;


    public Vector getNormal(Point3D p0){
return null;
    }

    @Override
    public String toString() {
        return "Tube{" +
                "axixRay=" + axixRay +
                ", radius=" + radius +
                '}';
    }
}
