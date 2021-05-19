package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;


public abstract class Geometry implements Intersectable {
    protected Color _emmission = Color.BLACK;
    protected Material _material = new Material();

    public Material getMaterial() {
        return _material;
    }


    public Color getEmmission() {
        return _emmission;
    }

    public Geometry setEmmission(Color emmission) {
        _emmission = emmission;
        return this;
    }

    public Geometry setMaterial(Material material) {
        _material = material;
        return this;
    }

    /**
     * function that receive a point in a body and return a normal in this point to the body
     *
     * @param point point pointing in the direction of the vector
     * @return normal vector to the to the Geometry
     */
    public abstract Vector getNormal(Point3D point);
}
