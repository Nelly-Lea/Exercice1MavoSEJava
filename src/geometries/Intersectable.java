package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * the interface which receive ray and return all the intersections between the ray and geometries
 */

public interface Intersectable {

    public List<GeoPoint> findGeoIntersections(Ray ray ,double maxdistance);

    /**
     * this function received a ray and call the function findGeoIntersection(Ray,double)
     * @param ray
     * @return a list of GeoPoint that are the received ray intersects
     */
    default List<GeoPoint> findGeoIntersections(Ray ray)
    {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }
    /**
     * this class contains a geometry and point on this geometry
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point3D point;

        /**
         * constructor of GeoPoint
         */
        public GeoPoint(Geometry geometry, Point3D point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            GeoPoint geoPoint = (GeoPoint) o;
          return(point.equals(geoPoint.point))&&(geometry.getClass().equals(geoPoint.geometry.getClass()));

        }

    }

    /**
     *this function returns a list of intersections point with the received ray
     * @param ray
     * @return
     */

    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }

}