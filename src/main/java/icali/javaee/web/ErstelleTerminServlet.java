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
import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Kategorie;
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
        HttpSession session = request.getSession();

        Benutzer benutzer = this.benutzerBean.getCurrentBenutzer();
        List<Kalender> kalenderList = benutzer.getKalenderList();
        session.setAttribute("allKalender", kalenderList);

        Termin termin = this.getRequestedTermin(request);

        if (termin.getTerminId() == null) {
            request.setAttribute("edit", false);
            request.setAttribute("readonly", false);
        } else {
            request.setAttribute("edit", true);
            request.setAttribute("readonly", termin.getErsteller().getUsername().equals(benutzer.getUsername()));
        }

        if (session.getAttribute("termin_form") == null) {
            // Keine Formulardaten mit fehlerhaften Daten in der Session,
            // daher Formulardaten aus dem Datenbankobjekt übernehmen
            request.setAttribute("termin_form", this.erstelleTerminForm(termin));
        }

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/termin_edit.jsp").forward(request, response);

        session.removeAttribute("termin_form");
        session.removeAttribute("kalender");

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
            case "choose":
                this.kalenderGewaehlt(request, response);
                break;
                
        }

    }

    private void kalenderGewaehlt(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        session.setAttribute("gewaehlt", true);
        String kalenderTitel = request.getParameter("kalenderWahl");
        Termin termin = new Termin();
        termin.setTerminInKalender(this.kalenderBean.findById(kalenderTitel));
        FormValues formValues = this.erstelleTerminForm(termin);

        List<Kategorie> katList = this.kategorieBean.findCategoriesByKalenderTitel(kalenderTitel);

        session.setAttribute("categories", katList);
        session.setAttribute("termin_form", formValues);
        response.sendRedirect(request.getRequestURI());

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
        String kalenderTitel = request.getParameter("kalenderWahl");
        String kategorie = request.getParameter("termin_category");

        Termin termin = new Termin();
        termin.setTerminTitel(terminTitel);
        termin.setTerminBeschreibung(beschreibung);

        if (kategorie != null && !kategorie.trim().isEmpty()) {
            try {
                Kategorie kategorieK = this.kategorieBean.findById(Long.parseLong(kategorie));
                termin.setTerminKategorie(kategorieK);
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

        termin.setErsteller(this.benutzerBean.getCurrentBenutzer());

        errors.addAll(validationBean.validate(termin));

        // Datensatz speichern
        if (errors.isEmpty()) {
            Kalender kalender = this.kalenderBean.findById(kalenderTitel);
            termin.setTerminInKalender(kalender);
            this.terminBean.update(termin);
            List<Termin> terminList = kalender.getTerminList();
            terminList.add(termin);
            kalender.setTerminList(terminList);
            this.kalenderBean.update(kalender);

            response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
        }
        if (errors.isEmpty()) {

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
        Termin termin = this.getRequestedTermin(request);
        this.terminBean.delete(termin);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
    }

    private Termin getRequestedTermin(HttpServletRequest request) {
        Termin termin = new Termin();
        termin.setErsteller(this.benutzerBean.getCurrentBenutzer());

        String terminId = request.getPathInfo();

        if (terminId == null) {
            terminId = "";
        }

        terminId = terminId.substring(1);

        if (terminId.endsWith("/")) {
            terminId = terminId.substring(0, terminId.length() - 1);
        }

        try {
            termin = this.terminBean.findById(Long.parseLong(terminId));
        } catch (NumberFormatException ex) {

        }
        return termin;
    }

    private FormValues erstelleTerminForm(Termin termin) {
        Map<String, String[]> values = new HashMap<>();
        
        if (termin.getTerminTitel() != null) {
            values.put("terminTitel", new String[]{
                termin.getTerminTitel()
            });
        }

        if (termin.getTerminInKalender() != null) {
            values.put("termin_kalender", new String[]{
                termin.getTerminInKalender().getKalenderTitel()
            });
        }

        if (termin.getTerminKategorie() != null) {
            values.put("termin_category", new String[]{
                termin.getTerminKategorie().toString()
            });
        }

        if (termin.getStartDatum() != null) {
            values.put("anfangsDatum", new String[]{
                WebUtils.formatDate((Date) termin.getStartDatum())
            });
        }

        if (termin.getStartUhrzeit() != null) {
            values.put("anfangsZeit", new String[]{
                WebUtils.formatTime(termin.getStartUhrzeit())
            });
        }

        if (termin.getEndeDatum() != null) {
            values.put("endDatum", new String[]{
                WebUtils.formatDate((Date) termin.getEndeDatum())
            });
        }

        if (termin.getEndeUhrzeit() != null) {
            values.put("endZeit", new String[]{
                WebUtils.formatTime(termin.getEndeUhrzeit())
            });
        }

        if (termin.getTerminBeschreibung() != null) {
            values.put("beschreibung", new String[]{
                termin.getTerminBeschreibung()
            });
        }

        if (termin.getTerminKategorie() != null) {
            values.put("category", new String[]{
                termin.getTerminKategorie().getKategorieName()
            });
        }

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
