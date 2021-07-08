package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.List;

public abstract class RayTracerBase {
    protected Scene _scene ;

    /**
     * constructor
     * @param scene
     */
    public RayTracerBase(Scene scene) {
        if(scene==null)
            throw new IllegalArgumentException("Error");
        this._scene = scene;
    }
    public abstract Color traceRay (Ray ray);
    public abstract Color ColorAverage(List<Ray> rays);
}