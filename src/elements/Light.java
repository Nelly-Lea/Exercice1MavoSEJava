package elements;

import primitives.Color;

abstract class Light {
    protected final Color _intensity;

    /**
     * constructor of Light
     * @param intensity
     */

    protected Light(Color intensity) {
        _intensity = intensity;
    }

    public Color getIntensity() {
        return _intensity;
    }
}
