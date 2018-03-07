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
public class Date {
    
    Calendar calendar;
    
    public Date(Calendar calendar){
        this.calendar = calendar;
    }
    
    public int getYear(){
        return calendar.get(Calendar.YEAR);
    }
    public int getMonth(){
        return calendar.get(Calendar.MONTH); // Jan = 0, not 1
    }
    public int getDayOfMonth(){
        return calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1 
    }
    public int getDayOfWeek(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public int getWeekOfYear(){
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public int getWeekOfMonth(){
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

}
