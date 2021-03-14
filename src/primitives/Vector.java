package primitives;

import static primitives.Point3D.ZERO;

public class Vector {
    Point3D _head;

    /**
     * constructor checks if the receive point is the point (0,0,0) if so it throws exception
     * @param head
     */

    public Vector(Point3D head) {
        if (head.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0");
        }
        _head = head;
    }

    /**
     * constructor checks if the receive point is the point (0,0,0) if so it throws exception
     *
     * @param x 1st number
     * @param y 2nd number
     * @param z 3nd number
     */
    public Vector(double x, double y, double z) {
        Point3D p0=new Point3D(x,y,z);
        if (p0.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0");
        }
        _head=p0;
    }

    /**
     * constructor checks if the receive point is the point (0,0,0) if so it throws exception
     *
     * @param x 1st coordinate
     * @param y 2nd coordinate
     * @param z 3nd coordinate
     */
    public Vector(Coordinate x, Coordinate y, Coordinate z) {
        Point3D p0=new Point3D(x,y,z);
        if (p0.equals(ZERO)) {
            throw new IllegalArgumentException("Vector head cannot be Point(0,0,0");
        }
        _head=p0;
    }

    /**
     * @param v
     * @return u1 * v1 + u2 * v2 + u3 * v3
     */
    public double dotProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;
        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

        return (u1 * v1 + u2 * v2 + u3 * v3);


    }

    /**
     * @param v
     * @return the orthogonal vector to vector v and the vector on which the operation is performed
     */
    public Vector crossProduct(Vector v) {
        double u1 = _head._x._coord;
        double u2 = _head._y._coord;
        double u3 = _head._z._coord;
        double v1 = v._head._x._coord;
        double v2 = v._head._y._coord;
        double v3 = v._head._z._coord;

       Point3D newhead=new Point3D(
                u2*v3-u3*v2,
                u3*v1-u1*v3,
                u1*v2-u2*v1

        );
       if(newhead.equals(ZERO)){
           throw new IllegalArgumentException("cross product resulting zero point head");
       }
       return new Vector(newhead);
    }

    /**
     * @param v
     * @return the addition of 2 vectors
     */
    public Vector add(Vector v){
        return new Vector(new Point3D(
               _head._x._coord +v._head._x._coord,
               _head._y._coord +v._head._y._coord,
               _head._z._coord +v._head._z._coord

        ));
    }

    /**
     * @param v
     * @return the substraction of 2 vectors
     */
    public Vector subtract(Vector v){
        if (v.equals(this)) {
            throw new IllegalArgumentException("cannot create Vector(0,0,0)");
        }

        return new Vector(new Point3D(
                _head._x._coord -v._head._x._coord,
                _head._y._coord -v._head._y._coord,
                _head._z._coord -v._head._z._coord

        ));
    }

    /**
     * @param d
     * @return the multiplication of the vector on which the operation is performed and number d
     */
    public Vector scale(double d){
        return new Vector(new Point3D(
                d*_head._x._coord,
                d*_head._y._coord,
                d*_head._z._coord

        ));
    }

    //return the vector length squared
    public double lengthSquared(){
        return (_head._x._coord)*(_head._x._coord)+(_head._y._coord)*(_head._y._coord)+(_head._z._coord)*(_head._z._coord);
    }

    //return the vector length
    public double length(){
        return Math.sqrt(this.lengthSquared());

    }

    //return the vector on which the operation is performed normalized
    public Vector normalize(){

        double x=_head._x._coord;
        double y=_head._y._coord;
        double z=_head._z._coord;
        double l=this.length();
        _head=new Point3D(x/l,y/l,z/l);
        return this;

    }

    //return new vector normal from the vector on which the operation is performed
    public Vector normalized(){
        return new Vector(new Point3D(
                _head._x._coord /this.length(),
                _head._y._coord /this.length(),
                _head._z._coord /this.length()

        ));
    }


    public Point3D getHead() {
        return _head;
    }

    /**
     * @param o
     * @return true if the vector received and the vector on which the operation is performed are equal
     */
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
