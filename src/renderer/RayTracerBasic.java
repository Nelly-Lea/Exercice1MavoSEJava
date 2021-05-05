package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {
    public RayTracerBasic(Scene scene) {
        super(scene);
    }
    public Color traceRay (Ray ray)
    {
        List<Point3D> intersections =_scene.geometries.findIntersections(ray);
        if(intersections==null)
            return _scene.background;
        Point3D closestPoint=ray.findClosestPoint(intersections);
        return calcColor(closestPoint);
    }
    public  Color calcColor(Point3D point)
    {
        return _scene.ambientLight.getIntensity();
    }



}
