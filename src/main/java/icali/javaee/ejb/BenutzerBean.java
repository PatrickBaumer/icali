/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Benutzer;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class BenutzerBean extends EntityBean<Benutzer, Long>{
    
    public BenutzerBean() {
        super(Benutzer.class);
    }
    
        public void signup(String username, String password) throws UserAlreadyExistsException {
        if (em.find(Benutzer.class, username) != null) {
            throw new UserAlreadyExistsException("Der Benutzername $B ist bereits vergeben.".replace("$B", username));
        }

        Benutzer benutzer = new Benutzer(username, password);
        //kalender.addToGroup("icali-app-user");<-------------- PRÜFEN
        em.persist(benutzer);
    }
        
        public class UserAlreadyExistsException extends Exception {

        public UserAlreadyExistsException(String message) {
            super(message);
        }
    }

    /**
     * Fehler: Das übergebene Passwort stimmt nicht mit dem des Benutzers
     * überein
     */
    public class InvalidCredentialsException extends Exception {

        public InvalidCredentialsException(String message) {
            super(message);
        }
    }

    
}
