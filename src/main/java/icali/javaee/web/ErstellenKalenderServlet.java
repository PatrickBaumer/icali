/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.BenutzerBean;
import icali.javaee.ejb.KalenderBean;
import icali.javaee.ejb.ValidationBean;
import icali.javaee.jpa.Farbe;
import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Kategorie;
import java.io.IOException;
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
@WebServlet(urlPatterns = {"/app/erstelleKalender/"})
public class ErstellenKalenderServlet extends HttpServlet {

    @EJB
    ValidationBean validationBean;

    @EJB
    KalenderBean kalenderBean;

    @EJB
    BenutzerBean benutzerBean;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/app/kalender_edit.jsp").forward(request, response);
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
                this.erstelleKalender(request, response);
                break;
            case "delete":
                this.loescheKalender(request, response);
                break;
        }
    }

    private void erstelleKalender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Formulareingaben prüfen
        List<String> errors = new ArrayList<>();

        String kalenderTitel = request.getParameter("kalenderTitel");
        String kalenderBeschreibung = request.getParameter("kalenderBeschreibung");
        String kategorieGelb = request.getParameter("gelbBeschreibung");
        String kategorieGruen = request.getParameter("gruenBeschreibung");
        String kategorieBlau = request.getParameter("blauBeschreibung");
        String kategorieRot = request.getParameter("rotBeschreibung");
        String kategorieLila = request.getParameter("lilaBeschreibung");
        String kategorieBraun = request.getParameter("braunBeschreibung");

        Kalender kalender = this.getRequestedKalender(request);
        kalender.setKalenderTitel(kalenderTitel);
        kalender.setBeschreibung(kalenderBeschreibung);
        
        List<Kategorie> kategorieList = new ArrayList<>();
        
        if(kategorieGelb != null)
            kategorieList.add(new Kategorie(kategorieGelb, Farbe.Gelb));
        if(kategorieGruen != null)
            kategorieList.add(new Kategorie(kategorieGruen, Farbe.Grün));
        if(kategorieBlau != null)
            kategorieList.add(new Kategorie(kategorieBlau, Farbe.Blau));
        if(kategorieRot != null)
            kategorieList.add(new Kategorie(kategorieRot, Farbe.Rot));
        if(kategorieLila != null)
            kategorieList.add(new Kategorie(kategorieLila, Farbe.Lila));
        if(kategorieBraun != null)
            kategorieList.add(new Kategorie(kategorieBraun, Farbe.Braun));
        
        kalender.setKalenderKategorie(kategorieList);
        
        this.validationBean.validate(kalender, errors);

        // Datensatz speichern
        if (errors.isEmpty()) {
            this.kalenderBean.update(kalender);
        }

        // Weiter zur nächsten Seite
        if (errors.isEmpty()) {
            // Keine Fehler: Startseite aufrufen
            response.sendRedirect(WebUtils.appUrl(request, "/app/main"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("kalender_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }
    }

    private Kalender getRequestedKalender(HttpServletRequest request) {
        // Zunächst davon ausgehen, dass ein neuer Satz angelegt werden soll
        Kalender kalender = new Kalender();
        // Hier sollte überprüft werden ob der CurrentBenutzer Admin ist
//        kalender.setErsteller(this.benutzerBean.getCurrentBenutzer());

        // ID aus der URL herausschneiden
        String kalenderId = request.getPathInfo();

        if (kalenderId == null) {
            kalenderId = "";
        }

        kalenderId = kalenderId.substring(1);

        if (kalenderId.endsWith("/")) {
            kalenderId = kalenderId.substring(0, kalenderId.length() - 1);
        }

        // Versuchen, den Datensatz mit der übergebenen ID zu finden
        try {
            kalender = this.kalenderBean.findById(Long.parseLong(kalenderId));
        } catch (NumberFormatException ex) {
            // Ungültige oder keine ID in der URL enthalten
        }

        return kalender;
    }
    
    private void loescheKalender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        Kalender kalender = this.getRequestedKalender(request);
        this.kalenderBean.delete(kalender);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/main/"));
    }

    private FormValues erstellekalenderForm(Kalender kalender) {
        Map<String, String[]> values = new HashMap<>();

        values.put("kalender_admin", new String[]{
            kalender.getKalenderAdmin().getUsername()
        });

        if (kalender.getKalenderKategorie()!= null) {
            values.put("kalender_category", new String[]{
                kalender.getKalenderKategorie().toString()
            });
        }

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}