package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import static primitives.Util.*;

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
        if(p0.equals(_axisRay.get_p0()))
            return _axisRay.get_dir();
        Vector v=p0.subtract(_axisRay.get_p0());
        double s=v.dotProduct(_axisRay.get_dir());
        if(isZero(s))
            return _axisRay.get_dir();//if p0 belongs to the base of the cylinder where _axisRay.get_p0() is
        else { //if p0 belongs to the opposite base
            Vector v2 = _axisRay.get_dir().scale(_height);//vector of length _height
            Point3D p02 = p0.add(v2);//projection of _axisRay.get_p0() on the opposite base
            Vector v3 = p02.subtract(p0);//vector between p0 and p02
            if (isZero(v3.dotProduct(_axisRay.get_dir())))
                return _axisRay.get_dir();
        }
        return super.getNormal(p0);
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
