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
import java.util.Map;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Manuel
 */
@WebServlet(urlPatterns = {"/app/kalender/"})
public class KalenderServlet extends HttpServlet {
    
    @EJB
    KalenderBean kalenderBean;
    
    @EJB
    BenutzerBean benutzerBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String calendar_button = request.getParameter("calendar_button");
        if (null != calendar_button) switch (calendar_button) {
            case "month_back":
                this.kalenderBean.lastMonthVoid();
                break;
            case "month_forth":
                this.kalenderBean.nextMonthVoid();
                break;
            case "today":
                this.kalenderBean.setCurrentLocalDate();
                break;
            case "year_back":
                this.kalenderBean.lastYearVoid();
                break;
            case "year_forth":
                this.kalenderBean.nextYearVoid();
                break;
            default:
                break;
        }
        
        /**
         * 
         * LocalDate[Welche Woche: 0-5][Welcher Tag in der Woche: 0-6 für Montag bis Sonntag]
         * 
         * 
         */
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
        
        
        
        
        
        
        
        
        
        
        
        LocalDate[][] monatsKalender = kalenderBean.weeksInMonth(this.kalenderBean.getLocalDate());
        
        Map<LocalDate,List<Termin>> week1 = kalenderBean.weeksInMonthForWeek(monatsKalender,monatsList, 0);
        Map<LocalDate,List<Termin>> week2 = kalenderBean.weeksInMonthForWeek(monatsKalender,monatsList, 1);
        Map<LocalDate,List<Termin>> week3 = kalenderBean.weeksInMonthForWeek(monatsKalender,monatsList, 2);
        Map<LocalDate,List<Termin>> week4 = kalenderBean.weeksInMonthForWeek(monatsKalender,monatsList, 3);
        Map<LocalDate,List<Termin>> week5 = kalenderBean.weeksInMonthForWeek(monatsKalender,monatsList, 4);
        Map<LocalDate,List<Termin>> week6 = kalenderBean.weeksInMonthForWeek(monatsKalender,monatsList, 5);
        

      
        
        
//        LocalDate[] week1 = new LocalDate[7];
//        LocalDate[] week2 = new LocalDate[7];
//        LocalDate[] week3 = new LocalDate[7];
//        LocalDate[] week4 = new LocalDate[7];
//        LocalDate[] week5 = new LocalDate[7];
//        LocalDate[] week6 = new LocalDate[7];
//        
//        
//        
//        for (int i= 0; i < monatsKalender.length; i++) {
//            switch (i) {
//            case 0:
//                System.arraycopy(monatsKalender[i], 0, week1, 0, 7);
//                break;
//            case 1: 
//                System.arraycopy(monatsKalender[i], 0, week2, 0, 7);
//                break;            
//            case 2: 
//                System.arraycopy(monatsKalender[i], 0, week3, 0, 7);
//                break;
//            case 3: 
//                System.arraycopy(monatsKalender[i], 0, week4, 0, 7);
//                break;
//            case 4: 
//                System.arraycopy(monatsKalender[i], 0, week5, 0, 7);
//                break;
//            case 5: 
//                System.arraycopy(monatsKalender[i], 0, week6, 0, 7);
//                break;
//            default:
//                break;
//            }
//        }


        List<Kalender> sidebar_kalender = this.benutzerBean.findAllKalenderByUser(this.benutzerBean.getCurrentBenutzer());
        
        request.setAttribute("mk_activated", true);
        request.setAttribute("sidebar_kalender",sidebar_kalender);
        request.setAttribute("shown_month", this.kalenderBean.getMonthName());
        request.setAttribute("shown_year", this.kalenderBean.getYear());
        request.setAttribute("week1",week1);
        request.setAttribute("week2",week2);
        request.setAttribute("week3",week3);
        request.setAttribute("week4",week4);
        request.setAttribute("week5",week5);
        request.setAttribute("week6",week6);
        request.getRequestDispatcher("/WEB-INF/app/main.jsp").forward(request, response);

    }
}
