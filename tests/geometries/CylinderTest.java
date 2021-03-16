package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point3D;
import primitives.Ray;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class CylinderTest {

    @Test
    void constructor()
   {
        try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,1,0));
            new Cylinder(3,ray,4);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

       // =============== Boundary Values Tests ==================
       // test radius ZERO
           try {
            Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,1,0));
            new Cylinder(3,ray,0);
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct cylinder");
        }

       // test Vector ZERO
       try {
           Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,0,0));
           new Cylinder(3,ray,2);
       } catch (IllegalArgumentException e) {
           fail("Failed constructing a correct cylinder");
       }

    }

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Ray ray=new Ray(new Point3D(0,0,0),new Vector(0,1,0));
        Cylinder cy=new Cylinder(3,ray,2);
        Vector N=cy.getNormal(new Point3D(0,0,0));
        assertEquals(N,new Vector(0,1,0));
    }
}