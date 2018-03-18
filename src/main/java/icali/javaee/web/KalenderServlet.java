/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.web;

import icali.javaee.ejb.KalenderBean;
import java.io.IOException;
import java.time.LocalDate;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Manuel
 */
@WebServlet(urlPatterns = {"/app/kalender/"})
public class KalenderServlet extends HttpServlet {
    
    @EJB
    KalenderBean kalenderBean;

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String button = request.getParameter("button");
        if (null != button) switch (button) {
            case "back":
                this.kalenderBean.lastMonthVoid();
                break;
            case "forth":
                this.kalenderBean.nextMonthVoid();
                break;
            case "today":
                this.kalenderBean.setCurrentLocalDate();
                break;
            default:
                break;
        }
        
        LocalDate[][] x = kalenderBean.weeksInMonth(this.kalenderBean.getLocalDate());
        
        int[] week1 = new int[7];
        int[] week2 = new int[7];
        int[] week3 = new int[7];
        int[] week4 = new int[7];
        int[] week5 = new int[7];
        int[] week6 = new int[7];
        
        for (int i=0; i<x.length; i++) {
            if (i==0) {
                for (int j=0; j<7; j++) {
                    week1[j] = x[i][j].getDayOfMonth();
                }
            }
            if (i==1) {
                for (int j=0; j<7; j++) {
                    week2[j] = x[i][j].getDayOfMonth();
                }
            }
            if (i==2) {
                for (int j=0; j<7; j++) {
                    week3[j] = x[i][j].getDayOfMonth();
                }
            }
            if (i==3) {
                for (int j=0; j<7; j++) {
                    week4[j] = x[i][j].getDayOfMonth();
                }
            }
            if (i==4) {
                for (int j=0; j<7; j++) {
                    week5[j] = x[i][j].getDayOfMonth();
                }
            }
            if (i==5) {
                for (int j=0; j<7; j++) {
                    week6[j] = x[i][j].getDayOfMonth();
                }
            }
        }
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
