package geometries;

import primitives.Point3D;

/*
 * The class is based on Point3D and an extends of Polygon
 */

public class Triangle extends Polygon{
    //constructor that receives 3 points
    public Triangle(Point3D p0,Point3D p1,Point3D p2) {
        super(p0,p1,p2);
    }


    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
