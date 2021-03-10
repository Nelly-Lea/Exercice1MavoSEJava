package geometries;

import primitives.Point3D;
import primitives.Vector;
import primitives.Coordinate;
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
        _radius = radius;
    }
    public Vector getNormal(Point3D p0){
//        if((((p0.get_x().getCoord()- _center.get_x().getCoord())*(p0.get_x().getCoord()- _center.get_x().getCoord()))+
//                ((p0.get_y().getCoord()- _center.get_y().getCoord())*(p0.get_y().getCoord()- _center.get_y().getCoord()))+
//                ((p0.get_z().getCoord()- _center.get_z().getCoord())* (p0.get_z().getCoord()- _center.get_z().getCoord()))!=0 ))
//        {
//            throw new IllegalArgumentException("This point doesn't belong to the sphere");
//        }
//          return new Vector(new Point3D(
//                 p0.get_x().getCoord()- _center.get_x().getCoord(),
//                  p0.get_y().getCoord()- _center.get_y().getCoord(),
//                  p0.get_z().getCoord()- _center.get_z().getCoord()
//          ));
return null;
    }

    @Override
    public String toString() {
        return "Sphere{" +
                "center=" + _center +
                ", radius=" + _radius +
                '}';
    }
}
