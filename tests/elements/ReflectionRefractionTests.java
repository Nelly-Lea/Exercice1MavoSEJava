/**
 *
 */
package elements;


import elements.*;
import geometries.Plane;
import geometries.Polygon;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.*;
import primitives.Color;
import renderer.*;
import scene.Scene;

import java.awt.*;

/**
 * Tests for reflection and transparency functionality, test for partial shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene("Test scene");

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(150, 150).setDistance(1000);

        scene.geometries.add( //
                new Sphere(50, new Point3D(0, 0, -50)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25, new Point3D(0, 0, -50)) //
                        .setEmmission(new Color(java.awt.Color.RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point3D(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        Render render = new Render() //
                .setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));
        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point3D(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(2500, 2500).setDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400, new Point3D(-950, -900, -1000)) //
                        .setEmmission(new Color(0, 0, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKt(0.5)),
                new Sphere(200, new Point3D(-950, -900, -1000)) //
                        .setEmmission(new Color(100, 20, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(670, 670, 3000)) //
                        .setEmmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point3D(1500, -1500, -1500), new Point3D(-1500, 1500, -1500),
                        new Point3D(-1500, -1500, -2000)) //
                        .setEmmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(0.5)));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point3D(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }


    /**
     * Produce a picture of a two triangles lighted by a spot light with a partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point3D(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setViewPlaneSize(500, 350).setDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(150, -150, -135), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point3D(-150, -150, -115), new Point3D(-70, 70, -140), new Point3D(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30, new Point3D(60, 50, -50)) //
                        .setEmmission(new Color(java.awt.Color.BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point3D(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));

        ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }
    @Test
    public void OurImage() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
     //Camera camera1 = new Camera(new Point3D(-0.21,-0.65,2.19), new Vector(0, 0, -0.29), new Vector(0, 1, 0)).setViewPlaneSize(200, 200).setDistance(300);
      scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
      /*1*/  scene.geometries.add(( new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-1,-1,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5))),
  /*2*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
/*3*/new Polygon(new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
     /*4*/           new Polygon(new Point3D(-0.6,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*5*/ new Polygon(new Point3D(0,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-0.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*6*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-1,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*7*/ new Polygon(new Point3D(0.4,-1,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,-1.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*8*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*9*/ new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.8,-1.2,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*10*/ new Polygon(new Point3D(-0.8,-1.2,0),new Point3D(-1,-1,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*11*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.72,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*12*/ new Polygon(new Point3D(-1,-0.72,0),new Point3D(-1,-0.4,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*13*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-1,-0.4,0),new Point3D(-0.8,-0.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*14*/ new Polygon(new Point3D(-0.8,-0.2,0),new Point3D(-0.6,0,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*15*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.6,0,0),new Point3D(-0.31,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*16*/ new Polygon(new Point3D(-0.31,0,0),new Point3D(0,0,0),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*17*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0,0,0),new Point3D(0.17,-0.17,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*18*/ new Polygon(new Point3D(0.17,-0.17,0),new Point3D(0.4,-0.4,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*19*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(0.4,-0.72,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*20*/ new Polygon(new Point3D(0.4,-0.72,0),new Point3D(0.4,-1,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*21*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.4,-1,0),new Point3D(0.2,-1.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*22*/ new Polygon(new Point3D(0.2,-1.2,0),new Point3D(0,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*23*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.33,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*24*/ new Polygon(new Point3D(-0.33,-1.4,0),new Point3D(-0.6,-1.4,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*25*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*26*/ new Polygon(new Point3D(0.1,-0.69,0.22),new Point3D(0.23,-0.93,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*27*/ new Polygon(new Point3D(0,-1,0.22),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*28*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.63,-0.99,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*29*/ new Polygon(new Point3D(-0.63,-0.99,0.22),new Point3D(-0.86,-0.94,0.15),new Point3D(-0.72,-0.7,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*30*/ new Polygon(new Point3D(-0.72,-0.7,0.22),new Point3D(-0.81,-0.48,0.15),new Point3D(-0.61,-0.36,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*31*/ new Polygon(new Point3D(-0.61,-0.36,0.22),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*32*/ new Polygon(new Point3D(-0.29,-0.31,0.22),new Point3D(-0.06,-0.17,0.15),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*33*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.61,-0.36,0.22),
                        new Point3D(-0.29,-0.31,0.22),new Point3D(0,-0.4,0.22),new Point3D(0.1,-0.69,0.22),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254
                )).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
  /*34*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*35*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.8,-1.2,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*36*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*37*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-1,-0.72,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*38*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.61,-0.36,0.22),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*39*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.8,-0.2,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*40*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.06,-0.17,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*41*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.31,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*42*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0,-0.4,0.22),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*43*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0.17,-0.17,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
/*44*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*45*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.4,-0.72,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*46*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*47*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.2,-1.2,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*48*/ new Polygon(new Point3D(-0.07,-1.23,0.15),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
/*49*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.33,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                new Polygon(new Point3D(1.29,-1.64,2),new Point3D(1.5,-0.3,2),new Point3D(1.5,-0.3,1.97)).setEmmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKt(0.5)),
/*34*/ new Plane(new Point3D(-2,-2,-0.82),new Point3D(1,-2,-0.82),new Point3D(1,0.5,-0.82)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(1)),

        Color red =new Color(java.awt.Color.red);
        red=red.add(new Color(java.awt.Color.blue));
//           new Sphere(0.3, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.pink)), // kt transparent =1 kr=1 mirroir
//                new Sphere(0.8, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.blue)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
          /*1*/  scene.geometries.add( new Sphere(0.2, new Point3D(1.73,-1.86,0.28)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.5)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// mirroir qui donne orange
                /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// rouge sur le cote gauche
                /*3*/new Sphere(0.03, new Point3D(-0.49,-0.26,0.5)).setEmmission(new Color(java.awt.Color.red)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*4*/new Sphere(0.05, new Point3D(0.16,0.48,0)).setEmmission(new Color(java.awt.Color.cyan)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
               /*5*/new Sphere(0.4, new Point3D(-0.79,0,1.11)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1).setKr(0)),
                /*6*/new Sphere(0.09, new Point3D(1.16,-1.11,0.5)).setEmmission(new Color(255,127,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),//boule orange
               /*13*/new Sphere(0.25, new Point3D(1.88,-1.61,1.3)).setEmmission(new Color(16,52,166)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0.35)),
                /*7*/new Sphere(0.04, new Point3D(-1.13,0.79,1.18)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),
               /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.18,-0.79,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.41,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.52,-0.67,1.13)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.8,-1.26,2.25)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.8,-0.96,1.62)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.81,0.29,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-2.01,1.41)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.72,0.71,1.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.18,0.82,1.81)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.68,-0.2,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.77,1.74,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-0.49,1.23,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.02,1.4,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.27,1.74,1.94)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.98,1.34)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.57,1.2,1.73)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.5,1,0)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.96,2.27,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,1.59,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0,2,3.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.79,0.17,0.7)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-3.21,0.58,1)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.76,-2.36,1.22)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.98,0.68,0.83)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.35,0.4,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.2,3.64)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-2.53,-0.36,1.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.51,0.91,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(4.13,2.24,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-5.56,-1.03,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.35,0.15,2.59)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(6.38,3.02,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//
                /*13*/new Sphere(0.8, new Point3D(-1.89,-2.87,1.64)).setEmmission(new Color(255,203,96)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0)),
               /*14*/new Sphere(0.15, new Point3D(-0.79,0,1.11)).setEmmission(new Color(249,66,158)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.9).setKr(0)));
//                /*15*/new Sphere(0.3, new Point3D(-13.59,-8.65,-15)).setEmmission(new Color(java.awt.Color.gray)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
//
        boolean turnon4=true;
        if(turnon4){
        scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));}// le rose sur le plane
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(-0.33, -0.77, -0.3)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight( new Color(400, 750, 500), new Point3D(0.13, -1.03, 1.16)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
       // scene.lights.add(new SpotLight(new Color(400, 750, 500),new Point3D(2.33,-0.63,0.68), new Vector(-0.43,0,-0.04))  .setKl(4E-5).setKq(2E-7)); // new Vector(0.19, -2.56, 5)
        boolean turnon3=true;
        if(turnon3){
        scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11,-1.32,0.56))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001)); }// new Vector(0.19, -2.56, 5)
        boolean turnon1=true;
        if(turnon1) {
            scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1))); // lumiere bleu sur plane
        }
        boolean turnon2=true;
        if(turnon2) {
            scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                    .setKl(4E-5).setKq(2E-7));// le bleute sur le devant du plane
        }
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-0.6,-1.4,0))//lumiere au fond plane blanche
//                .setKl(0.00001).setKq(0.000001)); // new Vector(0.19, -2.56, 5)
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(1.5,-0.3,1.98))//
//                .setKl(4E-5).setKq(2E-7));
        boolean turnon5=true;
        if(turnon5) {
            scene.lights.add(new PointLight(new Color(155, 155, 155), new Point3D(-0.61, -0.36, 00.22))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001));
        }

            //    .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(254,191,210), new Point3D(1,0.1,1.05)) // new Vector(0.19, -2.56, 5)
                .setKl(4E-5).setKq(2E-7));
      //  scene.lights.add( //
//                new SpotLight(new Color(400, 240, 0), new Point3D(-8, 5, 0), new Vector(0, -6, 0)) //
////                        .setKl(1E-5).setKq(1.5E-7));
//        scene.lights.add(new SpotLight(new Color(2000, 2000, 2000), new Point3D(-8.19, -5.44, 2), new Vector(-1.4,3.73,5)));

                ImageWriter imageWriter = new ImageWriter("OurImage", 600, 600);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(new BasicRayTracer(scene));

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ImageTest() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/
        scene.geometries.add(
              //  /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
              /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.38)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                      new Polygon(new Point3D(-0.24,-0.32,3.01),new Point3D(2,-4,-1.36), new Point3D(-2.52,2.35,-1.36))
                              .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                /*34*/ new Plane(new Point3D(-2, -2, -0.82), new Point3D(1, -2, -0.82), new Point3D(1, 0.5, -0.82))
                    //    .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
        scene.lights.add(new SpotLight(new Color(219, 0, 115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11, -1.32, 0.56))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));
        scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1)));
        scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        ImageWriter imageWriter = new ImageWriter("ImageTest", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(1);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS)
                .setMultithreading(3).setDebugPrint();


       // render.renderImageThreaded();
        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void OurImageImprovement() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        //Camera camera1 = new Camera(new Point3D(-0.21,-0.65,2.19), new Vector(0, 0, -0.29), new Vector(0, 1, 0)).setViewPlaneSize(200, 200).setDistance(300);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/  scene.geometries.add(( new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-1,-1,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5))),
                /*2*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*3*/new Polygon(new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*4*/           new Polygon(new Point3D(-0.6,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*5*/ new Polygon(new Point3D(0,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-0.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*6*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-1,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*7*/ new Polygon(new Point3D(0.4,-1,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,-1.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*8*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*9*/ new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.8,-1.2,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*10*/ new Polygon(new Point3D(-0.8,-1.2,0),new Point3D(-1,-1,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*11*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.72,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*12*/ new Polygon(new Point3D(-1,-0.72,0),new Point3D(-1,-0.4,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*13*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-1,-0.4,0),new Point3D(-0.8,-0.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*14*/ new Polygon(new Point3D(-0.8,-0.2,0),new Point3D(-0.6,0,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*15*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.6,0,0),new Point3D(-0.31,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*16*/ new Polygon(new Point3D(-0.31,0,0),new Point3D(0,0,0),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*17*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0,0,0),new Point3D(0.17,-0.17,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*18*/ new Polygon(new Point3D(0.17,-0.17,0),new Point3D(0.4,-0.4,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*19*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(0.4,-0.72,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*20*/ new Polygon(new Point3D(0.4,-0.72,0),new Point3D(0.4,-1,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*21*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.4,-1,0),new Point3D(0.2,-1.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*22*/ new Polygon(new Point3D(0.2,-1.2,0),new Point3D(0,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*23*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.33,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*24*/ new Polygon(new Point3D(-0.33,-1.4,0),new Point3D(-0.6,-1.4,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*25*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*26*/ new Polygon(new Point3D(0.1,-0.69,0.22),new Point3D(0.23,-0.93,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*27*/ new Polygon(new Point3D(0,-1,0.22),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*28*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.63,-0.99,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*29*/ new Polygon(new Point3D(-0.63,-0.99,0.22),new Point3D(-0.86,-0.94,0.15),new Point3D(-0.72,-0.7,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*30*/ new Polygon(new Point3D(-0.72,-0.7,0.22),new Point3D(-0.81,-0.48,0.15),new Point3D(-0.61,-0.36,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*31*/ new Polygon(new Point3D(-0.61,-0.36,0.22),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*32*/ new Polygon(new Point3D(-0.29,-0.31,0.22),new Point3D(-0.06,-0.17,0.15),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*33*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.61,-0.36,0.22),
                        new Point3D(-0.29,-0.31,0.22),new Point3D(0,-0.4,0.22),new Point3D(0.1,-0.69,0.22),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254
                )).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*34*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*35*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.8,-1.2,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*36*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*37*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-1,-0.72,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*38*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.61,-0.36,0.22),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*39*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.8,-0.2,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*40*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.06,-0.17,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*41*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.31,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*42*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0,-0.4,0.22),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*43*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0.17,-0.17,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*44*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*45*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.4,-0.72,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*46*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*47*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.2,-1.2,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*48*/ new Polygon(new Point3D(-0.07,-1.23,0.15),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*49*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.33,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                new Polygon(new Point3D(1.29,-1.64,2),new Point3D(1.5,-0.3,2),new Point3D(1.5,-0.3,1.97)).setEmmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKt(0.5)),
                /*34*/ new Plane(new Point3D(-2,-2,-0.82),new Point3D(1,-2,-0.82),new Point3D(1,0.5,-0.82)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(1)),

        Color red =new Color(java.awt.Color.red);
        red=red.add(new Color(java.awt.Color.blue));
//           new Sphere(0.3, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.pink)), // kt transparent =1 kr=1 mirroir
//                new Sphere(0.8, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.blue)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
        /*1*/  scene.geometries.add( new Sphere(0.2, new Point3D(1.73,-1.86,0.28)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.5)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// mirroir qui donne orange
                /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// rouge sur le cote gauche
                /*3*/new Sphere(0.03, new Point3D(-0.49,-0.26,0.5)).setEmmission(new Color(java.awt.Color.red)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*4*/new Sphere(0.05, new Point3D(0.16,0.48,0)).setEmmission(new Color(java.awt.Color.cyan)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*5*/new Sphere(0.4, new Point3D(-0.79,0,1.11)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1).setKr(0)),
                /*6*/new Sphere(0.09, new Point3D(1.16,-1.11,1.5)).setEmmission(new Color(255,127,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),//boule orange
                /*13*/new Sphere(0.25, new Point3D(1.88,-1.61,1.3)).setEmmission(new Color(16,52,166)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0.35)),
                /*7*/new Sphere(0.04, new Point3D(-1.13,0.79,1.18)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.18,-0.79,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.41,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.52,-0.67,1.13)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.8,-1.26,2.25)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.8,-0.96,1.62)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.81,0.29,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-2.01,1.41)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.72,0.71,1.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.18,0.82,1.81)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.68,-0.2,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.77,1.74,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-0.49,1.23,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.02,1.4,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.27,1.74,1.94)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.98,1.34)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.57,1.2,1.73)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.5,1,0)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.96,2.27,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,1.59,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0,2,3.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.79,0.17,0.7)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-3.21,0.58,1)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.76,-2.36,1.22)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.98,0.68,0.83)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.35,0.4,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.2,3.64)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-2.53,-0.36,1.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.51,0.91,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(4.13,2.24,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-5.56,-1.03,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.35,0.15,2.59)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(6.38,3.02,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//
                /*13*/new Sphere(0.8, new Point3D(-1.89,-2.87,1.64)).setEmmission(new Color(255,203,96)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0)),
                /*14*/new Sphere(0.15, new Point3D(-0.79,0,1.11)).setEmmission(new Color(249,66,158)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.9).setKr(0)));
//                /*15*/new Sphere(0.3, new Point3D(-13.59,-8.65,-15)).setEmmission(new Color(java.awt.Color.gray)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
//
        boolean turnon4=true;
        if(turnon4){
            scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                    .setKl(4E-5).setKq(2E-7));}// le rose sur le plane
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(-0.33, -0.77, -0.3)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight( new Color(400, 750, 500), new Point3D(0.13, -1.03, 1.16)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
        // scene.lights.add(new SpotLight(new Color(400, 750, 500),new Point3D(2.33,-0.63,0.68), new Vector(-0.43,0,-0.04))  .setKl(4E-5).setKq(2E-7)); // new Vector(0.19, -2.56, 5)
        boolean turnon3=true;
        if(turnon3){
            scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11,-1.32,0.56))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001)); }// new Vector(0.19, -2.56, 5)
        boolean turnon1=true;
        if(turnon1) {
            scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1))); // lumiere bleu sur plane
        }
        boolean turnon2=true;
        if(turnon2) {
            scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                    .setKl(4E-5).setKq(2E-7));// le bleute sur le devant du plane
        }
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-0.6,-1.4,0))//lumiere au fond plane blanche
//                .setKl(0.00001).setKq(0.000001)); // new Vector(0.19, -2.56, 5)
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(1.5,-0.3,1.98))//
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(155, 155, 155), new Point3D(-0.61,-0.36,00.22))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));

        //    .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(254,191,210), new Point3D(1,0.1,1.05)) // new Vector(0.19, -2.56, 5)
                .setKl(4E-5).setKq(2E-7));
        //  scene.lights.add( //
