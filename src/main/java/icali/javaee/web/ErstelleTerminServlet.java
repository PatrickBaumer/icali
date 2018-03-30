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
@WebServlet(urlPatterns = {"/app/erstelleTermin/*"})
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
        List<Kalender> kalenderList = this.benutzerBean.findAllKalenderByUser(this.benutzerBean.getCurrentBenutzer());
        request.setAttribute("kalender", kalenderList);
        
        if(request.getParameter("kalender") != null){
            String kalenderId = request.getParameter("kalender");
            Kalender currentKalender = this.kalenderBean.findById(Long.parseLong(kalenderId));
            request.setAttribute("kategories", this.kategorieBean.findCategoriesByKalenderId(currentKalender));
        }
        
        HttpSession session = request.getSession();

        Termin termin = new Termin();
        if (session.getAttribute("termin_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("termin_form", this.erstelleTerminForm(termin));
        }
       
        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/termin_edit.jsp").forward(request, response);
        
        session.removeAttribute("termin_form");

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
        String anfangsdatum = request.getParameter("anfangsDatum");
        String anfangszeit = request.getParameter("anfangsZeit");
        String enddatum = request.getParameter("endDatum");
        String endzeit = request.getParameter("endZeit");
        String beschreibung = request.getParameter("beschreibung");
        String kalenderId = request.getParameter("termin_kalende");
        String kategorie = request.getParameter("termin_category");
        
        
        Termin termin = new Termin();
        termin.setTerminTitel(terminTitel);
        termin.setTerminBeschreibung(beschreibung);
        
        if (kategorie != null && !kategorie.trim().isEmpty()) {
            try {
                termin.setTerminKategorie(this.kategorieBean.findById(Long.parseLong(kategorie)));
            } catch (NumberFormatException ex) {
                // Ungültige oder keine ID mitgegeben
            }
        }
        
        Date anfangsDatum = WebUtils.parseDate(anfangsdatum);
        Time anfangsZeit = WebUtils.parseTime(anfangszeit);
        Date endDatum = WebUtils.parseDate(enddatum);
        Time endZeit = WebUtils.parseTime(endzeit);
 
        if (anfangsDatum != null) {
            termin.setStartDatum(anfangsDatum);
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (anfangsZeit != null) {
            termin.setStartUhrzeit(anfangsZeit);
        } else { 
            errors.add("Die Uhrzeit muss dem Format hh:mm:ss entsprechen.");
        }   
        
        if (endDatum != null) {
            termin.setEndeDatum(endDatum);        
        } else {
            errors.add("Das Datum muss dem Format dd.mm.yyyy entsprechen.");
        }

        if (endZeit != null) {
            termin.setEndeUhrzeit(endZeit);
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
            response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("termin_form", formValues);

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

        if (termin.getTerminInKalender()!= null) {
        values.put("termin_kalender", new String[]{
            termin.getTerminInKalender().getKalenderId().toString()
        });
        }

        if (termin.getTerminKategorie()!= null) {
            values.put("task_category", new String[]{
                termin.getTerminKategorie().toString()
            });
        }

        if (termin.getStartDatum()!= null) {
        values.put("anfangsDatum", new String[]{
            WebUtils.formatDate((Date) termin.getStartDatum())
        });
        }

        if (termin.getStartUhrzeit()!= null) {
        values.put("anfangszeit", new String[]{
            WebUtils.formatTime(termin.getStartUhrzeit())
        });
        }
        
        if (termin.getEndeDatum()!= null) {
        values.put("endDatum", new String[]{
            WebUtils.formatDate((Date) termin.getEndeDatum())
        });
        }

        if (termin.getEndeUhrzeit()!= null) {
        values.put("endzeit", new String[]{
            WebUtils.formatTime(termin.getEndeUhrzeit())
        });
        }

        if (termin.getTerminTitel()!= null) {
        values.put("terminTitel", new String[]{
            termin.getTerminTitel()
        });
        }

        if (termin.getTerminBeschreibung()!= null) {
        values.put("beschreibung", new String[]{
            termin.getTerminBeschreibung()
        });
        }


        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }


}
