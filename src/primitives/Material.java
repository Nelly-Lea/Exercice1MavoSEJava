package primitives;

/**
 * this class contains coefficients kd, ks and nShininess
 */

public class Material {
    public double Kd=0;
    public double Ks=0;
    public int nShininess=0;


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
