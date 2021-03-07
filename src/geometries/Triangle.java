package geometries;

import primitives.Point3D;

public class Triangle extends Polygon {
    public Triangle(Point3D vertices) {
        super(vertices);
    }

    @Override
    public String toString() {
        return "Triangle{" +
                "vertices=" + vertices +
                ", plane=" + plane +
                '}';
    }
}
