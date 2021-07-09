package elements;

import primitives.Color;

abstract class Light {
    protected final Color _intensity;

    protected Light(Color intensity) {
        _intensity = intensity;
    }

    public Color getIntensity() {
        return _intensity;
    }
}
