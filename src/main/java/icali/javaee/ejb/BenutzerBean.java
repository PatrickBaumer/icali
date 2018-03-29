/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.ejb.EJBContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.annotation.Resource;


@Stateless
public class BenutzerBean extends EntityBean<Benutzer, Long> {

    @PersistenceContext
    EntityManager em;

    @Resource
    EJBContext ctx;

    public BenutzerBean() {
        super(Benutzer.class);
    }

    public Benutzer getCurrentBenutzer() {
        return this.em.find(Benutzer.class, this.ctx.getCallerPrincipal().getName());
    }
    
    public List<Kalender> findAllKalenderByUser (Benutzer benutzer) {
        return benutzer.getKalenderList();
    }

    public void signup(String username, String password, String vunname, String email) throws UserAlreadyExistsException {
        if (em.find(Benutzer.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }
        
        Benutzer benutzer = new Benutzer(username, password, vunname, email);
        benutzer.addToGroup("icali-app-user");
        em.persist(benutzer);
    }

    public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    @RolesAllowed("icali-app-user")
    public void changePassword(Benutzer benutzer, String oldPassword, String newPassword) throws InvalidCredentialsException {
        if (benutzer == null || !benutzer.checkPassword(oldPassword)) {
            throw new InvalidCredentialsException("Benutzername oder Passwort sind falsch.");
        }

        benutzer.setPassword(newPassword);
    }

    /**
     * @RolesAllowed("icali-app-user") public void changeBenutzerdaten (Benutzer
     * benutzer, String vunname, String email) { benutzer.setVunname(vunname);
     * benutzer.setEmail(email);
     *
     * }  
     
     
     Was brauchen wir da noch?!*
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }
    

}
