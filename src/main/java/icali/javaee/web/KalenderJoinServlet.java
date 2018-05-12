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
 * @author Manuel
 */
@WebServlet(urlPatterns = {"/app/kalenderBeitreten/*"})
public class KalenderJoinServlet extends HttpServlet {

    @EJB
    private KalenderBean kalenderBean;

    @EJB
    private BenutzerBean benutzerBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Kalender kalender = this.getRequestedKalender(request);

        session.setAttribute("kalenderTitel", kalender.getKalenderTitel());
        session.setAttribute("kalenderBeschreibung", kalender.getBeschreibung());
        session.setAttribute("kalenderAdmin", kalender.getKalenderAdmin().getUsername());

        if (session.getAttribute("kalenderjoin_form") == null) {
            request.setAttribute("kalenderjoin_form", this.erstelleKalenderJoinForm(kalender));
        }

        boolean mitglied = false;
        for (Benutzer b : this.kalenderBean.findById(kalender.getKalenderTitel()).getBenutzerList()) {
            if (b.getUsername().equals(this.benutzerBean.getCurrentBenutzer().getUsername())) {
                mitglied = true;
            }
        }
        if (mitglied) {
            session.setAttribute("mitglied", true);
        }
        else {
            session.setAttribute("mitglied", false);
        }
        
        
        

        request.getRequestDispatcher("/WEB-INF/app/kalender_join.jsp").forward(request, response);

        session.removeAttribute("kalenderjoin_form");

    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("utf-8");

        String action = request.getParameter("action");

        if (action == null) {
            action = "";
        }

        switch (action) {
            case "join":
                this.kalenderBeitreten(request, response);
                break;
            case "leave":
                this.kalenderVerlassen(request, response);
                break;
        }
    }

    private void kalenderVerlassen(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        Kalender k = this.getRequestedKalender(request);
        List<Benutzer> bList = k.getBenutzerList();
        
        for (Benutzer b : bList) {
            if(b.getUsername().equals(this.benutzerBean.getCurrentBenutzer().getUsername())) {
                
                bList.remove(bList.indexOf(b));
                break;
            }
        }

        k.setBenutzerList(bList);
        this.kalenderBean.update(k);
        HttpSession session = request.getSession();
        session.setAttribute("mitglied", false);
        
        response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
        
        

    }

    private void kalenderBeitreten(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String password1 = request.getParameter("password1");
        String password2 = request.getParameter("password2");

        List<String> errors = new ArrayList<>();

        if (password1 == null || password2 == null) {
            errors.add("Passwort darf nicht leer sein!");
        } else {

            if (!password1.equals(password2)) {
                errors.add("Die beiden Passwörter stimmen nicht überein.");
            } else {
                boolean x = password1.equals(this.getRequestedKalender(request).getKalenderPasswort());
                if (!x) {
                    errors.add("Dies ist nicht das richtige Passwort für den Kalender!");
                }
            }

        }

        if (errors.isEmpty()) {
            Kalender kalender = this.getRequestedKalender(request);
            List<Benutzer> benutzerList = kalender.getBenutzerList();
            benutzerList.add(this.benutzerBean.getCurrentBenutzer());
            kalender.setBenutzerList(benutzerList);
            this.kalenderBean.update(kalender);

            response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
        } else {
            // Fehler: Formuler erneut anzeigen
            FormValues formValues = new FormValues();
            formValues.setValues(request.getParameterMap());
            formValues.setErrors(errors);

            HttpSession session = request.getSession();
            session.setAttribute("kalenderjoin_form", formValues);

            response.sendRedirect(request.getRequestURI());
        }

    }

    private Kalender getRequestedKalender(HttpServletRequest request) {
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

        kalender = this.kalenderBean.findById(kalenderTitel);

        return kalender;
    }

    private FormValues erstelleKalenderJoinForm(Kalender kalender) {
        Map<String, String[]> values = new HashMap<>();

        values.put("password1", new String[]{
            kalender.getKalenderPasswort()
        });

        values.put("password2", new String[]{
            kalender.getKalenderPasswort()
        });

        FormValues formValues = new FormValues();
        formValues.setValues(values);
        return formValues;
    }

}
