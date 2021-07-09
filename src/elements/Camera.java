package elements;

import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;


import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

public class Camera {
    final Point3D _p0; //p0 is the point where is the camera
    final Vector _vTo; //vTo is the vector in the direction of the view plane
    final Vector _vUp; //vUp is the vector in upward direction
    final Vector _vRight; //vRight is the vector towards the right

    private double _distance; //distance between the camera and the view plane
    private double _width; //width of the view plane
    private double _height; //length of the view plane


    /**
     *construct the camera
     * @param p0
     * @param vTo
     * @param vUp
     */
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
     * @return the camera with width and height
     */
    public Camera setViewPlaneSize(double width, double height) {
        _width = width;
        _height = height;
        return this;
    }

    /**
     * setter
     * @param distance
     * @return the camera with the distance of the view plane
     */
    public Camera setDistance(double distance) {
        _distance = distance;
        return this;
    }

    public double get_Distance() {
        return _distance;
    }

    public double get_width() {
        return _width;
    }

    public void set_width(double _width) {
        this._width = _width;
    }

    public double get_height() {
        return _height;
    }

    public void set_height(double _height) {
        this._height = _height;
    }

    /**
     * construct a ray through the center of the pixel
     * @param nX number of the pixel on the x axis
     * @param nY number of the pixels on the y axis
     * @param j current pixel column
     * @param i current pixel row
     * @return Ray
     */
    public Ray constructRayThroughPixel(int nX, int nY, int j, int i) {

        Point3D Pc = _p0.add(_vTo.scale(_distance)); //central point of the view plane

        double Ry = _height / nY; //length of the pixel
        double Rx = _width / nX; //width of the pixel

        double Yi = -(i - (nY - 1) / 2d) * Ry; //is the length that we have to move pc on the y axis
        double Xj = (j - (nX - 1) / 2d) * Rx; //is the length that we have to move pc on the x axis

        Point3D Pij = Pc;

        if (isZero(Yi) && isZero(Xj)) {
            return new Ray(_p0, Pij.subtract(_p0)); //pc = Pij
        }

        if (isZero(Xj)) { //we have to move pc only on the y axis
            Pij = Pc.add(_vUp.scale(Yi));
            return new Ray(_p0, Pij.subtract((_p0)));
        }

        if (isZero(Yi)) { // we have to move pc only on the x axis
            Pij = Pc.add(_vRight.scale(Xj));
            return new Ray(_p0, Pij.subtract((_p0)));
        }

        Pij = Pc.add(_vRight.scale(Xj).add(_vUp.scale(Yi))); // is the central point on the pixel ij
        return new Ray(_p0, Pij.subtract(_p0));// central ray


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

    public static Random rand = new Random(System.currentTimeMillis());

    /**
     * @param nX        number of pixel on x-axis
     * @param nY        number of pixel on y-axis
     * @param j         number of column of current pixel
     * @param i         number of row of current pixel
     * @param amountRay
     * @return
     */

    public List<Ray> constructSeveralRayThroughPixel(int nX, int nY, int j, int i, int amountRay) {

        List<Ray> ListRays = new LinkedList<>();
        Point3D Pc = _p0.add(_vTo.scale(_distance));

        double Ry = _height / nY;// Ry length of the pixel
        double Rx = _width / nX; //Rx width of the pixel

        double Yi = -(i - (nY - 1) / 2d) * Ry;//the displacement in height from the center of the view plane to the center of the current pixel
        double Xj = (j - (nX - 1) / 2d) * Rx;//the displacement in width from the center of the view plane to the center of the current pixel

        Point3D Pij = Pc;

        if (isZero(Yi) && isZero(Xj)) {
            ListRays.add(new Ray(_p0, Pij.subtract(_p0)));
            return ListRays;

        }

        if (isZero(Xj)) {
            Pij = Pc.add(_vUp.scale(Yi));
            ListRays.add(new Ray(_p0, Pij.subtract((_p0))));
            return ListRays;

        }

        if (isZero(Yi)) {
            Pij = Pc.add(_vRight.scale(Xj));
            ListRays.add(new Ray(_p0, Pij.subtract((_p0))));
            return ListRays;

        }

        Pij = Pc.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));

        ListRays.add(new Ray(_p0, Pij.subtract(_p0)));// the central ray of the pixel i j

        double randx;
        double randy;
        Point3D NouvPoint;
        for (int k = 0; k < amountRay; k++) {
            do {
                do {
                    randx = (rand.nextDouble()) / 3.5; //displacement from pij in x axis
                    randy = (rand.nextDouble()) / 3.5; //displacement from pij in y axis

                } while (isZero(randx) || isZero(randy));

                NouvPoint = Pij.add(_vRight.scale(randx).add(_vUp.scale(randy)));
                if (!NouvPoint.equals(_p0))
                    ListRays.add(new Ray(_p0, NouvPoint.subtract(_p0).normalized()));
            } while (NouvPoint.equals(_p0));

        }

        return ListRays;


    }
}
