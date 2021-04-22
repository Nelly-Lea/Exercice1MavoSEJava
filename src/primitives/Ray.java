package primitives;

public class Ray {
    final Point3D _p0;
    final Vector _dir;

    //contructor checks if the vector reveived dir is normal and if not,it normalizes it
    public Ray(Point3D p0, Vector dir) {
//        if(dir.length()!=1) {
//            dir=dir.normalized();
//        }
        _p0 = p0;
        _dir = dir;
    }

    /**
     * @param o
     * @return true if the Ray received and the Ray on which the operation is performed are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ray ray = (Ray) o;
        return _p0.equals(ray._p0) && _dir.equals(ray._dir);
    }

    public Point3D get_p0() {
        return _p0;
    }

    public Vector get_dir() {
        return _dir;
    }

    public Point3D getPoint(double t){
        Point3D p= _p0.add(_dir.scale(t));
        return p;
    }

    @Override
    public String toString() {
        return "Ray{" +
                "_p0=" + _p0 +
                ", _dir=" + _dir +
                '}';
    }
}