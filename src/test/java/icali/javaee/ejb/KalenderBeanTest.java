/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.ejb.KalenderBean;
import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.BeforeClass;
/**
 *
 * @author Patrickneu
 */
public class KalenderBeanTest {
    
    private static EJBContainer c;

    public KalenderBeanTest() {
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
     * Test of saveNew method, of class KalenderBean.
     */
    @Test
    public void testKalender() throws Exception{
        KalenderBean instance = (KalenderBean)c.getContext().lookup("java:global/classes/KalenderBean");
        Kalender kalender = new Kalender();
        kalender.setPassword("123456");
        kalender.setKalenderTitel("Schule");
        kalender.setKalenderAdmin(new Benutzer("test123", "123456", "patrick", "p@p.de"));
        Kalender result = instance.saveNew(kalender);
        assertEquals(kalender, result);
        
//        List<Kalender> findkalender = instance.findByKalenderTitel("Schule");
//        assertEquals(kalender, findkalender.get(0));
        
        result.setKalenderTitel("Studium");
        Kalender updateKalender = instance.update(kalender);
        assertEquals("Studium", updateKalender.getKalenderTitel());
        
        List<Benutzer> benutzerList = new ArrayList<>();
        benutzerList.add(new Benutzer("test456","123456", "manuel", "p@p.de"));
        benutzerList.add(new Benutzer("test789","123456", "justin", "p@p.de"));
        benutzerList.add(new Benutzer("test025","123456", "philipp", "p@p.de"));
        kalender.setBenutzerList(benutzerList);
        
        assertEquals(benutzerList ,kalender.getBenutzerList());
        
//        instance.delete(result);
//        assertNull(null);
       
    }
    
}
