/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.calendar;

import java.util.Calendar;

/**
 *
 * @author x7
 */
public class Date {
    
    Calendar calendar;
    
    protected Date(Calendar calendar){
        this.calendar = calendar;
    }
    
    protected int getYear(){
        return calendar.get(Calendar.YEAR);
    }
    protected int getMonth(){
        return calendar.get(Calendar.MONTH); // Jan = 0, not 1
    }
    protected int getDayOfMonth(){
        return calendar.get(Calendar.DAY_OF_MONTH); // Jan = 0, not 1 
    }
    protected int getDayOfWeek(){
        return calendar.get(Calendar.DAY_OF_WEEK);
    }
    protected int getWeekOfYear(){
        return calendar.get(Calendar.WEEK_OF_YEAR);
    }
    protected int getWeekOfMonth(){
        return calendar.get(Calendar.WEEK_OF_MONTH);
    }

}
