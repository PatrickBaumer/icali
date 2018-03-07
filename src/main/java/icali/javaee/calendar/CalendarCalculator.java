/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.calendar;

import java.util.Calendar;
import java.util.GregorianCalendar;

/**
 *
 * @author x7
 */
public class CalendarCalculator {
    
    CurrentDate date;
    CurrentTime time;
    
    public CalendarCalculator(){
        
        Calendar calendar = new GregorianCalendar();
        date = new CurrentDate(calendar);
        time = new CurrentTime(calendar);
    
    }
}
