/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Kategorie;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

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
    
   public List<Kalender> findByUsername(String username) {
       return em.createQuery("SELECT k FROM Kalender k WHERE k.benutzerList.username = :username ORDER BY k.kalenderTitel")
               .setParameter("username", username)
               .getResultList();
   }
   
   public List<Kalender> findByKalenderId(Long KalenderId) {
       return em.createQuery("SELECT k FROM Kalender k WHERE k.kalenderId = :kalenderId ORDER BY k.kalenderTitel")
               .setParameter("KalenderId", KalenderId)
               .getResultList();
   }
   
   public List<Kalender> findByKalenderTitel(String kalenderTitel) {
       return em.createQuery("SELECT k FROM Kalender k WHERE k.kalenderTitel = :kalender ORDER BY k.kalenderTitel")
               .setParameter("kalenderTitel", kalenderTitel)
               .getResultList();
   }   
    
}
