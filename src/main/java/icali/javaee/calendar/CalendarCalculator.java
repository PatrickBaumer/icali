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
/**
 * Jannuar ist 0 -> Dezemeber 11 bei Month
 * Sonntag ist 1 -> Samstag ist 7 bei Day of Week
 */
public class CalendarCalculator {
    
    Date date;
    Time time;
    Calendar calendar;
    
    public CalendarCalculator(){
        
        calendar = new GregorianCalendar();
        date = new Date(calendar);
        time = new Time(calendar);
    
    }
    
    public int nextWeek(){
        calendar.add(Calendar.WEEK_OF_YEAR, +1);
        return date.getWeekOfYear();
    }
    
    public int nextMonth(){
        calendar.add(Calendar.MONTH, +1);
        return date.getMonth();
    }
    
    public int nextYear(){
        calendar.add(Calendar.YEAR, +1);
        return date.getMonth();
    }
    
    public int lastWeek(){
        calendar.add(Calendar.WEEK_OF_YEAR, -1);
        return date.getWeekOfYear();
    }
    
    public int lastMonth(){
        calendar.add(Calendar.MONTH, -1);
        return date.getMonth();
    }
    
    public int lastYear(){
        calendar.add(Calendar.YEAR, -1);
        return date.getMonth();
    }
}
