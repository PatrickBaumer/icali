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
public class CurrentDate {
    
    Calendar calendar;
    
    public CurrentDate(Calendar calendar){
        this.calendar = calendar;
    }
    
    public int getCurrentYear(){
        return calendar.get(Calendar.YEAR);
    }
    public int getCurrentMonth(){
        return calendar.get(Calendar.MONTH); // Jan = 0, not 1
    }
    public int getCurrentDayOfMonth(){
        return calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1 
    }
    public int getCurrentDayOfWeek(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    public int getCurrentWeekOfYear(){
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    public int getCurrentWeekOfMonth(){
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

}
