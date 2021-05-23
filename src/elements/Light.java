package elements;

import primitives.Color;

/**
 * this class is an abstract class that contains hte intensity I0
 */
abstract class Light {
    protected final Color _intensity;

    /**
     * constructor of Light
     */
    public Light(Color intensity) {
        _intensity = intensity;
    }

    public Color getIntensity() {
        return _intensity;
    }
}
