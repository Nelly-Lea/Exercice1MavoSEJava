package primitives;

/**
 * this class contains coefficients kd, ks and nShininess
 * kt is transparency coefficent
 * kr is a coefficient to evaluate radiance
 */

public class Material {
    public double Kd=0;
    public double Ks=0;
    public int nShininess=0;
    public double Kt=0.0;
    public double Kr=0.0;

    /**
     * we set the kt coefficient and return the object material
     * @param kt
     * @return Material that we are working on
     */
    public Material setKt(double kt) {
    Kt = kt;
    return this;
}

    /**
     * we set the kr coefficient and return the object material
     * @param kr
     * @return Material that we are working on
     */
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