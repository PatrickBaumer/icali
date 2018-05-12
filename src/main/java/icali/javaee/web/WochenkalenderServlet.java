/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.BenutzerBean;
import icali.javaee.ejb.KalenderBean;
import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Termin;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Manuel
 */
@WebServlet(urlPatterns = {"/app/wochenansicht/"})
public class WochenkalenderServlet extends HttpServlet {
    
    @EJB
    KalenderBean kalenderBean;
    
    @EJB
    BenutzerBean benutzerBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String calendar_button = request.getParameter("calendar_button");
        if (null != calendar_button) switch (calendar_button) {
            case "week_back":
                this.kalenderBean.lastWeekVoid();
                break;
            case "week_forth":
                this.kalenderBean.nextWeekVoid();
                break;
            case "today":
                this.kalenderBean.setCurrentLocalDate();
                break;
            default:
                break;
        } 
        
        Benutzer benutzer = this.benutzerBean.getCurrentBenutzer();
        List<Kalender> kList = this.benutzerBean.findAllKalenderByUser(benutzer);
        List<Termin> tList = new ArrayList<>();
        
        //alle Termine in eine Liste
        for (Kalender hilfK : kList) {
            tList.addAll(hilfK.getTerminList());
        }
        
        //Nur Termine des jeweiligen Monats in eine Liste
        List<Termin> monatsList = new ArrayList<>();
        for (Termin hilfsTermin : tList) {
            if (hilfsTermin.getStartDatum().toLocalDate().getMonthValue() == this.kalenderBean.getLocalDate().getMonthValue() && hilfsTermin.getStartDatum().toLocalDate().getYear() == this.kalenderBean.getLocalDate().getYear()) {
                monatsList.add(hilfsTermin);
            }
        }
        
        
        
        
        
        
        
        request.setAttribute("mk_activated", false);
        List<Kalender> sidebar_kalender = this.benutzerBean.findAllKalenderByBenutzer(this.benutzerBean.getCurrentBenutzer().getUsername());       
        request.setAttribute("sidebar_kalender",sidebar_kalender);
        
        
        request.setAttribute("shown_month", this.kalenderBean.getMonthName());
        request.setAttribute("week_of_year", this.kalenderBean.getWeekOfYear());
        request.setAttribute("shown_year", this.kalenderBean.getYear());
        request.setAttribute("week",kalenderBean.weekWithTermin(kalenderBean.getLocalDate(), monatsList));
        request.getRequestDispatcher("/WEB-INF/app/week_calendar.jsp").forward(request, response);

    }
}
