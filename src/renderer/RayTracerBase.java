package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

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
}