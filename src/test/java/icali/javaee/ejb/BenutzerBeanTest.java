/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author x7
 */
public class BenutzerBeanTest {
    
        private static EJBContainer c;
    
    public BenutzerBeanTest() {
    }
    
    @BeforeClass
    public static void setUpClass() throws Exception {
        Map<String, Object> properties = new HashMap<>();
        properties.put(EJBContainer.MODULES, new File("build/jar"));
        c = EJBContainer.createEJBContainer(properties);
        System.out.println("Opening the container");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        c.close();
        System.out.println("Closing the container");
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of findById method, of class BenutzerBean.
     */
    @Test
    public void testBenutzer() throws Exception {
        BenutzerBean instance = (BenutzerBean)c.getContext().lookup("java:global/classes/BenutzerBean");
        Benutzer benutzer = new Benutzer("test123", "123456", "holga", "l@p.de");
        assertNotNull(instance.saveNew(benutzer));
        
        benutzer.setVunname("Peter");
        assertEquals("Peter", instance.update(benutzer).getVunname());
    }
}
