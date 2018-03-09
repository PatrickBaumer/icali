/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.jpa;

import java.io.Serializable;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 *
 * @author Patrick Baumer
 */
@Entity
public class Benutzer implements Serializable{
    
        private static final long serialVersionUID = 1L;

    public Benutzer(String username, String passwordHash, String vunname, String email) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.vunname = vunname;
        this.email = email;
    }
    public Benutzer(String username, String passwordHash, String vunname) {
        this.username = username;
        this.passwordHash = passwordHash;
        this.vunname = vunname;
       
    }
        public Benutzer(String username, String passwordHash) {
        this.username = username;
        this.passwordHash = passwordHash;
        //sdasd
    }
        
    @Id
    @GeneratedValue(generator = "benutzer_ID")
    @TableGenerator(name = "benutzer_ID", initialValue = 0, allocationSize = 50)
    private long id;
    
    
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Der Benutzername muss zwischen fünf und 64 Zeichen lang sein.")
    @NotNull(message = "Der Benutzername darf nicht leer sein.")
    private String username;
    
    @ManyToMany(mappedBy = "benutzerList")
    List<Kalender> kalenderList = new ArrayList<>();
    
    @OneToMany
    List<Termin> terminList = new ArrayList<>();
    
  
    public class Password {
        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
        public String password = "";
    }
    @Transient
    private final Password password = new Password();

    @Column(name = "PASSWORD_HASH", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String passwordHash;
   
//    Prüfen ob notwendig    
//    @ElementCollection
//    @CollectionTable(
//            name = "ICALI_USER_GROUP",
//            joinColumns = @JoinColumn(name = "USERNAME")
//    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();
    
    @Column (name = "VUNNAME")
    @NotNull(message = "Vor- und Nachname darf nicht leer sein.")
    private String vunname;
     
    @Column (name = "EMAIL")
    @Pattern(regexp = "(\\w|\\W)*@(\\w|\\W)*.(\\w|\\W){1,4}", message ="Die E-Mail muss muster@muster.de")
    @NotNull (message = "E-Mail-Adresse darf nicht leer sein.")
    private String email;
 
    private String hashPassword(String password) {
        byte[] hash;

        if (password == null) {
            password = "";
        }
        
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        } catch (NoSuchAlgorithmException ex) {
            hash = "!".getBytes(StandardCharsets.UTF_8);
        }

        BigInteger bigInt = new BigInteger(1, hash);
        return bigInt.toString(16);
    }


    public void setPassword(String password) {
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }


    public Password getPassword() {
        return this.password;
    }

    public boolean checkPassword(String password) {
        return this.passwordHash.equals(this.hashPassword(password));
    }
}