package primitives;

import java.awt.*;
import java.util.Objects;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0");
        }
        _head = head;
    }

    public Vector(int x, int y, int z) {
        Point3D p0=new Point3D(x,y,z);
        if (p0.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0");
        }
        _head=p0;
    }


    public double dotProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);


    }

    public Vector crossProduct(Vector v) {
        double u1 = _head._x.coord;
        double u2 = _head._y.coord;
        double u3 = _head._z.coord;
        double v1 = v._head._x.coord;
        double v2 = v._head._y.coord;
        double v3 = v._head._z.coord;

        return new Vector(new Point3D(
                u2*v3-u3*v2,
                u3*v1-u1*v3,
                u1*v2-u2*v1

        ));

    }

    public Vector add(Vector v){
        return new Vector(new Point3D(
               _head._x.coord+v._head._x.coord,
               _head._y.coord+v._head._y.coord,
               _head._z.coord+v._head._z.coord

        ));
    }

    public Vector subtract(Vector v){
        if (v.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector(0,0,0)");
        }

        return new Vector(new Point3D(
                _head._x.coord-v._head._x.coord,
                _head._y.coord-v._head._y.coord,
                _head._z.coord-v._head._z.coord

        ));
    }

    public Vector scale(double d){
        return new Vector(new Point3D(
                d*_head._x.coord,
                d*_head._y.coord,
                d*_head._z.coord

        ));
    }

    public double lengthSquared(){
        return (_head._x.coord)*(_head._x.coord)+(_head._y.coord)*(_head._y.coord)+(_head._z.coord)*(_head._z.coord);
    }

    public double length(){
        return Math.sqrt(this.lengthSquared());

    }

    public Vector normalize(){

        double x=_head._x.coord;
        double y=_head._y.coord;
        double z=_head._z.coord;
        double l=this.length();
        _head=new Point3D(x/l,y/l,z/l);
   return this;

    }

    public Vector normalized(){
        return new Vector(new Point3D(
                _head._x.coord/this.length(),
                _head._y.coord/this.length(),
                _head._z.coord/this.length()

        ));
    }

    public Point3D getHead() {
        return _head;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return _head.equals(((Vector) o)._head);
//        Objects.equals(_head, vector._head);

    }

    @Override
    public String toString() {
        return "Vector{" +
                "_head=" + _head +
                '}';
    }

}
