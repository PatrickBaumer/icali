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
public class Termin implements Serializable{

    @Id
    @GeneratedValue(generator = "termin_id")
    @TableGenerator(name = "termin_id", initialValue = 0)
    private Long terminId;
    
    @Column(name = "t_titel")
    @NotNull(message = "Bitte geben Sie einen Terminnnamen ein")
    private String terminTitel;
    
    @Column(name = "t_ort")
    private String terminOrt;
        
    @Column(name = "t_beschreibung")
    @Lob
    private String terminBeschreibung;
    
    @Column(name = "t_erinnerung")
    private Boolean terminErinnerung;
    
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
    Kalender terminInKalender = null;
    
    @OneToOne
    Benutzer ersteller = null;
    
    @OneToOne (mappedBy = "katkategorieTermin")
    Kategorie terminKartegorie = null;  
    
    
    

    
    
}
