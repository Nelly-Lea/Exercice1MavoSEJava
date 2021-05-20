package geometries;

import static primitives.Util.isZero;


public abstract class RadialGeometry extends Geometry{
    final protected double _radius;

    public RadialGeometry(double radius) {
        if (isZero(radius))
            throw new IllegalArgumentException("radius cannot be ZERO");
        else
            _radius = radius;
    }

    public double get_radius() {
        return _radius;
    }
}
