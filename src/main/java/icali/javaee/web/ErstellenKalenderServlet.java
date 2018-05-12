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
@WebServlet(urlPatterns = "/app/gruppenkalender/*")
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
        
       
        
        HttpSession session = request.getSession();
        Kalender kalender = this.getRequestedKalender(request);
        Benutzer benutzer = this.benutzerBean.getCurrentBenutzer();
        
        
        
        if (kalender.getKalenderTitel() == null) {
           request.setAttribute("edit", false);
           request.setAttribute("readonly", false);
        } else {
            request.setAttribute ("kategorien", this.kategorieBean.findCategoriesByKalenderTitel(kalender.getKalenderTitel()));
            request.setAttribute("edit", true);
            request.setAttribute("readonly", kalender.getKalenderAdmin().equals(benutzer));
        }
        

        
        if (session.getAttribute("kalender_form") == null) {
            request.setAttribute("kalender_form", this.erstelleKalenderForm(kalender));
        }
        
        request.getRequestDispatcher("/WEB-INF/app/kalender_edit.jsp").forward(request, response);
        
        session.removeAttribute("kalender_form");

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
            case "verify":
                HttpSession session = request.getSession();
                session.setAttribute("idAnzeige", false);
                response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
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
        
        Kalender kalender = this.getRequestedKalender(request);
        
        kalender.setKalenderTitel(kalenderTitel);
        kalender.setBeschreibung(kalenderBeschreibung);
        
        if (password1 != null && password2 != null && !password1.equals(password2)) {
            errors.add("Die beiden Passwörter stimmen nicht überein.");
        }

        kalender.setKalenderAdmin(this.benutzerBean.getCurrentBenutzer());      
        kalender.setKalenderPasswort(password1);
        List<Benutzer> benutzerList = new ArrayList<>();
        benutzerList.add(this.benutzerBean.getCurrentBenutzer());
        kalender.setBenutzerList(benutzerList);
        
        
        
        
        List<Kategorie> kategorieList = new ArrayList<>();
        


        

        this.validationBean.validate(kalender, errors);
        
        if (errors.isEmpty() && this.getRequestedKalender(request).getKalenderTitel() == null) {
            try {
                this.kalenderBean.kalenderTry(kalenderTitel);
            } catch (KalenderBean.KalenderAlreadyExistsException ex) {
                errors.add(ex.getMessage());
            }
        }
        
        if (errors.isEmpty()){
            this.kalenderBean.update(kalender);
            
        if(kategorieGelb != null || !kategorieGelb.equals("")) {
            Kategorie katGelb = new Kategorie(kategorieGelb, Farbe.Gelb);
            katGelb.setKategorieKalender(this.kalenderBean.findById(kalenderTitel));
            kategorieList.add(this.kategorieBean.update(katGelb));
        }
        if(kategorieGruen != null  || !kategorieGruen.equals("")) {
            Kategorie katGruen = new Kategorie(kategorieGruen, Farbe.Grün);
            katGruen.setKategorieKalender(this.kalenderBean.findById(kalenderTitel));
            kategorieList.add(this.kategorieBean.update(katGruen));
        }
        if(kategorieBlau != null  || !kategorieBlau.equals("")) {
            Kategorie katBlau = new Kategorie(kategorieBlau, Farbe.Blau);
            katBlau.setKategorieKalender(this.kalenderBean.findById(kalenderTitel));            
            kategorieList.add(this.kategorieBean.update(katBlau));
        }
        if(kategorieRot != null  || !kategorieRot.equals("")) {
            Kategorie katRot = new Kategorie(kategorieRot, Farbe.Rot);
            katRot.setKategorieKalender(this.kalenderBean.findById(kalenderTitel));
            kategorieList.add(this.kategorieBean.update(katRot));
        }
        if(kategorieLila != null  || !kategorieLila.equals("")) {
            Kategorie katLila = new Kategorie(kategorieLila, Farbe.Lila);
            katLila.setKategorieKalender(this.kalenderBean.findById(kalenderTitel));
            kategorieList.add(this.kategorieBean.update(katLila));
        }
        if(kategorieBraun != null  || !kategorieBraun.equals("")) {
            Kategorie katBraun = new Kategorie(kategorieBraun, Farbe.Braun);
            katBraun.setKategorieKalender(this.kalenderBean.findById(kalenderTitel));
            kategorieList.add(this.kategorieBean.update(katBraun));
        }
        
        kalender.setKalenderKategorie(kategorieList);
        
        this.kalenderBean.update(kalender);

            
            //Kalender dem Benutzer hinzufügen
            List<Kalender> kList = this.benutzerBean.getCurrentBenutzer().getKalenderList();
            kList.add(this.kalenderBean.findById(kalenderTitel));
            this.benutzerBean.getCurrentBenutzer().setKalenderList(kList);
            this.benutzerBean.update(this.benutzerBean.getCurrentBenutzer());
        
        }
        
        if (errors.isEmpty()){              
            HttpSession session = request.getSession();
            session.setAttribute("idAnzeige", true);
            session.setAttribute("kId", this.kalenderBean.findById(kalenderTitel).getKalenderTitel());
            response.sendRedirect(request.getRequestURI());
            
        }else{
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);
            
            HttpSession session = request.getSession();
            session.setAttribute("kalender_form", formValues);
            
            response.sendRedirect(request.getRequestURI());
        }

  
    }


    private void loescheKalender(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Datensatz löschen
        // zum löschen muss die benötigte kalenderId aus der jsp ausgelesen werden
        Kalender kalender = this.getRequestedKalender(request);
        this.kalenderBean.delete(kalender);

        // Zurück zur Übersicht
        response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
    }
    
    private Kalender getRequestedKalender (HttpServletRequest request) {
        Kalender kalender = new Kalender();
        kalender.setKalenderAdmin(this.benutzerBean.getCurrentBenutzer());
        
        String kalenderTitel = request.getPathInfo();
        
        if (kalenderTitel == null) {
            kalenderTitel = "";
        }
        
        kalenderTitel = kalenderTitel.substring(1);
        
        if (kalenderTitel.endsWith("/")) {
            kalenderTitel = kalenderTitel.substring(0, kalenderTitel.length() - 1);
        }
        
        if (this.kalenderBean.findById(kalenderTitel) != null){
        kalender = this.kalenderBean.findById(kalenderTitel);
        }
        
 
        return kalender;
    }

    private FormValues erstelleKalenderForm(Kalender kalender) {
        
        Map<String, String[]> values = new HashMap<>();


        if (kalender.getKalenderKategorie()!= null) {
            values.put("kalender_category", new String[]{
                kalender.getKalenderKategorie().toString()
            });
        }
        
        values.put("kalenderTitel", new String[]{
            kalender.getKalenderTitel()
        });
        
        values.put("kB", new String[]{
            kalender.getBeschreibung()
        });
        
        values.put("password1", new String[]{
            kalender.getKalenderPasswort()
        });
        
        values.put("password2", new String[]{
            kalender.getKalenderPasswort()
        });
        
        String [] kategorieArray = new String[6];
        int i = 0;
        for (Kategorie hilf : kalender.getKalenderKategorie()) {
            kategorieArray[i] = hilf.getKategorieName();
            i++;
        }
        values.put("kategorie", kategorieArray);
        
        
        
        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}