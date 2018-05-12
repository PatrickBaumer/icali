/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.BenutzerBean;
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
 *
 * @author x7
 */
@WebServlet(urlPatterns = {"/index.html"})
public class IndexServlet extends HttpServlet{
    

        /**
     * GET-Anfrage: Seite anzeigen
     * 
     * @param request
     * @param response
     * @throws IOException
     * @throws ServletException 
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws IOException, ServletException {
        response.sendRedirect(WebUtils.appUrl(request, "/app/kalender/"));
    }
}
