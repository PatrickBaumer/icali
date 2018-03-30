/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.BenutzerBean;
import icali.javaee.ejb.KalenderBean;
import icali.javaee.ejb.KategorieBean;
import icali.javaee.ejb.ValidationBean;
import icali.javaee.jpa.Benutzer;
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
    
    @EJB
    KategorieBean kategorieBean;
    
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
        
        String kalenderTitel = request.getParameter("kalenderTitel");
        String kalenderBeschreibung = request.getParameter("kalenderBeschreibung");
        String kategorieGelb = request.getParameter("gelbBeschreibung");
        String kategorieGruen = request.getParameter("gruenBeschreibung");
        String kategorieBlau = request.getParameter("blauBeschreibung");
        String kategorieRot = request.getParameter("rotBeschreibung");
        String kategorieLila = request.getParameter("lilaBeschreibung");
        String kategorieBraun = request.getParameter("braunBeschreibung");
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");
 
        List<String> errors = new ArrayList<>();
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }
        
        Kalender kalender = new Kalender();
        kalender.setKalenderTitel(kalenderTitel);
        kalender.setBeschreibung(kalenderBeschreibung);
        kalender.setKalenderAdmin(this.benutzerBean.getCurrentBenutzer());
       
        kalender.setPassword(password1);
        List<Benutzer> benutzerList = new ArrayList<>();
        benutzerList.add(this.benutzerBean.getCurrentBenutzer());
        kalender.setBenutzerList(benutzerList);
        benutzerBean.getCurrentBenutzer().getKalenderList().add(kalender);
        
        
        
        List<Kategorie> kategorieList = new ArrayList<>();
        
        if(kategorieGelb != null)
            kategorieList.add(this.kategorieBean.saveNew(new Kategorie(kategorieGelb, Farbe.Gelb)));
        if(kategorieGruen != null)
            kategorieList.add(this.kategorieBean.saveNew(new Kategorie(kategorieGruen, Farbe.Grün)));
        if(kategorieBlau != null)
            kategorieList.add(this.kategorieBean.saveNew(new Kategorie(kategorieBlau, Farbe.Blau)));
        if(kategorieRot != null)
            kategorieList.add(this.kategorieBean.saveNew(new Kategorie(kategorieRot, Farbe.Rot)));
        if(kategorieLila != null)
            kategorieList.add(this.kategorieBean.saveNew(new Kategorie(kategorieLila, Farbe.Lila)));
        if(kategorieBraun != null)
            kategorieList.add(this.kategorieBean.saveNew(new Kategorie(kategorieBraun, Farbe.Braun)));
        
        kalender.setKalenderKategorie(kategorieList);

        errors.addAll(validationBean.validate(kalender));
        
        if(errors.isEmpty()){
            this.kalenderBean.saveNew(kalender);
            this.benutzerBean.getCurrentBenutzer().getKalenderList().add(kalender);
            response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
        }else{
            // fehlermeldung popup
        }
  
    }


    private void loescheKalender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        // zum löschen muss die benötigte kalenderId aus der jsp ausgelesen werden
        Kalender kalender = this.kalenderBean.findById(1L);
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