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
    
    public List<Kategorie> findCategoriesByKalenderTitel(String kalenderTitel) {
        return em.createQuery("SELECT k FROM Kategorie k WHERE k.kategorieKalender.kalenderTitel = :kalenderTitel ORDER BY k.id")
                .setParameter("kalenderTitel", kalenderTitel)
                .getResultList();
    }
    
    public List<Kategorie> findByBeschreibung(String beschreibung, Kalender kalender) {
        return this.em.createQuery("SELECT DISTINCT c FROM Kategorie c WHERE c.kategorieName = :beschreibung AND c.kategorieKalender = :kalender")
                .setParameter("beschreibung", beschreibung)
                .setParameter("kalender", kalender)
                .getResultList();
    }
    
}
