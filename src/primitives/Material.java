package primitives;

/**
 * this class contains coefficients kd, ks and nShininess
 */

public class Material {
    public double Kd=0;
    public double Ks=0;
    public int nShininess=0;
    public double Kt=0.0;
    public double Kr=0.0;

////    public void setKt(double kt) {
//        Kt = kt;
//    }
//
//    public void setKr(double kr) {
//        Kr = kr;
//    }
public Material setKt(double kt) {
    Kt = kt;
    return this;
}

        public Material setKr(double kr) {
        Kr = kr;
        return this;
    }

    /**
     * setter
     * @param kd
     * @return Material object
     */
    public Material setKd(double kd) {
        Kd = kd;
        return this;
    }

    /**
     * setter
     * @param ks
     * @return Material object
     */

    public Material setKs(double ks) {
        Ks = ks;
        return this;
    }

    /**
     * setter
     * @param nShininess
     * @return Material object
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}