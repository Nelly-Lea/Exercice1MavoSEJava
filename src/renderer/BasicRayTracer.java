package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static geometries.Intersectable.GeoPoint;
import static primitives.Util.alignZero;

public class BasicRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 10;

    private static final double MIN_CALC_COLOR_K = 0.001;


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

//    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
//        Color color = intersection.geometry.getEmmission();
//
//        color = color.add(calcLocalEffects(intersection, ray));
//        return 1 == level ? color : color.add(calcGlobalEffects(intersection, ray, level, k));
//    }
//
//    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k)
//    {
//        Color color = Color.BLACK;
//        Vector n = gp.geometry.getNormal(gp.point);
//        Material material = gp.geometry.getMaterial();
//        double kkr = k * material.Kr;
//        if (kkr > MIN_CALC_COLOR_K)
//            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.Kr, kkr);
//        double kkt = k * material.Kt;
//        if (kkt > MIN_CALC_COLOR_K)
//            color = color.add(
//                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.Kt, kkt));
//        return color;
//    }
//
//    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {
//        return new Ray(point,v);
//    }
//
//    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
//        Vector vn=v.crossProduct(n);
//        Vector vnn=vn.crossProduct(n);
//        return new Ray(point, v.subtract(vnn.scale(2)));
//    }

//    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
//        List<GeoPoint> listgp= _scene.geometries.findGeoIntersections(ray);
//        GeoPoint gp = ray.findClosestGeoPoint(listgp);
//        return (gp == null ? _scene.background : calcColor(gp, ray, level-1, kkx)).scale(kx);
//    }


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
                if (unshaded(lightSource, l, n, gpoint)) {
                    Color lightIntensity = lightSource.getIntensity(gpoint.point);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                    color = color.add(gpoint.geometry.getEmmission());

                }
            }

        }
        return color;
    }

    //LightSource lightsource)
private boolean unshaded(LightSource lightSource,Vector l,Vector n, GeoPoint geoPoint)
        {
            Vector lightDirection =l.scale(-1);//from point to light
            Ray lightRay=new Ray(geoPoint.point,n,lightDirection);
            List<GeoPoint>intersections=_scene.geometries
                    .findGeoIntersections(lightRay,lightSource.getDistance(geoPoint.point));
            return intersections==null;
        }

        /**
         * this function calculates a part of Phong formula
         * @param kd
         * @param l
         * @param n
         * @param lightIntensity
         * @return the result:  kd*|l*n|*Il
         */

        private Color calcDiffusive ( double kd, Vector l, Vector n, Color lightIntensity){

            double ln = Math.abs(l.dotProduct(n));
            return lightIntensity.scale(kd * ln);// kd*|l*n|*Il

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

        private Color calcSpecular ( double ks, Vector l, Vector n, Vector v,int nShininess, Color lightIntensity){
            double ln = l.dotProduct(n);
            Vector r = l.subtract(n.scale(ln * 2)).normalized();////
            v.normalized();////
            double minusVR = v.scale(-1).dotProduct(r);
            return lightIntensity.scale(ks * Math.max(0, Math.pow(minusVR, nShininess)));//ks*(max(0,-v*r))^nShininess*Il

        }


    }
