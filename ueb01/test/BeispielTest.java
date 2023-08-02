import org.junit.Assert;
import org.junit.Test;

import beispiel.Beispiel;

public class BeispielTest {


    @Test
    public void test1()  {
        
        Beispiel b = new Beispiel(25);
      
        Assert.assertEquals(5, b.divideByDivisor(5));
        Assert.assertEquals(2, b.divideByDivisor(10));
        Assert.assertEquals(-5, b.divideByDivisor(-5));
        
        // optional immer möglich: Beschreibung angeben
        Assert.assertEquals("Divisionsergebnis für 25/5", 5, b.divideByDivisor(5));        
    }
    
    @Test(expected = ArithmeticException.class)
    public void test2a() {
        
        Beispiel b = new Beispiel(10);
        b.divideByDivisor(0);
    }
    

    @Test(expected = AssertionError.class)
    public void test2b() {
        
        Beispiel b = new Beispiel(10);
        b.divideByNonZeroDivisor(0);
    }

    
    @Test
    public void test3() {
        
        Beispiel b1 = new Beispiel(10);
        Beispiel b2 = new Beispiel(-10);
        
        Assert.assertTrue(b1.isDividendPositive());
        Assert.assertFalse(b2.isDividendPositive());
    }
    
    @Test
    public void test4() {
        
        Beispiel b = new Beispiel(10);
        Beispiel b2 = new Beispiel(10);
        Beispiel b3 = new Beispiel(-10);
        
        Assert.assertEquals(b, b2);
        Assert.assertNotEquals(b, b3);  
        
        Assert.assertEquals("Dividend: 10", b.toString());
    }
           
}
