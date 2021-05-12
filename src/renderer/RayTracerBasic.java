package renderer;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public class RayTracerBasic extends RayTracerBase {
    /**
     * constructor calls father's constructor
     * @param scene
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    /**
     *  traceRay receives a ray return a color for pixel. If there are intersections with the ray
     *  calculs the color of the closest point else it return the background color
     * @param ray
     * @return color of pixel
     */
    public Color traceRay (Ray ray)
    {
        List<Point3D> intersections =_scene.geometries.findIntersections(ray);
        if(intersections!=null) {
            Point3D closestPoint = ray.findClosestPoint(intersections);
            return calcColor(closestPoint);
        }
        return _scene.background;
    }
    public  Color calcColor(Point3D point)
    {
        return _scene.ambientLight.getIntensity();
    }



}
