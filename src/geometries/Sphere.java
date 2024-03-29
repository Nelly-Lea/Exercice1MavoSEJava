package geometries;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Sphere extends RadialGeometry{
    final Point3D _center;
    public Point3D get_center() {
        return _center;
    }

    /**
     *  constructor that receives the center point and the radius
     */

    public Sphere( double radius,Point3D center){
        super(radius);
        _center = center;
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


    /**
     * his function returns a list of GeoPoint intersection with the received ray
     * @param ray
     * @param maxDistance
     * @return
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray , double maxDistance ) {///
        Point3D p0=ray.get_p0();//p0 of the received ray
        Vector v=ray.get_dir();//director vector of the received ray

        if(p0.equals(_center)){// we verify that p0 is not on the center of the sphere
            throw new IllegalArgumentException("Ray p0 cannot be equals to the center of the sphere");
        }

        Vector u=_center.subtract(p0);// u is the vector between the center and p0

        double tm=alignZero(v.dotProduct(u));
        double d=alignZero(Math.sqrt(u.lengthSquared()-(tm*tm)));

        if(d>=_radius){
            return null;
        }

        double th=alignZero(Math.sqrt(_radius*_radius-d*d));
        double t1=alignZero(tm-th);
        double t2=alignZero(tm+th);

        boolean validT1=alignZero(t1-maxDistance)<=0;
        boolean validT2=alignZero(t2-maxDistance)<=0;

        if(t1>0 && t2>0&& validT1&&validT2){//if there are 2 intersections
            Point3D p1=ray.getPoint(t1);
            Point3D p2= ray.getPoint(t2);

            return List.of(new GeoPoint(this,p1), new GeoPoint(this,p2));
        }
        if(t1>0 &&validT1){//if there is one intersection point
            Point3D p1= ray.getPoint(t1);

            return List.of(new GeoPoint(this,p1));
        }

        if(t2>0&& validT2){//if there is one intersection point
            Point3D p2= ray.getPoint(t2);

            return List.of(new GeoPoint(this,p2));
        }
        return null;
    }




}
