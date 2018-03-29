/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.BenutzerBean;
import icali.javaee.ejb.KalenderBean;
import icali.javaee.ejb.KategorieBean;
import icali.javaee.ejb.TerminBean;
import icali.javaee.ejb.ValidationBean;
import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Termin;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.HashMap;
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
 * @author Patrick Baumer  
 */
@WebServlet(urlPatterns = {"/app/erstelleTermin/"})
public class ErstelleTerminServlet extends HttpServlet {
    
    @EJB
    ValidationBean validationBean;
    @EJB
    TerminBean terminBean;
    @EJB
    BenutzerBean benutzerBean;
    @EJB
    KategorieBean kategorieBean;
    @EJB
    KalenderBean kalenderBean;
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Verfügbare Kategorien und Stati für die Suchfelder ermitteln
        List<Kalender> kalenderList = this.kalenderBean.findByUser(this.benutzerBean.getCurrentBenutzer());
        request.setAttribute("kalender", kalenderList);
        
        if(request.getParameter("kalender") != null){
            String kalenderId = request.getParameter("kalender");
            Kalender currentKalender = this.kalenderBean.findById(Long.parseLong(kalenderId));
            request.setAttribute("kategories", this.kategorieBean.findCategoriesByKalenderId(currentKalender));
        }
       
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/termin_edit.jsp").forward(request, response);

    }
    
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Angeforderte Aktion ausführen
        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "save":
                this.erstelleTermin(request, response);
                break;
            case "delete":
                this.loescheTermin(request, response);
                break;
        }
    }  

    private void erstelleTermin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String terminTitel = request.getParameter("terminTitel");
        Date anfangsDatum = WebUtils.parseDate(request.getParameter("anfangsDatum"));
        Time anfangszeit = WebUtils.parseTime(request.getParameter("anfangszeit"));
        Date endDatum = WebUtils.parseDate(request.getParameter("endDatum"));
        Time endzeit = WebUtils.parseTime(request.getParameter("endzeit"));
        String beschreibung = request.getParameter("beschreibung");
        String kalenderId = request.getParameter("termin_kalende");
        String kategorie = request.getParameter("termin_category");

        Termin termin = new Termin();
        termin.setTerminTitel(terminTitel);
        termin.setTerminBeschreibung(beschreibung);
        
        if (kategorie != null && !kategorie.trim().isEmpty()) {
            try {
                termin.setTerminKartegorie(this.kategorieBean.findById(Long.parseLong(kategorie)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }
 
        if (anfangsDatum != null) {
            termin.setStartDatum(anfangsDatum);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (anfangszeit != null) {
            termin.setStartUhrzeit(anfangszeit);
        } else { 
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }   
        
        if (endDatum != null) {
            termin.setEndeDatum(endDatum);        
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (endzeit != null) {
            termin.setEndeDatum(endDatum);
        } else { 
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }

        this.validationBean.validate(termin, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.terminBean.update(termin);
            Kalender kalender = this.kalenderBean.findById(Long.parseLong(kalenderId));
            List<Termin> terminList = new ArrayList<>(kalender.getTerminList());
            terminList.add(termin);
            kalender.setTerminList(terminList);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("task_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }
  
   private void loescheTermin(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Termin termin = new Termin();
        this.terminBean.delete(termin);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/main/"));
    }
   
   
private FormValues erstelleTerminForm(Termin termin) {
        Map<String, String[]> values = new HashMap<>();

        values.put("termin_owner", new String[]{
            termin.getErsteller().getUsername()
        });

        if (termin.getTerminKartegorie()!= null) {
            values.put("task_category", new String[]{
                termin.getTerminKartegorie().toString()
            });
        }

        values.put("anfangsDatum", new String[]{
            WebUtils.formatDate((Date) termin.getStartDatum())
        });

        values.put("anfangszeit", new String[]{
            WebUtils.formatTime(termin.getStartUhrzeit())
        });
        
        values.put("endDatum", new String[]{
            WebUtils.formatDate((Date) termin.getEndeDatum())
        });

        values.put("endzeit", new String[]{
            WebUtils.formatTime(termin.getEndeUhrzeit())
        });

        values.put("terminTitel", new String[]{
            termin.getTerminTitel().toString()
        });

        values.put("beschreibung", new String[]{
            termin.getTerminBeschreibung()
        });


        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }
}
