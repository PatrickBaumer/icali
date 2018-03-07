/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Kalender;

/**
 *
 * @author x7
 */
public class KalendarBean extends EntityBean<Kalender, Long>{
    
    public KalendarBean() {
        super(Kalender.class);
    }
    
}
