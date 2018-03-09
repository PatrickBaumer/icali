/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Kalender;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class KalenderBean extends EntityBean<Kalender, Long>{
    
    public KalenderBean() {
        super(Kalender.class);
    }
    
}
