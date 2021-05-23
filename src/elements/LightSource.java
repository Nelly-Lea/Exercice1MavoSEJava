package elements;
import primitives.Color;
import primitives.Point3D;

import primitives.Vector;

/**
 * LightSource in an interface that contains intensity at point p and and the the director vector of the LightSource
 */
public interface LightSource {
    public Color getIntensity (Point3D p);
    public Vector getL(Point3D p);

}
