package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;

public class Camera {
    final Point3D _p0;
    final Vector _vTo;
    final Vector _vUp;
    final Vector _vRight;

    private double _distance;
    private double _width;
    private double _height;

    public Camera(Point3D p0, Vector vTo, Vector vUp) {
        _p0 = p0;
        _vTo = vTo.normalized();
        _vUp = vUp.normalized();
        if (!isZero(_vTo.dotProduct(_vUp))) {
            throw new IllegalArgumentException("vUp is not orthogonal to vTo ");
        }
        _vRight = _vTo.crossProduct(_vUp);
    }

    /**
     * borrowing from Builder pattern
     *
     * @param width
     * @param height
     * @return
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }


    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {
//        double rX=vpWidth/nX;
//        double rY=vpHeight/nY;
//        double xJ=(j-(nX-1)/2d)*rX;
//        double yIminus=(i-(nY-1)/2d)*rY;
//
//        Point3D pIJ=_p0.add(_vTo.scale(vpDistance))
        Point3D Pc = _p0.add(_vTo.scale(_distance));

        double Ry = _height / nY;
        double Rx = _width / nX;

        double Yi = -(i - (nY - 1) / 2d) * Ry;
        double Xj = (j - (nX - 1) / 2d) * Rx;

        Point3D Pij = Pc;
//      if(Xj!=0){
//          Pij=Pij.add(_vRight.scale(Xj));
//      }
//      if(Yi!=0){
//          Pij=Pij.add(_vUp.scale(Yi));
//      }

        if (isZero(Yi) && isZero(Xj)) {
            return new Ray(_p0, Pij.subtract(_p0));
        }

        if (isZero(Xj)) {
            Pij = Pc.add(_vUp.scale(Yi));
            return new Ray(_p0, Pij.subtract((_p0)));
        }

        if (isZero(Yi)) {
            Pij = Pc.add(_vRight.scale(Xj));
            return new Ray(_p0, Pij.subtract((_p0)));
        }

        Pij = Pc.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));
        return new Ray(_p0, Pij.subtract(_p0));


    }

    public Point3D getP0() {
        return _p0;
    }

    public Vector getvTo() {
        return _vTo;
    }

    public Vector getvUp() {
        return _vUp;
    }

    public Vector getvRight() {
        return _vRight;
    }
}
