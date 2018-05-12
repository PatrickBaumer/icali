/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Termin;
import icali.javaee.jpa.Kategorie;
import javax.annotation.security.RolesAllowed;
import java.util.List;
import javax.ejb.Stateless;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class TerminBean extends EntityBean<Termin, Long> {

    public TerminBean() {
        super(Termin.class);
    }

    public List<Termin> findByKalendername(String kalendername) {
        return em.createQuery("SELECT t FROM Termin t WHERE t.terminInKalender.kalenderTitel = :kalendername ORDER BY t.startDatum, t.startUhrzeit")
                .setParameter("kalendername", kalendername)
                .getResultList();
    }

    public List<Termin> findByTerminTitel(String terminTitel) {
        return em.createQuery("SELECT t FROM Termin t WHERE t.terminTitel = :terminTitel ORDER BY t.startDatum, t.startUhrzeit")
                .setParameter("terminTitel", terminTitel)
                .getResultList();
    }
    
    
    public List<Termin> findByKategorie (Kategorie terminKategorie){
        return em.createQuery("SELECT t FROM Termin t WHERE t.terminKategorie = :terminKategorie ORDER BY t.startDatum, t.startUhrzeit")
                .setParameter("terminKategorie", terminKategorie)
                .getResultList();
    }
    
    public List<Termin> findByTerminId(Long terminId) {
        return em.createQuery("SELECT t FROM Termin t WHERE t.terminId = :terminId ORDER BY t.startDatum, t.startUhrzeit")
                .setParameter("terminId", terminId)
                .getResultList();
    }
}
