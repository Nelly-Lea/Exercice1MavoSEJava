package elements;//package elements;


import elements.*;
import geometries.*;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.*;
import scene.Scene;

/**
 * Testing basic shadows
 *
 * @author Dan
 */
public class ShadowTests {
    private Scene scene = new Scene("Test scene");
    private Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
            .setViewPlaneSize(200, 200).setDistance(1000);

    /**
     * Produce a picture of a sphere and triangle with point light and shade
     */
    @Test
    public void sphereTriangleInitial() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }

    /**
     * Produce a picture of a two triangles lighted by a spot light with a Sphere
     * producing a shading
     */
    @Test
    public void trianglesSphere() {
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                new Sphere(30, new Point3D(0, 0, -115)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(700, 400, 400), new Point3D(40, 40, 115), new Vector(-1, -1, -4)) //
                        .setKl(4E-4).setKq(2E-5));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void sphereTriangleInitial1() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-112, -126, 114), new Vector(42.18, 86.34, -114.59)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial1", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void sphereTriangleInitial2() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, 0), new Point3D(-40, -70, 0), new Point3D(-68, -68, -4)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0),new Point3D(-112.37, -55.62, 100), new Vector(-51.25, -9.73, -74.87)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial2", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void sphereTriangleInitial3() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, -71), new Point3D(-40, -70, -71), new Point3D(-68, -68, -75)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-112, -126, 114), new Vector(42.18, 86.34, -114.59)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial3", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void sphereTriangleInitial4() {
        scene.geometries.add( //
                new Sphere(60, new Point3D(0, 0, -200)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)), //
                new Triangle(new Point3D(-70, -40, 10), new Point3D(-40, -70, 10), new Point3D(-68, -68, 6)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
        );
        scene.lights.add( //
                new SpotLight(new Color(400, 240, 0), new Point3D(-112, -126, 114), new Vector(42.18, 86.34, -114.59)) //
                        .setKl(1E-5).setKq(1.5E-7));

        Render render = new Render(). //
                setImageWriter(new ImageWriter("shadowSphereTriangleInitial4", 400, 400)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }
}


