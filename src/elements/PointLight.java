package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Vector;

/**
 * this class inherited of Light and contain coefficients Kc, Kl, Kq and the position of PointLight
 */
public class PointLight extends Light implements LightSource{

    private final Point3D _position;
    private double _Kc=1d;
    private double _Kl=0d;
    private double _Kq=0d;

////    public double getKc() {
//        return _Kc;
//    }
//
//    public PointLight setKc(double kc) {
//        _Kc = kc;
//        return this;
//    }
//
//    public double getKl() {
//        return _Kl;
//    }

    public PointLight set_Kc(double Kc) {
        _Kc = _Kc;
        return this;
    }

    public PointLight setKl(double kl) {
        _Kl = kl;
        return this;
    }

    public double getKq() {
        return _Kq;
    }

    public PointLight setKq(double kq) {
        _Kq = kq;
        return this;
    }

    /**
     * constructor of PointLight receives intensity and position, calls LightSource and put the position
     * @param intensity
     * @param position
     */
    public PointLight(Color intensity,Point3D position) {
        super(intensity);
        _position = position;
    }

    /**
     * this function receives a point p and return the color at this point
     * @param p
     * @return Color at the point p
     */
    @Override
    public Color getIntensity(Point3D p) {
        double d=p.distance(_position);
        double attenuation=1d/(_Kc+_Kl*d+_Kq*d*d);// this is the denominator
        return _intensity.scale(attenuation);// I0*(1/kc+k*d+kq*d^2)

    }

    /**
     * this function receives a point p and return the director vector of the PointLight
     * @param p
     * @return Vector l
     */
    @Override
    public Vector getL(Point3D p) {
        return p.subtract(_position).normalized();
    }
    @Override
    public double getDistance(Point3D point)
    {
        return point.distance(_position);
    }
}
