package primitives;

public class Material {
    public double Kd=0;
    public double Ks=0;
    public int nShininess=0;

    public Material setKd(double kd) {
        Kd = kd;
        return this;
    }

    public Material setKs(double ks) {
        Ks = ks;
        return this;
    }

    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }
}
