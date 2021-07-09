package elements;

import primitives.Color;

public class AmbientLight extends Light{



    public AmbientLight() {
        super(Color.BLACK);
    }

    /**
     * Constructor
     * @param Ia intensity color
     * @param Ka constant for intensity
     */


    public AmbientLight(Color Ia, double Ka) {
        super(Ia.scale(Ka));
    }

}