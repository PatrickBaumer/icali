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
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 *
 * @author Patrick Baumer
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class Kalender implements Serializable{
 
      
    @Id
    @GeneratedValue(generator = "kalender_id")
    @TableGenerator(name = "kalender_id", initialValue = 0)
    private Long kalenderId;
    
    @Column(name = "k_titel")
    private String kalenderTitel;
    
    @Column(name = "k_admin")
    @NotNull(message = "Jede Gruppe braucht einen Führer")
    private Benutzer kalenderAdmin;
    
    @Column(name = "k_beschreibung")
    @Lob
    private String beschreibung;

    @OneToMany(mappedBy = "terminInKalender")
    private List<Termin> terminList = new ArrayList<>();
  
    @ManyToMany(mappedBy = "kalenderList")
    private List<Benutzer> benutzerList = new ArrayList<>();
    
    @OneToMany(mappedBy = "kategorieKalender")
    List<Kategorie> kalenderKategorie = new ArrayList<>();
    
    
    
    
    
    
    
    
    
    //passwort für Kalender
    public class Password {
        @Size(min = 6, max = 64, message = "Das Passwort muss zwischen sechs und 64 Zeichen lang sein.")
        public String password = "";
    }
    @Transient
    private final Password password = new Password();

    @Column(name = "k_password_hash", length = 64)
    @NotNull(message = "Das Passwort darf nicht leer sein.")
    private String kalenderPasswordHash;
    
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
    

    
    
}