//                new SpotLight(new Color(400, 240, 0), new Point3D(-8, 5, 0), new Vector(0, -6, 0)) //
////                        .setKl(1E-5).setKq(1.5E-7));
//        scene.lights.add(new SpotLight(new Color(2000, 2000, 2000), new Point3D(-8.19, -5.44, 2), new Vector(-1.4,3.73,5)));

        ImageWriter imageWriter = new ImageWriter("OurImageImprovement", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(10);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS)
                .setMultithreading(3).setDebugPrint();
        render.setLevel_adaptive_supersampling(10);

        render.renderImage();
        render.writeToImage();
    }



@Test
    public void ImageTestSupersampling() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/
        scene.geometries.add(
                //  /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.38)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)));
            //    new Polygon(new Point3D(-0.24,-0.32,3.01),new Point3D(2,-4,-1.36), new Point3D(-2.52,2.35,-1.36))
                       // .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                /*34*/ new Plane(new Point3D(-2, -2, -0.82), new Point3D(1, -2, -0.82), new Point3D(1, 0.5, -0.82))
        //    .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
        scene.lights.add(new SpotLight(new Color(219, 0, 115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11, -1.32, 0.56))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));
        scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1)));
        scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        ImageWriter imageWriter = new ImageWriter("ImageTestSupersampling", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(1); // nombre de rayons pour glossy effect
        Render render = new Render(); //
        render.setAmountRays(1000); // lenombre de rau super sampling
                render.setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS);




    render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ImageTest1() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/
        scene.geometries.add(
                //  /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.38)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(-0.24,-0.32,3.01),new Point3D(2,-4,-1.36), new Point3D(-2.52,2.35,-1.36))
                        .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                /*34*/ new Plane(new Point3D(-2, -2, -0.82), new Point3D(1, -2, -0.82), new Point3D(1, 0.5, -0.82))
        //    .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
        scene.lights.add(new SpotLight(new Color(219, 0, 115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11, -1.32, 0.56))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));
        scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1)));
        scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        ImageWriter imageWriter = new ImageWriter("ImageTestMirroir", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(10);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS);


        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void OurImageImprovementSupersampling() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        //Camera camera1 = new Camera(new Point3D(-0.21,-0.65,2.19), new Vector(0, 0, -0.29), new Vector(0, 1, 0)).setViewPlaneSize(200, 200).setDistance(300);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/  scene.geometries.add(( new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-1,-1,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5))),
                /*2*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*3*/new Polygon(new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*4*/           new Polygon(new Point3D(-0.6,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*5*/ new Polygon(new Point3D(0,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-0.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*6*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-1,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*7*/ new Polygon(new Point3D(0.4,-1,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,-1.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*8*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*9*/ new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.8,-1.2,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*10*/ new Polygon(new Point3D(-0.8,-1.2,0),new Point3D(-1,-1,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*11*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.72,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*12*/ new Polygon(new Point3D(-1,-0.72,0),new Point3D(-1,-0.4,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*13*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-1,-0.4,0),new Point3D(-0.8,-0.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*14*/ new Polygon(new Point3D(-0.8,-0.2,0),new Point3D(-0.6,0,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*15*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.6,0,0),new Point3D(-0.31,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*16*/ new Polygon(new Point3D(-0.31,0,0),new Point3D(0,0,0),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*17*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0,0,0),new Point3D(0.17,-0.17,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*18*/ new Polygon(new Point3D(0.17,-0.17,0),new Point3D(0.4,-0.4,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*19*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(0.4,-0.72,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*20*/ new Polygon(new Point3D(0.4,-0.72,0),new Point3D(0.4,-1,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*21*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.4,-1,0),new Point3D(0.2,-1.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*22*/ new Polygon(new Point3D(0.2,-1.2,0),new Point3D(0,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*23*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.33,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*24*/ new Polygon(new Point3D(-0.33,-1.4,0),new Point3D(-0.6,-1.4,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*25*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*26*/ new Polygon(new Point3D(0.1,-0.69,0.22),new Point3D(0.23,-0.93,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*27*/ new Polygon(new Point3D(0,-1,0.22),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*28*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.63,-0.99,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*29*/ new Polygon(new Point3D(-0.63,-0.99,0.22),new Point3D(-0.86,-0.94,0.15),new Point3D(-0.72,-0.7,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*30*/ new Polygon(new Point3D(-0.72,-0.7,0.22),new Point3D(-0.81,-0.48,0.15),new Point3D(-0.61,-0.36,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*31*/ new Polygon(new Point3D(-0.61,-0.36,0.22),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*32*/ new Polygon(new Point3D(-0.29,-0.31,0.22),new Point3D(-0.06,-0.17,0.15),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*33*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.61,-0.36,0.22),
                        new Point3D(-0.29,-0.31,0.22),new Point3D(0,-0.4,0.22),new Point3D(0.1,-0.69,0.22),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254
                )).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*34*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*35*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.8,-1.2,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*36*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*37*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-1,-0.72,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*38*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.61,-0.36,0.22),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*39*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.8,-0.2,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*40*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.06,-0.17,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*41*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.31,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*42*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0,-0.4,0.22),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*43*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0.17,-0.17,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*44*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*45*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.4,-0.72,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*46*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*47*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.2,-1.2,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*48*/ new Polygon(new Point3D(-0.07,-1.23,0.15),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*49*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.33,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                new Polygon(new Point3D(1.29,-1.64,2),new Point3D(1.5,-0.3,2),new Point3D(1.5,-0.3,1.97)).setEmmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKt(0.5)),
                /*34*/ new Plane(new Point3D(-2,-2,-0.82),new Point3D(1,-2,-0.82),new Point3D(1,0.5,-0.82)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(1)),

        Color red =new Color(java.awt.Color.red);
        red=red.add(new Color(java.awt.Color.blue));
//           new Sphere(0.3, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.pink)), // kt transparent =1 kr=1 mirroir
//                new Sphere(0.8, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.blue)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
        /*1*/  scene.geometries.add( new Sphere(0.2, new Point3D(1.73,-1.86,0.28)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.5)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// mirroir qui donne orange
                /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// rouge sur le cote gauche
                /*3*/new Sphere(0.03, new Point3D(-0.49,-0.26,0.5)).setEmmission(new Color(java.awt.Color.red)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*4*/new Sphere(0.05, new Point3D(0.16,0.48,0)).setEmmission(new Color(java.awt.Color.cyan)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*5*/new Sphere(0.4, new Point3D(-0.79,0,1.11)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1).setKr(0)),
                /*6*/new Sphere(0.09, new Point3D(1.16,-1.11,0.5)).setEmmission(new Color(255,127,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),//boule orange
                /*13*/new Sphere(0.25, new Point3D(1.88,-1.61,1.3)).setEmmission(new Color(16,52,166)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0.35)),
                /*7*/new Sphere(0.04, new Point3D(-1.13,0.79,1.18)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.18,-0.79,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.41,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.52,-0.67,1.13)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.8,-1.26,2.25)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.8,-0.96,1.62)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.81,0.29,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-2.01,1.41)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.72,0.71,1.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.18,0.82,1.81)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.68,-0.2,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.77,1.74,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-0.49,1.23,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.02,1.4,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.27,1.74,1.94)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.98,1.34)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.57,1.2,1.73)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.5,1,0)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.96,2.27,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,1.59,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0,2,3.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.79,0.17,0.7)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-3.21,0.58,1)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.76,-2.36,1.22)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.98,0.68,0.83)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.35,0.4,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.2,3.64)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-2.53,-0.36,1.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.51,0.91,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(4.13,2.24,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-5.56,-1.03,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.35,0.15,2.59)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(6.38,3.02,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//
                /*13*/new Sphere(0.8, new Point3D(-1.89,-2.87,1.64)).setEmmission(new Color(255,203,96)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0)),
                /*14*/new Sphere(0.15, new Point3D(-0.79,0,1.11)).setEmmission(new Color(249,66,158)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.9).setKr(0)));
//                /*15*/new Sphere(0.3, new Point3D(-13.59,-8.65,-15)).setEmmission(new Color(java.awt.Color.gray)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
//
        boolean turnon4=true;
        if(turnon4){
            scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                    .setKl(4E-5).setKq(2E-7));}// le rose sur le plane
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(-0.33, -0.77, -0.3)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight( new Color(400, 750, 500), new Point3D(0.13, -1.03, 1.16)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
        // scene.lights.add(new SpotLight(new Color(400, 750, 500),new Point3D(2.33,-0.63,0.68), new Vector(-0.43,0,-0.04))  .setKl(4E-5).setKq(2E-7)); // new Vector(0.19, -2.56, 5)
        boolean turnon3=true;
        if(turnon3){
            scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11,-1.32,0.56))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001)); }// new Vector(0.19, -2.56, 5)
        boolean turnon1=true;
        if(turnon1) {
            scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1))); // lumiere bleu sur plane
        }
        boolean turnon2=true;
        if(turnon2) {
            scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                    .setKl(4E-5).setKq(2E-7));// le bleute sur le devant du plane
        }
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-0.6,-1.4,0))//lumiere au fond plane blanche
//                .setKl(0.00001).setKq(0.000001)); // new Vector(0.19, -2.56, 5)
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(1.5,-0.3,1.98))//
//                .setKl(4E-5).setKq(2E-7));
        boolean turnon5=true;
        if(turnon5) {
            scene.lights.add(new PointLight(new Color(155, 155, 155), new Point3D(-0.61, -0.36, 00.22))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001));
        }
        //    .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(254,191,210), new Point3D(1,0.1,1.05)) // new Vector(0.19, -2.56, 5)
                .setKl(4E-5).setKq(2E-7));
        //  scene.lights.add( //
//                new SpotLight(new Color(400, 240, 0), new Point3D(-8, 5, 0), new Vector(0, -6, 0)) //
////                        .setKl(1E-5).setKq(1.5E-7));
//        scene.lights.add(new SpotLight(new Color(2000, 2000, 2000), new Point3D(-8.19, -5.44, 2), new Vector(-1.4,3.73,5)));

        ImageWriter imageWriter = new ImageWriter("OurImageScene", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(1);
        Render render = new Render();
                render.setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS);

        render.renderImage();
        render.writeToImage();
    }

    @Test
    public void ImageTestAdaptiveSuperSampling() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/
        scene.geometries.add(
                //  /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.38)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(-0.24,-0.32,3.01),new Point3D(2,-4,-1.36), new Point3D(-2.52,2.35,-1.36))
                        .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)),
                        new Sphere(2, new Point3D(-3.22,0.67,2.34)).setEmmission(new Color(255,0,0)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)));
