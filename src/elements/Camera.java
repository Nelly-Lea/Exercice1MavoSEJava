package elements;

import primitives.Color;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;
import renderer.RayTracerBase;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static primitives.Util.isZero;

public class Camera {
    final Point3D _p0;
    final Vector _vTo;
    final Vector _vUp;
    final Vector _vRight;

    private double _distance;
    private double _width;
    private double _height;


    /**
     *
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
        return new Ray(_p0, Pij.subtract(_p0));// rayon central


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
//        double rX=vpWidth/nX;
//        double rY=vpHeight/nY;
//        double xJ=(j-(nX-1)/2d)*rX;
//        double yIminus=(i-(nY-1)/2d)*rY;
//
//        Point3D pIJ=_p0.add(_vTo.scale(vpDistance))
        List<Ray> ListRays = new LinkedList<>();
        Point3D Pc = _p0.add(_vTo.scale(_distance));

        double Ry = _height / nY;// Ry longueur d'un pixel
        double Rx = _width / nX; //Rx largeur d'un pixel

        double Yi = -(i - (nY - 1) / 2d) * Ry;//the displacement in height from the center of the view plane to the center of the current pixel
        double Xj = (j - (nX - 1) / 2d) * Rx;//the displacement in width from the center of the view plane to the center of the current pixel

        Point3D Pij = Pc;
//      if(Xj!=0){
//          Pij=Pij.add(_vRight.scale(Xj));
//      }
//      if(Yi!=0){
//          Pij=Pij.add(_vUp.scale(Yi));
//      }

        if (isZero(Yi) && isZero(Xj)) {
            ListRays.add(new Ray(_p0, Pij.subtract(_p0)));
            return ListRays;
            //return new Ray(_p0, Pij.subtract(_p0));
        }

        if (isZero(Xj)) {
            Pij = Pc.add(_vUp.scale(Yi));
            ListRays.add(new Ray(_p0, Pij.subtract((_p0))));
            return ListRays;
            //return new Ray(_p0, Pij.subtract((_p0)));
        }

        if (isZero(Yi)) {
            Pij = Pc.add(_vRight.scale(Xj));
            ListRays.add(new Ray(_p0, Pij.subtract((_p0))));
            return ListRays;
            // return new Ray(_p0, Pij.subtract((_p0)));
        }

        Pij = Pc.add(_vRight.scale(Xj).add(_vUp.scale(Yi)));

        ListRays.add(new Ray(_p0, Pij.subtract(_p0)));// le rayon au centre du i j
        double Xj1;
        double Yi1;
        double randx;
        double randy;
        Point3D NouvPoint;
        for (int k = 0; k < amountRay; k++) {
            do {
                do {
                    randx = (rand.nextDouble()) / 3.5;
                    randy = (rand.nextDouble()) / 3.5;
                    // Xj1 = Xj + randx;
                    //Yi1 = Yi + randy;
                } while (isZero(randx) || isZero(randy));

                NouvPoint = Pij.add(_vRight.scale(randx).add(_vUp.scale(randy)));
                if (!NouvPoint.equals(_p0))
                    ListRays.add(new Ray(_p0, NouvPoint.subtract(_p0).normalized()));
            } while (NouvPoint.equals(_p0));

        }

        return ListRays;
        //return new Ray(_p0, Pij.subtract(_p0));// rayon central


    }
//
//    /**
//     *
//     * @param nX is number of pixels in x axis
//     * @param nY is number of pixel in y axis
//     * @param i is the row's number of the current pixel
//     * @param j is the column's number of the current pixel
//     * @param rayTracerBase
//     * @return
//     */
//    public Color AdpativeSuperSampling(int nX, int nY, int i, int j, RayTracerBase rayTracerBase) {
//        double Ry = _height / nY;// Ry is length of pixel
//        double Rx = _width / nX; //Rx is width of pixel
//        Point3D Pc = _p0.add(_vTo.scale(_distance));// Pc is the interscetion point on the center of the viewplane
//        Point3D PcornerLT = Pc.add(_vRight.scale(-_height / 2).add(_vUp.scale(_width / 2))); //PcornerLT is the point on the top corner left of the viewplane
//        Point3D PLT;
//        if (j == 0 && i == 0)//if the current pixel is the first pixel at the top corner left
//            PLT = PcornerLT;
//        else {
//            if (j == 0 && i != 0)// if the current pixel is in the first column
//                PLT = PcornerLT.add(_vUp.scale(-Rx * i));
//            else if (i == 0 && j != 0)//if the current pixel is in the first row
//                PLT = PcornerLT.add(_vRight.scale(Ry * j));
//            else
//                PLT = PcornerLT.add(_vUp.scale(-Rx * i)).add(_vRight.scale(Ry * j));// if the current pixel is other
//        }
//        Point3D PRT = PLT.add(_vRight.scale(Ry));// PRT is the point at the top right of the current pixel
//        Point3D PBR = PLT.add(_vRight.scale(Ry).add(_vTo.scale(-Rx)));// PBR is the point at the bottom right of the current pixel
//        Point3D PBL = PLT.add(_vUp.scale(-Rx));// PBL is the point at the bottom left of the current pixel
//        Color color;
//        color = AdpativeSuperSamplingCalcCol(Rx, Ry, PLT, PRT, PBR, PBL, rayTracerBase);
//        return color;
//
//
//    }
//
//    public Color AdpativeSuperSamplingCalcCol(double Rx, double Ry, Point3D p0, Point3D p1, Point3D p2, Point3D p3, RayTracerBase rayTracerBase) {
//        Ray raylt = new Ray(_p0, p0.subtract(_p0));//
//        Color clt = rayTracerBase.traceRay(raylt);
//        Ray rayrt = new Ray(_p0, p1.subtract(_p0));
//        Color crt = rayTracerBase.traceRay(rayrt);
//        Ray rayrb = new Ray(_p0, p2.subtract(_p0));
//        Color crb = rayTracerBase.traceRay(rayrb);
//        Ray raylb = new Ray(_p0, p3.subtract(_p0));
//        Color clb = rayTracerBase.traceRay(raylb);
//        Color color=Color.BLACK;
//
//        if (clt.equal(crt) && crt.equal(crb) && crb.equal(clb))// if the 4 corners of the current pixel have a similar color return the average of the 4 colors
//            return color.add(clt,crt,crb,clb).reduce(4);
//
//        int level = LEVEL;
//        Color color1 = Color.BLACK;
//        color = AdaptiveSuperSamplingRecursive(Rx, Ry, p0, p1, p2, p3, rayTracerBase, clt, crt, crb, clb, level, color1);
//        return color;
//
//    }
//
//    public Color AdaptiveSuperSamplingRecursive(double Rx, double Ry, Point3D p0, Point3D p1, Point3D p2, Point3D p3, RayTracerBase rayTracerBase, Color c0, Color c1, Color c2, Color c3, int level, /*double prop,*/ Color color1) {
//        if (level == 0) //if level=0  we stop the recursive and we return the color
//            return color1;
//        Rx = Rx / 2; //Rx is the half the width of the pixel
//        Ry = Ry / 2;//Ry is the half the length of the pixel
//        Point3D p5 = p0.add(_vRight.scale(Ry)); //p5 is the midpoint between the top left point and the the top right point
//        Point3D p6 = p1.add(_vUp.scale(-Rx));//p6 is the midpoint between the top right point and the the bottom right point
//        Point3D p7 = p2.add(_vRight.scale(-Ry));//p7 is the midpoint between the bottom right point and the the bottom left point
//        Point3D p8 = p0.add(_vUp.scale(-Rx));//p8 is the midpoint between the bottom left point and the the top left point
//        Point3D p4 = p0.add(_vRight.scale(Ry).add(_vUp.scale(-Rx)));//p4 is the point in the center of the pixel
//        Ray ray5 = new Ray(_p0, p5.subtract(_p0));//ray5 is the ray that passes through p5
//        Color c5 = rayTracerBase.traceRay(ray5); //c5 is the color of ray5
//        Ray ray6 = new Ray(_p0, p6.subtract(_p0));
//        Color c6 = rayTracerBase.traceRay(ray6);
//        Ray ray7 = new Ray(_p0, p7.subtract(_p0));
//        Color c7 = rayTracerBase.traceRay(ray7);
//        Ray ray8 = new Ray(_p0, p8.subtract(_p0));
//        Color c8 = rayTracerBase.traceRay(ray8);
//        Ray ray4 = new Ray(_p0, p4.subtract(_p0));
//        Color c4 = rayTracerBase.traceRay(ray4);
//
//        //we pass on the 4 sub pixels formed
//        if (c0.equal(c5) && c5.equal(c4) && c4.equal(c8)) //if the 4 colors of the top left sub pixel are similar we return the average of the 4 colors
//        {
//            color1 = color1.add(c0,c5,c4,c8);
//            color1=color1.reduce(4);}
//        else {
//            if (level - 1 == 0) { //if the next time we call the function is the last time as we calculate the final color of the sub pixel
//                color1 = c0.add(c5).add(c4).add(c8);
//                color1 = color1.reduce(4);
//            }
//
//
//            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p0, p5, p4, p8, rayTracerBase, c0, c5, c4, c8, level - 1,color1)).reduce(2);
//
//        }
//
//        if (c5.equal(c1) && c1.equal(c6) && c6.equal(c4)) {//if the 4 colors of the top right sub pixel are similar we return the average of the 4 colors
//            color1 = color1.add(c5,c1,c6,c4);
//            color1 = color1.reduce(5);
//        } else {
//            if (level - 1 == 0) {
//                color1 = c5.add(c1).add(c6).add(c4);
//                color1 = color1.reduce(4);
//            }
//
//            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p5, p1, p6, p4, rayTracerBase, c5, c1, c6, c4, level - 1,color1)).reduce(2);
//
//        }
//        if (c4.equal(c6) && c6.equal(c2) && c2.equal(c7)) {//if the 4 colors of the bottom right sub pixel are similar we return the average of the 4 colors
//            color1 = color1.add(c4,c6,c2,c7);
//            color1 = color1.reduce(5);
//        } else {
//            if (level - 1 == 0) {
//                color1 = c4.add(c6).add(c2).add(c7);
//                color1 = color1.reduce(4);
//            }
//
//            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p4, p6, p2, p7, rayTracerBase, c4, c6, c2, c7, level - 1, color1)).reduce(2);
//        }
//        if (c8.equal(c4) && c4.equal(c7) && c7.equal(c3))//if the 4 colors of the bottom left sub pixel are similar we return the average of the 4 colors
//        {
//            color1 = color1.add(c8,c4,c7,c3);
//            color1=color1.reduce(5);}
//        else {
//            if (level - 1 == 0) {
//                color1 = c8.add(c4).add(c7).add(c3);
//            color1=color1.reduce(4);}
//            color1 = color1.add(AdaptiveSuperSamplingRecursive(Rx, Ry, p8, p4, p7, p3, rayTracerBase, c8, c4, c7, c3, level - 1,color1)).reduce(2);
//        }
//        return color1;
//    }
//

    public List<Ray> constructListRayThroughPixel(int nX, int nY, int col, int row) {
        return null;
    }
}
