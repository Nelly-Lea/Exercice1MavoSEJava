package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;



public class DirectionalLight extends Light implements LightSource{

    private final Vector _direction;

    /**
     * constructor
     * @param intensity of the light
     * @param direction
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        _direction = direction.normalized();
    }

    @Override
    public Color getIntensity(Point3D p) {
        return _intensity;
    }

    /**
     * @param p
     * @return the direction vector of the light
     */
    @Override
    public Vector getL(Point3D p) {
        return _direction.normalized();
    }

    public double getDistance(Point3D point)
    {
        return Double.POSITIVE_INFINITY;
    }
}