package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point3D;
import primitives.Vector;

/**
 * this class is an abstract class and contains emission color of a geometry and material
 */
public abstract class Geometry implements Intersectable {
    protected Color _emmission = Color.BLACK;
    protected Material _material = new Material();

    /**
     * getter
     * @return Material object
     */
    public Material getMaterial() {
        return _material;
    }


    public Color getEmmission() {
        return _emmission;
    }

    /**
     * this function receives an emission color and return geometry with this color
     * @param emmission
     * @return
     */
    public Geometry setEmmission(Color emmission) {
        _emmission = emmission;
        return this;
    }

    /**
     * this function receives Material and return geometry with this Material
     * @param material
     * @return
     */
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