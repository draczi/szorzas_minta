/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package szorzas_minta;

import static org.testng.Assert.*;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 *
 * @author user
 */
public class Szorzas_mintaNGTest {
    
    public Szorzas_mintaNGTest() {
    }

    @BeforeClass
    public static void setUpClass() throws Exception {
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
    }

    @BeforeMethod
    public void setUpMethod() throws Exception {
    }

    @AfterMethod
    public void tearDownMethod() throws Exception {
    }

    /**
     * Test of multiplyNatural method, of class Szorzas_minta.
     */
    @Test
    public void test1() {
       
        assertEquals(100, multiplyNaturals(50,2));
    }
    @Test
    public void test2() {
       
        assertEquals(4, multiplyNaturals(2,2));
    }
    @Test
    public void test3() {
       
        assertEquals(10, multiplyNaturals(5,2));
    }
    @Test
    public void test4() {
       
        assertEquals(20, multiplyNaturals(10,2));
    }
    @Test
    public void test5() {
       
        assertEquals(-1, multiplyNaturals(-1,2));
    }

    private Object multiplyNaturals(int i, int i0) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
