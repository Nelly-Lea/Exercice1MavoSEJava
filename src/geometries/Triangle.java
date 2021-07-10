package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;

/*
 * The class is based on Point3D and an extends of Polygon
 */

public class Triangle extends Polygon {
    //constructor that receives 3 points
    public Triangle(Point3D p0, Point3D p1, Point3D p2) {

        super(p0, p1, p2);
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }

    /**
     * this function return a list of GeoPoint intersections with the received ray
     * @param ray
     * @param maxDistance
     * @return
     */
    public List<GeoPoint> findGeoIntersections(Ray ray,double maxDistance) {
        return super.findGeoIntersections(ray,maxDistance);

    }
}
