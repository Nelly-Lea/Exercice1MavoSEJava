package geometries;

import primitives.Point3D;
import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables=new LinkedList<>();

    /**
     * constructor of Geometrie
     * @param intersectables
     * return a List of intersectables
     */
    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<>();
        add(intersectables);
    }

    public Geometries(){
        //nothing to add
    }

    /**
     * this function adds the list of intersectables received to the existing list
     * @param intersectables
     */
    public void add(Intersectable... intersectables){

        _intersectables.addAll(Arrays.asList(intersectables));
    }

    /**
     * this function removes intersectables in the received list from the existing list
     * @param intersectables
     */

    public void remove(Intersectable... intersectables){
        _intersectables.removeAll(Arrays.asList(intersectables));
    }

//    @Override
//    public List<GeoPoint> findGeoIntersections(Ray ray) {
//        return null;
//    }

    /**
     * this function return a list of GeoPoint that the received ray intersects
     * @param ray
     * @param maxDistance
     * @return a list of GeoPoint
     */
    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) { ///
        List<GeoPoint> result= null;

        //we pass on all geometries intersectables we check if there is points of intersection. If there is any, we add intersection point in a list
        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray,maxDistance);// calls the function findGeoIntersection correspond to the item object
            if(itemPoints!=null){
                if(result==null){
                    result=new LinkedList<>();
                }
                result.addAll(itemPoints);// we add to result list intersections points
            }
        }

        return result;
    }



}