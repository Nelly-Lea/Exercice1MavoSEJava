package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this class inherited of PointLight and contains a director vector
 */
public class SpotLight extends PointLight {
    /**
     *
     */
    private final Vector _direction;

    /** constructor of SpotLight, calls the constructor of PointLight and put the director vector
     * @param intensity
     * @param position
     * @param direction
     */
    public SpotLight(Color intensity, Point3D position, Vector direction) {
        super(intensity, position);
        _direction = direction.normalized();
    }

    /**
     *this function return the intensity color at the point p
     * @param p
     * @return Color at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        Color baseIntensity= super.getIntensity(p); // baseIntensity =(I0/kc+k*d+kq*d^2)
        Vector l=getL(p);
        double factor=Math.max(0,_direction.dotProduct(l));
        return baseIntensity.scale(factor); //return baseIntensity*(max(0,dir*l))
    }
}