package renderer;

import elements.LightSource;
import primitives.Color;
import primitives.Material;
import primitives.Ray;
import primitives.Vector;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {
    /**
     * constructor calls father's constructor
     *
     * @param scene
     */
    public BasicRayTracer(Scene scene) {
        super(scene);
    }

    /**
     * traceRay receives a ray return a color for pixel. If there are intersections with the ray
     * calculs the color of the closest point else it return the background color
     *
     * @param ray
     * @return color of pixel
     */
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);

        if (intersections != null) {
            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
            return calcColor(closestPoint, ray);
        }
        return _scene.background;
    }

    public Color calcColor(GeoPoint Gpoint, Ray ray) {
        //return  _scene.ambientLight.getIntensity().add(point.geometry.getEmmission());
        return _scene.ambientLight.getIntensity()
                .add(Gpoint.geometry.getEmmission())
                //add calculated light contribution from all light sources
                .add(calcLocalEffects(Gpoint, ray));

    }

    private Color calcLocalEffects(GeoPoint gpoint, Ray ray) {
        Vector v = ray.get_dir();
        Vector n = gpoint.geometry.getNormal(gpoint.point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0) {
            return Color.BLACK;
        }
        Material material = gpoint.geometry.getMaterial();
        int nShininess = material.nShininess;
        double kd = material.Kd;
        double ks = material.Ks;
        Color color = Color.BLACK;
        for (LightSource lightSource : _scene.lights) {
            Vector l = lightSource.getL(gpoint.point);
            double nl = alignZero(n.dotProduct(l));
            if (nl * nv > 0) { // sign(nl) == sing(nv)
                Color lightIntensity = lightSource.getIntensity(gpoint.point);
                color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                        calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                color=color.add(gpoint.geometry.getEmmission());

            }
        }
        return color;
    }


    /**
     * this function calculates a part of Phong formula
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return the result:  kd*|l*n|*Il
     */

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {

        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd*ln);// kd*|l*n|*Il

    }

    /**
     * this function calculates a part of Phong formula
     * @param ks
     * @param l
     * @param n
     * @param v
     * @param nShininess
     * @param lightIntensity
     * @return the result: ks*(max(0,-v*r))^nShininess*Il
     */

    private Color calcSpecular(double ks, Vector l, Vector n, Vector v, int nShininess, Color lightIntensity) {
        double ln = l.dotProduct(n);
        Vector r = l.subtract(n.scale(ln * 2));
        double minusVR = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.max(0, Math.pow(minusVR, nShininess)));//ks*(max(0,-v*r))^nShininess*Il
    }


}
