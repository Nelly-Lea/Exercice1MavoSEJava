package primitives;

import org.junit.jupiter.api.Test;
import static primitives.Util.isZero;
import static java.lang.System.out;


import static org.junit.jupiter.api.Assertions.*;


 /**
  * Unit tests for primitives.Vector class
  * @author Dan
  */

class VectorTest {

     Vector v1 = new Vector(1, 2, 3);
     Vector v2 = new Vector(-2, -4, -6);

     @Test
     void testZeroPoint(){
         try {//test zero vector
             new Vector(0, 0, 0);
             fail("ERROR: zero vector does not throw an exception");
         }catch (IllegalArgumentException e){
             out.println("good:zero vector cannot exist");
         }
     }

     @Test
    void dotProduct() {
         double dot=v1.dotProduct(v2);
         assertEquals(dot,-28,"ERROR");
    }

     /**
      * Test method for {@link primitives.Vector#crossProduct(primitives.Vector)}.
      */


     @Test
    void crossProduct() {

         // ============ Equivalence Partitions Tests ==============
         Vector v3 = new Vector(0, 3, -2);
         Vector vr = v1.crossProduct(v3);

         // Test that length of cross-product is proper (orthogonal vectors taken for simplicity)
         assertEquals( v1.length() * v3.length(), vr.length(), 0.00001,"crossProduct() wrong result length");

         // Test cross-product result orthogonality to its operands
         assertTrue(isZero(vr.dotProduct(v1)),"crossProduct() result is not orthogonal to 1st operand");
         assertTrue(isZero(vr.dotProduct(v3)),"crossProduct() result is not orthogonal to 2nd operand");

         // =============== Boundary Values Tests ==================
         // test zero vector from cross-productof co-lined vectors
         try {
             v1.crossProduct(v2);
             fail("crossProduct() for parallel vectors does not throw an exception");
         } catch (Exception e) {}

     }

     @Test
    void add() {
         Vector v=v1.scale(-1);
         if(v2.equals(v))
             assertEquals(v2,v,"ERROR result is the vector 0");



     }
    @Test
    void subtract() {
        if(v2.equals(v1))
            assertEquals(v2,v1,"ERROR result is the vector 0");

    }

    @Test
    void scale() {
        Vector vscale=v1.scale(-0.99999);
        assertEquals(v1.length(),vscale.length(),0.0001,"delta too small");
    }

    @Test
    void lengthSquared() {
        if (!isZero(v1.lengthSquared() - 14))
            fail("ERROR: lengthSquared() wrong value");

    }

    @Test
    void length() {
         double result =new Vector(0, 3, 4).length();
         assertTrue(isZero(result -5),"ERROR: length() wrong value");

    }

    @Test
    void normalize() {
        Vector vec1 = new Vector(5, 0, 0);
        Vector vec2 = new Vector(1, 0, 0);
        assertEquals(vec1.normalize(),vec2,"Normalization error");
    }

    @Test
    void normalized() {
        Vector vec1 = new Vector(7, 0, 0);
        Vector vec2 = new Vector(1, 0, 0);
        Vector vec3=vec1.normalized();
        assertEquals(vec3,vec2,"Normalization error");
    }
}