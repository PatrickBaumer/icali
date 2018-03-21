/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Kategorie;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.Iterator;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class KalenderBean extends EntityBean<Kalender, Long>{
    
    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();
    
    public KalenderBean() {
        super(Kalender.class);
    }
    
   public List<Kalender> findByUsername(String username) {
       return em.createQuery("SELECT k FROM Kalender k WHERE k.benutzerList.username = :username ORDER BY k.kalenderTitel")
               .setParameter("username", username)
               .getResultList();
   }
   
   public List<Kalender> findByKalenderId(Long KalenderId) {
       return em.createQuery("SELECT k FROM Kalender k WHERE k.kalenderId = :kalenderId ORDER BY k.kalenderTitel")
               .setParameter("KalenderId", KalenderId)
               .getResultList();
   }
   
   public List<Kalender> findByKalenderTitel(String kalenderTitel) {
       return em.createQuery("SELECT k FROM Kalender k WHERE k.kalenderTitel = :kalender ORDER BY k.kalenderTitel")
               .setParameter("kalenderTitel", kalenderTitel)
               .getResultList();
   }   
   
   public LocalDate getCurrentLocalDate() {
       return LocalDate.now();
   }
   
   public void setCurrentLocalDate(){
       date = LocalDate.now();
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
    
    public int nextWeek(){
        LocalDate hilf = date.plusWeeks(1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        return hilf.get(weekFields.weekOfYear());
    }
    
    public int nextMonth(){
        LocalDate hilf = date.plusMonths(1);
        return hilf.getMonthValue();
    }
    
    public void nextMonthVoid(){
        date=date.plusMonths(1);
    }
    
    public String getMonthName() {
        String monthName = "";
        switch (date.getMonthValue()) {
            case 1:
                monthName = "Januar";
                break;
            case 2:
                monthName = "Februar";
                break;
            case 3:
                monthName = "März";
                break;
            case 4:
                monthName = "April";
                break;
            case 5:
                monthName = "Mai";
                break;
            case 6:
                monthName = "Juni";
                break;
            case 7:
                monthName = "Juli";
                break;
            case 8:
                monthName = "August";
                break;
            case 9:
                monthName = "September";
                break;
            case 10:
                monthName = "Oktober";
                break;
            case 11:
                monthName = "November";
                break;
            case 12:
                monthName = "Dezember";
                break;
            default:;
                break;               
        }
        return monthName;
    }
    
    public int getYear() {
        return date.getYear();
    }
    
    public int nextYear(){
        LocalDate hilf = date.plusYears(1);
        return hilf.getYear();
    }
    
    public void nextYearVoid() {
        date = date.plusYears(1);
    }
    
    public int lastWeek(){
        LocalDate hilf = date.minusWeeks(1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault()); 
        return hilf.get(weekFields.weekOfYear());
    }
    
    public int lastMonth(){
        LocalDate hilf = date.minusMonths(1);
        return hilf.getMonthValue();
    }
    
    public void lastMonthVoid(){
        date = date.minusMonths(1);
    }
    
    public int lastYear(){
        LocalDate hilf = date.minusYears(1);
        return hilf.getYear();
    }
    
    public void lastYearVoid() {
        date = date.minusYears(1);
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
        for(int i =1; i<=localDate.lengthOfMonth(); i++){
            LocalDate dayOfMonth = LocalDate.of(localDate.getYear(), month, i);
            monthMap.put(i, dayOfMonth.getDayOfWeek().name());
        }
        return monthMap; 
    }
    
    public LocalDate[][] weeksInMonth(LocalDate localDate){
        Map<LocalDate, String> shownMonthsMap = shownMonthRepresentation(localDate.withDayOfMonth(1));
        
        LocalDate[][] shownWeeks = new LocalDate[6][7];
        
        int weekCounter = 0;
        int dayInWeekCounter = 0;
        
        
        for(LocalDate iterationDate : shownMonthsMap.keySet()) {
            if (dayInWeekCounter==7) {
                weekCounter++;
                dayInWeekCounter = 0;
            }
            shownWeeks[weekCounter][dayInWeekCounter] = iterationDate;
            dayInWeekCounter++;
        }
        return shownWeeks;
    }
    
    public Map<LocalDate, String> shownMonthRepresentation(LocalDate localDate) {
        Map<LocalDate, String> shownMonthsMap = new TreeMap<>();
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
        
        boolean loopBreaker= false;
        
        for(int i=0; loopBreaker==false; i++) {
            LocalDate d = monday.plusDays(i);
            shownMonthsMap.put(d, d.getDayOfWeek().name());
            if (i>38 && d.getDayOfWeek().name().equals("SUNDAY")) {
                loopBreaker = true;
            }
        }
        return shownMonthsMap;
    }  
}
