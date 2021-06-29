package renderer;

import elements.LightSource;
import primitives.*;
import scene.Scene;
import geometries.Intersectable;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static geometries.Intersectable.GeoPoint;

import static primitives.Point3D.ZERO;
import static primitives.Util.*;

public class BasicRayTracer extends RayTracerBase {
    private static final double DELTA = 0.1;

    private static final double INITIAL_K = 1.0;
    private static final int MAX_CALC_COLOR_LEVEL = 5;

    private static final double MIN_CALC_COLOR_K = 0.001;

    public int getSampleCount() {
        return SampleCount;
    }

    public void setSampleCount(int sampleCount) {
        SampleCount = sampleCount;
    }

    int SampleCount;


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
//        List<GeoPoint> intersections = _scene.geometries.findGeoIntersections(ray);
//
//        if (intersections != null) {
//            GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
//            return calcColor(closestPoint, ray);
       // }
     //   return _scene.background;
        GeoPoint closestPoint = findClosestIntersection(ray); //GeoPoint is the closest intersection point with the ray
        return closestPoint == null ? _scene.background : calcColor(closestPoint, ray);
    }

//    public Color calcColor(GeoPoint Gpoint, Ray ray) {
//        //return  _scene.ambientLight.getIntensity().add(point.geometry.getEmmission());
//        return _scene.ambientLight.getIntensity()
//                .add(Gpoint.geometry.getEmmission())
//                //add calculated light contribution from all light sources
//                .add(calcLocalEffects(Gpoint, ray));
//
//    }

    /**
     * this function return the color at the given point taking into account all the effect
     * @param intersection given point on the object
     * @param ray
     * @param level
     * @param k
     * @return color at the given point
     */

    private Color calcColor(GeoPoint intersection, Ray ray, int level, double k) {
        Color color = intersection.geometry.getEmmission();

        color = color.add(calcLocalEffects(intersection, ray,k));
        return 1 == level ? color : color.add(calcGlobalEffects2(intersection, ray.get_dir(), level, k));
    }

    /**
     * this function is a help function that call to the other calcColor function and add the ambient light color at the given point
     * @param geopoint on the object
     * @param ray
     * @return color at the given point
     */
    private Color calcColor(GeoPoint geopoint, Ray ray) {
        return calcColor(geopoint, ray, MAX_CALC_COLOR_LEVEL, INITIAL_K)
                .add(_scene.ambientLight.getIntensity());
    }





    private Color calcGlobalEffects(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.Kr;
        if (kkr > MIN_CALC_COLOR_K) {

            color = calcGlobalEffect(constructReflectedRay(gp.point, v, n), level, material.Kr, kkr);
        }
        double kkt = k * material.Kt;
        if (kkt > MIN_CALC_COLOR_K)
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(gp.point, v, n), level, material.Kt, kkt));
        return color;
    }

    /**
     * this function return the refracted ray
     * @param point on the object
     * @param v vector direction of the light
     * @param n the normal
     * @return refratted ray
     */
    private Ray constructRefractedRay(Point3D point, Vector v, Vector n) {

        if(alignZero(v.dotProduct(n))==0)
            return new Ray(point,v);
        return new Ray(point,n, v);
    }

    /**
     * this function calculates the reflected ray
     * @param point on the object
     * @param v vector direction of the light
     * @param n the normal
     * @return reflected ray
     */
    private Ray constructReflectedRay(Point3D point, Vector v, Vector n) {
        double vn = v.dotProduct(n);
        Vector vnn = n.scale(vn);
        if(alignZero(v.dotProduct(n))==0)
            return new Ray(point,v);
        return new Ray(point,n, v.subtract(vnn.scale(2)));// return r=2-(v.n).n
    }

    private Color calcGlobalEffect(Ray ray, int level, double kx, double kkx) {
        List<GeoPoint> listgp = _scene.geometries.findGeoIntersections(ray);
        GeoPoint gp = ray.findClosestGeoPoint(listgp);
        return (gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
    }


    private Color calcLocalEffects(GeoPoint gpoint, Ray ray,double k) {
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
                 double ktr = transparency(lightSource, l, n, gpoint);
                if (ktr * k > MIN_CALC_COLOR_K) {

                    Color lightIntensity = lightSource.getIntensity(gpoint.point).scale(ktr);
                    color = color.add(calcDiffusive(kd, l, n, lightIntensity),
                            calcSpecular(ks, l, n, v, nShininess, lightIntensity));
                   // color = color.add(gpoint.geometry.getEmmission());

                }
            }

        }
        return color;
    }

    /**
     * this function return true if the point received is unshaded and return false if the point is shaded
     * @param lightSource
     * @param l
     * @param n
     * @param geoPoint
     * @return a boolean response
     */
    private boolean unshaded(LightSource lightSource, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1);//from point to light
        Ray lightRay = new Ray(geoPoint.point, n, lightDirection);// we create a ray from the point on the object to the light source
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay, lightSource.getDistance(geoPoint.point));

        if (intersections == null) return true;// we check if it exists an object between the point on the object and the light, if not the point is unshaded
        double lightDistance = lightSource.getDistance(geoPoint.point);
        for (GeoPoint gp : intersections) {
            if (alignZero(gp.point.distance(geoPoint.point) - lightDistance) <= 0 &&
                    gp.geometry.getMaterial().Kt == 0)// we check if there is an object1 between the light and the point on the object2 received and  that the object1 is opaque
                return false; // we return false because the point on object2 is shaded
        }
        return true;


    }

    /**
     * this function calculates a part of Phong formula
     *
     * @param kd
     * @param l
     * @param n
     * @param lightIntensity
     * @return the result:  kd*|l*n|*Il
     */

    private Color calcDiffusive(double kd, Vector l, Vector n, Color lightIntensity) {

        double ln = Math.abs(l.dotProduct(n));
        return lightIntensity.scale(kd * ln);// kd*|l*n|*Il

    }

    /**
     * this function calculates a part of Phong formula
     *
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
        Vector r = l.subtract(n.scale(ln * 2)).normalized();////
        v.normalized();////
        double minusVR = v.scale(-1).dotProduct(r);
        return lightIntensity.scale(ks * Math.max(0, Math.pow(minusVR, nShininess)));//ks*(max(0,-v*r))^nShininess*Il

    }

    /**
     * this function return the closest GeoPoint intersection with the ray
     * @param ray
     * @return the closest GeoPoint
     */

    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> listGeoPoints = _scene.geometries.findGeoIntersections(ray);
        if (listGeoPoints != null) {
            GeoPoint GeoP = ray.findClosestGeoPoint(listGeoPoints);
            return GeoP;
        }
        return null;

    }

    /**
     * this function returns the transparency coefficient
     * @param ls
     * @param l
     * @param n
     * @param geoPoint
     * @return kt
     */
    private double transparency(LightSource ls, Vector l, Vector n, GeoPoint geoPoint) {
        Vector lightDirection = l.scale(-1);//from point to light
        Ray lightRay = new Ray(geoPoint.point, n, lightDirection);
        List<GeoPoint> intersections = _scene.geometries
                .findGeoIntersections(lightRay);

        if (intersections == null) //if there is not object between the point and the light source
            return 1.0;  // return 1  if the object is completely transparent in our case the object doesn't exist
        double lightDistance = ls.getDistance(geoPoint.point);
        double ktr=1.0;
        for (GeoPoint geopoint : intersections) {
            if (alignZero(geopoint.point.distance(geoPoint.point) - lightDistance) <= 0)
            {
                ktr *= geopoint.geometry.getMaterial().Kt;
                if (ktr < MIN_CALC_COLOR_K) return 0.0;
            }

        }
        return ktr;


    }

    private Color constructReflectedRay2(Point3D point, Vector v, Vector n) {
        Color color=Color.BLACK;
        List<Ray> ListRay=new LinkedList<Ray>();
        double vn = v.dotProduct(n);
        Vector vnn = n.scale(vn);
        if(alignZero(v.dotProduct(n))==0) {
//            ListRay.add(new Ray(point, v));
//            return ListRay;
            GeoPoint gp=findClosestIntersection(new Ray(point, v));
            if(gp==null)
                color=_scene.background;
            else
                color=calcColor(gp,new Ray(point, v));

        }
        Ray r=new Ray(point,n, v.subtract(vnn.scale(2)));
        ListRay.add(r);


        Vector u=new Vector(-r.get_dir().getHead().getY(),r.get_dir().getHead().getX(),0).normalized();
//        Vector u=n;
//        Vector w=n.crossProduct(v);

        Vector w=new Vector(0,-r.get_dir().getHead().getZ(),r.get_dir().getHead().getY()).normalized();
         //Vector w=r.get_dir().crossProduct(u);
        //u = -a/2 + ε a
        for (int i=0;i<4;i++) {
//            double eps = random(-1, 1);
//            double eps1 = random(-1, 1);
//            double u1 = -1 / 2 + eps * 1;
//            double u2 = -1 / 2 + eps1 * 1;
          double cosTeta=random(-1,1);
          double sinTeta=Math.sqrt(1-cosTeta*cosTeta);
          double d=random(-2,2);
          double x=d*cosTeta;
          double y=d*sinTeta;

            Point3D p0=r.get_p0();
            double distance=r.get_dir().length();
            Point3D pc=p0.add(r.get_dir().scale(distance));
            Point3D pt=pc;
            if(!isZero(x))
                pt=pt.add(u.scale(x));
            if(!isZero(y))
                pt=pt.add(w.scale(y));
            ListRay.add(new Ray(p0,pt.subtract(p0).normalized()));


           // Vector v2=
          //  Vector v2=r.get_dir().add(u.scale(u1)).add(w.scale(u2));

//            if(v2.getHead().equals(ZERO))
//            {
//                eps = random(-1, 1);
//                eps1 = random(-1, 1);
//                u1 = -1 / 2 + eps * 1;
//                 u2 = -1 / 2 + eps1 * 1;
//                 v2=r.get_dir().add(u.scale(u1)).add(w.scale(u2));
//
//            }

//            Ray r2=new Ray(point,v2);
//            ListRay.add(r2);
//            v2=null;
        }
        for(Ray ray:ListRay)
        {
            GeoPoint gp1=findClosestIntersection(ray);
            color=color.add(calcColor(gp1,ray));
        }
        return color.reduce(ListRay.size());
        //return ListRay ;// return r=2-(v.n).n
    }

    private Color calcGlobalEffect2(List<Ray>  ListRays, int level, double kx, double kkx) {
        Color color=new Color(0,0,0);
        for (Ray ray :ListRays)
        {
            if(ray==ListRays.get(0)) {
                for (int i = 0; i < 3; i++) {
                    List<GeoPoint> listgp = _scene.geometries.findGeoIntersections(ray);
                    GeoPoint gp = ray.findClosestGeoPoint(listgp);
                    color.add(gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
                }
            }
            else {
                List<GeoPoint> listgp = _scene.geometries.findGeoIntersections(ray);
                GeoPoint gp = ray.findClosestGeoPoint(listgp);
                color.add(gp == null ? _scene.background : calcColor(gp, ray, level - 1, kkx)).scale(kx);
            }
        }

        return color.reduce(ListRays.size());
    }

    private Color constructRefractedRay2(Point3D point, Vector v, Vector n) {
    Color color=Color.BLACK;
        List<Ray> ListRay=new LinkedList<Ray>();
        if(alignZero(v.dotProduct(n))==0) {
            Ray r= new Ray(point, v);
            ListRay.add(r);
            //return ListRay;
            GeoPoint gp=findClosestIntersection(r);
            color=calcColor(gp,r);

        }
        Ray r= new Ray(point,n, v);

        ListRay.add(r);
//        if((-r.get_dir().getHead().getY()==0)&&(r.get_dir().getHead().getX()==0)) {
//            Color esai=Color.BLACK;
//        }
//
        Vector u=new Vector(-r.get_dir().getHead().getY(),r.get_dir().getHead().getX(),0).normalized();
//
//        if((-r.get_dir().getHead().getZ()==0)&&(r.get_dir().getHead().getY()==0)) {
//            Color esai=Color.BLACK;
//        }
//
       Vector w=new Vector(0,-r.get_dir().getHead().getZ(),r.get_dir().getHead().getY()).normalized();
//        Vector u=n;
//        Vector w=n.crossProduct(v);
        //u = -a/2 + ε a
        for (int i=0;i<4;i++) {
            double cosTeta=random(-1,1);
            double sinTeta=Math.sqrt(1-cosTeta*cosTeta);
            double d=random(-1,1);
            double x=d*cosTeta;
            double y=d*sinTeta;

            Point3D p0=r.get_p0();
            double distance=r.get_dir().length();
            Point3D pc=p0.add(r.get_dir().scale(distance));
            Point3D pt=pc;
            if(!isZero(x))
                pt=pt.add(u.scale(x));
            if(!isZero(y))
                pt=pt.add(w.scale(y));
            //Vector v1=pt.subtract(p0);

            ListRay.add(new Ray(p0,pt.subtract(p0).normalized()));
//            {
//                eps = random(-1, 1);
//                 eps1 = random(-1, 1);
//                 u1 = -1 / 2 + eps * 1;
//                 u2 = -1 / 2 + eps1 * 1;
//                 v2=r.get_dir().add(u.scale(u1)).add(w.scale(u2));
//            }

        }
        for(Ray ray:ListRay)
        {
            GeoPoint gp1=findClosestIntersection(ray);
            color=color.add(calcColor(gp1,ray));
        }
        return color.reduce(ListRay.size());
        //return ListRay ;// return r=2-(v.n).n


    }


    //    if (kkr > MIN_CALC_COLOR_K) {
//        Ray reflectedRay = constructReflectedRay(n, geopoint.point, inRay);
//        color = color.add(calcSampledColor(mat.getMatte(), //
//                level, reflectedRay, n, kkr).scale(kr));
//    }

    private Color calcGlobalEffects2(GeoPoint gp, Vector v, int level, double k) {
        Color color = Color.BLACK;
        Vector n = gp.geometry.getNormal(gp.point);
        Material material = gp.geometry.getMaterial();
        double kkr = k * material.Kr;
        if (kkr > MIN_CALC_COLOR_K) {
            Ray relfectray=constructReflectedRay(gp.point, v, n);
            //color = calcG(lobalEffect2(constructReflectedRay2(gp.point, v, n), level, material.Kr, kkr);
         //   color=color.add(constructReflectedRay2(gp.point,relfectray.get_dir(), n)).scale(material.Kr);
            color=color.add(calcSampledColor(0.05, level, relfectray,n, kkr)).scale(material.Kr);
         //   color=color.add(calcSampledColor1(level,relfectray,n,kkr)).scale(material.Kr);

        }
        double kkt = k * material.Kt;
        if (kkt > MIN_CALC_COLOR_K) {
            Ray refractedray=constructRefractedRay(gp.point, v, n);
//            color = color.add(
//                    calcGlobalEffect2(constructRefractedRay2(gp.point, v, n), level, material.Kt, kkt));
            //color=color.add(constructRefractedRay2(gp.point, refractedray.get_dir(),n)).scale(material.Kt);
           color=color.add(calcSampledColor(0.05, level, refractedray,n, kkt)).scale(material.Kt);
           // color=color.add(calcSampledColor1(level,refractedray,n,kkt)).scale(material.Kt);
        }
        return color;
    }

    private static final Random RANDOM=new Random(System.currentTimeMillis());
    private Color calcSampledColor(double radius, int level, Ray refRay, Vector n, double k) {


        Vector v=refRay.get_dir();
        Point3D point=refRay.get_p0();

//        if (radius == 0)
//            return color;


        GeoPoint gp = findClosestIntersection(refRay);



        Color color=Color.BLACK;
        List<Ray> ListRay=new LinkedList<Ray>();
        double ndir=alignZero(v.dotProduct(n));
        if(ndir==0) {

            return color;
//            Ray r= new Ray(point, v);
//            ListRay.add(r);
//            //return ListRay;
//
//            GeoPoint gp=findClosestIntersection(r);
//            if(gp==null)
//                color=color.add(_scene.background);
//            color=calcColor(gp,r);
//            return  color;

        }
        Color bg = _scene.background;
        if(gp==null)
            color=bg;
        else

         color = calcColor(gp, refRay, level - 1, k);
        Ray r= new Ray(point,n, v);
        if(this.getSampleCount()==0)
            return color;

        //ListRay.add(r);
//        if((-r.get_dir().getHead().getY()==0)&&(r.get_dir().getHead().getX()==0)) {
//            Color esai=Color.BLACK;
//        }
        double x = v.getHead().getX();
        double y = v.getHead().getY();
        double z =v.getHead().getZ();

        Vector u = null;
        if (x <= y) {
            if (x <= z)
                u = new Vector(0, z, -y).normalize();
            else
                u = new Vector(-y, x, 0).normalize();
        } else {
            if (y <= z)
                u = new Vector(-z, 0, x).normalize();
            else
                u = new Vector(-y, x, 0).normalize();
        }
//        Vector u = v.crossProduct(dir).normalize();
     //   Vector u=new Vector(-r.get_dir().getHead().getY(),r.get_dir().getHead().getX(),0).normalized();
//
//        if((-r.get_dir().getHead().getZ()==0)&&(r.get_dir().getHead().getY()==0)) {
//            Color esai=Color.BLACK;
//        }
//
        Vector w= u.crossProduct(refRay.get_dir()).normalize();
       // Vector w=new Vector(0,-r.get_dir().getHead().getZ(),r.get_dir().getHead().getY()).normalized();
//        Vector u=n;
//        Vector w=n.crossProduct(v);
        //u = -a/2 + ε a
        for (int i=0;i<this.getSampleCount()-1;i++) {
            double nw;
            Point3D p0;
            Point3D pt;
            Vector w1;
            do {
                double cosTeta =  RANDOM.nextDouble()*2-1;
                double sinTeta = Math.sqrt(1 - cosTeta * cosTeta);
//                double d = random(-0.5, 0.5);
//                double x1 = d * cosTeta;
//                double y1 = d * sinTeta;

                p0 = r.get_p0();
                double distance = r.get_dir().length();
                Point3D pc = p0.add(r.get_dir().scale(distance));
                pt = pc;
                if (!isZero(cosTeta))
                    pt = pt.add(u.scale(cosTeta));
                if (!isZero(sinTeta))
                    pt = pt.add(w.scale(sinTeta));
                //Vector v1=pt.subtract(p0);
                z=radius*(RANDOM.nextDouble()*2-1);
                pt=pc.add(pt.subtract(pc).scale(z));
                nw= alignZero(n.dotProduct(pt.subtract(refRay.get_p0())));

            } while (nw<0&&ndir>0 ||ndir<0&&nw>0);
           // ListRay.add(new Ray(p0,pt.subtract(p0).normalized()));
//            {
//                eps = random(-1, 1);
//                 eps1 = random(-1, 1);
//                 u1 = -1 / 2 + eps * 1;
//                 u2 = -1 / 2 + eps1 * 1;
//                 v2=r.get_dir().add(u.scale(u1)).add(w.scale(u2));
//            }
            Ray ray=new Ray(p0,pt.subtract(p0));
            gp=findClosestIntersection(ray);
            color=color.add(gp==null?bg:calcColor(gp,ray,level-1,k));

        }
//        for(Ray ray:ListRay) {
//            if (ray == ListRay.get(0)) {
//                for (int i = 0; i < 5; i++) {
//                    GeoPoint gp1 = findClosestIntersection(ray);
//                    if (gp1 == null)
//                        color = color.add(_scene.background);
//
//                    else
//                        color = color.add(calcColor(gp1, ray, level - 1, k));
//                }
//            } else {
//                GeoPoint gp1 = findClosestIntersection(ray);
//                if (gp1 == null)
//                    color = color.add(_scene.background);
//
//                else
//                    color = color.add(calcColor(gp1, ray, level - 1, k));
//            }
//        }
        return color.reduce(this.getSampleCount()+1);
        //return ListRay ;// return r=2-(v.n).n

    }

    private Color calcSampledColor1( int level, Ray refRay, Vector n, double k) {
        Vector v = refRay.get_dir();
        Point3D point = refRay.get_p0();
        Color color = Color.BLACK;
        Vector u;
        if (v.getHead().getX() == 0 && v.getHead().getY() == 0) {
            u = new Vector(new Point3D(0, -v.getHead().getZ(), v.getHead().getY()));
        } else
            u = new Vector(new Point3D(-v.getHead().getY(), v.getHead().getX(), 0));
        Vector w = v.crossProduct(u);

        GeoPoint gp = findClosestIntersection(refRay);
        if (gp != null) {
            color = color.add(calcColor(gp, refRay, level - 1, k));


            double distance = point.distance(gp.point);
            //double distance=refRay.get_dir().length();
            Point3D pc = point.add(v.scale(distance));
            Point3D p;
            Vector nouvVec;
            double nouvy;
            double nouvz;
            double y = pc.getY();
            double z = pc.getZ();
            Ray nouvRay;
            double verif;

                for (int i = 0; i < getSampleCount() - 1; i++) {
                    do {
                        double cosTeta = RANDOM.nextDouble() ;
                        double sinTeta = Math.sqrt(1 - cosTeta * cosTeta) ;

                        //double sinTeta=RANDOM.nextDouble();
                        nouvy = y + cosTeta;
                        nouvz = z + sinTeta;
                        nouvVec = u.scale(nouvz).add(w.scale(nouvy));
                        p = pc.add(nouvVec.scale(RANDOM.nextDouble()));
                        nouvRay = new Ray(point, p.subtract(point));
                        GeoPoint gp1 = findClosestIntersection(nouvRay);
                        if (gp1 != null) {
                            color = color.add(calcColor(gp1, nouvRay, level - 1, k));

                        } else
                            color = color.add(_scene.background);
                        //color=color.add(calcColor(gp, nouvRay, level - 1, k));
                        verif = alignZero(n.dotProduct(nouvRay.get_dir()));

                    }
                    while (verif != 0) ;
            }



            return color.reduce(getSampleCount());
//        //return color;
        } else
            return color.add(_scene.background);
    }


}
