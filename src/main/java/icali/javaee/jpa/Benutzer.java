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
import javax.persistence.Table;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @author Patrick Baumer
 */
@Entity
@Table(name = "ICALI_BENUTZER")
@NoArgsConstructor
public class Benutzer implements Serializable{
    

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
        
    private static final long serialVersionUID = 1L;
    
//    @Id
//    @GeneratedValue(generator = "benutzer_ID")
//    @TableGenerator(name = "benutzer_ID", initialValue = 0, allocationSize = 50)
//    private long id;
    
    @Id
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Der Benutzername muss zwischen fünf und 64 Zeichen lang sein.")
    @NotNull(message = "Der Benutzername darf nicht leer sein.")
    private String username;
    
    @ManyToMany (mappedBy = "benutzerList", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    List<Kalender> kalenderList = new ArrayList<>();
    
    @OneToMany (mappedBy = "ersteller", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
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
     
    @ElementCollection
    @CollectionTable(
            name = "ICALI_BENUTZER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
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
    
    
    
    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        for(String groupname: this.groups) {
            groupsCopy.add(groupname);
        }

        return groupsCopy;
    }

    /**
     * Fügt den Benutzer einer weiteren Benutzergruppe hinzu.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void addToGroup(String groupname) {
        if (!this.groups.contains(groupname)) {
            this.groups.add(groupname);
        }
    }

    /**
     * Entfernt den Benutzer aus der übergebenen Benutzergruppe.
     *
     * @param groupname Name der Benutzergruppe
     */
    public void removeFromGroup(String groupname) {
        this.groups.remove(groupname);
    }

//    public long getId() {
//        return id;
//    }
//
//    public void setId(long id) {
//        this.id = id;
//    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVunname() {
        return vunname;
    }

    public void setVunname(String vunname) {
        this.vunname = vunname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}