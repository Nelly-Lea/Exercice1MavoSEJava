package scene;

import elements.AmbientLight;
import elements.LightSource;
import geometries.Geometries;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

public class Scene {
    private final String _name;
    public Color background=Color.BLACK;
    public AmbientLight ambientLight=new AmbientLight(new Color(192, 192, 192),1.d);
    public Geometries geometries=null;
    public List<LightSource> _lights=new LinkedList<>();

    public Scene setLights(List<LightSource> lights) {
        _lights = lights;
        return this;
    }

    /**
     * constructor that creates a scene
     * @param name
     */
    public Scene(String name) {
        _name = name;
        geometries = new Geometries();
    }

    //chaining set method(this NOT a builder pattern)

    public Scene setBackground(Color background) {
        this.background = background;
        return this;
    }

    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setGeometries(Geometries geometries) {
        this.geometries = geometries;
        return this;
    }
}
