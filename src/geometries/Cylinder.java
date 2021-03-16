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
       Point3D o=_axisRay.get_p0();
       Vector v=_axisRay.get_dir();

       //projection of P-o on the ray:
        double t;
        try{
            t=alignZero(p0.subtract(o).dotProduct(v));
        }catch(IllegalArgumentException e) {//P=O
            return v;
        }
        //if the point is a base
        if(t==0||isZero(_height-t))//if it's close to 0,we'll get ZERO vector exception
           return v;
        o=o.add(v.scale(t));
        return p0.subtract(o).normalize();
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
