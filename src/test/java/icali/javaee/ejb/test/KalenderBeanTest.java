/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb.test;

import icali.javaee.ejb.KalenderBean;
import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Termin;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import javax.ejb.embeddable.EJBContainer;
import javax.validation.ConstraintViolationException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Patrickneu
 */
public class KalenderBeanTest {
    String kalenderTitel = "TETS";
    Benutzer kalenderAdmin = new Benutzer("hallo123", "hallo123");
    String beschreibung = "TETS";
    
    
    
    
    public KalenderBeanTest() {
    }
    
    @AfterClass
    public static void tearDownClass() throws Exception {
     
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
    //@Test
    public void testSaveNew() throws Exception{
        System.out.println("saveNew");
        try {
            Kalender expKalender = new Kalender(kalenderTitel, kalenderAdmin, beschreibung);
            EJBContainer container = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        KalenderBean instance = (KalenderBean)container.getContext().lookup("java:global/classes/KalenderBean");
        Kalender result = instance.saveNew(expKalender);
        assertEquals(expKalender, result);
        } catch (ConstraintViolationException e) {

        }
        
     
       
        // TODO review the generated test code and remove the default call to fail.
     
    }

}
