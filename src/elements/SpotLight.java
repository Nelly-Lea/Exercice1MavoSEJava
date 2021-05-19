package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 *
 */
public class SpotLight extends PointLight {
    /**
     *
     */
    private final Vector _direction;

    /**
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     *
     * @param p
     * @return
     */
    @Override
    public Color getIntensity(Point3D p) {
        Color baseIntensity= super.getIntensity(p);
        Vector l=getL(p);
        double factor=Math.max(0,_direction.dotProduct(l));
        return baseIntensity.scale(factor);
    }
}
