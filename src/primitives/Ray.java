package primitives;

import java.util.List;

import static geometries.Intersectable.GeoPoint;

public class Ray {
    final Point3D _p0;
    final Vector _dir;

    //contructor checks if the vector reveived dir is normal and if not,it normalizes it
    public Ray(Point3D p0, Vector dir) {
//        if(dir.length()!=1) {
//            dir=dir.normalized();
//        }
        _p0 = p0;
        _dir = dir.normalized();
    }

    /**
     * find the closest point to ray origin
     * @param pointsList intersections point List
     * @return closest point
     */

    public Point3D findClosestPoint(List<Point3D> pointsList){
        Point3D result=null;
        double closestDistance=Double.MAX_VALUE;

        if(pointsList==null){
            return null;
        }
        for(Point3D p:pointsList){
            double temp=p.distance(_p0);// temp is the distance between a point in the list and the _p0 of the ray
            if(temp<closestDistance){
                closestDistance=temp;// closestDistance is the minimum temp
                result=p;
            }
        }
        return result; //result is the closest point to the ray

    }

    /**
     * @param o
     * @return true if the Ray received and the Ray on which the operation is performed are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    public Point3D get_p0() {
        return _p0;
    }

    public Vector get_dir() {
        return _dir;
    }

    public Point3D getPoint(double t){
        Point3D p= _p0.add(_dir.scale(t));
        return p;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _dir=" + _dir +
                '}';
    }

    /**
     * find the closest point tin geopoint list
     * @param geoPointList list of geopoint
     * @return closest geopoint
     */

    public GeoPoint findClosestGeoPoint(List<GeoPoint> geoPointList)
    {
        GeoPoint result=null;
        double closestDistance=Double.MAX_VALUE;// closestDistance = a big value

        if(geoPointList==null){
            return null;
        }
        for(GeoPoint geo:geoPointList){
            double temp=geo.point.distance(_p0);// temp is the distance between a point in the list and the _p0 of the ray
            if(temp<closestDistance){
                closestDistance=temp;// closestDistance is the minimum temp
                result=geo;
            }
        }
        return result; //result is the closest point to the ray


    }
}