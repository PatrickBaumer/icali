/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package icali.javaee.ejb;

import icali.javaee.jpa.Benutzer;
import icali.javaee.jpa.Kalender;
import icali.javaee.jpa.Kategorie;
import icali.javaee.jpa.Termin;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.HashMap;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author x7
 */
@Stateless
@RolesAllowed("icali-app-user")
public class KalenderBean extends EntityBean<Kalender, Long> {

    private LocalDate date = LocalDate.now();
    private LocalTime time = LocalTime.now();

    public KalenderBean() {
        super(Kalender.class);
    }
    // Der Befehl ist falsch!
    public List<Kalender> findByUser(Benutzer benutzer) {
        
        return em.createQuery("SELECT k FROM Kalender k WHERE k.benutzerList = :benutzer OR k.kalenderAdmin = :benutzer ORDER BY k.kalenderTitel")
                .setParameter("benutzer", benutzer)
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

    public void setCurrentLocalDate() {
        date = LocalDate.now();
    }

    public LocalTime getCurrentLocalTime() {
        return LocalTime.now();
    }

    public LocalDate getLocalDate() {
        return date;
    }

    public LocalTime getLocalTime() {
        return time;
    }

    public int getWeekOfMonth() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfMonth());
    }

    public int getWeekOfYear() {
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return date.get(weekFields.weekOfYear());
    }

    public int nextWeek() {
        LocalDate hilf = date.plusWeeks(1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return hilf.get(weekFields.weekOfYear());
    }

    public int nextMonth() {
        LocalDate hilf = date.plusMonths(1);
        return hilf.getMonthValue();
    }

    public void nextMonthVoid() {
        date = date.plusMonths(1);
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

    public int nextYear() {
        LocalDate hilf = date.plusYears(1);
        return hilf.getYear();
    }

    public void nextYearVoid() {
        date = date.plusYears(1);
    }
    
    public void nextWeekVoid() {
        date = date.plusWeeks(1);
    }
    
    public void lastWeekVoid() {
        date = date.minusWeeks(1);
    }

    public int lastWeek() {
        LocalDate hilf = date.minusWeeks(1);
        WeekFields weekFields = WeekFields.of(Locale.getDefault());
        return hilf.get(weekFields.weekOfYear());
    }

    public int lastMonth() {
        LocalDate hilf = date.minusMonths(1);
        return hilf.getMonthValue();
    }

    public void lastMonthVoid() {
        date = date.minusMonths(1);
    }

    public int lastYear() {
        LocalDate hilf = date.minusYears(1);
        return hilf.getYear();
    }

    public void lastYearVoid() {
        date = date.minusYears(1);
    }

    // Mapping für die Terminausgabe
    /**
     * @param kalenderId
     * @param localDate
     * @return weekMap -> eine Map mit den LocalDate Tag und eine Liste mit Terminen zu
     * diesem Tag als Value mapping Tag & dazugehörigen Termine
     *
     */

    public Map<LocalDate, List<Termin>> getWeekMapByKalenderId(Long kalenderId, LocalDate localDate) {
        Map<LocalDate, List<Termin>> weekMap = new TreeMap<>();
        for (int i = 0; i < 7; i++) {
            List<Termin> terminByDayList = new ArrayList<>();
            
            for (Termin termin : findById(kalenderId).getTerminList()) {
                LocalDate startDatum = termin.getStartDatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                if (getMonday(localDate).plusDays(i).isEqual(startDatum)) {
                    terminByDayList.add(termin);
                }
            }
            weekMap.put(getMonday(localDate).plusDays(i), terminByDayList);
        }
        return weekMap;
    }



    // Ausgaben für Kalendardarstellung
    /**
     *
     * @param localDate
     * @return weekMap -> eine Map mit den Tag des Monats (als LocalDate) als Key und dem
     * Wochentag als Value mapping Tag & Wochentag
     */
    public Map<LocalDate, String> weekRepresentation(LocalDate localDate) {
        Map<LocalDate, String> weekMap = new TreeMap<>();

        for (int i = 0; i < 7; i++) {
            weekMap.put(getMonday(localDate).plusDays(i),
                    getMonday(localDate).plusDays(i).getDayOfWeek().name());
        }
        return weekMap;
    }
    
    public LocalDate[] shownWeek (LocalDate localDate) {
        Map<LocalDate, String> weekMap = weekRepresentation(localDate);
        LocalDate[] shownWeek = new LocalDate[7];
        int hilf = 0;
        for (LocalDate iterationDate : weekMap.keySet()) {
            shownWeek[hilf] = iterationDate;
            hilf++;
        }
        return shownWeek;
    }

    public LocalDate getMonday(LocalDate localDate) {
        DayOfWeek dayOfWeek = localDate.getDayOfWeek();

        switch (dayOfWeek) {
            case TUESDAY:
                return localDate.minusDays(1);

            case WEDNESDAY:
                return localDate.minusDays(2);

            case THURSDAY:
                return localDate.minusDays(3);

            case FRIDAY:
                return localDate.minusDays(4);

            case SATURDAY:
                return localDate.minusDays(5);

            case SUNDAY:
                return localDate.minusDays(6);

            default:
                return localDate;
        }
    }

    /**
     *
     * @param localDate
     * @return monthMap -> eine Map mit dem Tag des Monats als Key und dem
     * Wochentag als Value mapping Tag & Wochentag
     */
    public Map<Integer, String> monthRepresentation(LocalDate localDate) {
        Map<Integer, String> monthMap = new TreeMap<>();
        for (int i = 1; i <= localDate.lengthOfMonth(); i++) {
            LocalDate dayOfMonth = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), i);
            monthMap.put(i, dayOfMonth.getDayOfWeek().name());
        }
        return monthMap;
    }

    public LocalDate[][] weeksInMonth(LocalDate localDate) {
        Map<LocalDate, String> shownMonthsMap = shownMonthRepresentation(localDate.withDayOfMonth(1));

        LocalDate[][] shownWeeks = new LocalDate[6][7];

        int weekCounter = 0;
        int dayInWeekCounter = 0;

        for (LocalDate iterationDate : shownMonthsMap.keySet()) {
            if (dayInWeekCounter == 7) {
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

        boolean loopBreaker = false;
        for (int i = 0; loopBreaker == false; i++) {
            LocalDate d = getMonday(localDate).plusDays(i);
            shownMonthsMap.put(d, d.getDayOfWeek().name());
            if (i > 38 && d.getDayOfWeek().name().equals("SUNDAY")) {
                loopBreaker = true;
            }
        }
        return shownMonthsMap;
    }
    
        /**
     * @param kalenderId
     * @param localDate
     * @return weekMap -> eine Map mit den LocalDate Tag und eine Liste mit Terminen zu
     * diesem Tag als Value mapping Tag & dazugehörigen Termine
     *
     */
    public Map<LocalDate, List<Termin>> getMonthMapByKalenderId(Long kalenderId, LocalDate localDate) {
        Map<LocalDate, List<Termin>> monthMap = new TreeMap<>();
        for (int i = 1; i <=localDate.lengthOfMonth(); i++) {
            List<Termin> terminByDayList = new ArrayList<>();
            LocalDate dayOfMonth = LocalDate.of(localDate.getYear(), localDate.getMonthValue(), i);
                
            for (Termin termin : findById(kalenderId).getTerminList()) {
                LocalDate startDatum = termin.getStartDatum().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                
                if (dayOfMonth.isEqual(startDatum)) {
                    terminByDayList.add(termin);
                }
            }
            monthMap.put(dayOfMonth, terminByDayList);
        }
        return monthMap;
    }
    
//        public Map<Integer, List<LocalDate>> weeksInMonth(LocalDate localDate) {
//        Map<LocalDate, String> shownMonthsMap = shownMonthRepresentation(localDate.withDayOfMonth(1));
//
//        Map<Integer, List<LocalDate>> shownWeeks = new TreeMap<>();
//
//        int weekCounter = 1;
//        int dayInWeekCounter = 0;
//        List<LocalDate> datesPerWeek = new ArrayList<>();
//
//        for (LocalDate iterationDate : shownMonthsMap.keySet()) {
//            if (dayInWeekCounter == 7) {
//                dayInWeekCounter = 0;
//                shownWeeks.put(weekCounter, datesPerWeek);
//                weekCounter++;
//            }
//            datesPerWeek.add(iterationDate);
//            dayInWeekCounter++;
//        }
//        return shownWeeks;
//    }
        
//        public Map<Integer, Map<LocalDate, List<Termin>>> monthWithDates (Long kalenderId,LocalDate localDate) {
//            Map<Integer, List<LocalDate>> shownWeeks = weeksInMonth(localDate);
//            Map<LocalDate, List<Termin>> allDatesOfMonth = getMonthMapByKalenderId(kalenderId, localDate);
//            
//            Map<Integer, Map<LocalDate, List<Termin>>> monthWithDates = new TreeMap<>();
//            
//            for()
//            
//            
//            
//        }
}
