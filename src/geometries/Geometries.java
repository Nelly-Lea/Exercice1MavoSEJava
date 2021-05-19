package geometries;

import primitives.Ray;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Geometries implements Intersectable {
    private List<Intersectable> _intersectables=new LinkedList<>();

    public Geometries(Intersectable... intersectables) {
        _intersectables = new LinkedList<>();
        add(intersectables);
    }

    public Geometries(){
       //nothing to add
    }

    public void add(Intersectable... intersectables){

        _intersectables.addAll(Arrays.asList(intersectables));
    }

    public void remove(Intersectable... intersectables){
        _intersectables.removeAll(Arrays.asList(intersectables));
    }

    @Override
    public List<GeoPoint> findGeoIntersections(Ray ray) {
        List<GeoPoint> result= null;

        //we pass on all geometries intersectables we check if there is points of intersection. If there is any, we add intersection point in a list
        for (Intersectable item : _intersectables) {
            List<GeoPoint> itemPoints = item.findGeoIntersections(ray);
            if(itemPoints!=null){
                if(result==null){
                    result=new LinkedList<>();
                }
                result.addAll(itemPoints);
            }
        }

        return result;
    }
}