//                /*34*/ new Plane(new Point3D(-2, -2, -0.82), new Point3D(1, -2, -0.82), new Point3D(1, 0.5, -0.82))
        //    .setEmmission(new Color(java.awt.Color.black)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
        scene.lights.add(new SpotLight(new Color(219, 0, 115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11, -1.32, 0.56))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));
        scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1)));
        scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                .setKl(4E-5).setKq(2E-7));
        ImageWriter imageWriter = new ImageWriter("ImageTestAdaptiveSuperSampling", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(1);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS)
                .setMultithreading(3).setDebugPrint();
                render.setLevel_adaptive_supersampling(10);


        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void OurImageImprovementWithThreads() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        //Camera camera1 = new Camera(new Point3D(-0.21,-0.65,2.19), new Vector(0, 0, -0.29), new Vector(0, 1, 0)).setViewPlaneSize(200, 200).setDistance(300);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/  scene.geometries.add(( new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-1,-1,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5))),
                /*2*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*3*/new Polygon(new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*4*/           new Polygon(new Point3D(-0.6,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*5*/ new Polygon(new Point3D(0,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-0.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*6*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-1,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*7*/ new Polygon(new Point3D(0.4,-1,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,-1.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*8*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*9*/ new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.8,-1.2,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*10*/ new Polygon(new Point3D(-0.8,-1.2,0),new Point3D(-1,-1,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*11*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.72,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*12*/ new Polygon(new Point3D(-1,-0.72,0),new Point3D(-1,-0.4,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*13*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-1,-0.4,0),new Point3D(-0.8,-0.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*14*/ new Polygon(new Point3D(-0.8,-0.2,0),new Point3D(-0.6,0,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*15*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.6,0,0),new Point3D(-0.31,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*16*/ new Polygon(new Point3D(-0.31,0,0),new Point3D(0,0,0),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*17*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0,0,0),new Point3D(0.17,-0.17,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*18*/ new Polygon(new Point3D(0.17,-0.17,0),new Point3D(0.4,-0.4,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*19*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(0.4,-0.72,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*20*/ new Polygon(new Point3D(0.4,-0.72,0),new Point3D(0.4,-1,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*21*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.4,-1,0),new Point3D(0.2,-1.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*22*/ new Polygon(new Point3D(0.2,-1.2,0),new Point3D(0,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*23*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.33,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*24*/ new Polygon(new Point3D(-0.33,-1.4,0),new Point3D(-0.6,-1.4,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*25*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*26*/ new Polygon(new Point3D(0.1,-0.69,0.22),new Point3D(0.23,-0.93,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*27*/ new Polygon(new Point3D(0,-1,0.22),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*28*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.63,-0.99,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*29*/ new Polygon(new Point3D(-0.63,-0.99,0.22),new Point3D(-0.86,-0.94,0.15),new Point3D(-0.72,-0.7,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*30*/ new Polygon(new Point3D(-0.72,-0.7,0.22),new Point3D(-0.81,-0.48,0.15),new Point3D(-0.61,-0.36,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*31*/ new Polygon(new Point3D(-0.61,-0.36,0.22),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*32*/ new Polygon(new Point3D(-0.29,-0.31,0.22),new Point3D(-0.06,-0.17,0.15),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*33*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.61,-0.36,0.22),
                        new Point3D(-0.29,-0.31,0.22),new Point3D(0,-0.4,0.22),new Point3D(0.1,-0.69,0.22),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254
                )).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*34*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*35*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.8,-1.2,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*36*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*37*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-1,-0.72,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*38*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.61,-0.36,0.22),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*39*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.8,-0.2,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*40*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.06,-0.17,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*41*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.31,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*42*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0,-0.4,0.22),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*43*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0.17,-0.17,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*44*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*45*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.4,-0.72,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*46*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*47*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.2,-1.2,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*48*/ new Polygon(new Point3D(-0.07,-1.23,0.15),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*49*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.33,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                new Polygon(new Point3D(1.29,-1.64,2),new Point3D(1.5,-0.3,2),new Point3D(1.5,-0.3,1.97)).setEmmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKt(0.5)),
                /*34*/ new Plane(new Point3D(-2,-2,-0.82),new Point3D(1,-2,-0.82),new Point3D(1,0.5,-0.82)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(1)),

        Color red =new Color(java.awt.Color.red);
        red=red.add(new Color(java.awt.Color.blue));
//           new Sphere(0.3, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.pink)), // kt transparent =1 kr=1 mirroir
//                new Sphere(0.8, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.blue)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
        /*1*/  scene.geometries.add( new Sphere(0.2, new Point3D(1.73,-1.86,0.28)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.5)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// mirroir qui donne orange
                /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// rouge sur le cote gauche
                /*3*/new Sphere(0.03, new Point3D(-0.49,-0.26,0.5)).setEmmission(new Color(java.awt.Color.red)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*4*/new Sphere(0.05, new Point3D(0.16,0.48,0)).setEmmission(new Color(java.awt.Color.cyan)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*5*/new Sphere(0.4, new Point3D(-0.79,0,1.11)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1).setKr(0)),
                /*6*/new Sphere(0.09, new Point3D(1.16,-1.11,0.5)).setEmmission(new Color(255,127,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),//boule orange
                /*13*/new Sphere(0.25, new Point3D(1.88,-1.61,1.3)).setEmmission(new Color(16,52,166)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0.35)),
                /*7*/new Sphere(0.04, new Point3D(-1.13,0.79,1.18)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.18,-0.79,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-0.41,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.52,-0.67,1.13)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.8,-1.26,2.25)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.8,-0.96,1.62)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.81,0.29,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,-2.01,1.41)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.72,0.71,1.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.18,0.82,1.81)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.68,-0.2,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0.77,1.74,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-0.49,1.23,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.02,1.4,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.27,1.74,1.94)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.98,1.34)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.57,1.2,1.73)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.5,1,0)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.96,2.27,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.36,1.59,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(0,2,3.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.79,0.17,0.7)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-3.21,0.58,1)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.76,-2.36,1.22)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-1.98,0.68,0.83)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(3.35,0.4,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.76,1.2,3.64)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-2.53,-0.36,1.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(2.51,0.91,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(4.13,2.24,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(-5.56,-1.03,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(1.35,0.15,2.59)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
                /*8*/new Sphere(0.01, new Point3D(6.38,3.02,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//
                /*13*/new Sphere(0.8, new Point3D(-1.89,-2.87,1.64)).setEmmission(new Color(255,203,96)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0)),
                /*14*/new Sphere(0.15, new Point3D(-0.79,0,1.11)).setEmmission(new Color(249,66,158)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.9).setKr(0)));
//                /*15*/new Sphere(0.3, new Point3D(-13.59,-8.65,-15)).setEmmission(new Color(java.awt.Color.gray)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
//
        boolean turnon4=true;
        if(turnon4){
            scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                    .setKl(4E-5).setKq(2E-7));}// le rose sur le plane
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(-0.33, -0.77, -0.3)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight( new Color(400, 750, 500), new Point3D(0.13, -1.03, 1.16)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
        // scene.lights.add(new SpotLight(new Color(400, 750, 500),new Point3D(2.33,-0.63,0.68), new Vector(-0.43,0,-0.04))  .setKl(4E-5).setKq(2E-7)); // new Vector(0.19, -2.56, 5)
        boolean turnon3=true;
        if(turnon3){
            scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11,-1.32,0.56))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001)); }// new Vector(0.19, -2.56, 5)
        boolean turnon1=true;
        if(turnon1) {
            scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1))); // lumiere bleu sur plane
        }
        boolean turnon2=true;
        if(turnon2) {
            scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                    .setKl(4E-5).setKq(2E-7));// le bleute sur le devant du plane
        }
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-0.6,-1.4,0))//lumiere au fond plane blanche
//                .setKl(0.00001).setKq(0.000001)); // new Vector(0.19, -2.56, 5)
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(1.5,-0.3,1.98))//
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(155, 155, 155), new Point3D(-0.61,-0.36,00.22))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));

        //    .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(254,191,210), new Point3D(1,0.1,1.05)) // new Vector(0.19, -2.56, 5)
                .setKl(4E-5).setKq(2E-7));
        //  scene.lights.add( //
//                new SpotLight(new Color(400, 240, 0), new Point3D(-8, 5, 0), new Vector(0, -6, 0)) //
////                        .setKl(1E-5).setKq(1.5E-7));
//        scene.lights.add(new SpotLight(new Color(2000, 2000, 2000), new Point3D(-8.19, -5.44, 2), new Vector(-1.4,3.73,5)));

        ImageWriter imageWriter = new ImageWriter("OurImageImprovementWithThreads", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(10);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS)
                .setMultithreading(3).setDebugPrint();

        render.renderImage();
        render.writeToImage();
    }


    @Test
    public void OurImageImprovementAdaptiveSupersampling() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        //Camera camera1 = new Camera(new Point3D(-0.21,-0.65,2.19), new Vector(0, 0, -0.29), new Vector(0, 1, 0)).setViewPlaneSize(200, 200).setDistance(300);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/  scene.geometries.add(( new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-1,-1,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5))),
                /*2*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*3*/new Polygon(new Point3D(-1,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*4*/           new Polygon(new Point3D(-0.6,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*5*/ new Polygon(new Point3D(0,0,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-0.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*6*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0.4,-1,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*7*/ new Polygon(new Point3D(0.4,-1,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(0,-1.4,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*8*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.25,-0.71,-0.82),new Point3D(-0.6,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*9*/ new Polygon(new Point3D(-0.6,-1.4,0),new Point3D(-0.8,-1.2,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*10*/ new Polygon(new Point3D(-0.8,-1.2,0),new Point3D(-1,-1,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*11*/ new Polygon(new Point3D(-1,-1,0),new Point3D(-1,-0.72,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*12*/ new Polygon(new Point3D(-1,-0.72,0),new Point3D(-1,-0.4,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*13*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-1,-0.4,0),new Point3D(-0.8,-0.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*14*/ new Polygon(new Point3D(-0.8,-0.2,0),new Point3D(-0.6,0,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*15*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.6,0,0),new Point3D(-0.31,0,0)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*16*/ new Polygon(new Point3D(-0.31,0,0),new Point3D(0,0,0),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*17*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0,0,0),new Point3D(0.17,-0.17,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*18*/ new Polygon(new Point3D(0.17,-0.17,0),new Point3D(0.4,-0.4,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*19*/ new Polygon(new Point3D(0.4,-0.4,0),new Point3D(0.4,-0.72,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*20*/ new Polygon(new Point3D(0.4,-0.72,0),new Point3D(0.4,-1,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*21*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.4,-1,0),new Point3D(0.2,-1.2,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*22*/ new Polygon(new Point3D(0.2,-1.2,0),new Point3D(0,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*23*/ new Polygon(new Point3D(0,-1.4,0),new Point3D(-0.33,-1.4,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*24*/ new Polygon(new Point3D(-0.33,-1.4,0),new Point3D(-0.6,-1.4,0),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*25*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*26*/ new Polygon(new Point3D(0.1,-0.69,0.22),new Point3D(0.23,-0.93,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*27*/ new Polygon(new Point3D(0,-1,0.22),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*28*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.63,-0.99,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*29*/ new Polygon(new Point3D(-0.63,-0.99,0.22),new Point3D(-0.86,-0.94,0.15),new Point3D(-0.72,-0.7,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*30*/ new Polygon(new Point3D(-0.72,-0.7,0.22),new Point3D(-0.81,-0.48,0.15),new Point3D(-0.61,-0.36,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*31*/ new Polygon(new Point3D(-0.61,-0.36,0.22),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*32*/ new Polygon(new Point3D(-0.29,-0.31,0.22),new Point3D(-0.06,-0.17,0.15),new Point3D(0,-0.4,0.22)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*33*/ new Polygon(new Point3D(-0.3,-1.1,0.22),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.61,-0.36,0.22),
                        new Point3D(-0.29,-0.31,0.22),new Point3D(0,-0.4,0.22),new Point3D(0.1,-0.69,0.22),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254
                )).setMaterial(new Material().setKd(1).setKs(1).setShininess(30).setKt(0.5)),
                /*34*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-0.63,-0.99,0.22),new Point3D(-0.51,-1.22,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*35*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.8,-1.2,0),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*36*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.72,-0.7,0.22),new Point3D(-0.86,-0.94,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*37*/ new Polygon(new Point3D(-0.86,-0.94,0.15),new Point3D(-1,-0.72,0),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*38*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.61,-0.36,0.22),new Point3D(-0.81,-0.48,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*39*/ new Polygon(new Point3D(-0.81,-0.48,0.15),new Point3D(-0.8,-0.2,0),new Point3D(-0.52,-0.16,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*40*/ new Polygon(new Point3D(-0.52,-0.16,0.15),new Point3D(-0.06,-0.17,0.15),new Point3D(-0.29,-0.31,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*41*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(-0.52,-0.16,0.15),new Point3D(-0.31,0,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*42*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0,-0.4,0.22),new Point3D(-0.06,-0.17,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*43*/ new Polygon(new Point3D(-0.06,-0.17,0.15),new Point3D(0.17,-0.17,0),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(116,208,241)).setMaterial(new Material().setKt(0.5)),
                /*44*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.1,-0.69,0.22),new Point3D(0.27,-0.47,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*45*/ new Polygon(new Point3D(0.27,-0.47,0.15),new Point3D(0.4,-0.72,0),new Point3D(0.23,-0.93,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*46*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(0,-1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*47*/ new Polygon(new Point3D(0.23,-0.93,0.15),new Point3D(0.2,-1.2,0),new Point3D(-0.07,-1.23,0.15)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*48*/ new Polygon(new Point3D(-0.07,-1.23,0.15),new Point3D(-0.51,-1.22,0.15),new Point3D(-0.3,-1.1,0.22)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                /*49*/ new Polygon(new Point3D(-0.51,-1.22,0.15),new Point3D(-0.07,-1.23,0.15),new Point3D(-0.33,-1.4,0)).setEmmission(new Color(169,234,254)).setMaterial(new Material().setKt(0.5)),
                new Polygon(new Point3D(1.29,-1.64,2),new Point3D(1.5,-0.3,2),new Point3D(1.5,-0.3,1.97)).setEmmission(new Color(java.awt.Color.white)).setMaterial(new Material().setKt(0.5)),
               // new Polygon(new Point3D(10,0,5),new Point3D(4,-10,5),new Point3D(4,-10,-5),new Point3D(10,0,-5)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
                /*34*/ new Plane(new Point3D(-2,-2,-1),new Point3D(1,-2,-1),new Point3D(1,0.5,-1)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)));
//                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(1)),

        Color red =new Color(java.awt.Color.red);
        red=red.add(new Color(java.awt.Color.blue));
    //       new Sphere(0.3, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.pink)), // kt transparent =1 kr=1 mirroir
//                new Sphere(0.8, new Point3D(-4.01,-1.67,0)).setEmmission(new Color(java.awt.Color.blue)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
        /*1*/  scene.geometries.add( new Sphere(0.2, new Point3D(1.73,-1.86,0.28)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0)),
                /*2*/new Sphere(0.25, new Point3D(-0.75,-1.78,-0.5)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// mirroir qui donne orange
                /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),// rouge sur le cote gauche
//                /*3*/new Sphere(0.03, new Point3D(-0.49,-0.26,0.5)).setEmmission(new Color(java.awt.Color.red)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
//                /*4*/new Sphere(0.05, new Point3D(0.16,0.48,0)).setEmmission(new Color(java.awt.Color.cyan)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
                /*5*/new Sphere(0.4, new Point3D(-0.79,0,1.11)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1).setKr(0)),
//                /*6*/new Sphere(0.09, new Point3D(1.16,-1.11,1.5)).setEmmission(new Color(255,127,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),//boule orange
//                /*13*/new Sphere(0.25, new Point3D(1.88,-1.61,1.3)).setEmmission(new Color(16,52,166)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0.35)),
//                /*7*/new Sphere(0.04, new Point3D(-1.13,0.79,1.18)).setEmmission(new Color(204,85,0)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.4).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.18,-0.79,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.36,-0.17,1.11)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.36,-0.41,0.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(2.52,-0.67,1.13)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(0.8,-1.26,2.25)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(-1.8,-0.96,1.62)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(2.81,0.29,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.36,-2.01,1.41)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.72,0.71,1.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(2.18,0.82,1.81)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(2.68,-0.2,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(0.77,1.74,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(-0.49,1.23,2.75)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.02,1.4,1.5)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.27,1.74,1.94)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.76,1.98,1.34)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.57,1.2,1.73)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.5,1,0)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.96,2.27,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.36,1.59,2)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(0,2,3.3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.79,0.17,0.7)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(-3.21,0.58,1)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(2.76,-2.36,1.22)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(-1.98,0.68,0.83)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(3.35,0.4,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.76,1.2,3.64)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(-2.53,-0.36,1.89)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(2.51,0.91,3)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(4.13,2.24,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(-5.56,-1.03,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(1.35,0.15,2.59)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
//                /*8*/new Sphere(0.01, new Point3D(6.38,3.02,3.84)).setEmmission(new Color(java.awt.Color.white)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(1000).setKt(0.1).setKr(0.2)),
////
//                /*13*/new Sphere(0.8, new Point3D(-1.89,-2.87,1.64)).setEmmission(new Color(255,203,96)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0).setKr(0)),
                /*14*/new Sphere(0.15, new Point3D(-0.79,0,1.11)).setEmmission(new Color(249,66,158)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.9).setKr(0)));
////                /*15*/new Sphere(0.3, new Point3D(-13.59,-8.65,-15)).setEmmission(new Color(java.awt.Color.gray)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(1).setKr(0.95)),
//
        boolean turnon4=true;
        if(turnon4){
            scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                    .setKl(4E-5).setKq(2E-7));}// le rose sur le plane
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(-0.33, -0.77, -0.3)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight( new Color(400, 750, 500), new Point3D(0.13, -1.03, 1.16)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
        // scene.lights.add(new SpotLight(new Color(400, 750, 500),new Point3D(2.33,-0.63,0.68), new Vector(-0.43,0,-0.04))  .setKl(4E-5).setKq(2E-7)); // new Vector(0.19, -2.56, 5)
        boolean turnon3=true;
        if(turnon3){
            scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11,-1.32,0.56))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001)); }// new Vector(0.19, -2.56, 5)
        boolean turnon1=true;
        if(turnon1) {
            scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1))); // lumiere bleu sur plane
        }
        boolean turnon2=true;
        if(turnon2) {
            scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                    .setKl(4E-5).setKq(2E-7));// le bleute sur le devant du plane
        }
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-0.6,-1.4,0))//lumiere au fond plane blanche
//                .setKl(0.00001).setKq(0.000001)); // new Vector(0.19, -2.56, 5)
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(1.5,-0.3,1.98))//
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(155, 155, 155), new Point3D(-0.61,-0.36,00.22))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));

        //    .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(254,191,210), new Point3D(1,0.1,1.05)) // new Vector(0.19, -2.56, 5)
                .setKl(4E-5).setKq(2E-7));
        //  scene.lights.add( //
//                new SpotLight(new Color(400, 240, 0), new Point3D(-8, 5, 0), new Vector(0, -6, 0)) //
////                        .setKl(1E-5).setKq(1.5E-7));
//        scene.lights.add(new SpotLight(new Color(2000, 2000, 2000), new Point3D(-8.19, -5.44, 2), new Vector(-1.4,3.73,5)));

        ImageWriter imageWriter = new ImageWriter("OurImageImprovementAdaptiveSupersampling", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(10);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS);
               // .setMultithreading(3).setDebugPrint();
      //  render.setLevel_adaptive_supersampling(10);

        render.renderImage();
        render.writeToImage();
    }
    public void ImageTestScene2() {
//        Camera camera = new Camera(new Point3D(-8, -8, 100), new Vector(0, 0, -10), new Vector(0, 1, 0)) //
//                .setViewPlaneSize(200, 200).setDistance(1000);
        Camera camera = new Camera(new Point3D(-7.68, -1.97, 0), new Vector(1.1, 0.2, 0), new Vector(0, 0, 1)) //
                .setViewPlaneSize(200, 200).setDistance(350);
        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.black), 0.15));
        /*1*/
        scene.geometries.add(
                //  /*14*/new Sphere(0.3, new Point3D(-1.29,0.8,0.23)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                /*2*/new Sphere(0.4, new Point3D(6.76,6.95,0)).setEmmission(new Color(java.awt.Color.black)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(1).setKr(0)),
                /*14*/new Sphere(0.15, new Point3D(6.76,6.95,0)).setEmmission(new Color(249,66,158)) .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.9).setKr(0)),
                new Polygon(new Point3D(6,-6,-3),new Point3D(5.28,-3.92,0),new Point3D(6,-2,-3)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(6,-2,-3),new Point3D(5.28,-3.92,0),new Point3D(3,-2,-3)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(3,-2,-3),new Point3D(5.28,-3.92,0),new Point3D(3,-6,-3)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(3,-6,-3),new Point3D(5.28,-3.92,0),new Point3D(6,-6,-3)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(3,-6,-3),new Point3D(3,-2,-3),new Point3D(6,-2,-3),new Point3D(6,-6,-3)).setEmmission(new Color(217,1,21)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(100).setKt(0.5).setKr(0.3)),
                new Polygon(new Point3D(3.75,14.23,-4),new Point3D(16.7,2,-4),new Point3D(12.57,1.59,10)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(1)),
                new Polygon(new Point3D(12.57,1.59,10),new Point3D(2.87,-14.39,-4),new Point3D(14.7,2,-4)).setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0).setKr(0)));
        boolean turnon4=true;
        if(turnon4){
            scene.lights.add(new SpotLight(new Color(219,0,115), new Point3D(-1.38, 0.71, 1.34), new Vector(0.46, -0.46, -0.62)) //
                    .setKl(4E-5).setKq(2E-7));}// le rose sur le plane
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(-0.33, -0.77, -0.3)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
//        scene.lights.add(new PointLight( new Color(400, 750, 500), new Point3D(0.13, -1.03, 1.16)) // new Vector(0.19, -2.56, 5)
//                .setKl(4E-5).setKq(2E-7));
        // scene.lights.add(new SpotLight(new Color(400, 750, 500),new Point3D(2.33,-0.63,0.68), new Vector(-0.43,0,-0.04))  .setKl(4E-5).setKq(2E-7)); // new Vector(0.19, -2.56, 5)
        boolean turnon3=true;
        if(turnon3){
            scene.lights.add(new PointLight(new Color(96, 80, 220), new Point3D(0.11,-1.32,0.56))//lumiere au fond plane blanche
                    .setKl(0.00001).setKq(0.000001)); }// new Vector(0.19, -2.56, 5)
        boolean turnon1=true;
        if(turnon1) {
            scene.lights.add(new DirectionalLight(new Color(75, 0, 130), new Vector(0, 0, -1))); // lumiere bleu sur plane
        }
        boolean turnon2=true;
        if(turnon2) {
            scene.lights.add(new SpotLight(new Color(0, 0, 400), new Point3D(1.77, -1.44, 1.77), new Vector(-0.18, 0.17, -0.33)) //
                    .setKl(4E-5).setKq(2E-7));// le bleute sur le devant du plane
        }
//        scene.lights.add(new PointLight(new Color(255, 255, 255), new Point3D(-0.6,-1.4,0))//lumiere au fond plane blanche
//                .setKl(0.00001).setKq(0.000001)); // new Vector(0.19, -2.56, 5)
//        scene.lights.add(new PointLight(new Color(400, 750, 500), new Point3D(1.5,-0.3,1.98))//
//                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(155, 155, 155), new Point3D(-0.61,-0.36,00.22))//lumiere au fond plane blanche
                .setKl(0.00001).setKq(0.000001));

        //    .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(254,191,210), new Point3D(1,0.1,1.05)) // new Vector(0.19, -2.56, 5)
                .setKl(4E-5).setKq(2E-7));
        //  scene.lights.add( //
//                new SpotLight(new Color(400, 240, 0), new Point3D(-8, 5, 0), new Vector(0, -6, 0)) //
////                        .setKl(1E-5).setKq(1.5E-7));
//        scene.lights.add(new SpotLight(new Color(2000, 2000, 2000), new Point3D(-8.19, -5.44, 2), new Vector(-1.4,3.73,5)));

        ImageWriter imageWriter = new ImageWriter("OurImageScene2", 600, 600);
        BasicRayTracer BS=new BasicRayTracer(scene);
        BS.setSampleCount(10);
        Render render = new Render() //
                .setImageWriter(imageWriter) //
                .setCamera(camera) //
                .setRayTracer(BS);
        // .setMultithreading(3).setDebugPrint();
        //  render.setLevel_adaptive_supersampling(10);

        render.renderImage();
        render.writeToImage();
    }

}
