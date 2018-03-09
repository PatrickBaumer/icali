/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Kategorie;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class KategorieBean extends EntityBean<Kategorie, String>{
    
    public KategorieBean() {
        super(Kategorie.class);
    }
    
}
