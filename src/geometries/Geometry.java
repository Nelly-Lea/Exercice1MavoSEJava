package geometries;

import primitives.Point3D;
import primitives.Vector;


public interface Geometry extends Intersectable{
    /**
     * function that receive a point in a body and return a normal in this point to the body
     *
     * @param point point pointing in the direction of the vector
     * @return normal vector to the to the Geometry
     */
    Vector getNormal(Point3D point);
}
