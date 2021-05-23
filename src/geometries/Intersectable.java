package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.List;
import java.util.stream.Collectors;

/**
 * the interface which receive ray and return all the intersections between the ray and geometries
 */

public interface Intersectable {
    //List<Point3D> findIntersections(Ray ray);
    public List<GeoPoint> findGeoIntersections(Ray ray);
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
            return geometry.equals(geoPoint.geometry) && point.equals(geoPoint.point);
        }

    }

    default List<Point3D> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null
                : geoList.stream()
                .map(gp -> gp.point)
                .collect(Collectors.toList());
    }
//    default List<Point3D> findIGeontersections(Ray ray) {
//        var geoList = findGeoIntersections(ray);
//        return geoList == null ? null
//                : geoList.stream()
//                .map(gp -> gp.point)
//                .collect(Collectors.toList());
//    }

}
