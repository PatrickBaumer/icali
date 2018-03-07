/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Benutzer;

/**
 *
 * @author x7
 */
public class BenutzerBean extends EntityBean<Benutzer, Long>{
    
    public BenutzerBean() {
        super(Benutzer.class);
    }
    
}
