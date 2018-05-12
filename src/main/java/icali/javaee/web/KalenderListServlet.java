/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.KalenderBean;
import icali.javaee.jpa.Kalender;
import java.io.IOException;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet für die Startseite bzw. jede Seite, die eine Liste der Aufgaben
 * zeigt.
 */
@WebServlet(urlPatterns = {"/app/kalenderSuchen/"})
public class KalenderListServlet extends HttpServlet {
    
    @EJB
    private KalenderBean kalenderBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        
        
        String searchTitel = request.getParameter("search_titel");

        
        if (searchTitel != null) {
        Kalender kalender = this.kalenderBean.findById(searchTitel);
        request.setAttribute("kalender", kalender);
        }
        

        // Anfrage an die JSP weiterleiten
        request.getRequestDispatcher("/WEB-INF/app/kalender_list.jsp").forward(request, response);
    }  
}
