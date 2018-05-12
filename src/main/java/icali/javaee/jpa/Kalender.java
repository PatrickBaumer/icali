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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;



/**
 *
 * @author Patrick Baumer
 */

@Entity
public class Kalender implements Serializable{
    
    private static final long serialVersionUID = 1L;
   
    @Id
    @Column(name = "k_titel")
    @Size(min = 4, max = 50, message = "Der Kalendername muss zwischen vier und 50 Zeichen lang sein.")
    @NotNull (message = "Der Kalendername darf nicht leer sein.")
    private String kalenderTitel;
    
    @Column(name = "k_password")
    @NotNull (message = "Der Einschreibeschlüssel darf nicht leer sein.")
    private String kalenderPasswort;
    
    @OneToOne
    @NotNull(message = "Jede Gruppe braucht einen Führer")
    private Benutzer kalenderAdmin;
    
    @Column(name = "k_beschreibung")
    @Lob
    private String beschreibung;

    @OneToMany(mappedBy = "terminInKalender")
    private List<Termin> terminList = new ArrayList<>();
  
    @ManyToMany
    private List<Benutzer> benutzerList = new ArrayList<>();
    
    @OneToMany(mappedBy = "kategorieKalender")
    private List<Kategorie> kalenderKategorie = new ArrayList<>();
    
 
    public Kalender(String kalenderTitel, Benutzer kalenderAdmin, String beschreibung, String kalenderPasswort) {
        this.kalenderTitel = kalenderTitel;
        this.kalenderAdmin = kalenderAdmin;
        this.beschreibung = beschreibung;
        this.kalenderPasswort = kalenderPasswort;
    }


    public String getKalenderTitel() {
        return kalenderTitel;
    }

    public void setKalenderTitel(String kalenderTitel) {
        this.kalenderTitel = kalenderTitel;
    }

    public String getKalenderPasswort() {
        return kalenderPasswort;
    }

    public void setKalenderPasswort(String kalenderPasswort) {
        this.kalenderPasswort = kalenderPasswort;
    }
    
    

    public Benutzer getKalenderAdmin() {
        return kalenderAdmin;
    }

    public void setKalenderAdmin(Benutzer kalenderAdmin) {
        this.kalenderAdmin = kalenderAdmin;
    }

    public String getBeschreibung() {
        return beschreibung;
    }

    public void setBeschreibung(String beschreibung) {
        this.beschreibung = beschreibung;
    }

    public List<Termin> getTerminList() {
        return terminList;
    }

    public void setTerminList(List<Termin> terminList) {
        this.terminList = terminList;
    }

    public List<Benutzer> getBenutzerList() {
        return benutzerList;
    }

    public void setBenutzerList(List<Benutzer> benutzerList) {
        this.benutzerList = benutzerList;
    }

    public List<Kategorie> getKalenderKategorie() {
        return kalenderKategorie;
    }

    public void setKalenderKategorie(List<Kategorie> kalenderKategorie) {
        this.kalenderKategorie = kalenderKategorie;
    }


    public Kalender() {
    }
    
    
    
}
