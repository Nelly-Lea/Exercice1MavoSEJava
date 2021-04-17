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
        if(_vTo.dotProduct(_vUp)==0){
            throw new IllegalArgumentException("vUp is not orthogonal to vTo ");
        }
        _vRight=_vTo.crossProduct(_vUp);
    }

    /**
     * borrowing from Builder pattern
     * @param width
     * @param height
     * @return
     */
    public Camera setViewPlaneSize(double width, double height){
        _width=width;
        _height=height;
        return this;
    }

    public Camera setDistance(double distance){
        _distance=distance;
        return this;
    }

    public Ray constructRayThroughPixel(int nX, int nY, int j, int i){
      Point3D Pc=_p0.add(_vTo.scale(_distance));

      double Ry=_height/nY;
      double Rx=_width/nX;

      double Yi=-(i-(nY-1)/2)*Ry;
      double Xj=-(j-(nX-1)/2)*Rx;

      Point3D Pij;
      if(isZero(Yi) && isZero(Xj)){
          return new Ray(_p0,Pc.subtract(_p0));
      }

      Vector deltaX=_vRight.scale(Xj);
      Vector deltaY=_vUp.scale(Yi);

      Pij=Pc.add(deltaX.add(deltaY));
      Vector Vij=Pij.subtract(_p0);

      return new Ray(_p0,Vij);


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
