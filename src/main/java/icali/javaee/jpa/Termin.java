/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.jpa;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


/**
 *
 * @author Patrick Baumer
 */


@Entity
public class Termin implements Serializable{

    @Id
    @GeneratedValue(generator = "termin_id")
    @TableGenerator(name = "termin_id", initialValue = 0)
    private Long terminId;
    
    @Column(name = "t_titel")
    @NotNull(message = "Bitte geben Sie einen Terminnnamen ein")
    @Size(min = 1, max = 50, message = "Die Bezeichnung muss zwischen ein und 50 Zeichen lang sein.")
    private String terminTitel;
        
    @Column(name = "t_beschreibung")
    @Lob
    private String terminBeschreibung;
    
    @Column(name = "t_start_uhrzeit")
    @NotNull(message = "Bitte eine Anfangsuhrzeit angeben")
    private Time startUhrzeit;
    
    @Column(name = "t_start_datum")
    @NotNull(message = "Bitte eine Anfangsdatum angeben")
    private Date startDatum;
    
    @Column (name = "t_ende_uhrzeit")
    @NotNull(message = "Bitte eine Enduhrzeit angeben")
    private Time endeUhrzeit;
    
    @Column(name = "t_ende_datum")
    @NotNull(message = "Bitte eine Enddatum angeben")
    private Date endeDatum;
    
    
    @ManyToOne(fetch = FetchType.LAZY)
    private Kalender terminInKalender = null;
    
    @OneToOne
    private Benutzer ersteller = null;
    
    @OneToOne (mappedBy = "katkategorieTermin")
    private Kategorie terminKategorie = null;  

    public Long getTerminId() {
        return terminId;
    }

    public void setTerminId(Long terminId) {
        this.terminId = terminId;
    }

    public String getTerminTitel() {
        return terminTitel;
    }

    public void setTerminTitel(String terminTitel) {
        this.terminTitel = terminTitel;
    }

    public String getTerminBeschreibung() {
        return terminBeschreibung;
    }

    public void setTerminBeschreibung(String terminBeschreibung) {
        this.terminBeschreibung = terminBeschreibung;
    }

    public Time getStartUhrzeit() {
        return startUhrzeit;
    }

    public void setStartUhrzeit(Time startUhrzeit) {
        this.startUhrzeit = startUhrzeit;
    }

    public Date getStartDatum() {
        return startDatum;
    }

    public void setStartDatum(Date startDatum) {
        this.startDatum = startDatum;
    }

    public Time getEndeUhrzeit() {
        return endeUhrzeit;
    }

    public void setEndeUhrzeit(Time endeUhrzeit) {
        this.endeUhrzeit = endeUhrzeit;
    }

    public Date getEndeDatum() {
        return endeDatum;
    }

    public void setEndeDatum(Date endeDatum) {
        this.endeDatum = endeDatum;
    }

    public Kalender getTerminInKalender() {
        return terminInKalender;
    }

    public void setTerminInKalender(Kalender terminInKalender) {
        this.terminInKalender = terminInKalender;
    }

    public Benutzer getErsteller() {
        return ersteller;
    }

    public void setErsteller(Benutzer ersteller) {
        this.ersteller = ersteller;
    }

    public Kategorie getTerminKategorie() {
        return terminKategorie;
    }

    public void setTerminKategorie(Kategorie terminKategorie) {
        this.terminKategorie = terminKategorie;
    }
    
    

    public Termin(Long terminId, String terminTitel, String terminBeschreibung, Time startUhrzeit, Date startDatum, Time endeUhrzeit, Date endeDatum) {
        this.terminId = terminId;
        this.terminTitel = terminTitel;
        this.terminBeschreibung = terminBeschreibung;
        this.startUhrzeit = startUhrzeit;
        this.startDatum = startDatum;
        this.endeUhrzeit = endeUhrzeit;
        this.endeDatum = endeDatum;
    }
    
    public Termin (String terminTitel, String terminBeschreibung, Time startUhrzeit, Date startDatum, Time endeUhrzeit, Date endeDatum) {
        this.terminTitel = terminTitel;
        this.terminBeschreibung = terminBeschreibung;
        this.startUhrzeit = startUhrzeit;
        this.startDatum = startDatum;
        this.endeUhrzeit = endeUhrzeit;
        this.endeDatum = endeDatum;
    }

    public Termin() {
    }
    
    
    

    
    
}
