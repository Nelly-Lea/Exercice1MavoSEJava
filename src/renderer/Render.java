package renderer;

import elements.Camera;
import primitives.Color;
import primitives.Ray;
import scene.Scene;

import java.util.MissingResourceException;

public class Render {
    ImageWriter _imageWriter = null;
    Scene _scene = null;
    Camera _camera = null;
    RayTracerBase _rayTracerBase=null;

    public Render setImageWriter(ImageWriter imageWriter) {
        _imageWriter = imageWriter;
        return this;
    }

    public Render setScene(Scene scene) {
        _scene = scene;
        return this;
    }

    public Render setCamera(Camera camera) {
        _camera = camera;
        return this;
    }

    public Render setRayTracer(RayTracerBase rayTracer) {
        _rayTracerBase = rayTracer;

        return this;
    }

    /**
     * constructor that creates a ray for every pixel and return the color of every pixel
     */
    public void renderImage() {
        //we check that all fields are not null
        try {
            if (_imageWriter == null) {
                throw new MissingResourceException("missing ressource", ImageWriter.class.getName(), "");
            }
            if (_scene == null) {
                throw new MissingResourceException("missing ressource", Scene.class.getName(), "");
            }
            if (_camera == null) {
                throw new MissingResourceException("missing ressource", Camera.class.getName(), "");
            }
            if (_rayTracerBase == null) {
                throw new MissingResourceException("missing ressource", RayTracerBase.class.getName(), "");
            }

            //rendering the image
            int nX= _imageWriter.getNx();
            int nY= _imageWriter.getNy();
            for(int i=0;i<nY;i++) {
                for (int j = 0; j < nX; j++) {
                    Ray ray = _camera.constructRayThroughPixel(nX, nY, j, i);
                    Color pixelColor=_rayTracerBase.traceRay(ray);
                    _imageWriter.writePixel(j,i,pixelColor);
                }
            }

        }catch(MissingResourceException e){
            throw new UnsupportedOperationException("Not implemented yet "+e.getClassName());
        }
    }

    public void printGrid(int interval, Color color) {

        if(_imageWriter==null){
            throw new MissingResourceException("empty field","ImageWriter","_imageWriter");
        }
        int nX = _imageWriter.getNx();
        int nY = _imageWriter.getNy();
        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                if (i % interval == 0 || j % interval == 0) {
                        _imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * constructor checks if the imagewriter is null else call the constructor
     */
    public void writeToImage(){
        if(_imageWriter==null){
            throw new MissingResourceException("empty field","ImageWriter","_imageWriter");
        }
        _imageWriter.writeToImage();

    }

}
