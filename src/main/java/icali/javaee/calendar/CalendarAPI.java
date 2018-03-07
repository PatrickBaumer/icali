/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.calendar;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Locale;

/**
 *
 * @author x7
 */
public class CalendarAPI {
    
    LocalDate date;
    LocalTime time;
    
    public CalendarAPI(){
        
        date =  LocalDate.now();
        time = LocalTime.now();
    
    }
    
    public LocalDate getCurrentLocalDate(){
        return LocalDate.now();
    }
    
    public LocalTime getCurrentLocalTime(){
        return LocalTime.now();
    }
    
    public LocalDate getLocalDate(){
        return date;
    }
    
    public LocalTime getLocalTime(){
        return time;
    }
    
    // LocalDate manipulation
    public int nextWeek(){
        date = date.plusWeeks(1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        return date.get(weekFields.weekOfYear());
    }
    
    public int nextMonth(){
        date = date.plusMonths(1);
        return date.getMonthValue();
    }
    
    public int nextYear(){
        date = date.plusYears(1);
        return date.getYear();
    }
    
    public int lastWeek(){
        date = date.minusWeeks(1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        return date.get(weekFields.weekOfYear());
    }
    
    public int lastMonth(){
        date = date.minusMonths(1);
        return date.getMonthValue();
    }
    
    public int lastYear(){
        date = date.minusYears(1);
        return date.getYear();
    }
    
}
