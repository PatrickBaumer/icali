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
        
    @Id
    @GeneratedValue(generator = "benutzer_ID")
    @TableGenerator(name = "benutzer_ID", initialValue = 0, allocationSize = 50)
    private long id;
    
    
    @Column(name = "USERNAME", length = 64)
    @Size(min = 5, max = 64, message = "Der Benutzername muss zwischen fünf und 64 Zeichen lang sein.")
    @NotNull(message = "Der Benutzername darf nicht leer sein.")
    private String username;
    
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
            name = "MINIMARKT_USER_GROUP",
            joinColumns = @JoinColumn(name = "USERNAME")
    )
    @Column(name = "GROUPNAME")
    List<String> groups = new ArrayList<>();
    
    @Column (name = "VUNNAME")
    @NotNull(message = "Vor- und Nachname darf nicht leer sein.")
    private String vunname;
    
    @Column (name = "ANSCHRIFT")
    @NotNull (message = "Straße und Hausnummer dürfen nicht leer sein.")
    private String anschrift;
    
    @Column (name = "PLZ")
    @Pattern (regexp = "[0-9]{5}")
    @NotNull (message = "Postleitzahl darf nicht leer sein.")
    private String plz;
    
    @Column (name = "ORT")
    @NotNull (message = "Ort darf nicht leer sein.")
    private String ort;
    
    @Column (name = "TELEFON")
    @Pattern (regexp = "[0-9]*")
    @NotNull (message = "Telefonnummer darf nicht leer sein.")
    private String telefon;
    
    @Column (name = "EMAIL")
    @Pattern(regexp = "(\\w|\\W)*@(\\w|\\W)*.(\\w|\\W){1,4}", message ="Die E-Mail muss muster@muster.de")
    @NotNull (message = "E-Mail-Adresse darf nicht leer sein.")
    private String email;

    public String getTelefon() {
        return telefon;
    }

    public void setTelefon(String telefon) {
        this.telefon = telefon;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getVunname() {
        return vunname;
    }

    public void setVunname(String vunname) {
        this.vunname = vunname;
    }

    public String getAnschrift() {
        return anschrift;
    }

    public void setAnschrift(String anschrift) {
        this.anschrift = anschrift;
    }

    public String getPlz() {
        return plz;
    }

    public void setPlz(String plz) {
        this.plz = plz;
    }

    public String getOrt() {
        return ort;
    }

    public void setOrt(String ort) {
        this.ort = ort;
    }

    //<editor-fold defaultstate="collapsed" desc="Konstruktoren">
    public User() {
    }

    public User(String username, String password) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }
    
     public User(String username, String password, String vunname, String anschrift, String plz, String ort, String telefon, String email) {
        this.username = username;
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
        this.vunname = vunname;
        this.anschrift = anschrift;
        this.plz = plz;
        this.ort = ort;
        this.telefon = telefon;
        this.email = email;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Setter und Getter">
    public String getUsername() {
        return username;
    }

    public void setUsername(String id) {
        this.username = id;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    public void setTasks(List<Task> tasks) {
        this.tasks = tasks;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Passwort setzen und prüfen">
    /**
     * Berechnet der Hash-Wert zu einem Passwort.
     *
     * @param password Passwort
     * @return Hash-Wert
     */
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

    /**
     * Berechnet einen Hashwert aus dem übergebenen Passwort und legt ihn im
     * Feld passwordHash ab. Somit wird das Passwort niemals als Klartext
     * gespeichert.
     * 
     * Gleichzeitig wird das Passwort im nicht gespeicherten Feld password
     * abgelegt, um durch die Bean Validation Annotationen überprüft werden
     * zu können.
     *
     * @param password Neues Passwort
     */
    public void setPassword(String password) {
        this.password.password = password;
        this.passwordHash = this.hashPassword(password);
    }

    /**
     * Nur für die Validierung bei einer Passwortänderung!
     * @return Neues, beim Speichern gesetztes Passwort
     */
    public Password getPassword() {
        return this.password;
    }
    
    /**
     * Prüft, ob das übergebene Passwort korrekt ist.
     *
     * @param password Zu prüfendes Passwort
     * @return true wenn das Passwort stimmt sonst false
     */
    public boolean checkPassword(String password) {
        return this.passwordHash.equals(this.hashPassword(password));
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Zuordnung zu Benutzergruppen">
    /**
     * @return Eine unveränderliche Liste aller Benutzergruppen
     */
    public List<String> getGroups() {
        List<String> groupsCopy = new ArrayList<>();

        this.groups.forEach((groupname) -> {
            groupsCopy.add(groupname);
        });

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
    //</editor-fold>

}

}
