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
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
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
@AllArgsConstructor
public class Kategorie implements Serializable{

    @Id
    private Long id;

    @Column(name = "kat_name")
    @NotNull(message = "Bitte eine Kategorie vergeben")
    private String kategorieName;
    
    
    @Column(name = "kat_farbe")
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Bitte eine Farbe hinterlegen")
    private Farbe kategorieFarbe;
    
    @OneToOne(mappedBy = "terminKartegorie")
    Termin katkategorieTermin = null;
    
    @ManyToOne(fetch = FetchType.LAZY)
    Kalender kategorieKalender =null;
    
    
    
    
}
