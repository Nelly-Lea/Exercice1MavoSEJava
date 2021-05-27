package elements;

import primitives.Color;

public class AmbientLight extends Light{

    // final private Color _intensity;

    public AmbientLight() {
        // _intensity=Color.BLACK;
        super(Color.BLACK);
    }

    /**
     * Constructor
     * @param Ia intensity color
     * @param Ka constant for intensity
     */


    public AmbientLight(Color Ia, double Ka) {

        //_intensity=Ia.scale(Ka);
        super(Ia.scale(Ka));
    }

    /**
     * get intensity color
     * @return intensity
     */
    // public Color getIntensity() {
//        return _intensity;
//    }

}