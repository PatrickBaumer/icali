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
public class CalendarAPI {
    
    Date date;
    Time time;
    Calendar calendar;
    
    public CalendarAPI(){
        
        calendar = new GregorianCalendar();
        date = new Date(calendar);
        time = new Time(calendar);
    
    }
    
// Date getter
    public int getYear(){
        return date.getYear();
    }
    public int getMonth(){
        return date.getMonth();
    }
    public int getDayOfMonth(){
        return date.getDayOfMonth();
    }
    public int getDayOfWeek(){
        return date.getDayOfWeek();
    }
    public int getWeekOfYear(){
        return date.getWeekOfYear();
    }
    public int getWeekOfMonth(){
        return date.getWeekOfMonth();
    }
    
    // Date manipulation
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
    
    //Time getter
    
    public int getHour(){
        return time.getHour();      
    }
    public int getHourOfDay(){
        return time.getHourOfDay();
    }
    public int getMinute(){
        return time.getMinute();
    }
    public int getSecond(){
        return time.getSecond();
    }
    public int getMilliescond(){
        return time.getMilliescond();
    }
}
