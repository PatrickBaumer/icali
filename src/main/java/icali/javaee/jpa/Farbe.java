/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.jpa;

/**
 *
 * @author Patrick Baumer
 */
public enum Farbe {
    Gelb,Grün,Blau,Rot,Lila,Braun;
    
    public String getColor() {
        switch (this) {
            case Rot:
                return "#FF0000";
            case Gelb:
                return "#FFFF00";
            case Grün:
                return "#00FF00";
            case Blau:
                return "#0000FF";
            case Lila:
                return "#8000FF";
            case Braun:
                return "#2A1B0A";
            default:
                return this.toString();
        }
    }
    
}
