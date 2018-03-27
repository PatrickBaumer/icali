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
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 *
 * @author Patrick Baumer
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
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
    Kalender kategorieKalender =null;

    public Kategorie( String kategorieName, Farbe kategorieFarbe) {
        this.kategorieName = kategorieName;
        this.kategorieFarbe = kategorieFarbe;
    }
    
    
}
