/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Kategorie;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class KategorieBean extends EntityBean<Kategorie, Long> {

    public KategorieBean() {
        super(Kategorie.class);
    }

    public List<Kategorie> findAllSorted() {
        return this.em.createQuery("SELECT c FROM Kategorie c ORDER BY c.kategorieName").getResultList();
    }
    
    public List<Kalender> findCategoriesByKalenderId(Kalender kalender) {
        return em.createQuery("SELECT k FROM Kategorie k WHERE k.kategorieKalender = :kalender ORDER BY k.kategorieName")
                .setParameter("kalender", kalender)
                .getResultList();
    }
    
}
