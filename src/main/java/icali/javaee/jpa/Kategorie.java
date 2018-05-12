/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.jpa;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.TableGenerator;
import javax.validation.constraints.NotNull;


/**
 *
 * @author Patrick Baumer
 */
@Entity
public class Kategorie implements Serializable{

    @Id
    @GeneratedValue(generator = "kalender_id")
    @TableGenerator(name = "kalender_id", initialValue = 0)
    private Long id;

    @Column(name = "kat_name")
    @NotNull(message = "Bitte eine Kategorie vergeben")
    private String kategorieName;
    
    
    @Column(name = "kat_farbe")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Bitte eine Farbe hinterlegen")
    private Farbe kategorieFarbe;
    
    @OneToOne
    Termin katkategorieTermin = null;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Kalender kategorieKalender = null;

    public Kategorie( String kategorieName, Farbe kategorieFarbe) {
        this.kategorieName = kategorieName;
        this.kategorieFarbe = kategorieFarbe;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKategorieName() {
        return kategorieName;
    }

    public void setKategorieName(String kategorieName) {
        this.kategorieName = kategorieName;
    }

    public Farbe getKategorieFarbe() {
        return kategorieFarbe;
    }

    public void setKategorieFarbe(Farbe kategorieFarbe) {
        this.kategorieFarbe = kategorieFarbe;
    }

    public Termin getKatkategorieTermin() {
        return katkategorieTermin;
    }

    public void setKatkategorieTermin(Termin katkategorieTermin) {
        this.katkategorieTermin = katkategorieTermin;
    }

    public Kalender getKategorieKalender() {
        return kategorieKalender;
    }

    public void setKategorieKalender(Kalender kategorieKalender) {
        this.kategorieKalender = kategorieKalender;
    }

    public Kategorie() {
    }
    
    
}
