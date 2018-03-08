/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.calendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.time.temporal.WeekFields;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author x7
 */
public class CalendarAPI {
    
    private LocalDate date;
    private LocalTime time;
    
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
    
    public int getWeekOfMonth(){
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        return date.get(weekFields.weekOfMonth());
    }
    
    public int getWeekOfYear(){
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        return date.get(weekFields.weekOfYear());
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
    
    // Ausgaben für Kalendardarstellung
    /**
     * 
     * @param localDate
     * @return weekMap -> eine Map mit den Tag des Monats al Key und dem Wochentag als Value
     * mapping Tag & Wochentag
     */
    public Map<Integer, String> weekRepresentation(LocalDate localDate){
        Map<Integer, String> weekMap = new TreeMap<>();
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();
        LocalDate monday;
        switch (dayOfWeek) {  
            case TUESDAY:monday = localDate.minusDays(1);
              break;
            case WEDNESDAY:monday = localDate.minusDays(2);
              break;
            case THURSDAY:monday = localDate.minusDays(3);
              break;
            case FRIDAY:monday = localDate.minusDays(4);
              break;
            case SATURDAY:monday = localDate.minusDays(5);
              break;
            case SUNDAY:monday = localDate.minusDays(6);
              break;
            default:monday=localDate;
              break;
        }
        for(int i=0; i<7;i++){
            LocalDate d = monday.plusDays(i);
            weekMap.put(d.getDayOfMonth(), d.getDayOfWeek().name());
        }
        return weekMap; 
    }
    /**
     * 
     * @param localDate
     * @return monthMap -> eine Map mit dem Tag des Monats als Key und dem Wochentag als Value
     * mapping Tag & Wochentag
     */
    public Map<Integer, String> monthRepresentation(LocalDate localDate){
        Map<Integer, String> monthMap = new TreeMap<>();
        int month =localDate.getMonthValue();
        int year = localDate.getYear();
        for(int i =1; i<=localDate.lengthOfMonth(); i++){
            LocalDate dayOfMonth = LocalDate.of(year, month, i);
            monthMap.put(i, dayOfMonth.getDayOfWeek().name());
        }
        return monthMap; 
    }

}